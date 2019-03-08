package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.zilker.constants.Constants;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

@Repository
public class TaxiDAO {
	

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	/*
	 * Inserts route tables.
	 */

	public void insertRouteDetails(int sourceID, int destinationID, float distance) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_ROUTE_DETAILS);
			preparedStatement.setInt(1, sourceID);
			preparedStatement.setInt(2, destinationID);
			preparedStatement.setFloat(3, distance);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting route details to DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Updates current location of driver after the ride.
	 */

	public void updateDriverLocation(String zipCode, int driverID) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_DRIVER_LOCATION);
			preparedStatement.setString(1, zipCode);
			preparedStatement.setInt(2, driverID);
			preparedStatement.setInt(3, driverID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating driver location after ride completion in DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	 * Reads the zipCode corresponding to the destination ID.
	 */

	public String getZipCode(int bookingID){
		String zipCode = "";
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_ZIP_BY_DESTINATION_ID);
			preparedStatement.setInt(1, bookingID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				zipCode = resultSet.getString(1);
			}
			return zipCode;
		} catch (NumberFormatException ne) {
			LOGGER.log(Level.WARNING, "Error in parsing details.");
			return Constants.FAILURE;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in reading destination ID from DB.");
			return Constants.FAILURE;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Assigns the available cab to the driver.
	 */

	public void assignCabDriver(int cabID, int driverID) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_CAB_DRIVER);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setInt(2, cabID);
			preparedStatement.setInt(3, driverID);
			preparedStatement.setInt(4, driverID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in assigning driver to the cab.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */

	public void updateCabStatus(int cabID, int flag) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_CAB_STATUS);
			if (flag == 0) {
				preparedStatement.setInt(1, 6);
			} else {
				preparedStatement.setInt(1, 5);
			}
			preparedStatement.setInt(2, cabID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in updating cab status in DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	

	/*
	 * Inserts licence deatils of a driver.
	 */

	public void addLicenceDetails(int driverID, String licenceNumber, String zipCode) {
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_LICENCE_DETAILS);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setString(2, licenceNumber);
			preparedStatement.setString(3, zipCode);
			preparedStatement.setInt(4, driverID);
			preparedStatement.setInt(5, driverID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting licence details to DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Reads the cab ID of the available cab.
	 */

	public int getCabID() {
		int cabID = -1;
		String test = "";
		

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_ID);
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
			LOGGER.log(Level.SEVERE, "Error in reading cab ID from DB.");
			return -1;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}


	/*
	 * Completes the current ride.
	 */

	public String completeRide(int bookingID, int driverID) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int count = -1;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_RIDE_COMPLETION);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setInt(2, bookingID);
			count = preparedStatement.executeUpdate();
			if (count>0) {
				return Constants.SUCCESS;
			}
			return Constants.FAILURE;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in reading booking ID from DB.");
			return Constants.FAILURE;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

}
