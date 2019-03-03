package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.stereotype.Repository;

import com.zilker.bean.Login;
import com.zilker.bean.User;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

@Repository
public class UserDAO {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	public void register(User user) {
		
		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
			preparedStatement.setString(1, user.getUsername());
			preparedStatement.setString(2, user.getEmail());
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
	
	public String login(Login login) {
		
			String role = "";
			
			Connection connection = null;
			PreparedStatement preparedStatement = null;
			ResultSet resultSet = null;

			try {
				connection = DbConnect.getConnection();
				preparedStatement = connection.prepareStatement(SQLConstants.CHECK_LOGIN);
				preparedStatement.setString(1, login.getContact());
				preparedStatement.setString(2, login.getPassword());
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
}
