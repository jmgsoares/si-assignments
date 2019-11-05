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
	private String newPassword;

	public String register() {
		country = CountryConverter.StringToCountry(countryString);
		User userToRegister = new User(name, email, hashPassword(password), country);
		logger.info("Registering user " + name);
		logger.debug("User to register -> " + userToRegister.toString());
		HttpSession session = SessionUtils.getSession();
		session.invalidate();
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
				User loggedInUser = user.read(new User().setEmail(email));
				HttpSession session = SessionUtils.getSession();
				session.setAttribute("email", loggedInUser.getEmail());
				session.setAttribute("name", loggedInUser.getName());
				session.setAttribute("country", loggedInUser.getCountry());
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
        if(countryString != null) {
			country = CountryConverter.StringToCountry(countryString);
		}

		if(newPassword != null) {
			logger.debug("New password found");
			if(user.login(email, hashPassword(password))) {
				logger.debug("Account verified");
				User userToUpdate = new User(name, email, hashPassword(newPassword), country);
				logger.debug("BEFORE: Email: " + email + ", name: " + name + ", old password: "+  hashPassword(password) + ", new password: " +  hashPassword(newPassword) + ", country: " + country);
				if(user.update(userToUpdate)) {
					logger.debug("AFTER: Email: " + email + ", name: " + name + ", old password: "+  hashPassword(password) + ", new password: " +  hashPassword(newPassword) + ", country: " + country);
					newPassword = null;
					return "logout";
				}
			}
			logger.debug("Failed to verify account with email: "+email+" password: "+password+" new: "+newPassword);
			newPassword = null;
			return "profile";
		} else {
			if(user.update(new User(name, email, hashPassword(password), country))) {
				logger.debug("Email: " + email + ", name: " + name + ", password:" + password + ", country: " + country);
			}
			return "profile";
		}
    }

	public String delete() {
		try {
			HttpSession session = SessionUtils.getSession();
			email = session.getAttribute("email").toString();
			logger.info("Deleting account: " + email);
			if(user.delete(new User().setEmail(email))) {
				session.invalidate();
				logger.debug("Delete successful");
				loggedIn = false;
			}
			else {
				logger.debug("Something went wrong deleting account");
				return "profile";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
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
