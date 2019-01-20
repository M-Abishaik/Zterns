package com.zilker.taxi.delegate;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.RideInvoice;
import com.zilker.taxi.bean.Route;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.bean.UpdateRide;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.util.ShortestPath;

public class TaxiDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Checks if the email of a customer already exists.
	 */
	
	public int checkMailExists(String mail) {
		
		int customerID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			customerID = taxiDAO.checkMailExists(mail);
			
			
			return customerID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering mail to DAO.");
		    return -1;
		} 
	}
	
	
	/*
	 * Checks if the booking ID of a ride exists.
	 */
	
	public int checkBookingExists(int bID) {
		
	
		int bookingID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			bookingID = taxiDAO.checkBookingExists(bID);
			
		    return bookingID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering booking ID to DAO.");
		    return -1;
		} 
	}
	
	/*
	 * Checks if the driver exists.
	 */
	
	public int checkDriverExists() {
		
		int driverID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			driverID = taxiDAO.checkDriverExists();
			
		    return driverID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in checking if driver exists with DAO.");
		    return -1;
		} 
	}
	
	/*
	 * Checks if the cab exists. 
	 */
	
	public int checkCabExists() {
		
		int cabID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			cabID = taxiDAO.checkDriverExists();
			
		    return cabID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in checking if cab exists with DAO.");
		    return -1;
		} 
	}
	
	/*
	 * Retrieves all the route details.
	 */
	
	public ArrayList<Route> getRoutesList() {
		
		ArrayList<Route> routesList = new ArrayList<Route>();
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();			
			routesList = taxiDAO.getRoutesList();
			
		    return routesList;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in retrieving routes list from the DAO.");
		    return null;
		} 
	}
		
	/*
	 * Retrieves the booking ID of a ride. 
	 */
	
	public int fetchBookingID(int customerID, int driverID) {
		int bookingID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			bookingID = taxiDAO.fetchBookingID(customerID, driverID);
			
		    return bookingID;
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring customerID and driverID to DAO.");
		    return -1;
		} 
	}
	
	/*
	 * Updates the driver status as available or unavailable depending on the ride.
	 */
	
	public void updateDriverStatus(int driverID, int flag) {
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			taxiDAO.updateDriverStatus(driverID, flag);
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring driverID and flag to DAO.");
		    } 
	}
	
	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */
	
	public void updateCabStatus(int cabID, int flag) {
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			taxiDAO.updateDriverStatus(cabID, flag);
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring cabID and flag to DAO.");
		    } 
	}

	/*
	 * Retrieves the location using location ID. 
	 */
	
	public String findLocation(int locationID) {
		
		String location = "";
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			location = taxiDAO.findLocation(locationID);
			
		    return location;
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring location ID to DAO.");
		    return "";
		    } 
	}
	
	
	/*
	 * Retrieves the start time of the ride.
	 */
	
	public String findStartTime(int bookingID) {
		
		String startTime = "";
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			startTime = taxiDAO.findStartTime(bookingID);
			
		    return startTime;
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring booking ID to DAO.");
		    return "";
		    } 
	}
	
	/*
	 *	Removes customer account and its corresponding ride details. 
	 */
	
	public void deleteAccount(String mail, int  customerID) {
				
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
		    taxiDAO.deleteAccount(mail, customerID); 
		    
		    } catch (Exception e) {
		    	LOGGER.log(Level.INFO, "Error in transferring mail and customer ID to DAO.");
		    } 
	}
	
	/*
	 * Updates customer profile. 
	 */
	
	public void updatePersonalDetails(Customer customer) {
		
		try {
			
			TaxiDAO taxiDAO = new TaxiDAO();
		    taxiDAO.updatePersonalDetails(customer); 
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring customer profile to DAO.");
		    } 
	}
	
	
	/*
	 * Retrieves the location ID using location. 
	 */
	
	public int findLocationID(String location) {
		
		int locationID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			locationID = taxiDAO.findLocationID(location);
			
		    return locationID;
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering location to DAO.");
		    return -1;
		    } 
	}
		
	/*
	 * Updates the ride details of a customer. 
	 */
	
	public void updateRideDetails(UpdateRide updateRide) {
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			taxiDAO.updateRideDetails(updateRide);
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in updating ride details.");
		    } 
	}
		
	/*
	 * Passes the customer profile. 
	 */
	
	public int insertPersonalDetails(Customer customer) {
		
		int customerID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			
			customerID = checkMailExists(customer.getMailId());
			
			if (customerID != (-1)) {
				return customerID;
			}
			
			taxiDAO.insertPersonalDetails(customer);
			
			return -1;
			
		} catch(Exception e) {
			LOGGER.log(Level.INFO, "Error in transfering personal details to DAO.");
			return -1;
		}
	}
	
	
	/*
	 * Displays the customer profile. 
	 */
	
	public Customer displayProfile(String email) {
		
		Customer customer = null;
		int customerID = -1;
		
		try {
		      TaxiDAO taxiDAO = new TaxiDAO();
		      
		      customerID = checkMailExists(email);
				
				if (customerID == (-1)) {
					return null;
				}
		      
		      customer = taxiDAO.displayProfile(email);
		      return customer;
		      
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in retrieving profile details from the DB.");
		    	return null;
		  } 
	}
	
	/*
	 * Displays the ride details of a customer.
	 */
	
	public BookingResponse displayBookingDetails(int bookingID) {
		
		BookingResponse bookingResponse = null;
		int bID = -1; 
		
		try {
		     	TaxiDAO taxiDAO = new TaxiDAO();
		     	
		     	bID = checkBookingExists(bookingID);
		     	if(bID == -1) {
		     		return null;
		     	}
		     	
		     	bookingResponse = taxiDAO.displayBookingDetails(bookingID);
		      
		     	return bookingResponse;		      
		} catch (Exception e) {
		    	LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
		    	return null;
		} 
	}
	
	
	/*
	 * Cancels the ride of a customer. 
	 */
	
	public int cancelRide(int bookingID) {

		int bID = -1, driverID = -1, cabID = -1;
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>(); 

		try {
				TaxiDAO taxiDAO = new TaxiDAO();
				
				bID = checkBookingExists(bookingID);
		     	if(bID == -1) {
		     		return -1;
		     	}
				
				hashMap = taxiDAO.cancelRide(bookingID);
				
				driverID = (int) hashMap.keySet().toArray()[0];
				cabID = hashMap.get(driverID);
				
				updateDriverStatus(driverID, 1);
				updateCabStatus(cabID, 1);
				
		     	return 1;
		    } catch (Exception e) {
		    	LOGGER.log(Level.INFO, "Error in transferring booking ID to DAO.");
		    	return -1;
		    } 
	}
	
	/*
	 * Inserts the ride details of the customer. 
	 */
	
	public int insertRideDetails(RideInvoice invoice) {
		
		int bookingID = -1;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			bookingID = taxiDAO.insertRideDetails(invoice);
		    
		    return bookingID;
		   	} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring invoice details to DAO.");
		    return -1;
		    } 
	}
	
	public int calculateTravel(TravelInvoice travelInvoice, int flag) {
		
		int result = -1;
		HashMap<String, Float> hashMap = new HashMap<String, Float>();
		float price = 0.0f;
		String rideEndTime = "";
		
		try {
						
			ShortestPath shortestPath = new ShortestPath();
			hashMap = shortestPath.calculateTravel(travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getFormattedTime());
			
			rideEndTime = (String) hashMap.keySet().toArray()[0];
			price = hashMap.get(rideEndTime);

			
			RideInvoice rideInvoice = new RideInvoice(travelInvoice.getCustomerID(), travelInvoice.getDriverID(),
					travelInvoice.getCabID(), travelInvoice.getSourceID(), travelInvoice.getDestinationID(), 
					travelInvoice.getFormattedTime(), rideEndTime, price);
			
			if(flag==0) {
			result = insertRideDetails(rideInvoice);
		    

			// Updates the driver and cab status to be unavailable until the current ride
			// has been completed.
			
			updateDriverStatus(travelInvoice.getDriverID(), 0);
			updateCabStatus(travelInvoice.getCabID(), 0);
			
		    return result;
			} else {
				UpdateRide updateRide = new UpdateRide(travelInvoice.getFormattedTime(), rideEndTime, 
						travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getCustomerID(), price);
				updateRideDetails(updateRide);
				
				return travelInvoice.getCustomerID();
			}
		   	} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring invoice details to DAO.");
		    return -1;
		    } 
	}
	
	
	
}
