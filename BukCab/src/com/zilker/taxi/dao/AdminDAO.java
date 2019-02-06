package com.zilker.taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

/*
 * Describes the admin functionalities.
 */
public class AdminDAO {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);


	/*
	 * Inserts cab model details.
	 */

	public void insertCabModel(int adminID, CabModel cabModel) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_CAB_MODEL_DETAILS);
			preparedStatement.setString(1, cabModel.getModelName());
			preparedStatement.setString(2, cabModel.getModelDescription());
			preparedStatement.setInt(3, cabModel.getNumberSeats());
			preparedStatement.setInt(4, adminID);
			preparedStatement.setInt(5, adminID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting cab model details to DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

	/*
	 * Launches the cab model.
	 */

	public void launchCab(int modelID, int adminID, String licencePlate) {

		Connection connection = null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_CAB_LAUNCH_DETAILS);
			preparedStatement.setString(1, licencePlate);
			preparedStatement.setInt(2, modelID);
			preparedStatement.setInt(3, adminID);
			preparedStatement.setInt(4, adminID);
			preparedStatement.executeUpdate();
		} catch (SQLException e) {
			LOGGER.log(Level.SEVERE, "Error in inserting cab launch details to DB.");
		} finally {
			DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}

}
