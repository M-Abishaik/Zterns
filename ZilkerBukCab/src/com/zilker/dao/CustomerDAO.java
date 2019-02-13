package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.bean.Address;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

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

}
