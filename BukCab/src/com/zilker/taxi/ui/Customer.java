package com.zilker.taxi.ui;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.CustomerDelegate;
import com.zilker.taxi.delegate.TaxiDelegate;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.RegexUtility;

public class Customer {
	
	public Customer() {
	}

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void customerConsole(String userPhone) {

		int choice = 0;
		Customer customer = null;

		try {
			customer = new Customer();
			do {
				LOGGER.log(Level.INFO, Constants.DISPLAY_CUSTOMER);
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					customer.bookRide(userPhone);
					break;
				case 2:
					customer.displayDetails(userPhone);
					break;
				case 3:
					customer.updatePersonalDetails();
					break;
				case 4:
					customer.updateBookingDetails();
					break;
				case 5:
					customer.cancelRide();
					break;
				case 6:
					customer.deleteAccount();
					break;
				case 7: 
					customer.viewMyRides();
					break;
				case 8:
					return;
				default:
					LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
					break;
				}
			} while (choice != 8);
		} catch (InputMismatchException e) {
			LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
			SCANNER.next();
		}
	}
	
	public void bookRide(String userPhone) {
		
		CustomerDelegate customerDelegate = null;
		TravelInvoice travelInvoice = null;
		SharedDelegate sharedDelegate = null;
		RegexUtility regexUtility = null;
		Address object = null;
		ArrayList<Address> address = null;
		String response = "";
		String source = ""; 
		String destination = ""; 
		String zipCode = "";
		int customerID = -1; 
		String startDate = "";
		String startTime = "";
		String startTimeDate = "";
		int sourceID = 0; 
		int destinationID = -1; 
		int bookingID = -1;	
		int seats = -1;
		int cabID = -1;
		int driverID = -1; 
		int i = -1; 
		int size = -1;
		boolean check = false;

		try {
			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			regexUtility = new RegexUtility();
			travelInvoice = new TravelInvoice();

		
			customerID = sharedDelegate.getUserID(userPhone);

			do {
				LOGGER.log(Level.INFO, "Enter the date of your journey[dd-mm-yyyy]: ");
				startDate = SCANNER.next();
				
				check = isValidFormat(startDate, 0);
				if(check==true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid date format. Enter a valid one.");
				}
			}while(check!=true);
				
			check = false;
			
			SCANNER.nextLine();
				
			do {
				LOGGER.log(Level.INFO, "Enter the start time of your journey[hh:mm AM/PM]: ");
				startTime = SCANNER.nextLine();
				
				System.out.println(startTime);
				check = isValidFormat(startTime, 1);
				if(check==true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid time format. Enter a valid one.");
				}
			}while(check!=true);
				
			check = false;	
				
			startTimeDate = startDate + " " + startTime;
			
			LOGGER.log(Level.INFO, "Choose a source and destination location from these.");
			
			address = customerDelegate.displayLocations();
			
			size = address.size();
			
			for(i=0;i<size;i++) {
				object = address.get(i);
				LOGGER.log(Level.INFO, object.getAddress() + ", " + object.getZipCode());
			}
		
			do {	
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");
				source = SCANNER.nextLine();

				LOGGER.log(Level.INFO, "Enter the drop location: ");
				destination = SCANNER.nextLine();

				sourceID = customerDelegate.findLocationID(source);
				destinationID = customerDelegate.findLocationID(destination);

				if (sourceID != (-1) && destinationID != (-1)) {
					zipCode = customerDelegate.findZipByID(sourceID);
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			}while (check != true);

			check = false;
			
			LOGGER.log(Level.INFO, "Enter the number of seats: ");

			seats = SCANNER.nextInt();
			
			cabID = customerDelegate.findNearestCab(zipCode);
			
			if(cabID==(-1)) {
				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");
				return;
			}	
			
			driverID = customerDelegate.getDriverID(cabID);
			
			response = customerDelegate.checkCabSeats(cabID, seats);

			if(response.equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
				return;
			} else {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is available.");
			}
			
			if (driverID != (-1) && cabID != (-1)) {

			// Computes the estimated finish time and price corresponding to the distance
			// between source and destination.

				travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate);

				bookingID = customerDelegate.calculateTravel(travelInvoice, 0);

				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				LOGGER.log(Level.INFO, "Ride successfully booked.");

		} else {
			LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
		}
	
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in booking the ride.");
		}
		
		//Send Notification to the driver.
		
	}
	
	public void displayDetails(String userPhone) {
		int choice = 0;

		LOGGER.log(Level.INFO, "1. Display profile?" + "\n" + "2. Display booking details?" + "\n" + "3. Exit.");
		LOGGER.log(Level.INFO, "Enter your choice: ");

		choice = SCANNER.nextInt();

		switch (choice) {
		case 1:
			displayProfile(userPhone);
			break;
		case 2:
			displayBookingDetails();
			break;
		case 3:
			return;
		default:
			LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one");
			break;
		}
	}
	
	/*
	 * Displays customer profile.
	 */

	public void displayProfile(String userPhone) {
		String mail = "";
		boolean check = false;
		Customer customer = null;
		CustomerDelegate customerDelegate = null;

		try {
			customerDelegate = new CustomerDelegate();
			
			//customer = customerDelegate.displayProfile(mail);

			LOGGER.log(Level.INFO, "First name: " + customer.getFirstName() + "\nLast name: " + customer.getLastName()
					+ "\nMail: " + customer.getMailId());

			LOGGER.log(Level.INFO, "Profile displayed.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying the profile.");
		}
	}
	
	public void updatePersonalDetails() {
		
	}
	
	public void updateBookingDetails() {
		
	}
	
	public void cancelRide() {
		
	}
	
	public void deleteAccount() {
		
	}

	public void viewMyRides() {
	
}
	boolean isValidFormat(String input, int flag) {
		SimpleDateFormat date = null;
		SimpleDateFormat time = null;
		
		try {
			if(flag==0) {
				date = new SimpleDateFormat("dd-MM-yyyy");
				date.parse(input);
		        return true;	
		} else {
			time = new SimpleDateFormat("hh:mm aa");
			time.parse(input);
	        return true;
		}
		}catch(ParseException e){
	          return false;
	     }     
	}
	
	
}


