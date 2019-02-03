package com.zilker.taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelDetail;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;

public class TaxiDAO {
	
private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	
	/*
	* Retrieves the model ID.
	*/

	public int getModelID(String modelName, String modelDescription) {
		
		int modelID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_ID);
			preparedStatement.setString(1, modelName);
			preparedStatement.setString(2, modelDescription);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
		    	 modelID= Integer.parseInt(test);
		    }
		    return modelID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving model ID from DB.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
	/*
	* Retrieves the model ID using licence plate.
	*/

	public int getModelByLicencePlate(String licencePlate) {
		
		int modelID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_ID_BY_LICENCE);
			preparedStatement.setString(1, licencePlate);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
		    	 modelID= Integer.parseInt(test);
		    }
		    return modelID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving model ID using licence from DB.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	

	/*
	 * Updates cab model.
	 */
	
	public void updateCabModel(CabModel cabModel, int adminID, int modelID) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_CAB_MODEL);
			preparedStatement.setString(1, cabModel.getModelName());
			preparedStatement.setString(2, cabModel.getModelDescription());
			preparedStatement.setInt(3, cabModel.getNumberSeats());
			preparedStatement.setInt(4, adminID);
			preparedStatement.setInt(5, modelID);
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating cab model in DB.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 *	Removes customer account and its corresponding ride details. 
	 */
	
	public void deleteCabModel(int modelID) {
				
		try {
			connection = DbConnect.getConnection();
			
			preparedStatement = connection.prepareStatement(SQLConstants.DELETE_CAB_DETAILS);
		    preparedStatement.setInt(1, modelID);
		    preparedStatement.executeUpdate();
		      
			preparedStatement = connection.prepareStatement(SQLConstants.DELETE_CAB_MODEL);
			preparedStatement.setInt(1, modelID);
			preparedStatement.executeUpdate();
		  		          		      
		    } catch (SQLException e) {
		    	LOGGER.log(Level.INFO, "Error in deleting cab model and its details from DB.");
		    } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	

	/*
	 * Displays the cab details. 
	 */
	
	public ArrayList<CabModelDetail> displayCabModelDetails() {
		
		String modelName = "";
		String modelDescription = "";
		String licencePlate = "";
		int numSeats = 0;
		ArrayList<CabModelDetail> cabModel = null;
		CabModelDetail object = null;
		
		try {
			cabModel = new ArrayList<CabModelDetail>();
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_CAB_MODELS);
			resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
		    	  	licencePlate = resultSet.getString(1);
					modelName = resultSet.getString(2);
					modelDescription = resultSet.getString(3);
					numSeats = resultSet.getInt(4);
					
					object = new CabModelDetail(licencePlate, modelName, modelDescription, numSeats);
					cabModel.add(object);
					
		      }
		      	return cabModel;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in reading cab details from DB.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	
	/*
	 * Inserts licence deatils of a driver.
	 */
	
	public void addLicenceDetails(int driverID, String licenceNumber, String zipCode) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_LICENCE_DETAILS);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setString(2, licenceNumber);
			preparedStatement.setString(3, zipCode);
			preparedStatement.setInt(4, driverID);
			preparedStatement.setInt(5, driverID);
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting licence details to DB.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Reads the cab ID of the available cab.
	 */
	
	public int getCabID() {
		int cabID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_ID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
	    		 cabID= Integer.parseInt(test);
		    }
		    return cabID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in reading cab ID from DB.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}	
	}
	
	

	/*
	 * Assigns the available cab to the driver.
	 */
	
	public void assignCabDriver(int cabID, int driverID) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_CAB_DRIVER);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setInt(2, cabID);
			preparedStatement.setInt(3, driverID);
			preparedStatement.setInt(4, driverID);
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in assigning driver to the cab.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	
	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */
	
	public void updateCabStatus(int cabID, int flag) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_CAB_STATUS);
		    if(flag==0) {
		    	preparedStatement.setInt(1, 6);
		    }else {
		    	preparedStatement.setInt(1, 5);
		    }
		    preparedStatement.setInt(2, cabID);
		    preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating cab status in DB.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	

	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */
	
	public void updateDriverStatus(int driverID, int flag) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_DRIVER_STATUS);
		    if(flag==0) {
		    	preparedStatement.setInt(1, 6);
		    }else {
		    	preparedStatement.setInt(1, 5);
		    }
		    preparedStatement.setInt(2, driverID);
		    preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating driver status.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	

}
