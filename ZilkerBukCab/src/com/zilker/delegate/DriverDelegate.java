package com.zilker.delegate;

import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.constants.Constants;
import com.zilker.dao.TaxiDAO;



public class DriverDelegate {
	

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

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
	
	/*
	 * Completes a ride.
	 */

	public String completeRide(String zipCode, int driverID) {
		int bookingID = -1;
		TaxiDAO taxiDAO = null;

		try {
			taxiDAO = new TaxiDAO();

			bookingID = taxiDAO.completeRide(zipCode, driverID);

			if (bookingID != (-1)) {
				taxiDAO.updateBookingTable(bookingID, driverID);
				return Constants.SUCCESS;
			}

			return Constants.FAILURE;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring driverID and zip-code to DAO.");
			return Constants.FAILURE;
		}
	}

	/*
	 * Updates current location of driver.
	 */

	public String updateLocation(String zipCode, int driverID) {

		TaxiDAO taxiDAO = null;

		try {
			taxiDAO = new TaxiDAO();

			taxiDAO.updateLocation(zipCode, driverID);

			return Constants.SUCCESS;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring driverID and zip-code to DAO.");
			return Constants.FAILURE;
		}

	}


}
