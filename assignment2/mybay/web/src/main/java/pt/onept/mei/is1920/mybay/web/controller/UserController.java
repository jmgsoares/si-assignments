package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.contract.UserEJBRemote;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.utility.CountryConverter;
import pt.onept.mei.is1920.mybay.web.utility.MD5;
import pt.onept.mei.is1920.mybay.web.utility.SessionUtils;

import javax.ejb.EJB;
import javax.enterprise.context.SessionScoped;
import javax.inject.Named;
import javax.servlet.http.HttpSession;
import java.io.Serializable;

@Named(value = "userController")
@SessionScoped
@Getter
@Setter
public class UserController implements Serializable {
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@EJB
	private UserEJBRemote user;

	private String name, email, password, countryString;
	private Country country;

	private boolean loggedIn = false;

	public String register() {
		country = CountryConverter.StringToCountry(countryString);
		User userToRegister = new User(name, email, hashPassword(password), country);
		logger.info("Registering user " + name);
		email = null; name = null; country = null; password = null;
		logger.debug("User to register -> " + userToRegister.toString());
		if (user.create(userToRegister)) {
			return "signup";
		} else {
			logger.info("Failed to sign up");
			return "register";
		}
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
			if (user.login(email, hashPassword(password))) {
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("email", email);
				session.setAttribute("name", name);
				session.setAttribute("country", country);
				email = null; name = null; country = null; password = null;
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

	public String update() {
	    logger.info("Trying to update user");
        HttpSession session = SessionUtils.getSession();
        email = session.getAttribute("email").toString();

        if(user.update(new User().setEmail(email).setName(name).setPassword(password).setCountry(country))) {
        	logger.info("Update successful");
			session.setAttribute("email", email);
			session.setAttribute("name", name);
			session.setAttribute("country", country);
			name = null; password = null; country = null;
        	return "profile";
        }

        logger.info("Update unsuccessful");
        return "profile";
    }

	public String deleteAcc() {
		try {
			HttpSession session = SessionUtils.getSession();
			email = session.getAttribute("email").toString();
			logger.info("Deleting account: " + email);
			user.delete(new User().setEmail(email));
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
