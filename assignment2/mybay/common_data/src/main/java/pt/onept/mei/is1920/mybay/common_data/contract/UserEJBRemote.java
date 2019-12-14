package pt.onept.mei.is1920.mybay.common_data.contract;

import pt.onept.mei.is1920.mybay.common.type.Item;
import pt.onept.mei.is1920.mybay.common.type.User;

import javax.ejb.Local;
import java.util.List;

@Local
public interface UserEJBRemote extends Crudable<User> {
	List<Item> listSales(User user);
	List<String> getUsersEmails();
}
