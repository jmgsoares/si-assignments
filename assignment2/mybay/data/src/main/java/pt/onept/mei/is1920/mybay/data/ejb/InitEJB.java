package pt.onept.mei.is1920.mybay.data.ejb;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.search.jpa.FullTextEntityManager;
import org.hibernate.search.jpa.Search;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;

@Singleton
@Startup
@Getter
@Setter
public class InitEJB {

	private static final Logger logger = LoggerFactory.getLogger(InitEJB.class);

	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@PostConstruct
	public void initialize() {
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
