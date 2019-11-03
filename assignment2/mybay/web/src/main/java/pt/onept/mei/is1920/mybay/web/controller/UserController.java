package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.basicType.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.exception.DuplicatedException;
import pt.onept.mei.is1920.mybay.common.exception.IncompleteException;
import pt.onept.mei.is1920.mybay.common.util.CountryConverter;

import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import java.io.Serializable;

@Named(value = "userController")
@RequestScoped
@Getter @Setter
public class UserController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@EJB
	private UserEJBRemote user;

	private String name, email, password, countryString;
	private Country country;

	public void register() {
		try {
			country = CountryConverter.StringToCountry(countryString);
			User userToRegister = new User(name, email, password, country);
			logger.debug("Trying to register user " + userToRegister);
			user.register(userToRegister);
		} catch (DuplicatedException e) {
			e.printStackTrace();
		} catch (IncompleteException e) {
			e.printStackTrace();
		}
	}
	//TODO 	we have to map what happens next......
	// at com.sun.jsf-impl@2.3.9.SP03//com.sun.faces.application.ActionListenerImpl.getNavigationOutcome(ActionListenerImpl.java:96)

}
