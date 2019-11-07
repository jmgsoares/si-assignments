package pt.onept.mei.is1920.mybay.data.utility;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public final class QueryBuilderUtility {
	private QueryBuilderUtility() { }

	private static final Logger logger = LoggerFactory.getLogger(QueryBuilderUtility.class);

	public static Query BuildQuery(EntityManager em, Class tClass, SearchParameters searchParameters) {
		logger.debug("Building query for: " + tClass.toString() + " parameters: " + searchParameters.toString());

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(tClass).get();

		QueryBuilder userQueryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(PersistenceUser.class).get();

		org.apache.lucene.search.Query luceneQuery = null;

		if(searchParameters.getSearchQuery() == null) searchParameters.setSearchQuery("");

		if(tClass == PersistenceItem.class) {

			//TODO add sorting by capability

			switch (searchParameters.getSearchFilter()) {
				case PRICE:
					logger.debug("Price range query");
					luceneQuery =  queryBuilder
							.bool()
							.must(queryBuilder
									.keyword()
									.onField("name")
									.matching("*" + searchParameters.getSearchQuery() + "*")
									.createQuery()
							).must(queryBuilder
									.range()
									.onField("price")
									.from(Float.parseFloat(searchParameters.getPriceRange().from))
									.to(Float.parseFloat(searchParameters.getPriceRange().to))
									.createQuery()
							).createQuery();
					logger.debug("Query result: " + luceneQuery.toString());
					break;

				case CATEGORY:
					logger.debug("Category query");
					luceneQuery =  queryBuilder
							.bool()
							.must(queryBuilder
									.keyword()
									.onField("name")
									.matching("*" + searchParameters.getSearchQuery() + "*")
									.createQuery()
							).must(queryBuilder
									.keyword()
									.onField("category")
									.matching(ItemCategoryConverter.CategoryToString(searchParameters.getCategory()))
									.createQuery()
							).createQuery();
					logger.debug("Query result: " + luceneQuery.toString());
					break;

				case COUNTRY:
					logger.debug("Country query");
					luceneQuery =  queryBuilder
							.bool()
							.must(queryBuilder
									.keyword()
									.onField("name")
									.matching("*" + searchParameters.getSearchQuery() + "*")
									.createQuery()
							).must(userQueryBuilder
									.keyword()
									.onField("country")
									.matching(CountryConverter.CountryToString(searchParameters.getCountry()))
									.createQuery()
							).createQuery();
					logger.debug("Query result: " + luceneQuery.toString());
					break;

				case DATE:
					logger.debug("Date range query");
					luceneQuery =  queryBuilder
							.bool()
							.must(queryBuilder
									.keyword()
									.onField("name")
									.matching("*" + searchParameters.getSearchQuery() + "*")
									.createQuery()
							).must(queryBuilder
									.range()
									.onField("date")
									.from(Float.parseFloat(searchParameters.getPriceRange().from))
									.to(Float.parseFloat(searchParameters.getPriceRange().to))
									.createQuery()
							).createQuery();
					logger.debug("Query result: " + luceneQuery.toString());
					break;

			}
		}
		else if (tClass == PersistenceUser.class) {
			//TODO If we want to also do custom searches on the user
		}
		else {
			logger.error("Unknown class type " + tClass.toString() + " to build query");
		}

		return fullTextEntityManager.createFullTextQuery(luceneQuery, tClass);

	}
}
