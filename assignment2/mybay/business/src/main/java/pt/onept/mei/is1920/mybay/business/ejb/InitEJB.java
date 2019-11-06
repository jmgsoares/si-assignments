package pt.onept.mei.is1920.mybay.business.ejb;

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

@Singleton
@Startup
@Getter
@Setter
public class InitEJB {

	private static final Logger logger = LoggerFactory.getLogger(InitEJB.class);

	@PostConstruct
	public void initialize() {
		EntityManager em = Persistence.createEntityManagerFactory("myBayPersistenceUnit")
				.createEntityManager();
		FullTextEntityManager fullTextEntityManager = Search.getFullTextEntityManager(em);
		try {
			fullTextEntityManager.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			logger.error(e.getMessage(), e);
		}
	}
}
