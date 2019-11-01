package pt.onept.mei.is1920.mybay.ejb;

import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

@Stateless
public class UserEJB implements UserEJBRemote{
    @PersistenceUnit(unitName = "myBayPersistenceUnit")
    private EntityManager em;

    @Override
    public void register() throws DuplicatedException, IncompleteException {

    }
}
