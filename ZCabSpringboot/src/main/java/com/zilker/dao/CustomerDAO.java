package com.zilker.dao;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import org.springframework.stereotype.Repository;

import com.zilker.bean.Address;
import com.zilker.bean.TravelInvoice;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

@Repository
public class CustomerDAO {
	

	/*
	 * Displays the travel locations.
	 */

	public ArrayList<Address> displayLocations()throws SQLException{

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
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves the location ID using location.
	 */

	public int findLocationID(String location, String zipCode)throws SQLException {

		int locationID = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {

			connection = DbConnect.getConnection();

			preparedStatement = connection.prepareStatement(SQLConstants.GET_ADDRESS_ID);
			preparedStatement.setString(1, location);
			preparedStatement.setString(2, zipCode);
			resultSet = preparedStatement.executeQuery();

			if (resultSet.next()) {
				locationID = resultSet.getInt(1);
			}
			return locationID;
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Retrieves the nearest driver ID.
	 */

	public int findNearestDriver(String zipCode, int flag)throws SQLException {

		int driverID = -1;

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
				driverID = resultSet.getInt(1);
			}
			return driverID;
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Retrieves the nearest cab ID.
	 */

	public int findNearestCab(int driverID)throws SQLException {

		int cabID = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_DRIVER_ID);
			preparedStatement.setInt(1, driverID);
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
	 * Reads the driver ID of the available cab.
	 */

	public int getDriverID(int cabID)throws SQLException {
		int driverID = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_BY_CAB_ID);
			preparedStatement.setInt(1, cabID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				driverID = resultSet.getInt(1);
			}
			return driverID;
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}

	/*
	 * Reads the model ID of the available cab.
	 */

	public int getModelID(int cabID)throws SQLException {
		int modelID = -1;

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_BY_CAB_ID);
			preparedStatement.setInt(1, cabID);
			resultSet = preparedStatement.executeQuery();
			if (resultSet.next()) {
				modelID = resultSet.getInt(1);
			}
			return modelID;
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}

	/*
	 * Checks if the cab with requested number of seats is available.
	 */

	public boolean isSeatExists(int modelID, int seats)throws SQLException {
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
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Inserts the ride details of the customer.
	 */

	public int insertRideDetails(TravelInvoice invoice)throws SQLException {

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
				bookingID = fetchBookingID(invoice.getCustomerID(), invoice.getDriverID(), invoice.getCabID(),
						invoice.getFormattedTime());
			}
			return bookingID;
		} catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}

	/*
	 * Retrieves the booking ID of a ride.
	 */

	public int fetchBookingID(int customerID, int driverID, int cabID, String startTime) throws SQLException{
		int bookingID = -1;

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
				bookingID = resultSet.getInt(1);
			}
			return bookingID;
		}catch (SQLException exception) {
			throw exception;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}

	}

	/*
	 * Updates the ride details of a customer.
	 */

	public void updateRideDetails(TravelInvoice invoice)throws SQLException {

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
		} catch (SQLException exception) {
			throw exception;		
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
}
