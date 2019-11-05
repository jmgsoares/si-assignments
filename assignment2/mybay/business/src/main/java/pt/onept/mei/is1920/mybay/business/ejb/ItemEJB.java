package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Stateless
@Getter
@Setter
public class ItemEJB implements ItemEJBRemote {

	@PersistenceContext(unitName = "myBayPersistenceUnit")
	private EntityManager em;

	@Override
	public boolean create(Item itemToCreate) {
		return false;
	}

	@Override
	public Item read(Item itemToRead) {
		return null;
	}

	@Override
	public boolean update(Item itemToUpdate) {
		return false;
	}

	@Override
	public boolean delete(Item itemToDelete) {
		return false;
	}

	@Override
	public List<Item> search(SearchParameters searchParameters) {
		return null;
	}
}
