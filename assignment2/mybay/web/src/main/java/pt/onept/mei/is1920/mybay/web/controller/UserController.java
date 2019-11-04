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
import pt.onept.mei.is1920.mybay.web.util.SessionUtils;
import pt.onept.mei.is1920.mybay.web.util.MD5;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "userController")
@SessionScoped
@Getter @Setter
public class UserController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@EJB
	private UserEJBRemote user;

	private String name, email, password, countryString;
	private Country country;

	private boolean loggedIn = false;

	public String register() {
		try {
			country = CountryConverter.StringToCountry(countryString);
			User userToRegister = new User(name, email, hashPassword(password), country);
			logger.info("Registering user " + name);
			logger.debug("User to register -> " + userToRegister.toString());
			user.register(userToRegister);
			return "signup";
		} catch (DuplicatedException e) {
			e.printStackTrace();
		} catch (IncompleteException e) {
			e.printStackTrace();
		}
		logger.info("Failed to sign up");
		return "register";
	}
	public String logout() {
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
		loggedIn = false;
		return "logout";
	}

	public String login() {
		try {
			logger.info("Received user to login with eMail: " + email + " password: " + password);
			if(user.login(email, hashPassword(password))) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("email", email);
				session.setAttribute("name", name);
				session.setAttribute("country", country);
				logger.info("Login successful");
				loggedIn = true;
				return "home";
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("Login failed");
		return "login";
	}

	public String deleteAcc() {
        try {
            logger.info("Deleting account: " + email);
            user.delete(email);
            HttpSession session = SessionUtils.getSession();
            session.invalidate();
            logger.info("Delete successful");
			loggedIn = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
		return "delete";
    }

	private String hashPassword(String password) {
		for (int i = 0; i <= 7; i++) {
			password = MD5.GetMd5(password);
		}
		return password;
	}
}
