package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zilker.bean.Address;
import com.zilker.bean.TravelInvoice;
import com.zilker.constants.Constants;
import com.zilker.constants.ErrorConstants;
import com.zilker.customexception.ApplicationException;
import com.zilker.customexception.NearestCabNotFoundException;
import com.zilker.customexception.SeatNotExistsException;
import com.zilker.dao.CustomerDAO;
import com.zilker.dao.TaxiDAO;

@Service
public class CustomerDelegate {
	
	@Autowired
	SharedDelegate sharedDelegate;
	
	@Autowired
	CustomerDAO customerDAO;
	
	@Autowired
	TaxiDAO taxiDAO;
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Displays travel locations.
	 */

	public ArrayList<Address> displayLocations() throws ApplicationException{

		ArrayList<Address> address = null;

		try {
			address = new ArrayList<Address>();
			address = customerDAO.displayLocations();

			return address;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Extracts source location from the drop-down value
	 */

	public String extractLocation(String source, int flag)throws ApplicationException {

		StringBuffer stringBuffer = new StringBuffer("");

		String address[] = source.split(", ");
		int addressLength = address.length;
		int i = -1;

		if (flag == 0) {
			for (i = 0; i < addressLength - 1; i++) {
				stringBuffer.append(address[i] + ", ");
			}
			stringBuffer.setLength(stringBuffer.length() - 2);
			return stringBuffer.toString();

		}
		return address[addressLength - 1];
	}
	
	/*
	 * Retrieves the location ID using location.
	 */

	public int findLocationID(String location, String zipCode)throws ApplicationException {

		int locationID = -1;

		try {
			locationID = customerDAO.findLocationID(location, zipCode);

			return locationID;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}

	/*
	 * Finds the nearest cab corresponding to the source.
	 */

	public int findNearestCab(String zipCode, int flag)throws ApplicationException {

		int cabID = -1;
		int driverID = -1;

		try {
			driverID = customerDAO.findNearestDriver(zipCode, flag);

			if (driverID != (-1)) {
				cabID = customerDAO.findNearestCab(driverID);
				return cabID;
			}
			
			throw new NearestCabNotFoundException();

		}catch(NearestCabNotFoundException nearestCabNotFoundException) {
			throw nearestCabNotFoundException;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Finds the driver ID.
	 */

	public int getDriverID(int cabID) throws ApplicationException{
		int driverID = -1;

		try {
			driverID = customerDAO.getDriverID(cabID);

			return driverID;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}

	/*
	 * Checks if the cab with given seats is avaliable.
	 */

	public String checkCabSeats(int nearestCab, int seats) throws ApplicationException{
		int modelID = -1;
		boolean check = false;

		try {
			modelID = customerDAO.getModelID(nearestCab);

			check = customerDAO.isSeatExists(modelID, seats);

			if (check == true) {
					return Constants.SUCCESS;
			} 
					
			throw new SeatNotExistsException();
			
		}catch(SeatNotExistsException seatNotExistsException) {
			throw seatNotExistsException;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Calculates the ride price and distance.
	 */

	public int calculateTravel(TravelInvoice travelInvoice) throws ApplicationException{

		int bookingID = -1;

		try {

			bookingID = insertRideDetails(travelInvoice);

			taxiDAO.insertRouteDetails(travelInvoice.getSourceID(), travelInvoice.getDestinationID(),
					travelInvoice.getDistance());

				// Updates the driver and cab status to be unavailable until the current ride
				// has been completed.
			sharedDelegate.updateDriverStatus(travelInvoice.getDriverID(), 0);
			return bookingID;
	
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Inserts the ride details of the customer.
	 */

	public int insertRideDetails(TravelInvoice invoice)throws ApplicationException {

		int bookingID = -1;

		try {
			bookingID = customerDAO.insertRideDetails(invoice);

			return bookingID;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
}
