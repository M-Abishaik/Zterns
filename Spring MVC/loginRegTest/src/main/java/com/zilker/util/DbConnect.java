package com.zilker.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.constants.Constants;

/*
 * Handles the connection information with the database. 
 */

public class DbConnect {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	public DbConnect() {
	}

	/*
	 * Opens the database connection.
	 */

	public static Connection getConnection() {
		Connection connnection = null;
		try {
			connnection = DriverManager.getConnection(Constants.CONNECTION, Constants.USERNAME, Constants.PASSWORD);
		} catch (SQLException sqlException) {
			LOGGER.log(Level.SEVERE, "Error in connecting with driver.");
		}
		return connnection;
	}

	/*
	 * Closes the database connection.
	 */

	public static void closeConnection(Connection connection, PreparedStatement preparedStatement,
			ResultSet resultSet) {
		try {
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (SQLException sqlException) {
			LOGGER.log(Level.SEVERE, "Error in closing the connection.");
		}
	}

}
