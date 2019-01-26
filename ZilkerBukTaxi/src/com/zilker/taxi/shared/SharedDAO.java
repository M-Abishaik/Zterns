package com.zilker.taxi.shared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

public class SharedDAO {
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	

private Connection connection = null;
private PreparedStatement preparedStatement = null;
private ResultSet resultSet = null;

/*
 * Checks if the email of a customer already exists.
 */

public int checkMailExists(String mail) {
	
	int customerID = -1;
	String test = "";
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.CHECK_MAIL_EXISTS);
		preparedStatement.setString(1, mail);
		resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	    	 test = resultSet.getString(1);
    		 customerID= Integer.parseInt(test);
	    }
	    return customerID;
	} catch (NumberFormatException ne) {
	    LOGGER.log(Level.INFO, "Error in parsing details.");
	    return -1;
	}catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in checking if email exists.");
	    return -1;
	}finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	}
}

/*
 *	Removes customer account and its corresponding ride details. 
 */

public void deleteAccount(String mail, int  customerID) {
	
	int count = 0;
	
	try {
		connection = DbConnect.getConnection();
	      
		preparedStatement = connection.prepareStatement(SQLConstants.DELETE_PROFILE);
		preparedStatement.setInt(1, customerID);
		preparedStatement.executeUpdate();
	      if(count>0) {
		      LOGGER.log(Level.INFO, "Profile deleted successfully.");
	      }
	      
	      count = 0;
	      
	      preparedStatement = connection.prepareStatement(SQLConstants.DELETE_CUSTOMER_RIDES);
	      preparedStatement.setInt(1, customerID);
	      count = preparedStatement.executeUpdate();
	      
	      if(count>0) {
		      LOGGER.log(Level.INFO, "Rides deleted successfully.");
	      }
	      		      
	    } catch (SQLException e) {
	    	LOGGER.log(Level.INFO, "Error in deleting details.");
	    } finally {
	      DbConnect.closeConnection(connection, preparedStatement, resultSet);
	    }
}
	
	
	
	
}
