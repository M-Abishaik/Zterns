package com.zilker.taxi.delegate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.RideInvoice;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.CustomerDAO;
import com.zilker.taxi.dao.TaxiDAO;

public class CustomerDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Retrieves the location ID using location. 
	 */
	
	public int findLocationID(String location) {
		
		int locationID = -1;
		CustomerDAO customerDAO = null;
		
		try {
			customerDAO = new CustomerDAO();
			locationID = customerDAO.findLocationID(location);
			
		    return locationID;
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering location to DAO.");
		    return -1;
		    } 
	}
	

	/*
	 * Displays travel locations.
	 */
	
	public ArrayList<Address> displayLocations() {
		
		CustomerDAO customerDAO = null;
		ArrayList<Address> address = null;
		
		try {
			address = new ArrayList<Address>();
			customerDAO = new CustomerDAO();
			address = customerDAO.displayLocations();
			
			return address;
			} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in displaying travel locations.");
		    return null;
		    } 
	}
	
	/*
	 * Retrieves the zip-code using source ID. 
	 */
	
	public String findZipByID(int sourceID) {
		
		String zipCode = "";
		CustomerDAO customerDAO = null;
		try {
			customerDAO = new CustomerDAO();
			zipCode = customerDAO.findZipByID(sourceID);
			
		    return zipCode;
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring source ID to DAO.");
		    return "";
		    } 
	}
	

	/*
	 * Finds the nearest cab corresponding to the source.
	 */
	
	
	public int findNearestCab(String zipCode) {
		
		CustomerDAO customerDAO = null;
		int cabID = -1;
		int driverID = -1;
		
		try {
			customerDAO = new CustomerDAO();
			driverID = customerDAO.findNearestDriver(zipCode);
			
			if(driverID!=(-1)) {
				cabID = customerDAO.findNearestCab(driverID);
				return cabID;
			}
			
			return cabID;
			
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in fetching nearest cab from DAO.");
			    return -1;
			} 
	}
	

	/*
	 * Checks if the cab with given seats is avaliable.
	 */
	
	public String checkCabSeats(int nearestCab, int seats) {
		int modelID = -1;
		CustomerDAO customerDAO = null;
		boolean check = false;
			
		try {
			customerDAO = new CustomerDAO();
			modelID = customerDAO.getModelID(nearestCab);
			
			if(modelID!=(-1)) {
				check = customerDAO.isSeatExists(modelID, seats);
				
				if(check==true) {
					return Constants.SUCCESS;
				} else {
					return Constants.FAILURE;
				}
			}
				
			return Constants.FAILURE;
			
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cab seats to DAO.");
			    return Constants.FAILURE;
			} 
	}
	
	/*
	 * Finds the driver ID.
	 */
	
	public int getDriverID(int cabID) {
		int driverID = -1;
		CustomerDAO customerDAO = null;
		
		try {
			customerDAO = new CustomerDAO();
			driverID = customerDAO.getDriverID(cabID);
				
			return driverID;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cabID to DAO.");
			    return -1;
			} 
	}
	
public int calculateTravel(TravelInvoice travelInvoice, int flag) {
		
		int result = -1;
		HashMap<String, Float> hashMap = new HashMap<String, Float>();
		float price = 0.0f;
		String rideEndTime = "";
		float distance = 0.0f;
		float min = 5000;
		float max = 1000000;
		RideInvoice rideInvoice = null;
		Random random = null;

		
		//UpdateRide updateRide = null;
		
		try {
						
			random = new Random();
			distance = min + random.nextFloat() * (max - min);
			
			if(distance >= 5000 && distance <= 15000) {
				price = 2500;
			} else if(distance >= 15001 && distance <= 50000) {
				price = 10000;
			} else if(distance >= 50001 && distance <= 70000) {
				price = 20000;
			} else {
				price = 30000;
			}
			
			rideInvoice = new RideInvoice(travelInvoice.getCustomerID(), travelInvoice.getDriverID(),
					travelInvoice.getCabID(), travelInvoice.getSourceID(), travelInvoice.getDestinationID(), 
					travelInvoice.getFormattedTime(), price);
			
			if(flag==0) {
			result = insertRideDetails(rideInvoice);
		    

			// Updates the driver and cab status to be unavailable until the current ride
			// has been completed.
			
			updateDriverStatus(travelInvoice.getDriverID(), 0);			
		    return result;
		    
			} else {
				/*updateRide = new UpdateRide(travelInvoice.getFormattedTime(), rideEndTime, 
						travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getCustomerID(), price);
				updateRideDetails(updateRide);*/
				
				return travelInvoice.getCustomerID();
			}
		   	} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring invoice details to DAO.");
		    return -1;
		    } 
	}	

/*
 * Inserts the ride details of the customer. 
 */

public int insertRideDetails(RideInvoice invoice) {
	
	int bookingID = -1;
	CustomerDAO customerDAO = null;
	
	try {
		customerDAO = new CustomerDAO();
		bookingID = customerDAO.insertRideDetails(invoice);
	    
	    return bookingID;
	   	} catch (Exception e) {
	    LOGGER.log(Level.INFO, "Error in transferring invoice details to DAO.");
	    return -1;
	    } 
}

/*
 * Updates the driver status as available or unavailable depending on the ride.
 */

public void updateDriverStatus(int driverID, int flag) {
	TaxiDAO taxiDAO = null;
	
	try {
		taxiDAO = new TaxiDAO();
		taxiDAO.updateDriverStatus(driverID, flag);
		
	    } catch (Exception e) {
	    LOGGER.log(Level.INFO, "Error in transferring driverID and flag to DAO.");
	    } 
}
	
	

}
