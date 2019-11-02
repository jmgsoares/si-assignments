package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "userController")
@RequestScoped
@Getter @Setter
public class UserController implements Serializable {
	@EJB
	private UserEJBRemote user;

	public Country[] getCountries() {
		return Country.values();
	}
}
