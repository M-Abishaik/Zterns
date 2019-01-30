package com.zilker.taxi.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.CabLocation;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.RideInvoice;
import com.zilker.taxi.bean.Route;
import com.zilker.taxi.bean.UpdateRide;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.constant.SQLConstants;
import com.zilker.taxi.util.DbConnect;
/*
 * Handles the CRUD operations of customer details and booking details.
 */

public class TaxiDAO {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private Connection connection = null;
	private PreparedStatement preparedStatement = null;
	private ResultSet resultSet = null;
	
	public TaxiDAO() {}
	
	/*
	 * Checks if the email of a customer already exists.
	 */
	
	public int checkMailExists(String mail) {
		
		int customerID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			//preparedStatement = connection.prepareStatement(SQLConstants.CHECK_MAIL_EXISTS);
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
	 * Checks if the booking ID of a ride exists.
	 */
	
	public int checkBookingExists(int bID) {
		
		int bookingID = -1;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_BOOKING_EXISTS);
			preparedStatement.setInt(1, bID);
		    resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 bookingID = 1;
		    }
		    return bookingID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if booking ID exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Checks if the driver exists.
	 */
	
	public int checkDriverExists() {
		
		int driverID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_DRIVER_EXISTS);
		    resultSet = preparedStatement.executeQuery();
		    
		    while (resultSet.next()) {
		    	if(resultSet.getString(2).equals(Constants.AVAILABLE))
		    	  {
		    		  test = resultSet.getString(1);
		    		  driverID= Integer.parseInt(test);
		    		  break;
		    	  }
		    }
		    return driverID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if driver exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
		
	/*
	 * Checks if the cab exists. 
	 */
	
	public int checkCabExists() {
		
		int cabID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.CHECK_CAB_EXISTS);
			resultSet = preparedStatement.executeQuery();
		    
		    while (resultSet.next()) {
		    	if(resultSet.getString(2).equals(Constants.AVAILABLE))
		    	  {
		    		test = resultSet.getString(1);
		    		cabID= Integer.parseInt(test);
		    		break;
		    	  }
		    }
		    return cabID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if cab exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Retrieves all the route details.
	 */
	
	public ArrayList<Route> getRoutesList() {
		
		ArrayList<Route> routesList = new ArrayList<Route>();
		int source = -1, destination = -1;
		float distance = 0.0f;
		Route route = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_ROUTES_LIST);
			resultSet = preparedStatement.executeQuery();
		    
		    while (resultSet.next()) {
		    	source = resultSet.getInt(2);
		    	destination = resultSet.getInt(3);
		    	distance = resultSet.getFloat(4);

		    	route = new Route(source, destination, distance);
		    	routesList.add(route);
		    }
		    return routesList;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving routes list.");
		    return null;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
	/*
	 * Inserts the profile of customer. 
	 */
	
