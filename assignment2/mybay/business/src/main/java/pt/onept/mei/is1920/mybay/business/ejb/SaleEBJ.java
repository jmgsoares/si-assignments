package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common_business.contract.SaleEJBRemote;

import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleEBJ implements SaleEJBRemote {

	@Override
	public boolean createSale(Item item) {
		return false;
	}

	@Override
	public boolean deleteSale(Item item) {
		return false;
	}

	@Override
	public boolean updateSale(Item item) {
		return false;
	}

	@Override
	public List<Item> listAccountSales(User user) {
		return null;
	}

	@Override
	public List<Item> searchSales(SearchParameters searchParameters) {
		return null;
	}
}
