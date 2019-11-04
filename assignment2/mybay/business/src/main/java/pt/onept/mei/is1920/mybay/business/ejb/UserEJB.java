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
import java.util.List;

@Stateless
@Getter
@Setter
public class UserEJB implements UserEJBRemote {

    @PersistenceContext(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    private static final Logger logger = LoggerFactory.getLogger(UserEJB.class);

    @Override
    public User register(User user) throws DuplicatedException, IncompleteException {
        logger.info("Trying to persist -> " + user.toString());
        em.persist(new PersistenceUser(user));
        return user;
    }

    @Override
    public boolean login(String email, String password) {
        logger.info("Trying to query -> " + email + " " + password);
        Query q = em.createQuery("FROM PersistenceUser u WHERE u.email = :e AND u.password = :p");
        q.setParameter("e", email);
        q.setParameter("p", password);
        List<User> result = q.getResultList();
        logger.info("Response -> " + result.toString());
        return !result.isEmpty();
    }

    @Override
    public void delete(String email) {
        logger.info("Trying to delete account ->" + email);
        Query q = em.createQuery("DELETE FROM PersistenceUser u WHERE u.email = :e");
        q.setParameter("e", email);
        q.executeUpdate();
    }
}
