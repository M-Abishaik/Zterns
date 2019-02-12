package com.zilker.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.constants.Constants;
import com.zilker.dao.SharedDAO;
import com.zilker.bean.User;


public class SharedDelegate {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Passes user registration information to the DAO.
	 */

	public String register(User user) {
		SharedDAO sharedDAO = null;
		boolean contactExists = false;
		int userID = -1;

		try {
			sharedDAO = new SharedDAO();
			contactExists = isContactExists(user.getContact());

			if (contactExists == false) {
				sharedDAO.createAccount(user);
				userID = getUserID(user.getContact());

				if (userID != (-1)) {
					sharedDAO.updateAccount(userID, user.getContact());
				}

				sharedDAO.createUserAddress(user, userID);
				return Constants.SUCCESS;
			}

			return Constants.FAILURE;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering user details to Shared DAO.");
			return Constants.FAILURE;
		}
	}

	
	
	
	/*
	 * Checks if the login credentials are proper.
	 */

	public String login(String phone, String password) {
		SharedDAO sharedDAO = null;
		String role = "";

		try {
			sharedDAO = new SharedDAO();

			role = sharedDAO.login(phone, password);
			if (!role.equals("")) {
				return role;
			}

			return Constants.FAILURE;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering login details to Shared DAO.");
			return Constants.FAILURE;
		}
	}
	
	
	/*
	 * Checks if the contact of a customer already exists.
	 */

	public boolean isContactExists(String contact) {

		boolean contactExists = false;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			contactExists = sharedDAO.checkContactExists(contact);

			return contactExists;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering contact to Shared DAO.");
			return false;
		}
	}
	
	/*
	 * Retrieves the User ID.
	 */

	public int getUserID(String contact) {

		SharedDAO sharedDAO = null;
		int userID = -1;

		try {
			sharedDAO = new SharedDAO();
			userID = sharedDAO.getUserID(contact);

			return userID;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering user contact to Shared DAO.");
			return -1;
		}
	}

}
