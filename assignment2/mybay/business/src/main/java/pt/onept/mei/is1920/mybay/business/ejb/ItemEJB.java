package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
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
		return null;
	}
}
