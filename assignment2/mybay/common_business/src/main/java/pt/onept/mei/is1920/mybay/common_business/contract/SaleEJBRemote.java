package pt.onept.mei.is1920.mybay.common_business.contract;

import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.type.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SaleEJBRemote {
	boolean createSale(Item item);
	boolean deleteSale(Item item);
	boolean updateSale(Item item);
	List<Item> listAccountSales(User user);
	List<Item> searchSales(SearchParameters searchParameters);
	Item listSale(Item item);
}
