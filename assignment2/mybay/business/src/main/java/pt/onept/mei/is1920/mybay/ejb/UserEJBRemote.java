package pt.onept.mei.is1920.mybay.ejb;

import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;

import javax.ejb.Local;
import javax.ejb.Remote;

@Local
public interface UserEJBRemote {
	void register() throws DuplicatedException, IncompleteException;

}
