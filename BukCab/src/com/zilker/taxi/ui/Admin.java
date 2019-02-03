package com.zilker.taxi.ui;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;


import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelDetail;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.AdminDelegate;
import com.zilker.taxi.util.RegexUtility;

public class Admin {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void adminConsole(String userPhone) {
		
		int choice = 0;
		Admin admin = null;

		try {
			admin = new Admin();
			do {
				LOGGER.log(Level.INFO, "1. Add a cab model." + "\n" + "2. Display cab models." + "\n" + "3. Update a cab model."
						+ "\n" + "4. Delete a cab model." + "\n" + "5. View driver assigned cabs." + "\n" + "6. Logout.");
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					admin.addCabModel(userPhone);
					break;
				case 2:
					admin.displayCabs();
					break;
				case 3:
					admin.updateCabModel(userPhone);
					break;
				case 4:
					admin.deleteCabModel();
					break;
				case 5:
					admin.viewDriverCabs();
					break;
				case 6: return;
				default:
					LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
					break;
				}
			} while (choice != 6);
		} catch (InputMismatchException e) {
			LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
			SCANNER.next();
		}
		
	}
	
	/*
	 * Adds cab model details. 
	 */
	
	public void addCabModel(String userPhone) {
		String cabModelName = "";
		String cabModelDescription = "";
		String licencePlate = "";
		int numSeats = -1;
		boolean check = false;
		CabModel cabModel = null;
		AdminDelegate adminDelegate = null;
		RegexUtility regexUtility = null;

		String response = "";
		
		try {
			
			regexUtility = new RegexUtility();
			cabModel = new CabModel();
			adminDelegate = new AdminDelegate();
		
			SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter the name of the model: ");
			cabModelName = SCANNER.nextLine();
			
			do {
				LOGGER.log(Level.INFO, "Enter the licence plate number: ");
				licencePlate = SCANNER.next();
				
				check = regexUtility.validateRegex(Constants.VALID_LICENCE_PLATE_REGEX, licencePlate);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid licence plate number. Enter a valid one.");
				}
			
				}while(check!=true);
			
			check = false;

			LOGGER.log(Level.INFO, "Enter the description of the model: ");
			cabModelDescription = SCANNER.next();
		
			do {
				LOGGER.log(Level.INFO, "Enter the number of seats in the model: ");
				numSeats = SCANNER.nextInt();
				
				if(numSeats<=0 || numSeats==1) {
					LOGGER.log(Level.INFO, "Invalid number of seats.");
				} else {
					check = true;
					break;
				}
			}while(check!=true);
			
			
			cabModel = new CabModel(cabModelName, cabModelDescription, licencePlate, numSeats);
			response = adminDelegate.addCabModel(cabModel, userPhone);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab model successfully added.");
			} else {
				LOGGER.log(Level.WARNING, "Error in adding cab model details.");
			}
			
		}catch(Exception exception) {
			LOGGER.log(Level.WARNING, "Error in passing cab model details to DAO.");
		}
	}
	
	
	/*
	 * Updates cab model.
	 */

	public void updateCabModel(String userPhone) {

		String licencePlate = ""; 
		String modelName = ""; 
		String modelDescription = ""; 
		int numSeats;
		boolean check = false;
		int modelID = -1;
		AdminDelegate adminDelegate = null;
		RegexUtility regexUtility = null;
		CabModel cabModel = null;
		String response = "";

		try {
			adminDelegate = new AdminDelegate();
			regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter the licence plate number: ");
				licencePlate = SCANNER.next();
				
				check = regexUtility.validateRegex(Constants.VALID_LICENCE_PLATE_REGEX, licencePlate);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid licence plate number. Enter a valid one.");
				}
			
				}while(check!=true);
			
			check = false;

			modelID = adminDelegate.getModelByLicencePlate(licencePlate);
			if (modelID == (-1)) {
				LOGGER.log(Level.WARNING, licencePlate +" doesn't exist.");
				return;
			}

			SCANNER.nextLine();

			LOGGER.log(Level.INFO, "Enter model name: ");
			modelName = SCANNER.nextLine();

			LOGGER.log(Level.INFO, "Enter model description: ");
			modelDescription = SCANNER.nextLine();

			LOGGER.log(Level.INFO, "Enter number of seats: ");
			numSeats = SCANNER.nextInt();

			cabModel = new CabModel(modelName, modelDescription, licencePlate, numSeats);
			response = adminDelegate.updateCabModel(cabModel, userPhone, modelID);

			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab model successfully updated.");
			} else {
				LOGGER.log(Level.WARNING, "Couldn't update model.");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in updating cab model.");
		}
	}
	
	/*
	 * Removes cab model and makes it unavailable for ride.
	 */

	public void deleteCabModel() {
		boolean check = false;
		String licencePlate = ""; 
		AdminDelegate adminDelegate = null;
		RegexUtility regexUtility = null;
		int modelID = -1;
		String response = "";

		try {
			adminDelegate = new AdminDelegate();
			regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter the licence plate number: ");
				licencePlate = SCANNER.next();
				
				check = regexUtility.validateRegex(Constants.VALID_LICENCE_PLATE_REGEX, licencePlate);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid licence plate number. Enter a valid one.");
				}
			
				}while(check!=true);
			
			check = false;

			modelID = adminDelegate.getModelByLicencePlate(licencePlate);
			if (modelID == (-1)) {
				LOGGER.log(Level.WARNING, licencePlate +" doesn't exist.");
				return;
			}

			response = adminDelegate.deleteCabModel(modelID);

			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab model deleted successfully.");
			} else {
				LOGGER.log(Level.WARNING, "Couldn't delete cab model.");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in deleting the account.");
		}
	}
	
	/*
	 * Displays cab model details.
	 */
	
	public void displayCabs() {
		
		AdminDelegate adminDelegate = null;
		ArrayList<CabModelDetail> cabModel = null;
		CabModelDetail object = null;
		int size = -1;
		int i = 0;
		
		LOGGER.log(Level.WARNING, "List of cab model details.");
		
		try {
			
			adminDelegate = new AdminDelegate();
			object = new CabModelDetail();
			cabModel = adminDelegate.displayCabModelDetails();
			
			size = cabModel.size();
			
			for(i=0;i<size;i++) {
				object = cabModel.get(i);
				LOGGER.log(Level.INFO, object.getLicencePlate() + " " + object.getModelName() + " " + object.getModelDescription()
				+ " " + object.getNumSeats());
			}
			
		
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error displaying cab model details.");
		}
	}
	
	/*
	 * Displays details of cab assigned drivers.
	 */
	
	public void viewDriverCabs() {
		
	}
}
