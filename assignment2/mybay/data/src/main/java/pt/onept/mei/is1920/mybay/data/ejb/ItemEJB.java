package pt.onept.mei.is1920.mybay.data.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common_data.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.data.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.utility.MapItemUtility;

import javax.ejb.*;
import javax.inject.Inject;
import javax.persistence.*;
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

	@Inject
	private QueryBuilderEJB queryBuilder;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean create(Item itemToCreate) {
		logger.info("Creating item");
		try {
			em.persist(MapItemUtility.MapItemToPersistenceItem(itemToCreate));
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
		logger.info("Reading item");
		try {
			PersistenceItem persistenceItemToRead = em.find(PersistenceItem.class, itemToRead.getId());
			if(persistenceItemToRead != null) {
				logger.debug("Found item " + persistenceItemToRead.getName());
				return MapItemUtility.MapPersistenceItemToItem(persistenceItemToRead);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("Returning null item");
		return null;                          
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean update(Item itemToUpdate) {
		try {
			logger.info("Updating item");
			PersistenceItem persistenceItemToUpdate = em.find(PersistenceItem.class, itemToUpdate.getId());
			if(persistenceItemToUpdate == null) {
				logger.debug("Item id " + itemToUpdate.getId() + " not found" );
				return false;
			}

			logger.debug("Item to update: " +
					MapItemUtility.MapPersistenceItemToItem(persistenceItemToUpdate).toString());
			logger.debug("New information: " + itemToUpdate.toString());

			if(itemToUpdate.getName() != null) {
				persistenceItemToUpdate.setName(itemToUpdate.getName());
			}

			if(itemToUpdate.getPrice() > 0.0f) {
				persistenceItemToUpdate.setPrice(itemToUpdate.getPrice());
			}

			if(itemToUpdate.getCategory() != null) {
				persistenceItemToUpdate.setCategory(itemToUpdate.getCategory());
			}

			if(itemToUpdate.getPhotoUrl() != null) {
				persistenceItemToUpdate
						.setImageUrl(itemToUpdate.getPhotoUrl())
						.setImageDeleteHash(itemToUpdate.getPhotoDeleteHash());
			}

			em.merge(persistenceItemToUpdate);
			logger.debug("Updated item with name -> " + persistenceItemToUpdate.getName());
			return true;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean delete(Item itemToDelete) {
	    logger.info("Deleting item");
	    try {
	        PersistenceItem persistenceItem = em.find(PersistenceItem.class, itemToDelete.getId());
	        if(persistenceItem == null) {
	            logger.debug("Attempt to delete item failed. Item id not found: " + itemToDelete.getId());
	            return false;
            }
	        em.remove(persistenceItem);
	        logger.debug("Deleted item id " + itemToDelete.getId());
	        return true;
        } catch (IllegalArgumentException e) {
	        logger.error(e.getMessage(), e);
        }
		return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public List<Item> search(SearchParameters searchParameters) {
		logger.info("Searching for items");
		logger.debug(searchParameters.toString());

		Query jpaQuery = queryBuilder.buildQuery(PersistenceItem.class, searchParameters);

		logger.debug(jpaQuery.toString());

		List result = jpaQuery.getResultList();

		logger.debug("Search results count: " + result.size());

		List<Item> itemList = new ArrayList<>();

		for (Object o : result) {
			itemList.add(MapItemUtility.MapPersistenceItemToItem((PersistenceItem) o));
		}
		return itemList;
	}
}
