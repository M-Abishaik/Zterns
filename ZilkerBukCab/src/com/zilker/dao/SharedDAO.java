package com.zilker.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.constants.SQLConstants;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.CompleteRating;
import com.zilker.bean.UpdateProfile;
import com.zilker.bean.User;
import com.zilker.util.DbConnect;

public class SharedDAO {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	/*
	 * Login the user.
	 */

	public String login(String phone, String password) {
		String role = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_LOGIN);
			preparedStatement.setString(1, phone);
			preparedStatement.setString(2, password);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				role = resultSet.getString(1);
			}
			return role;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in validating login credentials from DB.");
			return role;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Checks if the contact of a user already exists.
	 */

	public boolean checkContactExists(String contact) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_CONTACT_EXISTS);
			preparedStatement.setString(1, contact);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				return true;
			}
			return false;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return false;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in checking if contact exists from DB.");
			return false;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the user ID.
	 */

	public int getUserID(String contact) {

		int userID = -1;
		String test = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_USER_ID);
			preparedStatement.setString(1, contact);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				userID = Integer.parseInt(test);
			}
			return userID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving user ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Creates a user account
	 */

	public void createAccount(User user) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
			preparedStatement.setString(1, user.getUserName());
			preparedStatement.setString(2, user.getMail());
			preparedStatement.setString(3, user.getContact());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.setString(5, user.getPassword());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting personal details to DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Updates the update timestamp of customer.
	 */

	public void updateAccount(int userID, String contact) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_USER_ID);
			preparedStatement.setInt(1, userID);
			preparedStatement.setInt(2, userID);
			preparedStatement.setString(3, contact);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating user record in DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Creates an address record for the customer.
	 */

	public void createUserAddress(User user, int userID) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_USER_ADDRESS);
			preparedStatement.setInt(1, userID);
			preparedStatement.setString(2, user.getAddress());
			preparedStatement.setString(3, "chennai");
			preparedStatement.setString(4, user.getZipCode());
			preparedStatement.setInt(5, userID);
			preparedStatement.setInt(6, userID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting customer address record into DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Displays the user profile.
	 */

	public User displayProfile(String userPhone) {

		String userName = "";
		String mail = "";
		String contact = "";
		String password = "";
		String address = "";
		String city = "";
		String zipCode = "";

		User user = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection
					.prepareStatement("SELECT USERNAME, MAIL, CONTACT, PASS, STREET_ADDRESS, CITY, ZIP_CODE "
							+ "FROM USER_DETAIL, ADDRESS_DETAIL WHERE ADDRESS_DETAIL.USER_ID=USER_DETAIL.USER_ID AND CONTACT=\""
							+ userPhone + "\"");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString(1);
				mail = resultSet.getString(2);
				contact = resultSet.getString(3);
				password = resultSet.getString(4);
				address = resultSet.getString(5);
				city = resultSet.getString(6);
				zipCode = resultSet.getString(7);
			}

			user = new User(userName, mail, contact, city, password, address, zipCode);
			return user;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in  retrieving profile details from DB.");
			return null;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in retrieving profile details from the DB.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Updates customer profile.
	 */

	public void updateProfile(UpdateProfile updateProfile) {


		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int userID = -1;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_PROFILE);
			preparedStatement.setString(1, updateProfile.getEmail());
			preparedStatement.setString(2,updateProfile.getPassword());
			preparedStatement.setString(3, updateProfile.getPhone());
			preparedStatement.executeUpdate();
			
			userID = getUserID(updateProfile.getPhone());
			
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_USER_ADDRESS);
			preparedStatement.setString(1, updateProfile.getAddress());
			preparedStatement.setString(2, "chennai");
			preparedStatement.setString(3, updateProfile.getZipCode());
			preparedStatement.setInt(4, userID);
			preparedStatement.executeUpdate();
				
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating personal details.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Retrieves the driver details for the ride.
	 */

	public String findDriverByID(int driverID) {
		String driverName = "";
		String driverContact = "";
		String driver = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_BY_ID);
			preparedStatement.setInt(1, driverID);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				driverName = resultSet.getString(1);
				driverContact = resultSet.getString(2);

				driver = driverName + "- " + driverContact;
			}
			return driver;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return "";
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving driver details from DB.");
			return "";
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}

	/*
	 * Retrieves the cab details for a ride.
	 */

	public String findCabByID(int cabID) {
		String cabModel = "";
		String cabDescription = "";
		String cab = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_BY_ID);
			preparedStatement.setInt(1, cabID);

			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				cabModel = resultSet.getString(1);
				cabDescription = resultSet.getString(2);

				cab = cabModel + "- " + cabDescription;
			}
			return cab;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return "";
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving cab details from DB.");
			return "";
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Updates driver status and location after completion of the ride.
	 */
	
	public void updateDriverLocation(int driverID, int flag) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_DRIVER_STATUS);
			if (flag == 0) {
				preparedStatement.setInt(1, 6);
			} else {
				preparedStatement.setInt(1, 5);
			}
			preparedStatement.setInt(2, driverID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating driver status.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */

	public void updateDriverStatus(int driverID, int flag) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_DRIVER_STATUS);
			if (flag == 0) {
				preparedStatement.setInt(1, 6);
			} else {
				preparedStatement.setInt(1, 5);
			}
			preparedStatement.setInt(2, driverID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating driver status.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Checks if the ride is in progress.
	 */
	
	public boolean checkBookingStatus(int userID, int flag) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DbConnect.getConnection();
			if(flag==0) {
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CUSTOMER_BOOKING_STATUS);
			}else {
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_BOOKING_STATUS);
			}
			preparedStatement.setInt(1, userID);
			resultSet = preparedStatement.executeQuery();
			
			if (resultSet.next()) {
				return true;
			}
			return false;
			
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in fetching booking status from DB.");
			return false;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
	
	/*
	 * Displays the ride details of a customer.
	 */

	public BookingResponse displayBookingDetails(int userID, int flag) {

		int driverID = -1;
		String startTime = "";
		float price = 0.0f;
		String source = "";
		String destination = "";
		String driver = "";
		String cab = "";
		int sourceID = -1;
		int destinationID = -1;
		int cabID = -1;
		int bookingID = -1;
		BookingResponse bookingResponse = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			
			if(flag==0) {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_CUSTOMER_BOOKING_DETAILS);
			} else {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_DRIVER_BOOKING_DETAILS);
			}
			preparedStatement.setInt(1, userID);
			
			preparedStatement.setInt(2, 1);	
			
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				bookingID = resultSet.getInt(1);
				driverID = resultSet.getInt(2);
				startTime = resultSet.getString(3);
				sourceID = resultSet.getInt(4);
				destinationID = resultSet.getInt(5);
				cabID = resultSet.getInt(6);
				price = resultSet.getFloat(7);
			}

			source = findLocation(sourceID);
			destination = findLocation(destinationID);

			driver = findDriverByID(driverID);
			cab = findCabByID(cabID);

			bookingResponse = new BookingResponse(bookingID, driver, cab, source, destination, startTime, price);

			return bookingResponse;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Displays the completed Ride history
	 */
	
	public ArrayList<BookingResponse> displayCompletedRides(int userID, int flag){
		int driverID = -1;
		String startTime = "";
		float price = 0.0f;
		String source = "";
		String destination = "";
		String driver = "";
		String cab = "";
		int sourceID = -1;
		int destinationID = -1;
		int cabID = -1;
		int bookingID = -1;
		ArrayList<BookingResponse> completedRides = null;
		BookingResponse bookingResponse = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			completedRides = new ArrayList<BookingResponse>();
			
			connection = DbConnect.getConnection();
			if(flag==0) {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_CUSTOMER_BOOKING_DETAILS);
			}else {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_DRIVER_BOOKING_DETAILS);
			}
			preparedStatement.setInt(1, userID);
			
			preparedStatement.setInt(2, 3);	
			
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				bookingID = resultSet.getInt(1);
				driverID = resultSet.getInt(2);
				startTime = resultSet.getString(3);
				sourceID = resultSet.getInt(4);
				destinationID = resultSet.getInt(5);
				cabID = resultSet.getInt(6);
				price = resultSet.getFloat(7);
				
				source = findLocation(sourceID);
				destination = findLocation(destinationID);

				driver = findDriverByID(driverID);
				cab = findCabByID(cabID);
				
				bookingResponse = new BookingResponse(bookingID, driver, cab, source, destination, startTime, price);
				completedRides.add(bookingResponse);
			}

			return completedRides;
			
		}catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Displays the completed rated Ride history
	 */
	
	public ArrayList<CompleteRating> displayCompletedRatedRides(int userID){
		int driverID = -1;
		String startTime = "";
		float price = 0.0f;
		String source = "";
		String destination = "";
		String driver = "";
		String cab = "";
		int sourceID = -1;
		int destinationID = -1;
		int cabID = -1;
		int bookingID = -1;
		float rating = -1f;
		ArrayList<CompleteRating> completedRides = null;
		CompleteRating bookingResponse = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			completedRides = new ArrayList<CompleteRating>();
			
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_COMPLETED_RATED_RIDES);
			preparedStatement.setInt(1, userID);
			
			preparedStatement.setInt(2, 3);	
			
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				bookingID = resultSet.getInt(1);
				driverID = resultSet.getInt(2);
				startTime = resultSet.getString(3);
				sourceID = resultSet.getInt(4);
				destinationID = resultSet.getInt(5);
				cabID = resultSet.getInt(6);
				price = resultSet.getFloat(7);
				rating = resultSet.getFloat(8);
				
				source = findLocation(sourceID);
				destination = findLocation(destinationID);

				driver = findDriverByID(driverID);
				cab = findCabByID(cabID);
								
				bookingResponse = new CompleteRating(bookingID, driver, cab, source, destination, startTime, price, rating);
				completedRides.add(bookingResponse);
			}

			return completedRides;
			
		}catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Displays the cancelled Ride history
	 */
	
	public ArrayList<BookingResponse> displayCancelledRides(int userID, int flag){
		int driverID = -1;
		String startTime = "";
		float price = 0.0f;
		String source = "";
		String destination = "";
		String driver = "";
		String cab = "";
		int sourceID = -1;
		int destinationID = -1;
		int cabID = -1;
		int bookingID = -1;
		ArrayList<BookingResponse> cancelledRides = null;
		BookingResponse bookingResponse = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			cancelledRides = new ArrayList<BookingResponse>();
			
			connection = DbConnect.getConnection();
			if(flag==0) {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_CUSTOMER_BOOKING_DETAILS);
			}else {
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_DRIVER_BOOKING_DETAILS);
			}
			preparedStatement.setInt(1, userID);
			
			preparedStatement.setInt(2, 4);	
			
			resultSet = preparedStatement.executeQuery();

			while(resultSet.next()) {
				bookingID = resultSet.getInt(1);
				driverID = resultSet.getInt(2);
				startTime = resultSet.getString(3);
				sourceID = resultSet.getInt(4);
				destinationID = resultSet.getInt(5);
				cabID = resultSet.getInt(6);
				price = resultSet.getFloat(7);
				
				source = findLocation(sourceID);
				destination = findLocation(destinationID);

				driver = findDriverByID(driverID);
				cab = findCabByID(cabID);
				
				bookingResponse = new BookingResponse(bookingID, driver, cab, source, destination, startTime, price);
				cancelledRides.add(bookingResponse);
			}

			return cancelledRides;
			
		}catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Retrieves the location using location ID.
	 */

	public String findLocation(int locationID) {

		String streetAddress = "";
		String zipCode = "";
		String location = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_LOCATION);
			preparedStatement.setInt(1, locationID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				streetAddress = resultSet.getString(1);
				zipCode = resultSet.getString(2);

				location = streetAddress + ", " + zipCode;
			}
			return location;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in finding address from DB.");
			return "";
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Cancels the ride of a customer.
	 */

	public int cancelRide(int bookingID) {

		int driverID = 0;
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_BY_BOOKING_ID);
			preparedStatement.setInt(1, bookingID);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				driverID = resultSet.getInt(1);
			}

			preparedStatement = connection.prepareStatement(SQLConstants.CANCEL_RIDE);
			preparedStatement.setInt(1, 4);
			preparedStatement.setInt(2, bookingID);
			preparedStatement.executeUpdate();

			return driverID;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Rates a trip.
	 */

	public void rateTrip(float rating, int bookingID, int userID) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_TRIP_RATING);
			preparedStatement.setInt(1, bookingID);
			preparedStatement.setFloat(2, rating);
			preparedStatement.setInt(3, userID);
			preparedStatement.setInt(4, userID);

			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting rating for a ride into DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the booking ID of a ride.
	 */

	public boolean checkBookingExists(int bookingID, int driverID) {

		boolean check = false;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_BOOKING_EXISTS);
			preparedStatement.setInt(1, bookingID);
			preparedStatement.setInt(2, driverID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				check = true;
			}
			return check;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in checking if booking ID exists from DB.");
			return check;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
}

