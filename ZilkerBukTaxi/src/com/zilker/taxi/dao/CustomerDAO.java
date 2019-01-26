package com.zilker.taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

public class CustomerDAO {
	
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	/*
	 * Checks if the booking ID of a ride exists.
	 */
	
	public int checkBookingExists(int bID) {
		
		int bookingID = -1;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_BOOKING_EXISTS);
			preparedStatement.setInt(1, bID);
		    resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 bookingID = 1;
		    }
		    return bookingID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if booking ID exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Inserts the profile of customer. 
	 */
	
	public void insertPersonalDetails(Customer customer) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getMailId());
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting personal details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Displays the customer profile. 
	 */
	
	public Customer displayProfile(String email) {
		
		String firstName = "", lastName = "", mail = "";
		Customer customer = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_PROFILE);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
					firstName = resultSet.getString(2); 
					lastName = resultSet.getString(3);
					mail = resultSet.getString(4);
		      }
		      
		      customer = new Customer(firstName, lastName, mail);
		      return customer;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in retrieving profile details from the DB.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	
	
	/*
	 * Updates customer profile. 
	 */
	
	public void updatePersonalDetails(Customer customer) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_PROFILE);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getMailId());
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating personal details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
}
