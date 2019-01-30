package com.zilker.taxi.ui;


import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.CabDetail;
import com.zilker.taxi.bean.CabModel;
import com.zilker.taxi.bean.CabModelID;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.AdminDelegate;
import com.zilker.taxi.util.RegexUtility;

public class AdminUI {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);
	
	public void adminConsole(String userPhone) {
		
		int choice = 0;
		AdminUI adminUI = null;

		try {
			adminUI = new AdminUI();
			do {
				LOGGER.log(Level.INFO, "1. Add cab model details." + "\n" + "2. Launch cab." + "\n" + "3. Logout.");
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					adminUI.addCabModelDetails(userPhone);
					break;
				case 2:
					adminUI.launchCab(userPhone);
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
	
	/*
	 * Adds cab model details. 
	 */
	
	public void addCabModelDetails(String userPhone) {
		String cabModelName = "";
		String cabModelDescription = "";
		int numSeats = -1;
		boolean check = false;
		CabModel cabModel = null;
		AdminDelegate adminDelegate = null;
		String response = "";
		
		try {
			
			cabModel = new CabModel();
			adminDelegate = new AdminDelegate();
		
			SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter the name of the model: ");
			cabModelName = SCANNER.nextLine();

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
			
			
			cabModel = new CabModel(cabModelName, cabModelDescription, numSeats);
			response = adminDelegate.addCabModelDetails(cabModel, userPhone);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab model successfully added.");
			} else {
				LOGGER.log(Level.WARNING, "Error in adding cab model details.");
			}
			
		}catch(Exception exception) {
			LOGGER.log(Level.WARNING, "Error in passing cab model details to DAO.");
		}
	}
	
	public void launchCab(String userPhone) {
		String licencePlate = "";
		String lat = "";
		String lng = "";
		float latitude = 0.0f;
		float longitude = 0.0f;
		int modelID = -1;
		boolean check = false;
		RegexUtility regexUtility = null;
		CabDetail cabDetail = null;
		String response = "";
		AdminDelegate adminDelegate = null;
		ArrayList<CabModelID> cabModel = null;
		CabModelID object = null;
		int size = -1;
		int i = 0;
		
		LOGGER.log(Level.WARNING, "List of cab model details. Choose a cab to launch.");
		
		try {
			
			adminDelegate = new AdminDelegate();
			object = new CabModelID();
			cabModel = adminDelegate.displayCabModelDetails();
			
			size = cabModel.size();
			
			for(i=0;i<size;i++) {
				object = cabModel.get(i);
				LOGGER.log(Level.INFO, object.getModelID() + " " + object.getModelName() + " " + object.getModelDescription()
				+ " " + object.getNumberSeats());
			}
			
			regexUtility = new RegexUtility();
			cabDetail = new CabDetail();
			adminDelegate = new AdminDelegate();
			
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
			
			do {
				LOGGER.log(Level.INFO, "Enter the latitude: ");
				lat = SCANNER.next();
				
				LOGGER.log(Level.INFO, "Enter the longitude: ");
				lng = SCANNER.next();
				
				check = isValidFormat(lat, lng);
				if(check==true) {
					latitude = Float.parseFloat(lat);
					longitude = Float.parseFloat(lng);
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid latitude and longitude. Enter a valid one.");
				}
			}while(check!=true);
				
			check = false;	
			
			LOGGER.log(Level.INFO, "Enter the model ID of the cab ");
			modelID = SCANNER.nextInt();
			
			cabDetail = new CabDetail(licencePlate, latitude, longitude, modelID);
			response = adminDelegate.addCabLaunchDetails(cabDetail, userPhone);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Cab model successfully launched.");
			} else {
				LOGGER.log(Level.WARNING, "Error in launching cab model details.");
			}
		
		} catch(InputMismatchException inputMismatchException) {
			LOGGER.log(Level.INFO, "Invalid model ID. Enter a valid one.");
		}catch(Exception e) {
			LOGGER.log(Level.WARNING, "Error in passing cab launch details to DAO.");
		}
	}
	

	boolean isValidFormat(String latitude, String longitude) {
		float validLatitude = 0.0f;
		float validLongitude = 0.0f;
		
		try {
			validLatitude = Float.parseFloat(latitude);
			validLongitude = Float.parseFloat(longitude);
			
			return true;
		}catch(NumberFormatException numberFormatException){
	          return false;
	     }     
	}
} 
