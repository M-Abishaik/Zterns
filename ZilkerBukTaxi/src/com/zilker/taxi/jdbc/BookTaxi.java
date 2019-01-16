package com.zilker.taxi.jdbc;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.Invoice;
import com.zilker.taxi.constant.ConstantVar;
import com.zilker.taxi.dao.TaxiDAO;
import com.zilker.taxi.util.ShortestPath;
/*
 *	Inputs the personal details and booking details of a customer. 
 */

public class BookTaxi {
	
	public BookTaxi() {}

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	private final static Scanner SCANNER = new Scanner(System.in);
	
	public static void main(String[] args) {
		
		int choice=0;
	    BookTaxi bookTaxi = new BookTaxi();
	    	
	    do {
	    	LOGGER.log(Level.INFO, "Taxi Booking Application.");
	    	LOGGER.log(Level.INFO,"1. Register account." + "\n" + "2. Book a ride." + "\n" + "3. Display details." + "\n" + "4. Update personal details." + "\n" + "5. Update booking details. " + "\n" + "6. Cance a ride." +"\n" + "7. Delete account." + "\n" + "8. Exit");
	    	LOGGER.log(Level.INFO, "Enter your choice: ");
	    	
	    	choice = SCANNER.nextInt();
	    		    	
	    	switch(choice) {
	    		case 1: bookTaxi.registerAccount();
	    				break;
	    		case 2: bookTaxi.bookRide();
	    				break;
	   			case 3: bookTaxi.displayDetails();
	   					break;
	   			case 4: bookTaxi.updatePersonalDetails();
	   					break;
    			case 5: bookTaxi.updateBookingDetails();
						break;
    			case 6: bookTaxi.cancelRide();
						break;
    			case 7: bookTaxi.deleteAccount();
						break;
    			case 8: System.exit(0);
    			default:LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
    					break;

	    	}
	   	}while(choice!=8);
	   }
	
	/*
	 *	Creates a new customer account. 
	 */
	
	public void registerAccount() {
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			String mail = "", firstName = "", lastName = "";
			Matcher matcher = null;
			boolean check = false;
			int customerID = 0;
			
			SCANNER.nextLine();

			LOGGER.log(Level.INFO, "Enter first name: ");
			firstName = SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter Last name: ");
			lastName = SCANNER.nextLine();
			
			do{
				LOGGER.log(Level.INFO, "Enter your mail: ");
				mail = SCANNER.next();
				
				matcher = ConstantVar.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if(matcher.find() == true) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid e-mail. Enter a valid one.");
				}
			}while(check != true);
			
			customerID = taxiDAO.checkMailExists(mail);
			if(customerID!=(-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "already exists.");
				return;
			}
			
    		
    		Customer customer = new Customer(firstName, lastName, mail);
        	taxiDAO.insertPersonalDetails(customer);
        	
