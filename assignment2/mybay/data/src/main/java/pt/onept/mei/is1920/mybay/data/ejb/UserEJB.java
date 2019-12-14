package pt.onept.mei.is1920.mybay.data.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common_data.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.data.type.PersistenceItem;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;
import pt.onept.mei.is1920.mybay.data.utility.MapItemUtility;
import pt.onept.mei.is1920.mybay.data.utility.MapUserUtility;

import javax.ejb.*;
import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class UserEJB implements UserEJBRemote {

	private static final Logger logger = LoggerFactory.getLogger(UserEJB.class);
	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean create(User user) {
		logger.info("Registering new user");
		try {
			em.persist(MapUserUtility.MapUserToPersistenceUser(user));
		} catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		logger.debug("Registered user -> " + user.toString());
		return true;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public User read(User userToRead) {
		logger.info("Read request to user " + userToRead.getEmail());
		try {
			PersistenceUser persistedUserToRead = em.find(PersistenceUser.class, userToRead.getEmail());
			if(persistedUserToRead != null) {
				logger.debug("Found user " + persistedUserToRead.getEmail());
				return MapUserUtility.MapPersistenceUserToUser(persistedUserToRead);
			}
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		logger.debug("Returning null user");
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean update(User userToUpdate) {
	    try {
	    	logger.debug("Entered update");
            PersistenceUser persistedUserToUpdate = em.find(PersistenceUser.class, userToUpdate.getEmail());
            if (persistedUserToUpdate == null) {
                logger.debug("Attempt to update user " + userToUpdate.getEmail() + " unsuccessful. User not found");
                return false;
            }

			if(userToUpdate.getName() != null) {
				persistedUserToUpdate.setName(userToUpdate.getName());
			}

			if(userToUpdate.getPassword() != null) {
				persistedUserToUpdate.setPassword(userToUpdate.getPassword());
            }

			if(userToUpdate.getCountry() != null) {
				persistedUserToUpdate.setCountry(userToUpdate.getCountry());
			}

			em.merge(persistedUserToUpdate);
            logger.debug("Updated account with email " + userToUpdate.getEmail());
            return true;
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
        return false;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean delete(User userToDelete) {
		logger.info("Delete account request from " + userToDelete.getEmail());
		try {
			PersistenceUser persistedUserToDelete = em.find(PersistenceUser.class, userToDelete.getEmail());
			if (persistedUserToDelete == null) {
				logger.debug("Attempt to delete user " + userToDelete.getEmail() + " unsuccessful. User not found");
				return false;
			}
			em.remove(persistedUserToDelete);
			logger.debug("Deleted account with email " + userToDelete.getEmail());
			return true;
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		return false;
	}

	@Override
	public List<Item> listSales(User user) {
		try {
			logger.debug("List user sales");
			PersistenceUser persistenceUser = em.find(PersistenceUser.class, user.getEmail());
			if (persistenceUser != null) {
				List<Item> itemList = new ArrayList<>();
				for (PersistenceItem pi: persistenceUser.getItems()) {
					itemList.add(MapItemUtility.MapPersistenceItemToItem(pi));
				}
				return itemList;
			}
			else {
				logger.debug("Attempt get user " + user.getEmail() + " unsuccessful. User not found");
			}
		}catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	public List<String> getUsersEmails() {
		logger.debug("Getting user email list");
		Query jpaQuery = em.createQuery("SELECT u.email FROM PersistenceUser u");

		logger.debug(jpaQuery.toString());

		List result = jpaQuery.getResultList();

		List<String> emailList = new ArrayList<>();

		for (Object o : result) {
			emailList.add((String) o);
		}
		return emailList;
	}
}
