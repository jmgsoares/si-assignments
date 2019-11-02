package pt.onept.mei.is1920.mybay.web.controller;

import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;

import javax.ejb.EJB;
import javax.faces.bean.ManagedBean;
import java.io.Serializable;

@ManagedBean(name = "itemController")
public class ItemController implements Serializable {
	@EJB
	private ItemEJBRemote item;

	public ItemEJBRemote getItem() {
		return item;
	}

	public void setItem(ItemEJBRemote item) {
		this.item = item;
	}
}
