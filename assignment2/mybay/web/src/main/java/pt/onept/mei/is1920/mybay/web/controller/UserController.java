package pt.onept.mei.is1920.mybay.web.controller;

import pt.onept.mei.is1920.mybay.ejb.UserEJBRemote;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name = "userController")
public class UserController implements Serializable {
	@EJB
	private UserEJBRemote user;

	public UserEJBRemote getUser() {
		return user;
	}

	public void setUser(UserEJBRemote user) {
		this.user = user;
	}
}
