package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.business.utility.MapItemUtility;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;

import javax.ejb.*;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class ItemEJB implements ItemEJBRemote {

	private static final Logger logger = LoggerFactory.getLogger(ItemEJB.class);
	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean create(Item itemToCreate) {
		logger.info("Creating item");
		try {
			em.persist(new PersistenceItem(itemToCreate));
		} catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		logger.debug("Added item: " + itemToCreate.toString());
		return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public Item read(Item itemToRead) {
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean update(Item itemToUpdate) {
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean delete(Item itemToDelete) {
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Item> search(SearchParameters searchParameters) {
		logger.info("Searching for items");

		logger.debug("Search parameters: " + searchParameters.toString());

		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);

		QueryBuilder queryBuilder = fullTextEntityManager.getSearchFactory().buildQueryBuilder()
				.forEntity(PersistenceItem.class).get();

		org.apache.lucene.search.Query luceneQuery = queryBuilder
				.keyword()
				.onField("name")
				//TODO make this generic
				.matching("caldas")
				.createQuery();

		javax.persistence.Query jpaQuery = fullTextEntityManager.createFullTextQuery(luceneQuery, PersistenceItem.class);

		List result = jpaQuery.getResultList();

		logger.debug("Search results count: " + result.size());

		List<Item> itemList = new ArrayList<>();

		for (Object o : result) {
			itemList.add(MapItemUtility.MapPersistenceItemToItem((PersistenceItem) o));
		}

		return itemList;
	}
}
