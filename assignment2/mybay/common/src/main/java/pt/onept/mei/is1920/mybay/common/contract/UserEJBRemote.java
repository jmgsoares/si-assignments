package pt.onept.mei.is1920.mybay.common.contract;

import pt.onept.mei.is1920.mybay.common.basicType.User;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;

import javax.ejb.Local;

@Local
public interface UserEJBRemote {
	User register(User user) throws DuplicatedException, IncompleteException;
	boolean login(String email, String password);
	void delete(String email);
}