			LOGGER.log(Level.INFO, "Account successfully created.");

		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 
	}
	
	/*
	 *	Books a new ride for the customer. 
	 */
	
	public void bookRide() {
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			ShortestPath shortestPath = new ShortestPath();
			
			String source = "", destination = "", rideStartTime = "", rideEndTime = "", mail = "", formattedTime = "";
			Matcher matcher = null;
			boolean check = false;
			int customerID = 0, driverID = 0, cabID = 0, sourceID = 0, destinationID = 0, bookingID = 0;
			Date date = null;
			float price = 0.0f;
			HashMap<String, Float> hashMap= new HashMap<String, Float>();
		    DateFormat dateFormat = null, outputFormat = null;
			
			do{
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
				
				mail = SCANNER.nextLine();
				
				matcher = ConstantVar.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if(matcher.find() == true) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid e-mail. Enter a valid one.");
				}
			}while(check != true);
			
			check = false;
    		
			customerID = taxiDAO.checkMailExists(mail);
			if(customerID==(-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}
			
			do{
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");			
				source = SCANNER.nextLine();
				
				LOGGER.log(Level.INFO, "Enter the drop location: ");			
				destination = SCANNER.nextLine();
				
				sourceID = taxiDAO.findLocationID(source);
				destinationID = taxiDAO.findLocationID(destination);
				
				if(sourceID != (-1) && destinationID != (-1)) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			}while(check != true);
			
			driverID = taxiDAO.checkDriverExists();
			
			cabID = taxiDAO.checkCabExists();
			
			if(driverID != (-1) && cabID != (-1)) {
				LOGGER.log(Level.INFO, "At what time do you wish to start the ride? ");			
				rideStartTime = SCANNER.nextLine();
				
				dateFormat = new SimpleDateFormat("hh:mm aa");
			    outputFormat = new SimpleDateFormat("HH:mm");
			    
			    try {
			         date= dateFormat.parse(rideStartTime);
			         formattedTime = outputFormat.format(date);
			    } catch(ParseException pe) {
					LOGGER.log(Level.INFO, pe.getMessage());	
			    }
			    
			    // Computes the estimated finish time and price corresponding to the distance between source and destination.
			    
			    hashMap = shortestPath.calculateTravel(sourceID, destinationID, formattedTime);
			    if(hashMap==null) {
					LOGGER.log(Level.WARNING, "Please enter a valid set of source and destination.");
					return;
			    }
			    
			    rideEndTime = (String) hashMap.keySet().toArray()[0];
			    price = hashMap.get(rideEndTime);
			    
			    Invoice invoice = new Invoice(customerID, driverID, cabID, sourceID, destinationID, formattedTime, 
			    						rideEndTime, price);
			    
			    bookingID = taxiDAO.insertRideDetails(invoice);
				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				
				// Updates the driver and cab status to be unavailable until the current ride has been completed. 
				
			    taxiDAO.updateDriverStatus(driverID, 0);
				taxiDAO.updateCabStatus(cabID, 0);
				
				LOGGER.log(Level.INFO, "Ride successfully booked.");
			    		
			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}
			
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 
	}
	
	/*
	 *	Displays customer profile and booking details. 
	 */
	
	public void displayDetails() {
		
		int choice=0;
	    	
		LOGGER.log(Level.INFO,"1. Display profile?" + "\n" + "2. Display booking details?" + "\n" + "3. Exit.");
	    LOGGER.log(Level.INFO, "Enter your choice: ");
	    	
	    choice = SCANNER.nextInt();
	    		    	
	    switch(choice) {
	    		case 1: displayProfile();
	    				break;
	    		case 2: displayBookingDetails();
	    				break;
    			case 3: return;
    			default:LOGGER.log(Level.WARNING, "Invalid choice.");
    					break;
	    }
	}
	
	/*
	 *	Displays customer profile. 
	 */
	
	public void displayProfile() {
		String mail = "";
		Matcher matcher = null;
		boolean check = false;
		int customerID = 0;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			do{
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
			
				mail = SCANNER.nextLine();
			
				matcher = ConstantVar.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if(matcher.find() == true) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid e-mail. Enter a valid one.");
				}
			}while(check != true);
		
			customerID = taxiDAO.checkMailExists(mail);
			if(customerID==(-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}
			taxiDAO.displayProfile(mail);
			LOGGER.log(Level.INFO, "Profile displayed.");
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 
	}
	
	/*
	 *	Displays customer booking details. 
	 */
	
	public void displayBookingDetails() {
		int bookingID = 0;
		boolean check = false;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			
			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();
		
			bookingID = SCANNER.nextInt();
			check = taxiDAO.checkBookingExists(bookingID);
			
			if(check==false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}
			
			taxiDAO.displayBookingDetails(bookingID);
			LOGGER.log(Level.INFO, "Booking details displayed.");
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 
	}
	
	/*
	 *	Updates customer profile. 
	 */
	
	public void updatePersonalDetails() {
		
		String mail = "", firstName = "", lastName = "";
		Matcher matcher = null;
		boolean check = false;
		int customerID = 0;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			
			do{
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
			
				mail = SCANNER.next();
			
				matcher = ConstantVar.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if(matcher.find() == true) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid e-mail. Enter a valid one.");
				}
			}while(check != true);
			
			
			customerID = taxiDAO.checkMailExists(mail);
			if(customerID==(-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}
			
			SCANNER.nextLine();

			LOGGER.log(Level.INFO, "Enter first name: ");
			firstName = SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter Last name: ");
			lastName = SCANNER.nextLine();
			
			if(firstName.isEmpty() || lastName.isEmpty()) {
				LOGGER.log(Level.WARNING, "First name and Last name details are mandatory");
			} 
			
			Customer customer = new Customer(firstName, lastName, mail);
        	taxiDAO.updatePersonalDetails(customer);
    		
			LOGGER.log(Level.INFO, "Profile successfully updated.");

		}catch (Exception e) {
    			LOGGER.log(Level.SEVERE, e.getMessage());
    	} 
	}
	
	/*
	 *	Updates the booking details of the customer. 
	 */
	
	public void updateBookingDetails() {
	
		int bookingID = 0, sourceID = 0, destinationID = 0;
		boolean check = false;
		String startTime = "", currentTime = "", rideStartTime = "", rideEndTime = "", source = "", destination = "", formattedTime = "";
		Calendar calendar = null;
		SimpleDateFormat simpleDateFormat = null;
		Date startDate = null, currentDate = null, date = null;
		long elapsed = 0, remaining = 0;
		DateFormat dateFormat = null, outputFormat = null;
		float price = 0.0f;
		HashMap<String, Float> hashMap= new HashMap<String, Float>();
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			ShortestPath shortestPath = new ShortestPath();
			
			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();
		
			bookingID = SCANNER.nextInt();
			check = taxiDAO.checkBookingExists(bookingID);
			
			if(check==false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}
			
			check = false;
						
			startTime = taxiDAO.findStartTime(bookingID);
			
			calendar = Calendar.getInstance();
	        simpleDateFormat = new SimpleDateFormat("HH:mm");
	        
	        currentTime = simpleDateFormat.format(calendar.getTime());
	        startDate = simpleDateFormat.parse(startTime);
	        currentDate = simpleDateFormat.parse(currentTime);
	        
	        elapsed = currentDate.getTime() - startDate.getTime();
	        
	        
	        // If the time difference between current time of booking and start time is positive, no updation is possible.
	        
	        remaining = elapsed/3600;
	        
	        if(remaining >=0 ) {
	        	LOGGER.log(Level.WARNING, "You can't change your ride details now.");
				return;
	        }
	        	        
	        do{
	        	SCANNER.nextLine();
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");			
				source = SCANNER.nextLine();
				
				LOGGER.log(Level.INFO, "Enter the drop location: ");			
				destination = SCANNER.nextLine();
				
				sourceID = taxiDAO.findLocationID(source);
				destinationID = taxiDAO.findLocationID(destination);
				
				if(sourceID != (-1) && destinationID != (-1)) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			}while(check != true);
			
	        LOGGER.log(Level.INFO, "At what time do you wish to start the ride? ");			
			rideStartTime = SCANNER.nextLine();
			
		    dateFormat = new SimpleDateFormat("hh:mm aa");
		    outputFormat = new SimpleDateFormat("HH:mm");
		    
		    try {
		         date = dateFormat.parse(rideStartTime);
		         formattedTime = outputFormat.format(date);
		    } catch(ParseException pe) {
				LOGGER.log(Level.INFO, pe.getMessage());	
		    }
		    
		    // Recomputes the new estimated finish time and price corresponding to the distance between source and destination.
	    
		    hashMap = shortestPath.calculateTravel(sourceID, destinationID, formattedTime);
		    if(hashMap==null) {
				LOGGER.log(Level.WARNING, "Please enter a valid set of source and destination.");
				return;
		    }
		    
		    rideEndTime = (String) hashMap.keySet().toArray()[0];
		    price = hashMap.get(rideEndTime);
	        
			taxiDAO.updateRideDetails(bookingID, sourceID, destinationID, formattedTime, rideEndTime, price);
			LOGGER.log(Level.INFO, "Booking details successfully updated.");
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 	
	}
	
	/*
	 *	Cancels the ride booked by the customer. 
	 */
	
	public void cancelRide() {
		
		int bookingID = 0, driverID = 0, cabID = 0;
		boolean check = false;
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>(); 
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			
			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();
		
			bookingID = SCANNER.nextInt();
			check = taxiDAO.checkBookingExists(bookingID);
			
			if(check==false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}
			
			// Fetches corresponding driver and cab ID's.
			
			hashMap = taxiDAO.cancelRide(bookingID);
			
			driverID = (int) hashMap.keySet().toArray()[0];
		    cabID = hashMap.get(driverID);
		    
		    // Updates the driver and cab statuses to be available now.
		    
		    taxiDAO.updateDriverStatus(driverID, 1);
		    taxiDAO.updateCabStatus(cabID, 1);
		    
			LOGGER.log(Level.INFO, "Ride cancelled successfully.");
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		} 		
	}
	
	/*
	 *	Removes customer account and its corresponding ride details. 
	 */
	
	public void deleteAccount() {
		String mail = "";
		Matcher matcher = null;
		boolean check = false;
		int customerID = 0;
		
		try {
			TaxiDAO taxiDAO = new TaxiDAO();
			do{
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
			
				mail = SCANNER.nextLine();
			
				matcher = ConstantVar.VALID_EMAIL_ADDRESS_REGEX.matcher(mail);
				if(matcher.find() == true) {
					check = true;
					break;
				}
				else {
					LOGGER.log(Level.WARNING, "Invalid e-mail. Enter a valid one.");
				}
			}while(check != true);
		
			customerID = taxiDAO.checkMailExists(mail);
			if(customerID==(-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist.");
				return;
			}
			taxiDAO.deleteAccount(mail, customerID);
			LOGGER.log(Level.INFO, "Account deleted successfully.");
		} catch(Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
}
