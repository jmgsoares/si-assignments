package pt.onept.mei.is1920.mybay.business.utility;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.enums.SearchType;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public final class QueryBuilderUtility {
	private QueryBuilderUtility() { }

	private static final Logger logger = LoggerFactory.getLogger(QueryBuilderUtility.class);

	private static String something(SearchParameters searchParameters) {



		return null;
	}

	public static Query BuildQuery(EntityManager em, Class tClass, SearchParameters searchParameters) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(tClass).get();

		org.apache.lucene.search.Query luceneQuery;

		if(tClass == PersistenceItem.class) {

			switch (searchParameters.getSearchType()) {
				case ALL:
					break;
				case USER:
					break;
			}







			luceneQuery =  queryBuilder
					.keyword()
					.onField("name")
					//TODO make this generic
					.matching("caldas")
					.createQuery();
		}
		else if (tClass == PersistenceUser.class) {
			//TODO If we want to also do custom searches on the user
			luceneQuery = null;
		}
		else {
			logger.error("Unknown class type " + tClass.toString() + " to build query");
			luceneQuery = null;
		}

		return fullTextEntityManager.createFullTextQuery(luceneQuery, tClass);

	}
}
