package com.zilker.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.zilker.constants.Constants;
import com.zilker.dao.TaxiDAO;

@Service
public class DriverDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Completes a ride.
	 */

	public String completeRide(int bookingID, int driverID) {
		TaxiDAO taxiDAO = null;
		String response = "";
		String zipCode = "";

		try {
			taxiDAO = new TaxiDAO();

			response = taxiDAO.completeRide(bookingID, driverID);

			if (response.equals(Constants.SUCCESS)) {
				
				zipCode = findZipByID(bookingID);
				
				updateDriverLocation(zipCode, driverID);
				
				return Constants.SUCCESS;
			}

			return Constants.FAILURE;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring driverID and zip-code to DAO.");
			return Constants.FAILURE;
		}
	}
	
	/*
	 * Retrives the zipcode of the destination location.
	 */
	
	public String findZipByID(int bookingID) {
		
		String zipCode = "";
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
			zipCode = taxiDAO.getZipCode(bookingID);
			
			return zipCode;
		}catch(Exception exception) {
			LOGGER.log(Level.WARNING, "Error in transferring destinationID to DAO.");
			return Constants.FAILURE;
		}
		
	}
	

	/*
	 * Updates current location of driver.
	 */

	public String updateDriverLocation(String zipCode, int driverID) {

		TaxiDAO taxiDAO = null;

		try {
			taxiDAO = new TaxiDAO();

			taxiDAO.updateDriverLocation(zipCode, driverID);

			return Constants.SUCCESS;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring driverID and zip-code to DAO.");
			return Constants.FAILURE;
		}

	}
	
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

			if (driverID != (-1)) {
				taxiDAO.addLicenceDetails(driverID, licenceNumber, zipCode);
				LOGGER.log(Level.INFO, "Driver Licence details successfully added.");

				cabID = getCabID();
				if (cabID != (-1)) {
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
			LOGGER.log(Level.WARNING, "Error in transfering licence details to DAO.");
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
			LOGGER.log(Level.WARNING, "Error in transferring cabID to DAO.");
			return -1;
		}
	}

}
