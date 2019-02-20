package com.zilker.delegate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.constants.Constants;
import com.zilker.dao.SharedDAO;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.UpdateProfile;
import com.zilker.bean.User;


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
			LOGGER.log(Level.WARNING, "Error in transfering user details to Shared DAO.");
			return Constants.FAILURE;
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
			LOGGER.log(Level.WARNING, "Error in transfering login details to Shared DAO.");
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
			LOGGER.log(Level.WARNING, "Error in transfering contact to Shared DAO.");
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
			LOGGER.log(Level.WARNING, "Error in transfering user contact to Shared DAO.");
			return -1;
		}
	}
	
	/*
	 * Fetch personal details of user
	 */
	
	public User displayProfile(String userPhone) {
		User user = null;
		SharedDAO sharedDAO = null;
		
		try {
			sharedDAO = new SharedDAO();
			user = sharedDAO.displayProfile(userPhone);
		
		return user;
	}catch (Exception exception) {
		LOGGER.log(Level.WARNING, "Error in fetching user profile from Shared DAO.");
		return null;
	}
	}
	
	/*
	 * Updates customer profile.
	 */

	public String updateProfile(UpdateProfile updateProfile) {

		SharedDAO sharedDAO = null;

		try {

			sharedDAO = new SharedDAO();
			sharedDAO.updateProfile(updateProfile);

			return Constants.SUCCESS;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring customer profile to DAO.");
			return Constants.FAILURE;
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
	 * Displays on-going ride details.
	 */

	public BookingResponse displayBookingDetails(String userPhone) {

		int userID = -1;
		
		BookingResponse bookingResponse = null;
		SharedDAO sharedDAO = null;
		boolean check = false;

		try {
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();

			/*check = checkBookingExists(userID);
			if (check == false) {
				return null;
			}*/

			bookingResponse = sharedDAO.displayBookingDetails(userID);

			return bookingResponse;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying booking details from DAO.");
			return null;
		}
	}
	
	/*
	 * Display completed ride details 
	 */
	
	public ArrayList<BookingResponse> displayCompletedRides(String userPhone){
		SharedDAO sharedDAO = null;
		ArrayList<BookingResponse> completedRides = null;
		int userID = -1;
		
		try {
			completedRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();
			completedRides = sharedDAO.displayCompletedRides(userID);

			return completedRides;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}
	
	/*
	 * Display completed ride details 
	 */
	
	public ArrayList<BookingResponse>displayCancelledRides(String userPhone){
		SharedDAO sharedDAO = null;
		ArrayList<BookingResponse> cancelledRides = null;
		int userID = -1;
		
		try {
			cancelledRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();
			cancelledRides = sharedDAO.displayCancelledRides(userID);

			return cancelledRides;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}
}
