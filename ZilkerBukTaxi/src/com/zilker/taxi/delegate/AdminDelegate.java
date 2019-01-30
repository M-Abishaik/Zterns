package com.zilker.taxi.delegate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.CabDetail;
import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelID;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.AdminDAO;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.shared.SharedDelegate;

public class AdminDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Adds cab model details of a cab.
	 */
	
	public String addCabModelDetails(CabModel cabModel, String userPhone) {
		int adminID = -1;
		AdminDAO adminDAO = null;
		SharedDelegate sharedDelegate = null;
			
		try {
			adminDAO = new AdminDAO();
			sharedDelegate = new SharedDelegate();
			
			adminID = sharedDelegate.getUserID(userPhone);
			
			if(adminID!=(-1)) {
				adminDAO.insertCabModelDetails(adminID, cabModel);
			} 
			
			return Constants.SUCCESS;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cab model details to DAO.");
			    return Constants.FAILURE;
			} 
	}
	
	/*
	 * Adds cab launch details.
	 */
	
	public String addCabLaunchDetails(CabDetail cabDetail, String userPhone) {
		int adminID = -1;
		AdminDAO adminDAO = null;
		SharedDelegate sharedDelegate = null;
			
		try {
			adminDAO = new AdminDAO();
			sharedDelegate = new SharedDelegate();
			
			adminID = sharedDelegate.getUserID(userPhone);
			
			if(adminID!=(-1)) {
				adminDAO.insertCabLaunchDetails(adminID, cabDetail);
			} 
			
			return Constants.SUCCESS;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cab launch details to DAO.");
			    return Constants.FAILURE;
			} 
	}
	
	/*
	 * Displays all the cab model details.
	 */
	
	public ArrayList<CabModelID> displayCabModelDetails() {
		
		ArrayList<CabModelID> cabModel = null;
		AdminDAO adminDAO = null;
		
		try {
			adminDAO = new AdminDAO();
			cabModel = adminDAO.displayCabModelDetails();
			
			return cabModel;
			} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in displaying cab model details.");
		    return null;
		    } 
	}
	
}
