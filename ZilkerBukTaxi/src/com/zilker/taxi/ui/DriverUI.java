package com.zilker.taxi.ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.TaxiDelegate;

public class DriverUI {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void driverConsole(String userPhone) {
		
		int choice = 0;
		DriverUI driverUI = null;

		try {
			driverUI = new DriverUI();
			do {
				LOGGER.log(Level.INFO, "1. Add Licence number." + "\n" + "2. View my rides." + "\n" + "3. Logout.");
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					driverUI.addLicenceDetails(userPhone);
					break;
				case 2:
					//driverUI.viewMyRides();
					break;
				case 3:
					return;
				default:
					LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
					break;
				}
			} while (choice != 3);
		} catch (InputMismatchException e) {
			LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
			SCANNER.next();
		}	
	}
	
	private void addLicenceDetails(String userPhone) {
		String licenceNumber = "";
		boolean check = false;
		TaxiDelegate taxiDelegate = null;
		String response = "";

		try {
			taxiDelegate = new TaxiDelegate();
			
			do {
				LOGGER.log(Level.INFO, "Enter your licence number[5 characters]: ");
				licenceNumber = SCANNER.next();
		
				if(licenceNumber.length() == 5) {
					check = true;
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid licence number. Enter a valid one. ");
				}
			}while(check!=true);
			
			response = taxiDelegate.addLicenceDetails(licenceNumber, userPhone);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab successfully allocated!");
			} else {
				LOGGER.log(Level.WARNING, "Licence number already exists!Please login.");
			}
			
		}catch(Exception exception) {
			LOGGER.log(Level.INFO, "Error in passing licence details to DAO.");
		}	
		
	}

}
