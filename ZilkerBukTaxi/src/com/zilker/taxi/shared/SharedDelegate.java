package com.zilker.taxi.shared;

import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.dao.TaxiDAO;

public class SharedDelegate {
	
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Checks if the email of a customer already exists.
	 */
	
	public int checkMailExists(String mail) {
		
		int customerID = -1;
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
			customerID = taxiDAO.checkMailExists(mail);
			
			
			return customerID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering mail to DAO.");
		    return -1;
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
