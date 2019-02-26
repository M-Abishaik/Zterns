package com.zilker.delegate;

import java.util.ArrayList;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.bean.Address;
import com.zilker.bean.TravelInvoice;
import com.zilker.dao.CustomerDAO;
import com.zilker.dao.TaxiDAO;
import com.zilker.constants.Constants;

public class CustomerDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Displays travel locations.
	 */

	public ArrayList<Address> displayLocations() {

		CustomerDAO customerDAO = null;
		ArrayList<Address> address = null;

		try {
			address = new ArrayList<Address>();
			customerDAO = new CustomerDAO();
			address = customerDAO.displayLocations();

			return address;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in fetching travel locations from DAO.");
			return null;
		}
	}
	
	/*
	 * Extracts source location from the drop-down value
	 */
	
	public String extractLocation(String source, int flag) {
		
		StringBuffer stringBuffer = new StringBuffer("");
		
		String address[] = source.split(", ");
		int addressLength = address.length;
		int i = -1;
		
		if(flag==0) {
			for(i=0;i<addressLength-1;i++) {
				stringBuffer.append(address[i] + ", ");
			}
			stringBuffer.setLength(stringBuffer.length()-2);
			return stringBuffer.toString();

		}
			return address[addressLength-1];
	}
	
	/*
	 * Retrieves the location ID using location.
	 */

	public int findLocationID(String location, String zipCode) {

		int locationID = -1;
		CustomerDAO customerDAO = null;

		try {
			customerDAO = new CustomerDAO();
			locationID = customerDAO.findLocationID(location, zipCode);

			return locationID;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering location to customer DAO.");
			return -1;
		}
	}
	
	/*
	 * Finds the nearest cab corresponding to the source.
	 */

	public int findNearestCab(String zipCode, int flag) {

		CustomerDAO customerDAO = null;
		int cabID = -1;
		int driverID = -1;

		try {
			customerDAO = new CustomerDAO();
			driverID = customerDAO.findNearestDriver(zipCode, flag);

			if (driverID != (-1)) {
				cabID = customerDAO.findNearestCab(driverID);
				return cabID;
			}

			return cabID;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in fetching nearest cab from DAO.");
			return -1;
		}
	}
	
	/*
	 * Finds the driver ID.
	 */

	public int getDriverID(int cabID) {
		int driverID = -1;
		CustomerDAO customerDAO = null;

		try {
			customerDAO = new CustomerDAO();
			driverID = customerDAO.getDriverID(cabID);

			return driverID;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring cabID to DAO.");
			return -1;
		}
	}
	
	/*
	 * Checks if the cab with given seats is avaliable.
	 */

	public String checkCabSeats(int nearestCab, int seats) {
		int modelID = -1;
		CustomerDAO customerDAO = null;
		boolean check = false;

		try {
			customerDAO = new CustomerDAO();
			modelID = customerDAO.getModelID(nearestCab);

			if (modelID != (-1)) {
				check = customerDAO.isSeatExists(modelID, seats);

				if (check == true) {
					return Constants.SUCCESS;
				} else {
					return Constants.FAILURE;
				}
			}

			return Constants.FAILURE;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring cab seats to DAO.");
			return Constants.FAILURE;
		}
	}
	

	/*
	 * Calculates the ride price and distance.
	 */

	public int calculateTravel(TravelInvoice travelInvoice, int flag) {

		int bookingID = -1;
		String zipCode = "";
		
		SharedDelegate sharedDelegate = null;
		TaxiDAO taxiDAO = null;

		try {
			taxiDAO = new TaxiDAO();
			sharedDelegate = new SharedDelegate();

			if (flag == 0) {
				bookingID = insertRideDetails(travelInvoice);

				taxiDAO.insertRouteDetails(travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getDistance());
				
				// Updates the driver and cab status to be unavailable until the current ride
				// has been completed.

				sharedDelegate.updateDriverStatus(travelInvoice.getDriverID(), 0);
				return bookingID;

			} else {

				updateRideDetails(travelInvoice);

				return travelInvoice.getCustomerID();
			}
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring invoice details to DAO.");
			return -1;
		}
	}
	
	/*
	 * Inserts the ride details of the customer.
	 */

	public int insertRideDetails(TravelInvoice invoice) {

		int bookingID = -1;
		CustomerDAO customerDAO = null;

		try {
			customerDAO = new CustomerDAO();
			bookingID = customerDAO.insertRideDetails(invoice);

			return bookingID;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring invoice details to DAO.");
			return -1;
		}
	}
	
	/*
	 * Updates the ride details of a customer.
	 */

	public void updateRideDetails(TravelInvoice invoice) {

		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			customerDAO.updateRideDetails(invoice);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in updating ride details.");
		}
	}

}
