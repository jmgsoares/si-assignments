package pt.onept.mei.is1920.mybay.business.ejb;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.SearchParameters;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common_business.contract.SaleEJBRemote;
import pt.onept.mei.is1920.mybay.common_data.contract.ItemEJBRemote;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import java.util.List;

@Stateless
@Getter
@Setter
@TransactionManagement(TransactionManagementType.CONTAINER)
public class SaleEJB implements SaleEJBRemote {

	private static final Logger logger = LoggerFactory.getLogger(SaleEJB.class);


	@EJB
	private ItemEJBRemote itemEJBRemote;

	@Override
	public boolean createSale(Item item) {
		logger.info("Processing create sale request");
		if(itemEJBRemote.create(item)) {
			logger.debug("Sale created successfully");
			return true;
		}
		logger.debug("Sale couldn't be created");
		return false;
	}

	@Override
	public boolean deleteSale(Item item) {
		logger.info("Processing delete sale");
		if(itemEJBRemote.delete(item)) {
			logger.debug("Sale deleted successfully");
			return true;
		}
		logger.debug("Sale couldn't be deleted");
		return false;
	}

	@Override
	public boolean updateSale(Item item) {
		logger.info("Processing update request");
		if(itemEJBRemote.update(item)) {
			logger.debug("Sale update successfully");
			return true;
		}
		logger.debug("Sale couldn't be updated");
		return false;	}

	@Override
	public List<Item> listAccountSales(User user) {
		return null;
	}

	@Override
	public List<Item> searchSales(SearchParameters searchParameters) {
		return null;
	}
}
