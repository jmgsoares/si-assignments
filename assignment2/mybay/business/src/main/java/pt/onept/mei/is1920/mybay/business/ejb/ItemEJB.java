package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.business.utility.MapItem;
import pt.onept.mei.is1920.mybay.common.exception.NotFoundException;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Getter
@Setter
public class ItemEJB implements ItemEJBRemote {

	private static final Logger logger = LoggerFactory.getLogger(ItemEJB.class);
	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@Override
	public boolean create(Item itemToCreate) {
		//get the persistence entity and the fill the persistence user for hibernate to associate
		logger.info("Creating new item");
		try {
			em.persist(new PersistenceItem(itemToCreate));
		} catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		logger.debug("Added item -> " + itemToCreate.toString());
		return true;
	}

	@Override
	public Item read(Item itemToRead) {
		return null;
	}

	@Override
	public boolean update(Item itemToUpdate) {
		return false;
	}

	@Override
	public boolean delete(Item itemToDelete) {
		return false;
	}

	@Override
	public List<Item> search(SearchParameters searchParameters) {
		logger.info("Searching for items");
		try {
			CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
			CriteriaQuery<PersistenceItem> criteriaQuery = criteriaBuilder.createQuery(PersistenceItem.class);
			Root<PersistenceItem> from = criteriaQuery.from(PersistenceItem.class);
			CriteriaQuery<PersistenceItem> all = criteriaQuery.select(from);
			TypedQuery<PersistenceItem> typedQuery = em.createQuery(all);
			List<PersistenceItem> persistenceItems = typedQuery.getResultList();
			List<Item> items = new ArrayList<>();
			if(!persistenceItems.isEmpty()) {
				for (PersistenceItem persItem : persistenceItems) {
					logger.debug("Adding item to list -> " + persItem.getName());
					items.add(MapItem.MapPersistenceItemToItem(persItem));
				}
			}
			return items;
		} catch (IllegalArgumentException e){
			logger.error(e.getMessage(), e);
		}
		return null;
	}
}
