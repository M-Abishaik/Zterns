package com.zilker.taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.CabDetail;
import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelID;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

public class AdminDAO {
	

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	/*
	 * Inserts cab model details.
	 */
	
	public void insertCabModelDetails(int adminID, CabModel cabModel) {
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
		    LOGGER.log(Level.INFO, "Error in inserting cab model details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Inserts cab launch details.
	 */
	
	public void insertCabLaunchDetails(int adminID, CabDetail cabDetail) {
		try {	  
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_CAB_LAUNCH_DETAILS);
			preparedStatement.setString(1, cabDetail.getLicencePlate());
			preparedStatement.setFloat(2, cabDetail.getLatitude());
			preparedStatement.setFloat(3, cabDetail.getLatitude());
			preparedStatement.setInt(4, cabDetail.getModelID());
			preparedStatement.setInt(5, adminID);
			preparedStatement.setInt(6, adminID);
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting cab launch details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	
	/*
	 * Displays the cab details. 
	 */
	
	public ArrayList<CabModelID> displayCabModelDetails() {
		
		String modelName = "";
		String modelDescription = "";
		int numSeats = 0;
		int modelID = 0;
		ArrayList<CabModelID> cabModel = null;
		CabModelID object = null;
		
		try {
			cabModel = new ArrayList<CabModelID>();
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_CAB_MODELS);
			resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
					modelID = resultSet.getInt(1); 
					modelName = resultSet.getString(2);
					modelDescription = resultSet.getString(3);
					numSeats = resultSet.getInt(4);
					
					object = new CabModelID(modelName, modelDescription, numSeats, modelID);
					cabModel.add(object);
					
		      }
		      	return cabModel;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in reading cab details.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	

}
