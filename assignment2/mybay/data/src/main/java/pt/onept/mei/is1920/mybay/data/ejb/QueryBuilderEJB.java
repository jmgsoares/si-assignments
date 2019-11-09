package pt.onept.mei.is1920.mybay.data.ejb;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.common.converter.ItemCategoryConverter;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.type.PersistenceItem;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class QueryBuilderEJB {

	private static final Logger logger = LoggerFactory.getLogger(QueryBuilderEJB.class);

	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	public Query buildQuery(Class tClass, SearchParameters searchParameters) {

		logger.debug("Building query for: " + tClass.toString() + " " + searchParameters.toString());

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(tClass).get();

		org.apache.lucene.search.Query luceneQuery = null;

		if(searchParameters.getSearchQuery() == null) searchParameters.setSearchQuery("");

		if (searchParameters.getSearchFilter() == null) {
			logger.debug("Name query");
			luceneQuery =  queryBuilder
							.keyword()
							.wildcard()
							.onField("name")
							.matching("*" + searchParameters.getSearchQuery() + "*")
							.createQuery();
		}
		else {
			if(tClass == PersistenceItem.class) {

				switch (searchParameters.getSearchFilter()) {
					case PRICE:
						logger.debug("Price range query");
						luceneQuery =  queryBuilder
								.bool()
								.must(queryBuilder
										.keyword()
										.wildcard()
										.onField("name")
										.matching("*" + searchParameters.getSearchQuery() + "*")
										.createQuery()
								).must(queryBuilder
										.range()
										.onField("price")
										.from(searchParameters.getPriceRange().from)
										.to(searchParameters.getPriceRange().to)
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
										.wildcard()
										.onField("name")
										.matching("*" + searchParameters.getSearchQuery() + "*")
										.createQuery()
								).must(queryBuilder
										.keyword()
										.onField("category")
										.matching(searchParameters.getCategory())
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
										.wildcard()
										.onField("name")
										.matching("*" + searchParameters.getSearchQuery() + "*")
										.createQuery()
								).must(queryBuilder
										.keyword()
										.onField("country")
										.matching(searchParameters.getCountry())
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
										.wildcard()
										.onField("name")
										.matching("*" + searchParameters.getSearchQuery() + "*")
										.createQuery()
								).must(queryBuilder
										.range()
										.onField("date")
										.from(searchParameters.getDateRange().from)
										.to(searchParameters.getDateRange().to)
										.createQuery()
								).createQuery();
						logger.debug("Query result: " + luceneQuery.toString());
						break;
				}
			}
			else {
				logger.error("Unknown class type " + tClass.toString() + " to build query");
			}

		}
		return fullTextEntityManager.createFullTextQuery(luceneQuery, tClass);
	}
}
