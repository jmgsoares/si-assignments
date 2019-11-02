package pt.onept.mei.is1920.mybay.ejb;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
@Getter
@Setter
public class UserEJB implements UserEJBRemote {

    @PersistenceContext(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    @Override
    public void register() throws DuplicatedException, IncompleteException {

    }

}
