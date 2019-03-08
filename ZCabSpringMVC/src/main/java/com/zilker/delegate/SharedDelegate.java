package com.zilker.delegate;

import java.util.ArrayList;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Service;

import com.zilker.constants.Constants;
import com.zilker.dao.SharedDAO;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.CompleteRating;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.UpdateProfile;
import com.zilker.bean.User;


@Service
public class SharedDelegate {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
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
	 * Checks if the ride is in progress.
	 */
	
	public int checkBookingStatus(String userPhone, int flag) {
		
		SharedDAO sharedDAO = null;
		int userID = -1;
		int bookingID = -1;
		
		try {
			sharedDAO = new SharedDAO();
			userID = getUserID(userPhone);
			
			bookingID = sharedDAO.checkBookingStatus(userID, flag);
			return bookingID;
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering user contact to DAO.");
			return -1;
		}
	}
	
	/*
	 * Display completed ride details 
	 */
	
	public ArrayList<BookingResponse>displayCancelledRides(String userPhone, int flag){
		SharedDAO sharedDAO = null;
		ArrayList<BookingResponse> cancelledRides = null;
		int userID = -1;
		
		try {
			cancelledRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();
			cancelledRides = sharedDAO.displayCancelledRides(userID, flag);

			return cancelledRides;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}
	
	/*
	 * Get ride details by booking ID.
	 */
	
	public PostConfirm getBookingDetails(int bookingID) {
		
		PostConfirm postConfirm = null;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			postConfirm = sharedDAO.getBookingDetails(bookingID);
			
			return postConfirm;
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering booking ID to DAO.");
			return null;
		}
		
	}
	
	/*
	 * Finds the street address corresponding to the location ID.
	 */
	
	public String findLocation(int locationID) {
		
		String location = "";
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			location = sharedDAO.findLocation(locationID);

			return location;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering location ID to DAO.");
			return Constants.FAILURE;
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
	 * Cancels the ride of a customer.
	 */

	public boolean cancelRide(int bookingID) {

		int driverID = -1;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();

			driverID = sharedDAO.cancelRide(bookingID);
			System.out.println(driverID);

			updateDriverStatus(driverID, 1);

			return true;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring booking ID for cancellation to DAO.");
			return false;
		}
	}
	
	/*
	 * Rates a trip.
	 */

	public String rateTrip(float rating, int bookingID, String userPhone) {

		SharedDAO sharedDAO = null;
		int userID = -1;

		try {

			sharedDAO = new SharedDAO();
			
			userID = getUserID(userPhone);
			
			sharedDAO.rateTrip(rating, bookingID, userID);

			return Constants.SUCCESS;

		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transferring rating to DAO.");
			return Constants.FAILURE;
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
	 * Display completed ride details 
	 */
	
	public ArrayList<BookingResponse> displayCompletedRides(String userPhone, int flag){
		SharedDAO sharedDAO = null;
		ArrayList<BookingResponse> completedRides = null;
		int userID = -1;
		
		try {
			completedRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();
			completedRides = sharedDAO.displayCompletedRides(userID, flag);

			return completedRides;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}
	
	/*
	 * Display completed ride details 
	 */
	
	public ArrayList<CompleteRating> displayCompletedRatedRides(String userPhone, int flag){
		SharedDAO sharedDAO = null;
		ArrayList<CompleteRating> completedRides = null;
		int userID = -1;
		
		try {
			completedRides = new ArrayList<CompleteRating>();
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();
			completedRides = sharedDAO.displayCompletedRatedRides(userID, flag);

			return completedRides;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying ride history.");
			return null;
		}
	}
	
	/*
	 * Displays on-going ride details.
	 */

	public BookingResponse displayBookingDetails(String userPhone, int flag) {

		int userID = -1;
		
		BookingResponse bookingResponse = null;
		SharedDAO sharedDAO = null;

		try {
			userID = getUserID(userPhone);
			sharedDAO = new SharedDAO();

			bookingResponse = sharedDAO.displayBookingDetails(userID, flag);

			return bookingResponse;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in displaying booking details from DAO.");
			return null;
		}
	}
	
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
	 * Checks if the booking ID exists.
	 */
	
	public boolean checkBookingExists(int bookingID, int driverID) {
		
		boolean check = false;
		SharedDAO sharedDAO = null;

		try {
			sharedDAO = new SharedDAO();
			check = sharedDAO.checkBookingExists(bookingID, driverID);

			return check;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in transfering booking ID to DAO.");
			return false;
		}
	}
	
}
