package com.zilker.delegate;

import java.sql.SQLException;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zilker.bean.BookingResponse;
import com.zilker.bean.CompleteRating;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.User;
import com.zilker.bean.UpdateProfile;
import com.zilker.constants.Constants;
import com.zilker.constants.ErrorConstants;
import com.zilker.customexception.ApplicationException;
import com.zilker.customexception.ContactAlreadyExistsException;
import com.zilker.customexception.UserNotFoundException;
import com.zilker.dao.SharedDAO;

@Service
public class SharedDelegate {
	
	@Autowired
	SharedDAO sharedDAO;
	
	/*
	 * Passes user registration information to the DAO.
	 */

	public String register(User user) throws ApplicationException{
		int userID = -1;

		try {
			sharedDAO.createAccount(user);
			userID = getUserID(user.getContact());
			
			sharedDAO.updateAccount(userID, user.getContact());	
			sharedDAO.createUserAddress(user, userID);
			
			return Constants.SUCCESS;
		
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Retrieves the User ID.
	 */

	public int getUserID(String contact)throws ApplicationException {

		int userID = -1;

		try {
			userID = sharedDAO.getUserID(contact);

			return userID;

		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Checks if the contact of a customer already exists.
	 */

	public boolean isContactExists(String contact) throws ApplicationException{

		boolean contactExists = false;

		try {
			contactExists = sharedDAO.checkContactExists(contact);

			if(contactExists==false) {
				return contactExists;
			}
			
			throw new ContactAlreadyExistsException();
			
		} catch(ContactAlreadyExistsException contactAlreadyExistsException) {
			throw contactAlreadyExistsException;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Fetch personal details of user
	 */

	public User displayProfile(String userPhone)throws ApplicationException {
		User user = null;

		try {
			user = sharedDAO.displayProfile(userPhone);

			return user;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Checks if the login credentials are proper.
	 */

	public String login(String phone, String password)throws ApplicationException {
		String role = "";

		try {
			role = sharedDAO.login(phone, password);
			if (!role.equals("")) {
				return role;
			}

			//return Constants.FAILURE;
			throw new UserNotFoundException();

		}catch(UserNotFoundException userNotFoundException) {
			throw userNotFoundException;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}

	
	/*
	 * Get ride details by booking ID.
	 */

	public PostConfirm getBookingDetails(int bookingID)throws ApplicationException {

		PostConfirm postConfirm = null;

		try {
			postConfirm = sharedDAO.getBookingDetails(bookingID);

			return postConfirm;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}	
	}
	
	/*
	 * Checks if the ride is in progress.
	 */

	public int checkBookingStatus(String userPhone, int flag)throws ApplicationException {

		int userID = -1;
		int bookingID = -1;

		try {
			userID = getUserID(userPhone);

			bookingID = sharedDAO.checkBookingStatus(userID, flag);
			return bookingID;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}

	/*
	 * Display completed ride details
	 */

	public ArrayList<BookingResponse> displayCancelledRides(String userPhone, int flag)throws ApplicationException {
		ArrayList<BookingResponse> cancelledRides = null;
		int userID = -1;

		try {
			cancelledRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			cancelledRides = sharedDAO.displayCancelledRides(userID, flag);

			return cancelledRides;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Finds cab details by ID.
	 */

	public String findCabByID(int cabID) throws ApplicationException{

		String cab = "";

		try {
			cab = sharedDAO.findCabByID(cabID);

			return cab;

		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}	
	}

	/*
	 * Finds cab details by ID.
	 */

	public String findDriverByID(int driverID) throws ApplicationException{

		String driver = "";

		try {
			driver = sharedDAO.findDriverByID(driverID);

			return driver;

		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Finds the street address corresponding to the location ID.
	 */

	public String findLocation(int locationID)throws ApplicationException {

		String location = "";

		try {
			location = sharedDAO.findLocation(locationID);

			return location;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	

	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */

	public void updateDriverStatus(int driverID, int flag) throws ApplicationException{

		try {
			sharedDAO.updateDriverStatus(driverID, flag);

		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Cancels the ride of a customer.
	 */

	public boolean cancelRide(int bookingID)throws ApplicationException {

		int driverID = -1;

		try {

			driverID = sharedDAO.cancelRide(bookingID);

			updateDriverStatus(driverID, 1);

			return true;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Display completed ride details
	 */

	public ArrayList<BookingResponse> displayCompletedRides(String userPhone, int flag)throws ApplicationException {
		ArrayList<BookingResponse> completedRides = null;
		int userID = -1;

		try {
			completedRides = new ArrayList<BookingResponse>();
			userID = getUserID(userPhone);
			completedRides = sharedDAO.displayCompletedRides(userID, flag);

			return completedRides;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Checks if the booking ID exists.
	 */

	public boolean checkBookingExists(int bookingID, int driverID)throws ApplicationException {

		boolean check = false;

		try {
			check = sharedDAO.checkBookingExists(bookingID, driverID);

			return check;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Rates a trip.
	 */

	public String rateTrip(int rating, int bookingID, String userPhone)throws ApplicationException {

		int userID = -1;

		try {

			userID = getUserID(userPhone);

			sharedDAO.rateTrip(rating, bookingID, userID);

			return Constants.SUCCESS;

		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}

	/*
	 * Displays on-going ride details.
	 */

	public BookingResponse displayBookingDetails(String userPhone, int flag) throws ApplicationException{

		int userID = -1;

		BookingResponse bookingResponse = null;

		try {
			userID = getUserID(userPhone);
			System.out.println(userPhone + " " + userID);
			bookingResponse = sharedDAO.displayBookingDetails(userID, flag);

			return bookingResponse;
		}catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Display completed ride details
	 */

	public ArrayList<CompleteRating> displayCompletedRatedRides(String userPhone, int flag)throws ApplicationException {
		ArrayList<CompleteRating> completedRides = null;
		int userID = -1;

		try {
			completedRides = new ArrayList<CompleteRating>();
			userID = getUserID(userPhone);
			completedRides = sharedDAO.displayCompletedRatedRides(userID, flag);

			return completedRides;
		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
	
	/*
	 * Updates customer profile.
	 */

	public String updateProfile(UpdateProfile updateProfile)throws ApplicationException {

		try {
			sharedDAO.updateProfile(updateProfile);

			return Constants.SUCCESS;

		} catch (SQLException SQLexception) {
			throw new ApplicationException(ErrorConstants.SQL_ERROR_CODE,ErrorConstants.SQL_ERROR_MESSAGE);
		}catch (Exception exception) {
			throw new ApplicationException(ErrorConstants.GERNERIC_ERROR_CODE,ErrorConstants.GERNERIC_ERROR_MESSAGE);
		}
	}
}
