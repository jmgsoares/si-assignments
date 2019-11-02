package pt.onept.mei.is1920.mybay.ejb;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.basicType.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;
import pt.onept.mei.is1920.mybay.data.type.PersistenceUser;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Getter
@Setter
public class UserEJB implements UserEJBRemote {

    @PersistenceContext(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    @Override
    public User register(User user) throws DuplicatedException, IncompleteException {
        em.persist(new PersistenceUser(user));
        return user;
    }

}
