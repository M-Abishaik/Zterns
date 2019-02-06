package com.zilker.taxi.shared;

import java.util.ArrayList;
import java.util.logging.Level;

import java.util.logging.Logger;

import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.Profile;
import com.zilker.taxi.bean.RideHistory;
import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.Constants;

public class SharedDelegate {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Passes user registration information to the DAO.
	 */

	public String register(User user) {
		SharedDAO sharedDAO = null;
		boolean contactExists = false;
		int userID = -1;

		try {
			sharedDAO = new SharedDAO();
			contactExists = isContactExists(user.getContact());

			if (contactExists == false) {
				sharedDAO.createAccount(user);
				userID = getUserID(user.getContact());

				if (userID != (-1)) {
					sharedDAO.updateAccount(userID, user.getContact());
				}

				sharedDAO.createUserAddress(user, userID);
				return Constants.SUCCESS;
			}

			return Constants.FAILURE;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering user details to DAO.");
			return Constants.FAILURE;
		}
	}

	/*
	 * Checks if the contact of a customer already exists.
	 */

	public boolean isContactExists(String contact) {

		boolean contactExists = false;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			contactExists = sharedDAO.checkContactExists(contact);

			return contactExists;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering contact to DAO.");
			return false;
		}
	}

	/*
	 * Retrieves the User ID.
	 */

	public int getUserID(String contact) {

		SharedDAO sharedDAO = null;
		int userID = -1;

		try {
			sharedDAO = new SharedDAO();
			userID = sharedDAO.getUserID(contact);

			return userID;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering user contact to DAO.");
			return -1;
		}
	}

	/*
	 * Checks if the login credentials are proper.
	 */

	public String login(String phone, String password) {
		SharedDAO sharedDAO = null;
		String role = "";

		try {
			sharedDAO = new SharedDAO();

			role = sharedDAO.login(phone, password);
			if (!role.equals("")) {
				return role;
			}

			return Constants.FAILURE;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering login details to DAO.");
			return Constants.FAILURE;
		}
	}

	/*
	 * Displays the profile.
	 */

	public Profile displayProfile(String userPhone) {

		Profile profile = null;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();

			profile = sharedDAO.displayProfile(userPhone);
			return profile;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in passing user contact for profile details to DAO.");
			return null;
		}
	}

	/*
	 * Displays the ride details.
	 */

	public BookingResponse displayBookingDetails(int bookingID) {

		BookingResponse bookingResponse = null;
		SharedDAO sharedDAO = null;
		boolean check = false;

		try {
			sharedDAO = new SharedDAO();

			check = checkBookingExists(bookingID);
			if (check == false) {
				return null;
			}

			bookingResponse = sharedDAO.displayBookingDetails(bookingID);

			return bookingResponse;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying booking details from DAO.");
			return null;
		}
	}

	/*
	 * Retrieves the booking ID of a ride.
	 */

	public boolean checkBookingExists(int bID) {

		boolean check = false;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			check = sharedDAO.checkBookingExists(bID);

			return check;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering booking ID to DAO.");
			return false;
		}
	}

	/*
	 * Finds cab details by ID.
	 */

	public String findCabByID(int cabID) {

		String cab = "";
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			cab = sharedDAO.findCabByID(cabID);

			return cab;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering booking ID to DAO.");
			return "";
		}

	}

	/*
	 * Finds cab details by ID.
	 */

	public String findDriverByID(int driverID) {

		String driver = "";
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			driver = sharedDAO.findDriverByID(driverID);

			return driver;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering driver ID to DAO.");
			return "";
		}

	}

	/*
	 * Updates customer profile.
	 */

	public String updateProfile(User user) {

		SharedDAO sharedDAO = null;

		try {

			sharedDAO = new SharedDAO();
			sharedDAO.updateProfile(user);

			return Constants.SUCCESS;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring customer profile to DAO.");
			return Constants.FAILURE;
		}
	}

	/*
	 * Cancels the ride of a customer.
	 */

	public boolean cancelRide(int bookingID) {

		int bID = -1;
		int driverID = -1;
		SharedDAO sharedDAO = null;
		boolean check = false;

		try {
			sharedDAO = new SharedDAO();

			check = checkBookingExists(bookingID);
			if (check == false) {
				return false;
			}

			driverID = sharedDAO.cancelRide(bookingID);

			updateDriverStatus(driverID, 1);

			return true;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring booking ID for cancellation to DAO.");
			return false;
		}
	}

	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */

	public void updateDriverStatus(int driverID, int flag) {
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			sharedDAO.updateDriverStatus(driverID, flag);

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring driverID and flag to DAO.");
		}
	}

	/*
	 * Displays travel locations.
	 */

	public ArrayList<RideHistory> displayRideHistory(int userID, int flag) {

		SharedDAO sharedDAO = null;
		ArrayList<RideHistory> history = null;

		try {
			sharedDAO = new SharedDAO();
			history = sharedDAO.displayRideHistory(userID, flag);

			return history;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}

	/*
	 * Displays travel locations by zip-code.
	 */

	public ArrayList<RideHistory> displayRideHistoryByLocation(int driverID, String zipCode) {

		SharedDAO sharedDAO = null;
		ArrayList<RideHistory> history = null;

		try {
			history = new ArrayList<RideHistory>();
			sharedDAO = new SharedDAO();
			history = sharedDAO.displayRideHistoryByLocation(driverID, zipCode);

			return history;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}

	/*
	 * Retrieves the booking ID of a ride for rating.
	 */

	public boolean checkBookingExistsForRating(int bID, int userID) {

		boolean check = false;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			check = sharedDAO.checkBookingExistsForRating(bID, userID);

			return check;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering booking ID to DAO.");
			return false;
		}
	}

	/*
	 * Rates a trip.
	 */

	public String rateTrip(float rating, int bookingID, int userID) {

		SharedDAO sharedDAO = null;

		try {

			sharedDAO = new SharedDAO();
			sharedDAO.rateTrip(rating, bookingID, userID);

			return Constants.SUCCESS;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring rating to DAO.");
			return Constants.FAILURE;
		}
	}

	/*
	 * Displays the completed ride details.
	 */

	/*
	 * public ArrayList<CompletedTrip> displayCompletedTrips(int userID) {
	 * 
	 * SharedDAO sharedDAO = null; ArrayList<CompletedTrip> completedTrip = null;
	 * boolean check = false;
	 * 
	 * try { sharedDAO = new SharedDAO();
	 * 
	 * check = checkBookingExists(bookingID); if(check == false) { return null; }
	 * 
	 * bookingResponse = sharedDAO.displayBookingDetails(bookingID);
	 * 
	 * return completedTrip; } catch (Exception e) { LOGGER.log(Level.SEVERE,
	 * "Error in displaying booking details from DAO."); return null; } }
	 */

}
