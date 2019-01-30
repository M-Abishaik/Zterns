package com.zilker.taxi.delegate;


import java.util.ArrayList;

import java.util.HashMap;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.CabLocation;
import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.RideInvoice;
import com.zilker.taxi.bean.Route;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.bean.UpdateRide;
import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.shared.SharedDAO;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.ShortestPath;

/*
 * Taxi Delegate. 
 */

public class TaxiDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Checks if the email of a customer already exists.
	 */
	
	public int checkMailExists(String mail) {
		
		int customerID = -1;
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();			
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
			taxiDAO.updateDriverStatus(driverID, flag);
			
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring driverID and flag to DAO.");
		    } 
	}
	
	/*
	 * Updates the cab status as available or unavailable depending on the ride.
	 */
	
	public void updateCabStatus(int cabID, int flag) {
		
		TaxiDAO taxiDAO = null;
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		try {
			taxiDAO = new TaxiDAO();
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
			
		TaxiDAO taxiDAO = null;
		try {
			taxiDAO = new TaxiDAO();
		    taxiDAO.deleteAccount(mail, customerID); 
		    
		    } catch (Exception e) {
		    	LOGGER.log(Level.INFO, "Error in transferring mail and customer ID to DAO.");
		    } 
	}
	
		
	/*
	 * Retrieves the location ID using location. 
	 */
	
	public int findLocationID(String location) {
		
		int locationID = -1;
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		
		TaxiDAO taxiDAO = null;
		try {
			taxiDAO = new TaxiDAO();
			taxiDAO.updateRideDetails(updateRide);
		    } catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in updating ride details.");
		    } 
	}
		
	/*
	 * Displays the ride details of a customer.
	 */
	
	public BookingResponse displayBookingDetails(int bookingID) {
		
		BookingResponse bookingResponse = null;
		TaxiDAO taxiDAO = null;
		int bID = -1; 
		
		try {
		     	taxiDAO = new TaxiDAO();
		     	
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
		TaxiDAO taxiDAO = null;

		try {
				taxiDAO = new TaxiDAO();
				
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
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
		ShortestPath shortestPath = null;
		RideInvoice rideInvoice = null;
		UpdateRide updateRide = null;
		
		try {
						
			shortestPath = new ShortestPath();
			hashMap = shortestPath.calculateTravel(travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getFormattedTime());
			
			rideEndTime = (String) hashMap.keySet().toArray()[0];
			price = hashMap.get(rideEndTime);

			
			rideInvoice = new RideInvoice(travelInvoice.getCustomerID(), travelInvoice.getDriverID(),
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
				updateRide = new UpdateRide(travelInvoice.getFormattedTime(), rideEndTime, 
						travelInvoice.getSourceID(), travelInvoice.getDestinationID(), travelInvoice.getCustomerID(), price);
				updateRideDetails(updateRide);
				
				return travelInvoice.getCustomerID();
			}
		   	} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transferring invoice details to DAO.");
		    return -1;
		    } 
	}	
	
	/*
	 * Displays travel locations.
	 */
	
	public ArrayList<Address> displayLocations() {
		
		TaxiDAO taxiDAO = null;
		ArrayList<Address> address = null;
		
		try {
			address = new ArrayList<Address>();
			taxiDAO = new TaxiDAO();
			address = taxiDAO.displayLocations();
			
			return address;
			} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in displaying travel locations.");
		    return null;
		    } 
	}
	
	/*
	 * Add licence details.
	 */
	
	public String addLicenceDetails(String licenceNumber, String userPhone) {
		TaxiDAO taxiDAO = null;
		SharedDelegate sharedDelegate = null;
		int driverID = -1;
		int cabID = -1; 
		
		try {
			taxiDAO = new TaxiDAO();
			sharedDelegate = new SharedDelegate();
			
			driverID = sharedDelegate.getUserID(userPhone);
			
			if(driverID!=(-1)) {
					taxiDAO.addLicenceDetails(driverID, licenceNumber);
				    LOGGER.log(Level.INFO, "Driver Licence details successfully added.");
				    
				    cabID = getCabID();
				    if(cabID!=(-1)) {
				    	taxiDAO.assignCabDriver(cabID, driverID);
				    	taxiDAO.updateCabStatus(cabID, 0);
				    	
				    } else {
					    LOGGER.log(Level.INFO, "No cabs are available yet. Please wait for some time.");
						return Constants.FAILURE;
				    }
				   
					return Constants.SUCCESS;
				}
								
			return Constants.FAILURE;
				
		} catch (Exception e) {
		    LOGGER.log(Level.INFO, "Error in transfering address details to DAO.");
		    return Constants.FAILURE;
		}
	}
	
	
	/*
	 * Retrieves the cab ID of a cab.
	 */
	
	public int getCabID() {
		int cabID = -1;
		TaxiDAO taxiDAO = null;
			
		try {
			taxiDAO = new TaxiDAO();
			cabID = taxiDAO.getCabID();
				
			return cabID;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring cabID to DAO.");
			    return -1;
			} 
	}
	
	
	/*
	 * Finds the nearest cab corresponding to the source.
	 */
	
	
	public int findNearestCab(String source) {
		
		float latitude = 0.0f;
		float longitude = 0.0f;
		TaxiDAO taxiDAO = null;
		int cabID = -1;
		ArrayList<CabLocation> cabLocation = null;
		
		latitude = generateRandomLocation(source);
		longitude = generateRandomLocation(source);
		
		try {
			taxiDAO = new TaxiDAO();
			cabLocation = taxiDAO.getCabLocations();
			
			cabID = calculateDistance(cabLocation, latitude, longitude);
			return cabID;
			
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in fetching cab location details from DAO.");
			    return -1;
			} 
	}
	
	
	
	/*
	 * Calculates the minimum distance and finds the nearest cab. 
	 */
	
	public int calculateDistance(ArrayList<CabLocation> cabLocations, float latitude, float longitude) {
		float minimum = Float.MAX_VALUE;
		float distance = 0.0f;
		CabLocation object = new CabLocation();
		
		int size = cabLocations.size();
		int i = 0;
		int cabID = -1;
		
		for(i=0;i<size;i++) {
			object = cabLocations.get(i);
			
			distance = ((object.getCabLatitude()-latitude)*(object.getCabLatitude()-latitude)) + ((object.getCabLongitude()-longitude)*(object.getCabLongitude()-longitude));
			if(distance<minimum) {
				minimum = distance;
				cabID = object.getCabID();
			}
		}
		
		return cabID;
	}
	
	
	/*
	 * Generates random latitude and longitude.
	 */
	
	
	public float generateRandomLocation(String source) {
		
		float min = 100;
		float max = 900;
		float latLng = 0.0f;
		
		Random random = new Random();
		latLng = min + random.nextFloat() * (max - min);
		
		return latLng;
	}
	
	
	/*
	 * Checks if the cab with given seats is avaliable.
	 */
	
	public String checkCabSeats(int nearestCab, int seats) {
		int modelID = -1;
		int cabID = -1;
		int driverID = -1;
		TaxiDAO taxiDAO = null;
		boolean check = false;
			
		try {
			taxiDAO = new TaxiDAO();
			modelID = taxiDAO.getModelID(nearestCab);
			
			if(modelID!=(-1)) {
				check = taxiDAO.isSeatExists(modelID, seats);
				
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
		TaxiDAO taxiDAO = null;
		
		try {
			taxiDAO = new TaxiDAO();
			driverID = taxiDAO.getDriverID(cabID);
				
			return driverID;
			} catch (Exception e) {
			    LOGGER.log(Level.INFO, "Error in transferring driverID to DAO.");
			    return -1;
			} 
	}
}

