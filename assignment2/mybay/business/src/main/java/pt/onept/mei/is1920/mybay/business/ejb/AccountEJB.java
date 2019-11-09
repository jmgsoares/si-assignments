package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common_business.contract.AccountEJBRemote;
import pt.onept.mei.is1920.mybay.common_data.contract.UserEJBRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class AccountEJB implements AccountEJBRemote {

    private static final Logger logger = LoggerFactory.getLogger(AccountEJB.class);

    @EJB
    private UserEJBRemote userEJBRemote;

    @Override
    public boolean signUp(User user) {
        logger.info("Processing signUp request");
        if (userEJBRemote.create(user)) {
            logger.debug("User created successfully");
            return true;
        }
        logger.debug("User couldn't be created");
        return false;
    }

    @Override
    public User login(User user) {
        logger.info("Processing login request");
        logger.debug("Searching user: " + user.toString());
        User persistedUser = userEJBRemote.read(user);
        if (persistedUser != null) {
			logger.debug("Found matching persisted user: " + persistedUser.toString());
			if (persistedUser.getPassword().equals(user.getPassword())) {
                logger.debug("Passwords match. Returning user");
                return persistedUser;
            }
        }
        logger.debug("User not found or passwords DONT match. Returning null");
        return null;
    }

    @Override
    public boolean removeAccount(User user) {
        logger.info("Processing remove request");
        if (userEJBRemote.delete(user)) {
            logger.debug("User deleted successfully");
            return true;
        }
        logger.debug("User couldn't be deleted");
        return false;
    }

    @Override
    public boolean updateAccount(User user) {
        logger.info("Processing update request");
        if (userEJBRemote.update(user)) {
            logger.debug("User update successfully");
            return true;
        }
        logger.debug("User couldn't be updated");
        return false;
    }
}
