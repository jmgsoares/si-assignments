package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.basicType.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;

import javax.ejb.Stateless;
import javax.persistence.*;
import javax.validation.constraints.Null;
import java.util.List;

@Stateless
@Getter @Setter
public class UserEJB implements UserEJBRemote {

    @PersistenceContext(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(UserEJB.class);

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
        }
        else {
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
        } catch (IllegalArgumentException e) {
            logger.error(e.getMessage(), e);
        }
        em.remove(userToDelete);
        logger.debug("Deleted account with email " + userToDelete.getEmail());
        return true;
    }
}
