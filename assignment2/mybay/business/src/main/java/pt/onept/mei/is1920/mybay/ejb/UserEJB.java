package pt.onept.mei.is1920.mybay.ejb;

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
        logger.debug("Trying to persist -> " + user.toString());
        em.persist(new PersistenceUser(user));
        return user;
    }

    @Override
    public boolean login(String email, String password) {
        logger.debug("Trying to query -> " + email + " " + password);
        Query q = em.createQuery("from PersistenceUser u where u.email = :e and u.password = :p");
        q.setParameter("e", email);
        q.setParameter("p", password);
        List<User> result = q.getResultList();
        logger.debug("Response -> " + result.toString());
        return !result.isEmpty();
    }

}
