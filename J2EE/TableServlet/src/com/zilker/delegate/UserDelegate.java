package com.zilker.delegate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.bean.User;
import com.zilker.dao.UserDAO;

public class UserDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public boolean registerUser(User user) {
		boolean check = false;
		UserDAO userDAO = null;
		
		try {
			userDAO = new UserDAO();
			check = userDAO.registerUser(user);
			return check;
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error in passing personal details to DAO.");
			return false;
		}
	}
	
	public ArrayList<User> fetchDetails() {
		ArrayList<User> user = null;
		UserDAO userDAO = null;
		
		try {
			userDAO = new UserDAO();
			user = userDAO.fetchDetails();
			return user;
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error in fetching personal details from DAO.");
			return null;
		}
	}
}
