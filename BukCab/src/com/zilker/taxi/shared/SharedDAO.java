package com.zilker.taxi.shared;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

public class SharedDAO {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	

	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;

	

/*
 * Checks if the contact of a user already exists.
 */

public boolean checkContactExists(String contact) {
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.CHECK_CONTACT_EXISTS);
		preparedStatement.setString(1, contact);
		resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	    	 return true;
	    } 
	    return false;
	} catch (NumberFormatException ne) {
	    LOGGER.log(Level.INFO, "Error in parsing details.");
	    return false;
	}catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in checking if contact exists from DB.");
	    return false;
	}finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	}
}

/*
 * Creates a user account
 */

public void createAccount(User user) {
		
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
	    LOGGER.log(Level.INFO, "Error in inserting personal details to DB.");
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
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.GET_USER_ID);
		preparedStatement.setString(1, contact);
		resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	    	 test = resultSet.getString(1);
   		 userID= Integer.parseInt(test);
	    }
	    return userID;
	}catch (NumberFormatException ne) {
	    LOGGER.log(Level.INFO, "Error in parsing details.");
	    return -1;
	} catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in retrieving user ID from DB.");
	    return -1;
	} finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	}
}

/*
 * Updates the update timestamp of customer. 
 */

public void updateAccount(int userID, String contact) {
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_USER_ID);
	    preparedStatement.setInt(1, userID);
	    preparedStatement.setInt(2, userID);
	    preparedStatement.setString(3, contact);
	    preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in updating user record in DB.");
	    } finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	    }
}


/*
 * Creates an address record for the customer.
 */

public void createUserAddress(User user, int userID) {
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.INSERT_USER_ADDRESS);
	    preparedStatement.setInt(1, userID);
	    preparedStatement.setString(2, user.getAddress());
	    preparedStatement.setString(3, user.getCity());
	    preparedStatement.setString(4, user.getZipCode());
	    preparedStatement.setInt(5, userID);
	    preparedStatement.setInt(6, userID);
	    preparedStatement.executeUpdate();
	    } catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in inserting customer address record into DB.");
	    } finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	    }
}

/*
 * Login the user.
 */

public String login(String phone, String password) {
	String role = "";
	
	try {
		connection = DbConnect.getConnection();
		preparedStatement = connection.prepareStatement(SQLConstants.CHECK_LOGIN);
		preparedStatement.setString(1, phone);
		preparedStatement.setString(2, password);
		resultSet = preparedStatement.executeQuery();
	    if(resultSet.next()) {
	    	 role = resultSet.getString(1);
	    } 
	    return role;
	} catch (SQLException e) {
	    LOGGER.log(Level.INFO, "Error in validating login credentials from DB.");
	    return role;
	}finally {
	    DbConnect.closeConnection(connection, preparedStatement, resultSet);
	}
}


}
