package com.zilker.taxi.dao;

import java.sql.Connection;


import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.BookingResponse;
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
	
	private Connection conn = null;
	private PreparedStatement pst = null;
	private ResultSet rs = null;
	
	public TaxiDAO() {}
	
	/*
	 * Checks if the email of a customer already exists.
	 */
	
	public int checkMailExists(String mail) {
		
		int customerID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.CHECK_MAIL_EXISTS);
		    pst.setString(1, mail);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 String test = rs.getString(1);
	    		 customerID= Integer.parseInt(test);
		    }
		    return customerID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if email exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
	}
	
	/*
	 * Checks if the booking ID of a ride exists.
	 */
	
	public int checkBookingExists(int bID) {
		
		int bookingID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.CHECK_BOOKING_EXISTS);
		    pst.setInt(1, bID);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 bookingID = 1;
		    }
		    return bookingID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if booking ID exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
	}
	
	/*
	 * Checks if the driver exists.
	 */
	
	public int checkDriverExists() {
		
		int driverID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.CHECK_DRIVER_EXISTS);
		    rs = pst.executeQuery();
		    
		    while (rs.next()) {
		    	if(rs.getString(5).equals(Constants.AVAILABLE))
		    	  {
		    		  String test = rs.getString(1);
		    		  driverID= Integer.parseInt(test);
		    		  break;
		    	  }
		    }
		    return driverID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if driver exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
	}
		
	/*
	 * Checks if the cab exists. 
	 */
	
	public int checkCabExists() {
		
		int cabID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.CHECK_CAB_EXISTS);
		    rs = pst.executeQuery();
		    
		    while (rs.next()) {
		    	if(rs.getString(3).equals(Constants.AVAILABLE))
		    	  {
		    		String test = rs.getString(1);
		    		cabID= Integer.parseInt(test);
		    		break;
		    	  }
		    }
		    return cabID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in checking if cab exists.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
	}
	
	/*
	 * Retrieves all the route details.
	 */
	
	public ArrayList<Route> getRoutesList() {
		
		ArrayList<Route> routesList = new ArrayList<Route>();
		int source = -1, destination = -1;
		float distance = 0.0f;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.GET_ROUTES_LIST);
		    rs = pst.executeQuery();
		    
		    while (rs.next()) {
		    	source = rs.getInt(2);
		    	destination = rs.getInt(3);
		    	distance = rs.getFloat(4);

		    	Route route = new Route(source, destination, distance);
		    	routesList.add(route);
		    }
		    return routesList;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving routes list.");
		    return null;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
		
	}
	
	/*
	 * Inserts the profile of customer. 
	 */
	
	public void insertPersonalDetails(Customer customer) {
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.INSERT_PERSONAL_DETAILS);
		    pst.setString(1, customer.getFirstName());
		    pst.setString(2, customer.getLastName());
		    pst.setString(3, customer.getMailId());
		    pst.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting personal details.");
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Inserts the ride details of the customer. 
	 */
	
	public int insertRideDetails(RideInvoice invoice) {
		
		int bookingID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.INSERT_RIDE_DETAILS);
		    pst.setInt(1, invoice.getCustomerID());
		    pst.setInt(2, invoice.getDriverID());
		    pst.setString(3, invoice.getRideStartTime());
		    pst.setString(4, invoice.getRideEndTime());
		    pst.setInt(5, invoice.getSourceID());
		    pst.setInt(6, invoice.getDestinationID());
		    pst.setInt(7, invoice.getCabID());
		    pst.setFloat(8, invoice.getPrice());
		    int count = pst.executeUpdate();
		    
		    if(count>0) {
		    	bookingID = fetchBookingID(invoice.getCustomerID(), invoice.getDriverID());
		    }
		    return bookingID;
		   	} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in inserting ride details.");
		    return -1;
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
		
	}
	
	/*
	 * Retrieves the booking ID of a ride. 
	 */
	
	public int fetchBookingID(int customerID, int driverID) {
		int bookingID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.GET_BOOKING_ID);
		    pst.setInt(1, customerID);
		    pst.setInt(2, driverID);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 String test = rs.getString(1);
	    		 bookingID= Integer.parseInt(test);
		    }
		    return bookingID;
		} catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving booking ID.");
		    return -1;
		} finally {
		    DbConnect.closeConnection(conn, pst, rs);
		}
		
	}
	
	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */
	
	public void updateDriverStatus(int driverID, int flag) {
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.UPDATE_DRIVER_STATUS);
		    if(flag==0) {
		    	pst.setString(1, Constants.NOT_AVAILABLE);
		    }else {
		    	pst.setString(1, Constants.AVAILABLE);
		    }
		    pst.setInt(2, driverID);
		    pst.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating driver status.");
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */
	
	public void updateCabStatus(int cabID, int flag) {
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.UPDATE_CAB_STATUS);
		    if(flag==0) {
		    	pst.setString(1, Constants.NOT_AVAILABLE);
		    }else {
		    	pst.setString(1, Constants.AVAILABLE);
		    }
		    pst.setInt(2, cabID);
		    pst.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating cab status.");
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Retrieves the location ID using location. 
	 */
	
	public int findLocationID(String location) {
		
		int locationID = -1;
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.GET_LOCATION_ID);
		    pst.setString(1, location);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 String test = rs.getString(1);
	    		 locationID= Integer.parseInt(test);
		    }
		    return locationID;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in retrieving location.");
		    return -1;
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Retrieves the location using location ID. 
	 */
	
	public String findLocation(int locationID) {
		
		String location = "";
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.GET_LOCATION);
		    pst.setInt(1, locationID);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 location  = rs.getString(3);
		    }
		    return location;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in finding city.");
		    return "";
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Retrieves the start time of the ride.
	 */
	
	public String findStartTime(int bookingID) {
		
		String startTime = "";
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.GET_START_TIME);
		    pst.setInt(1, bookingID);
		    rs = pst.executeQuery();
		    if(rs.next()) {
		    	 startTime  = rs.getString(4);
		    }
		    return startTime;
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in finding start time.");
		    return "";
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Displays the customer profile. 
	 */
	
	public Customer displayProfile(String email) {
		
		String firstName = "", lastName = "", mail = "";
		
		try {
		      conn = DbConnect.getConnection();
		      pst = conn.prepareStatement(SQLConstants.DISPLAY_PROFILE);
		      pst.setString(1, email);
		      rs = pst.executeQuery();
		      while (rs.next()) {
					firstName = rs.getString(2); 
					lastName = rs.getString(3);
					mail = rs.getString(4);
		      }
		      
		      Customer customer = new Customer(firstName, lastName, mail);
		      return customer;
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in retrieving profile details from the DB.");
		    	return null;
		  } finally {
		      DbConnect.closeConnection(conn, pst, rs);
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
		
		try {
		      conn = DbConnect.getConnection();
		      pst = conn.prepareStatement(SQLConstants.DISPLAY_BOOKING_DETAILS);
		      pst.setInt(1, bookingID);
		      rs = pst.executeQuery();
		      
		      if (rs.next()) {
		    	  	startTime = rs.getString(4);
		    	  	endTime = rs.getString(5);
		    		sourceID = rs.getInt(6);
		    	  	destinationID = rs.getInt(7);
		    	  	price = rs.getFloat(9);
		    	  	
		      }
		      
		      source = findLocation(sourceID);
		      destination = findLocation(destinationID);
		      
		      BookingResponse bookingResponse = new BookingResponse(bookingID, price, source, destination, startTime, endTime);
		      
		      return bookingResponse;		      
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
		    	return null;
		} finally {
		      DbConnect.closeConnection(conn, pst, rs);
		}
	}
	
	/*
	 * Cancels the ride of a customer. 
	 */
	
	public HashMap<Integer, Integer> cancelRide(int bookingID) {
		
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>(); 
		int driverID = 0, cabID = 0;

		try {
		      conn = DbConnect.getConnection();
		      pst = conn.prepareStatement(SQLConstants.DISPLAY_BOOKING_DETAILS);
		      pst.setInt(1, bookingID);
		      rs = pst.executeQuery();
		      
		      if (rs.next()) {
		    		driverID = rs.getInt(3);
		    	  	cabID = rs.getInt(8);
		    	  	
		    	  	hashMap.put(driverID, cabID);
		      }
		      
		      pst = conn.prepareStatement(SQLConstants.DELETE_RIDE);
		      pst.setInt(1, bookingID);
		      int count = pst.executeUpdate();
		      if(count>0) {
			      LOGGER.log(Level.INFO, "Ride cancelled successfully.");
		      }
		      return hashMap;
		    } catch (SQLException e) {
		    	LOGGER.log(Level.INFO, "Error in cancelling the ride.");
		    	return null;
		    } finally {
		      DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 *	Removes customer account and its corresponding ride details. 
	 */
	
	public void deleteAccount(String mail, int  customerID) {
		
		int count = 0;
		
		try {
		      conn = DbConnect.getConnection();
		      
		      pst = conn.prepareStatement(SQLConstants.DELETE_PROFILE);
		      pst.setInt(1, customerID);
		      pst.executeUpdate();
		      if(count>0) {
			      LOGGER.log(Level.INFO, "Profile deleted successfully.");
		      }
		      
		      count = 0;
		      
		      pst = conn.prepareStatement(SQLConstants.DELETE_CUSTOMER_RIDES);
		      pst.setInt(1, customerID);
		      count = pst.executeUpdate();
		      
		      if(count>0) {
			      LOGGER.log(Level.INFO, "Rides deleted successfully.");
		      }
		      		      
		    } catch (SQLException e) {
		    	LOGGER.log(Level.INFO, "Error in deleting details.");
		    } finally {
		      DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Updates customer profile. 
	 */
	
	public void updatePersonalDetails(Customer customer) {
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.UPDATE_PROFILE);
		    pst.setString(1, customer.getFirstName());
		    pst.setString(2, customer.getLastName());
		    pst.setString(3, customer.getMailId());
		    pst.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating personal details.");
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}
	
	/*
	 * Updates the ride details of a customer. 
	 */
	
	public void updateRideDetails(UpdateRide updateRide) {
		
		try {
			conn = DbConnect.getConnection();
		    pst = conn.prepareStatement(SQLConstants.UPDATE_RIDE_DETAILS);
		    pst.setString(1, updateRide.getRideStartTime());
		    pst.setString(2, updateRide.getRideEndTime());
		    pst.setInt(3, updateRide.getSourceID());
		    pst.setInt(4, updateRide.getDestinationID());
		    pst.setFloat(5, updateRide.getPrice());
		    pst.setInt(6, updateRide.getBookingID());
		    pst.executeUpdate();
		    } catch (SQLException e) {
		    LOGGER.log(Level.INFO, "Error in updating ride details.");
		    } finally {
		    DbConnect.closeConnection(conn, pst, rs);
		    }
	}	
}
