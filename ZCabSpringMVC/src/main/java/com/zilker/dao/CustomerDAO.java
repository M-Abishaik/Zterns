package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.zilker.bean.Address;
import com.zilker.bean.TravelInvoice;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

@Repository
public class CustomerDAO {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	/*
	 * Displays the travel locations.
	 */

	public ArrayList<Address> displayLocations() {

		String streetAddress = "";
		String zipCode = "";
		ArrayList<Address> address = null;
		Address object = null;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			address = new ArrayList<Address>();
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_LOCATIONS);
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				streetAddress = resultSet.getString(1);
				zipCode = resultSet.getString(2);

				object = new Address(streetAddress, zipCode);
				address.add(object);

			}
			return address;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving travel locations.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the location ID using location.
	 */

	public int findLocationID(String location, String zipCode) {

		int locationID = -1;
		String test = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			
			connection = DbConnect.getConnection();
		
			preparedStatement = connection.prepareStatement(SQLConstants.GET_ADDRESS_ID);
			preparedStatement.setString(1,location);
			preparedStatement.setString(2,zipCode);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				test = resultSet.getString(1);
				locationID = Integer.parseInt(test);
			}
			return locationID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving location ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the nearest driver ID.
	 */

	public int findNearestDriver(String zipCode, int flag) {

		int driverID = -1;
		String test = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_ID);
			if (flag == 0) {
				preparedStatement.setInt(1, 5);
			} else {
				preparedStatement.setInt(1, 6);
			}
			preparedStatement.setString(2, zipCode);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				driverID = Integer.parseInt(test);
			}
			return driverID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving driver ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the nearest cab ID.
	 */

	public int findNearestCab(int driverID) {

		int cabID = -1;
		String test = "";

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_DRIVER_ID);
			preparedStatement.setInt(1, driverID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				cabID = Integer.parseInt(test);
			}
			return cabID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving cab ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Reads the driver ID of the available cab.
	 */

	public int getDriverID(int cabID) {
		int driverID = -1;
		String test = "";


		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_BY_CAB_ID);
			preparedStatement.setInt(1, cabID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				driverID = Integer.parseInt(test);
			}
			return driverID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in reading driver ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}
	
	/*
	 * Reads the model ID of the available cab.
	 */

	public int getModelID(int cabID) {
		int modelID = -1;
		String test = "";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_BY_CAB_ID);
			preparedStatement.setInt(1, cabID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				modelID = Integer.parseInt(test);
			}
			return modelID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in reading model ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}
	
	/*
	 * Checks if the cab with requested number of seats is available.
	 */

	public boolean isSeatExists(int modelID, int seats) {
		int numSeats = -1;
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_SEATS);
			preparedStatement.setInt(1, modelID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				numSeats = resultSet.getInt(1);
			}

			if (numSeats >= seats) {
				return true;
			} else {
				return false;
			}
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return false;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in checking the number of seats from DB.");
			return false;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Inserts the ride details of the customer.
	 */

	public int insertRideDetails(TravelInvoice invoice) {

		int bookingID = -1;
		int count = 0;
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_RIDE_DETAILS);
			preparedStatement.setInt(1, invoice.getCustomerID());
			preparedStatement.setInt(2, invoice.getDriverID());
			preparedStatement.setString(3, invoice.getFormattedTime());
			preparedStatement.setInt(4, invoice.getSourceID());
			preparedStatement.setInt(5, invoice.getDestinationID());
			preparedStatement.setInt(6, invoice.getCabID());
			preparedStatement.setFloat(7, invoice.getPrice());
			preparedStatement.setInt(8, invoice.getCustomerID());
			preparedStatement.setInt(9, invoice.getCustomerID());

			count = preparedStatement.executeUpdate();

			if (count > 0) {
				bookingID = fetchBookingID(invoice.getCustomerID(), invoice.getDriverID(), invoice.getCabID(), invoice.getFormattedTime());
			}
			return bookingID;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting ride details to DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}
	
	/*
	 * Retrieves the booking ID of a ride.
	 */

	public int fetchBookingID(int customerID, int driverID, int cabID, String startTime) {
		int bookingID = -1;
		String test = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_BOOKING_ID);
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, driverID);
			preparedStatement.setInt(3, cabID);
			preparedStatement.setString(4, startTime);
			
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				test = resultSet.getString(1);
				bookingID = Integer.parseInt(test);
			}
			return bookingID;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return -1;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving booking ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}
	
	/*
	 * Updates the ride details of a customer.
	 */

	public void updateRideDetails(TravelInvoice invoice) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_RIDE_DETAILS);
			preparedStatement.setInt(1, invoice.getDriverID());
			preparedStatement.setString(2, invoice.getFormattedTime());
			preparedStatement.setInt(3, invoice.getSourceID());
			preparedStatement.setInt(4, invoice.getDestinationID());
			preparedStatement.setInt(5, invoice.getCabID());
			preparedStatement.setFloat(6, invoice.getPrice());
			preparedStatement.setInt(7, invoice.getCustomerID());
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating ride details.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}


}
