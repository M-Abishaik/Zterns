package com.zilker.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.constants.SQLConstants;
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


}
