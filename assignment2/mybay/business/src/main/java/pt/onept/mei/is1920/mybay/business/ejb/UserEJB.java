package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.data.persistence.type.PersistenceUser;

import javax.ejb.Stateless;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TransactionRequiredException;
import java.lang.reflect.Field;

@Stateless
@Getter
@Setter
public class UserEJB implements UserEJBRemote {

	private static final Logger logger = LoggerFactory.getLogger(UserEJB.class);
	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@Override
	public boolean create(User user) {
		logger.info("Registering new user");
		try {
			em.persist(new PersistenceUser(user));
		} catch (EntityExistsException | TransactionRequiredException | IllegalArgumentException e) {
			logger.error(e.getMessage(), e);
			return false;
		}
		logger.debug("Registered user -> " + user.toString());
		return true;
	}

	@Override
	public boolean update(User userToUpdate) {
	    try {
            PersistenceUser persistedUserToUpdate = em.find(PersistenceUser.class, userToUpdate.getEmail());
            if (persistedUserToUpdate == null) {
                logger.debug("Attempt to update user " + userToUpdate.getEmail() + " unsuccessful. User not found");
                return false;
            }

			if(userToUpdate.getName() != null) {
				persistedUserToUpdate.setName(userToUpdate.getName());
			} else if(userToUpdate.getPassword() != null) {
				persistedUserToUpdate.setPassword(userToUpdate.getPassword());
            } else if(userToUpdate.getCountry() != null) {
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
	public User read(User userToRead) {
		return null;
	}

	@Override
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
