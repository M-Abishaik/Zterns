package com.zilker.taxi.ui;


import java.util.InputMismatchException;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.DriverDelegate;
import com.zilker.taxi.util.RegexUtility;


public class Driver {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void driverConsole(String userPhone) {
		
		int choice = 0;
		Driver driver = null;

		try {
			driver = new Driver();
			do {
				LOGGER.log(Level.INFO, "1. Add Licence number." + "\n" + "2. Complete a ride." + 
						"\n" + "3. View my rides." + "\n" + "4. View rides by location." + "\n" +"5. Logout.");
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					driver.addLicenceDetails(userPhone);
					break;
				case 2:
					driver.completeRide();
					break;
				case 3:
					driver.viewRides();
					return;
				case 4:
					driver.viewRidesByLocation();
					return;
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
			LOGGER.log(Level.INFO, "Enter the zipcode of your current location: ");
			zipCode = SCANNER.nextLine();
			
			response = driverDelegate.addLicenceDetails(licenceNumber, userPhone, zipCode);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab successfully allocated!");
			} 
			
		}catch(Exception exception) {
			LOGGER.log(Level.INFO, "Error in adding licence details.");
		}	
		
	}
	
	public void completeRide() {
		
	}
	
	public void viewRides() {
		
	}
	
	public void viewRidesByLocation() {
		
	}
	
	
	
	
	
	
}
