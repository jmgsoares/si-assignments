package pt.onept.mei.is1920.mybay.ejb;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.Calendar;
import java.util.Date;

public class UserEJB {
    private EntityManagerFactory emf = Persistence.createEntityManagerFactory("myBayPersistenceUnit");

    public UserEJB() {

    }

    public EntityManager getEntityManager() { return emf.createEntityManager(); }

}
