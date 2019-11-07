package pt.onept.mei.is1920.mybay.common_business.contract;

import pt.onept.mei.is1920.mybay.common.type.User;

import javax.ejb.Local;

@Local
public interface AccountEJBRemote {
	boolean signUp(User user);
	User login(User user);
	boolean removeAccount(User user);
	boolean updateAccount(User user);
}
