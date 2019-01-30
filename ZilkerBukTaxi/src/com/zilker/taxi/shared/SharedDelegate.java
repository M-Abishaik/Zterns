package com.zilker.taxi.shared;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.TaxiDAO;

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
			
			if(contactExists==false) {
				sharedDAO.createAccount(user);
				userID = getUserID(user.getContact());
				
				if(userID!=(-1)) {
					sharedDAO.updateAccount(userID, user.getContact());
				}
				
				sharedDAO.createUserAddress(user, userID);
				return Constants.SUCCESS;
			} 
				
			return Constants.FAILURE;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering user details to DAO.");
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
			if(!role.equals("")) {
				return role;
			}

			return Constants.FAILURE;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering login details to DAO.");
		    return Constants.FAILURE;
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
		    LOGGER.log(Level.INFO, "Error in transfering user contact to DAO.");
		    return -1;
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
		    LOGGER.log(Level.INFO, "Error in transfering contact to DAO.");
		    return false;
		} 
	}
	
	
	/*
	 *	Removes customer account and its corresponding ride details. 
	 */
	
	public void deleteAccount(String mail, int  customerID) {
			
		TaxiDAO taxiDAO = null;
		try {
			taxiDAO = new TaxiDAO();
		    taxiDAO.deleteAccount(mail, customerID); 
		    
		    } catch (Exception e) {
		    	LOGGER.log(Level.INFO, "Error in transferring mail and customer ID to DAO.");
		    } 
	}
	
	
	
	

}
