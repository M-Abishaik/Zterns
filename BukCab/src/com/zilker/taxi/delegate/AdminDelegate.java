package com.zilker.taxi.delegate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelDetail;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.AdminDAO;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.shared.SharedDAO;
import com.zilker.taxi.shared.SharedDelegate;

public class AdminDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Adds cab model details of a cab.
	 */
	
	public String addCabModel(CabModel cabModel, String userPhone) {
		int adminID = -1;
		int modelID = -1;
		String modelName = "";
		String modelDescription = "";
		String licencePlate = "";
		AdminDAO adminDAO = null;
		SharedDelegate sharedDelegate = null;
			
		try {
			adminDAO = new AdminDAO();
			sharedDelegate = new SharedDelegate();
			
			adminID = sharedDelegate.getUserID(userPhone);
			
			
			adminDAO.insertCabModel(adminID, cabModel);
				
			modelName = cabModel.getModelName();
			modelDescription = cabModel.getModelDescription();
			licencePlate = cabModel.getLicencePlate();
				
			modelID = getModelID(modelName, modelDescription);
				
			adminDAO.launchCab(modelID, adminID, licencePlate); 
			
			return Constants.SUCCESS;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cab model details to DAO.");
			    return Constants.FAILURE;
			} 
	}
	
	/*
	 * Retrieves the model ID.
	 */
	
	public int getModelID(String modelName, String modelDescription) {
		
		TaxiDAO taxiDAO = null;
		int modelID = -1;
		
		try {
			taxiDAO = new TaxiDAO();
			modelID = taxiDAO.getModelID(modelName, modelDescription);
			
			return modelID;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering model name and description to DAO.");
		    return -1;
		}
	}
	
	
	/*
	 * Retrieves the model ID using licence plate.
	 */
	
	public int getModelByLicencePlate(String licencePlate) {
		
		TaxiDAO taxiDAO = null;
		int modelID = -1;
		
		try {
			taxiDAO = new TaxiDAO();
			modelID = taxiDAO.getModelByLicencePlate(licencePlate);
			
			return modelID;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering cab licence plate to DAO.");
		    return -1;
		}
	}
	
	
	/*
	 * Updates cab model. 
	 */
	
	public String updateCabModel(CabModel cabModel, String userPhone, int modelID) {
		
		int adminID = -1;
		TaxiDAO taxiDAO = null;
		
		SharedDelegate sharedDelegate = null;
		
		try {
			sharedDelegate = new SharedDelegate();
			taxiDAO = new TaxiDAO();
			
			adminID = sharedDelegate.getUserID(userPhone);
		    taxiDAO.updateCabModel(cabModel, adminID, modelID); 
		    
		    return Constants.SUCCESS;
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring updated cab model details to DAO.");
		    return Constants.FAILURE;
		    } 
	}
	
	/*
	 *	Deletes the cab model and its corresponding ride details. 
	 */
	
	public String deleteCabModel(int modelID) {
			
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();

			taxiDAO.deleteCabModel(modelID); 
		    		    
		    return Constants.SUCCESS;
		    
		    } catch (Exception e) {
		    	LOGGER.log(Level.INFO, "Error in transferring model ID to DAO for deletion.");
		    	return Constants.FAILURE;
		    } 
	}
	
	/*
	 * Displays all the cab model details.
	 */
	
	public ArrayList<CabModelDetail> displayCabModelDetails() {
		
		ArrayList<CabModelDetail> cabModel = null;
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
			cabModel = taxiDAO.displayCabModelDetails();
			
			return cabModel;
			} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in displaying cab model details.");
		    return null;
		    } 
	}
	
	

}
