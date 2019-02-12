package com.zilker.dao;

import java.sql.Connection;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import com.zilker.bean.User;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;


public class UserDAO {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	public boolean registerUser(User user) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
			preparedStatement.setString(1, user.getName());
			preparedStatement.setString(2, user.getEmail());
			preparedStatement.setString(3, user.getContact());
			preparedStatement.setString(4, user.getRole());
			preparedStatement.setString(5, user.getPassword());
				
			preparedStatement.executeUpdate();
			
			return true;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting personal details to DB.");
			return false;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	public ArrayList<User> fetchDetails() {
		String userName = "";
		String mail = "";
		String contact = "";
		String role = "";
		String password = "";

		User object = null;
		ArrayList<User> user = null;
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			user = new ArrayList<User>();
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement("SELECT * FROM USER_DETAIL");
			resultSet = preparedStatement.executeQuery();
			while (resultSet.next()) {
				userName = resultSet.getString(2);
				mail = resultSet.getString(3);
				contact = resultSet.getString(4);
				role = resultSet.getString(5);
				password = resultSet.getString(6);
				
				object = new User(userName, mail, contact, role, password);
				user.add(object);
			}
			
			return user;
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in validating login credentials from DB.");
			return null;
		}finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
}
