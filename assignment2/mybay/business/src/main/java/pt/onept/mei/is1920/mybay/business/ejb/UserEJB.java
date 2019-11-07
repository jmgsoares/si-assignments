package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.business.utility.MapUserUtility;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

import javax.ejb.*;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;

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
	public User read(User userToRead) {
		logger.info("Read request to user  " + userToRead.getEmail());
		try {
			PersistenceUser persistedUserToRead = em.find(PersistenceUser.class, userToRead.getEmail());
			return MapUserUtility.MapPersistenceUserToUser(persistedUserToRead);
		} catch (IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	@Override
	@TransactionAttribute(TransactionAttributeType.REQUIRES_NEW)
	public boolean login(String email, String password) {
		logger.info("Login request from " + email);
		PersistenceUser userToLogIn = em.find(PersistenceUser.class, email);
		if (userToLogIn == null) {
			logger.debug("Attempt login from " + email + " unsuccessful. User not found");
			return false;
		}
		if (userToLogIn.getPassword().equals(password)) {
			logger.debug("Attempt login from " + email + " successful");
			return true;
		} else {
			logger.debug("Attempt login from " + email + " unsuccessful. Incorrect password");
			return false;
		}
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
}