	public void insertPersonalDetails(Customer customer) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getMailId());
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting personal details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Inserts the ride details of the customer. 
	 */
	
	public int insertRideDetails(RideInvoice invoice) {
		
		int bookingID = -1;
		int count = 0;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_RIDE_DETAILS);
			preparedStatement.setInt(1, invoice.getCustomerID());
			preparedStatement.setInt(2, invoice.getDriverID());
			preparedStatement.setString(3, invoice.getRideStartTime());
			preparedStatement.setString(4, invoice.getRideEndTime());
			preparedStatement.setInt(5, invoice.getSourceID());
			preparedStatement.setInt(6, invoice.getDestinationID());
			preparedStatement.setInt(7, invoice.getCabID());
			preparedStatement.setFloat(8, invoice.getPrice());
		    count = preparedStatement.executeUpdate();
		    
		    if(count>0) {
		    	bookingID = fetchBookingID(invoice.getCustomerID(), invoice.getDriverID());
		    }
		    return bookingID;
		   	} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting ride details.");
		    return -1;
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
		
	}
	
	/*
	 * Retrieves the booking ID of a ride. 
	 */
	
	public int fetchBookingID(int customerID, int driverID) {
		int bookingID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_BOOKING_ID);
			preparedStatement.setInt(1, customerID);
			preparedStatement.setInt(2, driverID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
	    		 bookingID= Integer.parseInt(test);
		    }
		    return bookingID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving booking ID.");
		    return -1;
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
		    	preparedStatement.setString(1, Constants.NOT_AVAILABLE);
		    }else {
		    	preparedStatement.setString(1, Constants.AVAILABLE);
		    }
		    preparedStatement.setInt(2, driverID);
		    preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating driver status.");
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
		    	preparedStatement.setString(1, Constants.NOT_AVAILABLE);
		    }else {
		    	preparedStatement.setString(1, Constants.AVAILABLE);
		    }
		    preparedStatement.setInt(2, cabID);
		    preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating cab status.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Retrieves the location ID using location. 
	 */
		
	public int findLocationID(String location) {
		
		int locationID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			//preparedStatement = connection.prepareStatement(SQLConstants.GET_LOCATION_ID);
			//preparedStatement.setString(1, location);
			
			preparedStatement = connection.prepareStatement("SELECT ADDRESS_ID FROM ADDRESS_DETAIL WHERE STREET_ADDRESS LIKE \"%" + location + "%\"");                              
			resultSet = preparedStatement.executeQuery();
			
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
	    		 locationID= Integer.parseInt(test);
		    }
		    return locationID;
		    } catch (NumberFormatException ne) {
			    LOGGER.log(Level.INFO, "Error in parsing details.");
			    return -1;
			}catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving location.");
		    return -1;
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Retrieves the location using location ID. 
	 */
	
	public String findLocation(int locationID) {
		
		String location = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_LOCATION);
			preparedStatement.setInt(1, locationID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 location  = resultSet.getString(1);
		    }
		    return location;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in finding city.");
		    return "";
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Retrieves the start time of the ride.
	 */
	
	public String findStartTime(int bookingID) {
		
		String startTime = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_START_TIME);
			preparedStatement.setInt(1, bookingID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 startTime  = resultSet.getString(1);
		    }
		    return startTime;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in finding start time.");
		    return "";
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Displays the customer profile. 
	 */
	
	public Customer displayProfile(String email) {
		
		String firstName = "", lastName = "", mail = "";
		Customer customer = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_PROFILE);
			preparedStatement.setString(1, email);
			resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
					firstName = resultSet.getString(2); 
					lastName = resultSet.getString(3);
					mail = resultSet.getString(4);
		      }
		      
		      customer = new Customer(firstName, lastName, mail);
		      return customer;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in retrieving profile details from the DB.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	
	
	/*
	 * Displays the travel locations. 
	 */
	
	public ArrayList<Address> displayLocations() {
		
		String streetAddress = "";
		String zipCode = "";
		ArrayList<Address> address = null;
		Address object = null;
		
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
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in reading travel locations.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	
	/*
	 * Inserts licence deatils of a driver.
	 */
	
	public void addLicenceDetails(int driverID, String licenceNumber) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.INSERT_LICENCE_DETAILS);
			preparedStatement.setInt(1, driverID);
			preparedStatement.setString(2, licenceNumber);
			preparedStatement.setInt(3, driverID);
			preparedStatement.setInt(4, driverID);
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting licence details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	/*
	 * Displays the ride details of a customer.
	 */
	
	public BookingResponse displayBookingDetails(int bookingID) {
		
		String startTime = "", endTime = "";
		float price = 0.0f;
		String source = "", destination = "";
		int sourceID = 0, destinationID = 0;
		BookingResponse bookingResponse = null;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_BOOKING_DETAILS);
			preparedStatement.setInt(1, bookingID);
			resultSet = preparedStatement.executeQuery();
		      
		      if (resultSet.next()) {
		    	  	startTime = resultSet.getString(4);
		    	  	endTime = resultSet.getString(5);
		    		sourceID = resultSet.getInt(6);
		    	  	destinationID = resultSet.getInt(7);
		    	  	price = resultSet.getFloat(9);
		    	  	
		      }
		      
		      source = findLocation(sourceID);
		      destination = findLocation(destinationID);
		      
		      bookingResponse = new BookingResponse(bookingID, price, source, destination, startTime, endTime);
		      
		      return bookingResponse;		      
		} catch (SQLException e) {
		    	LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
		    	return null;
		} finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	/*
	 * Cancels the ride of a customer. 
	 */
	
	public HashMap<Integer, Integer> cancelRide(int bookingID) {
		
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>(); 
		int driverID = 0, cabID = 0, count = 0;

		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.DISPLAY_BOOKING_DETAILS);
			preparedStatement.setInt(1, bookingID);
			resultSet = preparedStatement.executeQuery();
		      
		      if (resultSet.next()) {
		    		driverID = resultSet.getInt(3);
		    	  	cabID = resultSet.getInt(8);
		    	  	
		    	  	hashMap.put(driverID, cabID);
		      }
		      
		      preparedStatement = connection.prepareStatement(SQLConstants.DELETE_RIDE);
		      preparedStatement.setInt(1, bookingID);
		      count = preparedStatement.executeUpdate();
		      if(count>0) {
			      LOGGER.log(Level.INFO, "Ride cancelled successfully.");
		      }
		      return hashMap;
		    } catch (SQLException e) {
		    	LOGGER.log(Level.INFO, "Error in cancelling the ride.");
		    	return null;
		    } finally {
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
	
	/*
	 * Updates customer profile. 
	 */
	
	public void updatePersonalDetails(Customer customer) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_PROFILE);
			preparedStatement.setString(1, customer.getFirstName());
			preparedStatement.setString(2, customer.getLastName());
			preparedStatement.setString(3, customer.getMailId());
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating personal details.");
		    } finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		    }
	}
	
	/*
	 * Updates the ride details of a customer. 
	 */
	
	public void updateRideDetails(UpdateRide updateRide) {
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.UPDATE_RIDE_DETAILS);
			preparedStatement.setString(1, updateRide.getRideStartTime());
			preparedStatement.setString(2, updateRide.getRideEndTime());
			preparedStatement.setInt(3, updateRide.getSourceID());
			preparedStatement.setInt(4, updateRide.getDestinationID());
			preparedStatement.setFloat(5, updateRide.getPrice());
			preparedStatement.setInt(6, updateRide.getBookingID());
			preparedStatement.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating ride details.");
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
			preparedStatement.setString(1, Constants.AVAILABLE);
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
		    LOGGER.log(Level.INFO, "Error in reading cab ID.");
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
	 * Reads the model ID of the available cab.
	 */
	
	public int getModelID(int cabID) {
		int modelID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_ID);
			preparedStatement.setInt(1, cabID);
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
		    LOGGER.log(Level.INFO, "Error in reading model ID.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
	/*
	 * Reads the cab ID corresponding to the model ID.
	 */
	
	public int getModelCabID(int modelID) {
		int cabID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_MODEL_CAB_ID);
			preparedStatement.setInt(1, modelID);
			preparedStatement.setString(2, Constants.NOT_AVAILABLE);

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
		    LOGGER.log(Level.INFO, "Error in reading cab ID corresponding to model ID.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
	/*
	 * Displays the cab locations. 
	 */
	
	public ArrayList<CabLocation> getCabLocations() {
		
		int cabID = -1;
		float latitude = 0.0f;
		float longitude = 0.0f;
		ArrayList<CabLocation> cabLocation = null;
		CabLocation object = null;
		
		try {
			cabLocation = new ArrayList<CabLocation>();
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_LOCATIONS);
			resultSet = preparedStatement.executeQuery();
		      while (resultSet.next()) {
					cabID = resultSet.getInt(1); 
					latitude = resultSet.getFloat(2);
					longitude	 = resultSet.getFloat(3);


					object = new CabLocation(cabID, latitude, longitude);
					cabLocation.add(object);
					
		      }
		      	return cabLocation;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in reading cab locations.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(connection, preparedStatement, resultSet);
		  }
	}
	
	
	/*
	 * Reads the driver ID of the available cab.
	 */
	
	public int getDriverID(int cabID) {
		int driverID = -1;
		String test = "";
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_DRIVER_ID);
			preparedStatement.setInt(1, cabID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 test = resultSet.getString(1);
	    		 driverID= Integer.parseInt(test);
		    }
		    return driverID;
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return -1;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in reading driver ID.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
		
	}
	
	/*
	 * Checks if the cab with requested number of seats is available.
	 */
	
	public boolean isSeatExists(int modelID, int seats) {
		int numSeats = -1;
		
		try {
			connection = DbConnect.getConnection();
			preparedStatement = connection.prepareStatement(SQLConstants.GET_CAB_SEATS);
			preparedStatement.setInt(1, modelID);
			resultSet = preparedStatement.executeQuery();
		    if(resultSet.next()) {
		    	 numSeats = resultSet.getInt(1);
		    }
		    
		    if(numSeats>=seats) {
		    	return true;
		    } else {
		    	return false;
		    }
		}catch (NumberFormatException ne) {
		    LOGGER.log(Level.INFO, "Error in parsing details.");
		    return false;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in reading driver ID.");
		    return false;
		} finally {
		    DbConnect.closeConnection(connection, preparedStatement, resultSet);
		}
	}
	
	
}
