package com.zilker.delegate;

import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zilker.constants.Constants;
import com.zilker.constants.ErrorConstants;
import com.zilker.customexception.ApplicationException;
import com.zilker.customexception.CabNotExistsException;
import com.zilker.dao.TaxiDAO;

@Service
public class DriverDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	TaxiDAO taxiDAO;
	
	@Autowired
	SharedDelegate sharedDelegate;
	
	/*
	 * Add licence details.
	 */

	public String addLicenceDetails(int driverID, String licenceNumber, String zipCode) throws ApplicationException{
		int cabID = -1;

		try {
			
			if (driverID != (-1)) {
				taxiDAO.addLicenceDetails(driverID, licenceNumber, zipCode);
				LOGGER.log(Level.INFO, "Driver Licence details successfully added.");

				cabID = getCabID();
				if (cabID != (-1)) {
					taxiDAO.assignCabDriver(cabID, driverID);
					taxiDAO.updateCabStatus(cabID, 0);

				} else {
					
					throw new CabNotExistsException();
				}

				return Constants.SUCCESS;
			}

			return Constants.FAILURE;

		}catch(CabNotExistsException cabNotExistsException) {
			throw cabNotExistsException;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Retrieves the cab ID of a cab.
	 */

	public int getCabID() throws ApplicationException{
		int cabID = -1;

		try {
			cabID = taxiDAO.getCabID();

			return cabID;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Completes a ride.
	 */

	public String completeRide(int bookingID, int driverID) throws ApplicationException{
		String response = "";
		String zipCode = "";

		try {

			response = taxiDAO.completeRide(bookingID, driverID);

			if (response.equals(Constants.SUCCESS)) {

				zipCode = findZipByID(bookingID);

				updateDriverLocation(zipCode, driverID);

				return Constants.SUCCESS;
			}

			return Constants.FAILURE;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Retrives the zipcode of the destination location.
	 */

	public String findZipByID(int bookingID) throws ApplicationException{

		String zipCode = "";

		try {
			zipCode = taxiDAO.getZipCode(bookingID);

			return zipCode;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Updates current location of driver.
	 */

	public String updateDriverLocation(String zipCode, int driverID)throws ApplicationException {

		try {

			taxiDAO.updateDriverLocation(zipCode, driverID);

			return Constants.SUCCESS;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}	
	}
}
