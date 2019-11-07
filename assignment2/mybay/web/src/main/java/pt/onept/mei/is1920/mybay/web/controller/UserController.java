package pt.onept.mei.is1920.mybay.web.controller;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import pt.onept.mei.is1920.mybay.common.type.User;
import pt.onept.mei.is1920.mybay.common.enums.Country;
import pt.onept.mei.is1920.mybay.common.converter.CountryConverter;
import pt.onept.mei.is1920.mybay.common_business.contract.AccountEJBRemote;
import pt.onept.mei.is1920.mybay.web.utility.MD5Utility;
import pt.onept.mei.is1920.mybay.web.utility.SessionUtility;

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
	private AccountEJBRemote account;

	private String name, email, password, countryString, newPassword;
	private Country country;

	private boolean loggedIn = false;
	private User loggedInAccount = null;

	public String signUp() {
		country = CountryConverter.StringToCountry(countryString);
		User userToRegister = new User(name, email, MD5Utility.HashPassword(password), country);
		logger.info("Registering user " + name);
		logger.debug("User to register name:" + name +
					 " email:" + email + " password:" + MD5Utility.HashPassword(password) +
					 " country", CountryConverter.CountryToString(country));
		HttpSession session = SessionUtility.getSession();
		session.invalidate();
		if (account.signUp(userToRegister)) {
			return "signup";
		} else {
			logger.info("Failed to sign up");
			return "register";
		}
	}

	public String logout() {
		HttpSession session = SessionUtility.getSession();
		session.invalidate();
		loggedIn = false;
		loggedInAccount = null;
		return "logout";
	}

	public String login() {
		try {
			logger.info("Logging in user: " + email);
			User accountLoggedIn = account.login(new User()
					.setEmail(email)
					.setPassword(MD5Utility.HashPassword(password)));
			if (accountLoggedIn != null) {
				logger.debug("Account read: " + accountLoggedIn.toString());
				HttpSession session = SessionUtility.getSession();
				session.setAttribute("email", accountLoggedIn.getEmail());
				this.loggedInAccount = accountLoggedIn;
				this.loggedIn = true;
				logger.info("Login successful");
				return "home";
			}
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		logger.info("Login failed");
		return "login";
	}

	public String updateAccount() {
	    logger.info("Trying to update user");
        HttpSession session = SessionUtility.getSession();
        email = session.getAttribute("email").toString();
        if(countryString != null) {
			country = CountryConverter.StringToCountry(countryString);
		}

		if(newPassword != null) {
			logger.debug("New password found");
			if(this.loggedInAccount.getPassword().equals(MD5Utility.HashPassword(password))) {
				logger.debug("Account verified");
				User accountToUpdate = new User(
						this.loggedInAccount.getName(),
						this.loggedInAccount.getEmail(),
						MD5Utility.HashPassword(newPassword),
						this.loggedInAccount.getCountry());
				logger.debug("BEFORE: Email: " + email + ", name: " + name + ", old password: " +
						MD5Utility.HashPassword(password) + ", new password: " +
						MD5Utility.HashPassword(newPassword) + ", country: " + country);
				if(account.updateAccount(accountToUpdate)) {
					logger.debug("AFTER: Email: " + email + ", name: " + name + ", old password: " +
							MD5Utility.HashPassword(password) + ", new password: " +
							MD5Utility.HashPassword(newPassword) + 	", country: " + country);
					newPassword = null;
					return "logout";
				}
			}
			logger.debug("Failed to verify account with email: "+email+" password: "+password+" new: "+newPassword);
			newPassword = null;
			return "profile";
		}
		else {
			User accountToUpdate = new User(
					this.name,
					this.loggedInAccount.getEmail(),
					this.loggedInAccount.getPassword(),
					this.country
			);
			if(account.updateAccount(accountToUpdate)) {
				logger.debug("Email: " + email + ", name: " + name + ", password:" +
						MD5Utility.HashPassword(password) + ", country: " + country);
			}
			return "profile";
		}
    }

	public String deleteAccount() {
		try {
			HttpSession session = SessionUtility.getSession();
			email = session.getAttribute("email").toString();
			logger.info("Deleting account: " + email);
			if(account.removeAccount(new User().setEmail(email))) {
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

}
