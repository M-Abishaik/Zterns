package com.zilker.taxi.delegate;

import java.util.ArrayList;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.RideInvoice;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.CustomerDAO;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.shared.SharedDelegate;

/*
 * Accepts values from the customer UI and passes them to the corresponding DAO's.
 */
public class CustomerDelegate {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Retrieves the location ID using location.
	 */

	public int findLocationID(String location) {

		int locationID = -1;
		CustomerDAO customerDAO = null;

		try {
			customerDAO = new CustomerDAO();
			locationID = customerDAO.findLocationID(location);

			return locationID;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering location to DAO.");
			return -1;
		}
	}

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
			LOGGER.log(Level.WARNING, "Error in displaying travel locations.");
			return null;
		}
	}

	/*
	 * Retrieves the zip-code using source ID.
	 */

	public String findZipByID(int sourceID) {

		String zipCode = "";
		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			zipCode = customerDAO.findZipByID(sourceID);

			return zipCode;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring source ID to DAO.");
			return "";
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
	 * Calculates the ride price and distance.
	 */

	public int calculateTravel(TravelInvoice travelInvoice, int flag) {

		int result = -1;
		float price = 0.0f;
		float distance = 0.0f;
		float min = 5000;
		float max = 1000000;
		RideInvoice rideInvoice = null;
		SharedDelegate sharedDelegate = null;
		TaxiDAO taxiDAO = null;
		Random random = null;

		// UpdateRide updateRide = null;

		try {
			taxiDAO = new TaxiDAO();
			sharedDelegate = new SharedDelegate();
			random = new Random();
			distance = min + random.nextFloat() * (max - min);

			if (distance >= 5000 && distance <= 15000) {
				price = 2500;
			} else if (distance >= 15001 && distance <= 50000) {
				price = 10000;
			} else if (distance >= 50001 && distance <= 70000) {
				price = 20000;
			} else {
				price = 30000;
			}

			rideInvoice = new RideInvoice(travelInvoice.getCustomerID(), travelInvoice.getDriverID(),
					travelInvoice.getCabID(), travelInvoice.getSourceID(), travelInvoice.getDestinationID(),
					travelInvoice.getFormattedTime(), price);

			if (flag == 0) {
				result = insertRideDetails(rideInvoice);

				taxiDAO.insertRouteDetails(travelInvoice.getSourceID(), travelInvoice.getDestinationID(), distance);

				// Updates the driver and cab status to be unavailable until the current ride
				// has been completed.

				sharedDelegate.updateDriverStatus(travelInvoice.getDriverID(), 0);
				return result;

			} else {

				updateRideDetails(rideInvoice);

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

	public int insertRideDetails(RideInvoice invoice) {

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

	public void updateRideDetails(RideInvoice invoice) {

		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			customerDAO.updateRideDetails(invoice);
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in updating ride details.");
		}
	}

	/*
	 * Retrieves the start time of the ride.
	 */

	public String findStartTime(int bookingID) {

		String startTime = "";
		CustomerDAO customerDAO = null;

		try {
			customerDAO = new CustomerDAO();
			startTime = customerDAO.findStartTime(bookingID);

			return startTime;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring booking ID to DAO.");
			return "";
		}
	}
}
