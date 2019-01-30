package com.zilker.taxi.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.dao.CustomerDAO;
import com.zilker.taxi.shared.SharedDelegate;

public class CustomerDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	/*
	 * Checks if the booking ID of a ride exists.
	 */
	
	public int checkBookingExists(int bID) {
		
	
		int bookingID = -1;
		CustomerDAO customerDAO = null;
		
		try {
			customerDAO = new CustomerDAO();
			bookingID = customerDAO.checkBookingExists(bID);
			
		    return bookingID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering booking ID to DAO.");
		    return -1;
		} 
	}
	
	/*
	 * Updates customer profile. 
	 */
	
	public void updatePersonalDetails(Customer customer) {
		
		CustomerDAO customerDAO = null;
		
		try {
			
			customerDAO = new CustomerDAO();
		    customerDAO.updatePersonalDetails(customer); 
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring customer profile to DAO.");
		    } 
	}
	
	/*
	 * Passes the customer profile. 
	 */
	
	/*public int insertPersonalDetails(Customer customer) {
		
		int customerID = -1;
		SharedDelegate sharedDelegate = null;
		CustomerDAO customerDAO = null;
		
		try {
			customerDAO = new CustomerDAO();
			sharedDelegate = new SharedDelegate();
			
			customerID = sharedDelegate.checkMailExists(customer.getMailId());
			
			if (customerID != (-1)) {
				return customerID;
			}
			
			customerDAO.insertPersonalDetails(customer);
			
			return -1;
			
		} catch(Exception e) {
			LOGGER.log(Level.INFO, "Error in transfering personal details to DAO.");
			return -1;
		}
	}*/
	

	/*
	 * Displays the customer profile. 
	 */
	
	/*public Customer displayProfile(String email) {
		
		Customer customer = null;
		int customerID = -1;
		SharedDelegate sharedDelegate = null;
		CustomerDAO customerDAO = null;
		
		try {
		      sharedDelegate = new SharedDelegate();
		      customerDAO = new CustomerDAO();
		      
		      customerID = sharedDelegate.checkMailExists(email);
				
				if (customerID == (-1)) {
					return null;
				}
		      
		      customer = customerDAO.displayProfile(email);
		      return customer;
		      
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in retrieving profile details from the DB.");
		    	return null;
		  } 
	}*/
	
	

}
