package com.zilker.taxi.ui;


import java.util.ArrayList;
import java.util.InputMismatchException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.RideHistory;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.DriverDelegate;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.RegexUtility;

/*
 * Lists all the driver functionalities.
 */

public class Driver {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void driverConsole(String userPhone) {
		
		int choice = 0;
		Driver driver = null;

		try {
			driver = new Driver();
			do {
				LOGGER.log(Level.INFO, Constants.DRIVER_MESSAGE);
				
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					driver.addLicenceDetails(userPhone);
					break;
				case 2:
					driver.completeRide(userPhone);
					break;
				case 3:
					driver.viewRides(userPhone);
					break;
				case 4:
					driver.viewRidesByLocation(userPhone);
					break;
				case 5:
					return;
				default:
					LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
					break;
				}
			} while (choice != 5);
		} catch (InputMismatchException e) {
			LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
			SCANNER.next();
		}
	}
	
	/*
	 * Adds licence details of the driver.
	 */
	
	private void addLicenceDetails(String userPhone) {
		String licenceNumber = "";
		String zipCode = "";
		boolean check = false;
		DriverDelegate driverDelegate = null;
		String response = "";
		RegexUtility regexUtility = null;


		try {
			driverDelegate = new DriverDelegate();
			regexUtility = new RegexUtility();
			
			LOGGER.log(Level.INFO, "Enter your licence number[5 characters]: ");
			licenceNumber = SCANNER.next();
	
			/*do {
				LOGGER.log(Level.INFO, "Enter your licence number[5 characters]: ");
				licenceNumber = SCANNER.next();
		
				check = regexUtility.validateRegex(Constants.VALID_LICENCE_REGEX, licenceNumber);
				if(check==true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid licence number. Enter a valid one. ");
				}
			}while(check!=true);*/
			
			check = false;
			
			SCANNER.nextLine();
			do {
				LOGGER.log(Level.INFO, "Enter pin-code of your current location: ");
				zipCode = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_ZIPCODE_REGEX, zipCode);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid zip-code. Enter a valid one.");
				}
			} while (check != true);
					
			response = driverDelegate.addLicenceDetails(licenceNumber, userPhone, zipCode);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab successfully allocated!");
			} 
			
		}catch(Exception exception) {
			LOGGER.log(Level.WARNING, "Error in adding licence details.");
		}	
		
	}
	
	/*
	 * Completes an on-going ride.
	 */
	public void completeRide(String userPhone) {
		int driverID = -1;
		SharedDelegate sharedDelegate = null;
		DriverDelegate driverDelegate = null;
		String response = "";
		String zipCode = "";
		boolean check = false;
		RegexUtility regexUtility = null;

		
		try {
			sharedDelegate = new SharedDelegate();
			driverDelegate = new DriverDelegate();
			regexUtility = new RegexUtility();
			driverID = sharedDelegate.getUserID(userPhone);
			
			do {
				LOGGER.log(Level.INFO, "Enter pin-code of the destination reached: ");
				zipCode = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_ZIPCODE_REGEX, zipCode);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid zip-code. Enter a valid one.");
				}
			} while (check != true);
			
			response = driverDelegate.completeRide(zipCode, driverID);
			
			if(response.equals(Constants.SUCCESS)) {
				do {
					LOGGER.log(Level.INFO, "Enter new-pincode of your current location: ");
					zipCode = SCANNER.next();

					check = regexUtility.validateRegex(Constants.VALID_ZIPCODE_REGEX, zipCode);
					if (check == true) {
						break;
					} else {
						LOGGER.log(Level.WARNING, "Invalid zip-code. Enter a valid one.");
					}
				} while (check != true);
				
				response = driverDelegate.updateLocation(zipCode, driverID);
	
				if(response.equals(Constants.SUCCESS)) {
					LOGGER.log(Level.INFO, "Location successfully updated.");
				} 
				
				LOGGER.log(Level.INFO, "Ride completed successfully.");
			} else {
				LOGGER.log(Level.WARNING, "The zip-code you entered is wrong.");
			}
			
		}  catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in completing the ride.");
		}
	}
	
	/*
	 * Views the rides of a driver.
	 */
	public void viewRides(String userPhone) {
		int driverID = -1;
		RideHistory object = null;
		ArrayList<RideHistory> history = null;
		SharedDelegate sharedDelegate = null;
		int i = -1;
		int size = -1;

		try {
			sharedDelegate = new SharedDelegate();
			driverID = sharedDelegate.getUserID(userPhone);

			history = sharedDelegate.displayRideHistory(driverID, 1);
			
			if(history==null) {
				LOGGER.log(Level.INFO, "You haven't taken any rides yet.");
				return;
			}
		
			size = history.size();
		
			for(i=0;i<size;i++) {
				object = history.get(i);
				
				LOGGER.log(Level.INFO,
						"Source: " + object.getSource() + "\nDestination: " + object.getDestintion() + 
						"\nPrice: "+ object.getPrice() + "\nStart time: " + object.getDate()+ 
						"\nCustomer name-contact : " + object.getPerson() +  "\nCab details : " + object.getCab());

				switch(object.getStatusID()) {
					case 1: LOGGER.log(Level.INFO, "Ride booked.");
							break;
					case 2: LOGGER.log(Level.INFO, "Ride started.");
							break;
					case 3: LOGGER.log(Level.INFO, "Ride completed.");
							break;
					case 4: LOGGER.log(Level.INFO, "Ride cancelled.");
							break;
				}
			}
		}  catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying ride history.");
		}
		
	}
	
	/*
	 * Views rides by location.
	 */
	public void viewRidesByLocation(String userPhone) {
		int driverID = -1;
		boolean check = false;
		String zipCode = "";
		RideHistory object = null;
		ArrayList<RideHistory> history = null;
		SharedDelegate sharedDelegate = null;
		RegexUtility regexUtility = null;

		int i = -1;
		int size = -1;
		
		try {
			sharedDelegate = new SharedDelegate();
			regexUtility = new RegexUtility();
			driverID = sharedDelegate.getUserID(userPhone);
			
			do {
				LOGGER.log(Level.INFO, "Enter pin-code: ");
				zipCode = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_ZIPCODE_REGEX, zipCode);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid zip-code. Enter a valid one.");
				}
			} while (check != true);
			
			check = false;
			
			history = sharedDelegate.displayRideHistoryByLocation(driverID, zipCode);
			
			if(history==null) {
				LOGGER.log(Level.INFO, "You haven't taken any rides in" + "yet.");
				return;
			}
		
			size = history.size();
		
			for(i=0;i<size;i++) {
				object = history.get(i);
				
				LOGGER.log(Level.INFO,
						"Source: " + object.getSource() + "\nDestination: " + object.getDestintion() + 
						"\nPrice: "+ object.getPrice() + "\nStart time: " + object.getDate()+ 
						"\nCustomer name-contact : " + object.getPerson() +  "\nCab details : " + object.getCab());

				switch(object.getStatusID()) {
					case 1: LOGGER.log(Level.INFO, "Ride booked.");
							break;
					case 2: LOGGER.log(Level.INFO, "Ride started.");
							break;
					case 3: LOGGER.log(Level.INFO, "Ride completed.");
							break;
					case 4: LOGGER.log(Level.INFO, "Ride cancelled.");
							break;
				}
			}
		}  catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying ride history.");
		}

	}
	
	
	
	
	
	
}
