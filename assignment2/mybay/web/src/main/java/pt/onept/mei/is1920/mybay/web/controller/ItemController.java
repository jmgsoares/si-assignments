package pt.onept.mei.is1920.mybay.web.controller;

import pt.onept.mei.is1920.mybay.ejb.ItemEJBRemote;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name = "itemController")
public class ItemController implements Serializable {
	@EJB
	private ItemEJBRemote user;

	public ItemEJBRemote getUser() {
		return user;
	}

	public void setUser(ItemEJBRemote user) {
		this.user = user;
	}
}
