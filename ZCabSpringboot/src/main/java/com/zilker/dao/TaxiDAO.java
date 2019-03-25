package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.stereotype.Repository;

import com.zilker.constants.Constants;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

@Repository
public class TaxiDAO {
	
	/*
	 * Assigns the available cab to the driver.
	 */

	public void assignCabDriver(int cabID, int driverID) throws SQLException {

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */

	public void updateCabStatus(int cabID, int flag) throws SQLException{

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Inserts licence deatils of a driver.
	 */

	public void addLicenceDetails(int driverID, String licenceNumber, String zipCode)throws SQLException {

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Reads the cab ID of the available cab.
	 */

	public int getCabID() throws SQLException{
		int cabID = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_ID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				cabID = resultSet.getInt(1);
			}
			return cabID;
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Inserts route tables.
	 */

	public void insertRouteDetails(int sourceID, int destinationID, float distance)throws SQLException {

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Updates current location of driver after the ride.
	 */

	public void updateDriverLocation(String zipCode, int driverID) throws SQLException{

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Reads the zipCode corresponding to the destination ID.
	 */

	public String getZipCode(int bookingID) throws SQLException{
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
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Completes the current ride.
	 */

	public String completeRide(int bookingID, int driverID) throws SQLException{

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
			if (count > 0) {
				return Constants.SUCCESS;
			}
			return Constants.FAILURE;
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

}
