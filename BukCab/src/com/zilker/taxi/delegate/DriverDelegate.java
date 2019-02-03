package com.zilker.taxi.delegate;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.shared.SharedDelegate;

public class DriverDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	/*
	 * Add licence details.
	 */
	
	public String addLicenceDetails(String licenceNumber, String userPhone, String zipCode) {
		TaxiDAO taxiDAO = null;
		SharedDelegate sharedDelegate = null;
		int driverID = -1;
		int cabID = -1; 
		
		try {
			taxiDAO = new TaxiDAO();
			sharedDelegate = new SharedDelegate();
			
			driverID = sharedDelegate.getUserID(userPhone);
			
			if(driverID!=(-1)) {
					taxiDAO.addLicenceDetails(driverID, licenceNumber, zipCode);
				    LOGGER.log(Level.INFO, "Driver Licence details successfully added.");
				    
				    cabID = getCabID();
				    if(cabID!=(-1)) {
				    	taxiDAO.assignCabDriver(cabID, driverID);
				    	taxiDAO.updateCabStatus(cabID, 0);
				    	
				    } else {
					    LOGGER.log(Level.INFO, "No cabs are available yet. Please wait for some time.");
						return Constants.FAILURE;
				    }
				   
					return Constants.SUCCESS;
				}
								
			return Constants.FAILURE;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering licence details to DAO.");
		    return Constants.FAILURE;
		}
	}
	
	
	/*
	 * Retrieves the cab ID of a cab.
	 */
	
	public int getCabID() {
		int cabID = -1;
		TaxiDAO taxiDAO = null;
			
		try {
			taxiDAO = new TaxiDAO();
			cabID = taxiDAO.getCabID();
				
			return cabID;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cabID to DAO.");
			    return -1;
			} 
	}

}
