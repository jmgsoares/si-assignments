package pt.onept.mei.is1920.mybay.business.utility;

import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;

import javax.persistence.EntityManager;

public final class QueryBuilderUtility {
	private QueryBuilderUtility() { }

	public static javax.persistence.Query QueryBuilder(EntityManager em, SearchParameters searchParameters) {

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(PersistenceItem.class).get();

		org.apache.lucene.search.Query luceneQuery =  queryBuilder
																.keyword()
																.onField("name")
																//TODO make this generic
																.matching("caldas")
																.createQuery();

		return fullTextEntityManager.createFullTextQuery(luceneQuery, PersistenceItem.class);

	}
}
