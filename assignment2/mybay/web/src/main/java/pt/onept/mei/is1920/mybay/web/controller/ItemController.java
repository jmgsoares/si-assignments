package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.contract.ItemEJBRemote;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "itemController")
@RequestScoped
@Getter
@Setter
public class ItemController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(ItemController.class);



	@EJB
	private ItemEJBRemote item;



}
