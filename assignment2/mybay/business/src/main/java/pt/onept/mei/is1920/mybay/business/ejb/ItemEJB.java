package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.basicType.Item;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Stateless
@Getter @Setter
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
}
