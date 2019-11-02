package pt.onept.mei.is1920.mybay.ejb;

import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;

import javax.ejb.Stateless;
import javax.persistence.*;

@Stateless
public class UserEJB implements UserEJBRemote {

    @PersistenceContext(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    @Override
    public void register() throws DuplicatedException, IncompleteException {
    }

    public EntityManager getEm() {
        return em;
    }

    public void setEm(EntityManager em) {
        this.em = em;
    }

}
