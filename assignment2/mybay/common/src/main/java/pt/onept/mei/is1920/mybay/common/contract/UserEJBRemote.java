package pt.onept.mei.is1920.mybay.common.contract;

import pt.onept.mei.is1920.mybay.common.type.User;

import javax.ejb.Local;

@Local
public interface UserEJBRemote extends Crudable<User> {
	boolean login(String email, String password);
}
