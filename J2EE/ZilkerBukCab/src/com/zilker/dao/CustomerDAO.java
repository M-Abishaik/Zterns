package com.zilker.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.bean.Address;
import com.zilker.constants.SQLConstants;
import com.zilker.util.DbConnect;

public class CustomerDAO {
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	
	/*
	 * Displays the travel locations.
	 */

	public ArrayList<Address> displayLocations() {

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
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in retrieving travel locations.");
			return null;
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
}
