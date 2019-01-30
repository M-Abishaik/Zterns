package com.zilker.taxi.ui;

import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.RegexUtility;

public class MasterUI {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);

	
	public static void main(String args[]) {
		int choice = 0;
		MasterUI masterUI = null;

		try {
			do {
				masterUI = new MasterUI();
				
				LOGGER.log(Level.INFO, "Taxi Booking Application.");
				LOGGER.log(Level.INFO,"1. Register." + "\n" + "2. Login." + "\n" + "3. Exit.");
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				case 1:
					masterUI.register();
					break;
				case 2:
					masterUI.login();
					break;
				case 3:
					System.exit(0);
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
	
	private void register() {
		String userName = "";
		String contact = "";
		String mail = "";
		String userRole = "";
		String password = "";
		String rePass = "";
		String address = "";
		String city = "";
		String zipCode = "";
		String response = "";
		boolean check = false;
		int passLength = 0;
		RegexUtility regexUtility = null;
		SharedDelegate sharedDelegate = null;
		
		LOGGER.log(Level.INFO, "Please fill the details below.");
		
		try {
			regexUtility = new RegexUtility();
			sharedDelegate = new SharedDelegate();
			
			SCANNER.nextLine();
			
			do {
				LOGGER.log(Level.INFO, "Enter your username:");
				userName = SCANNER.nextLine();
			
				check = regexUtility.validateRegex(Constants.VALID_NAME_REGEX, userName);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid name. Enter a valid one.");
				}	
			}while(check!=true);
		
			check = false;
			
			do {
				LOGGER.log(Level.INFO, "Enter your contact number[Only 10 digits]:");
				contact = SCANNER.next();
			
				check = regexUtility.validateRegex(Constants.VALID_MOBILE_REGEX, contact);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid contact number. Enter a valid one.");
				}	
			}while(check!=true);
		
			check = false;
			
			do {
				LOGGER.log(Level.INFO, "Enter your mail: ");
				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			check = false;
			
			do {
				LOGGER.log(Level.INFO, "As whom do you want to register(Customer or Driver or Admin)?");
				userRole = SCANNER.next();
				
				userRole = userRole.toLowerCase();
				
				if(((userRole.equals(Constants.ADMIN)) || (userRole.equals(Constants.CUSTOMER)) || (userRole.equals(Constants.DRIVER)))) {
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid role. Enter a valid one.");
				}
			} while (check != true);

			check = false;
			
			SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter your address: ");
			address = SCANNER.nextLine();
			
			LOGGER.log(Level.INFO, "Enter the city: ");
			city = SCANNER.nextLine();
			
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
			
			SCANNER.nextLine();
			
			do {
				LOGGER.log(Level.INFO, "Enter your password: ");
				password = SCANNER.nextLine();

				passLength = password.length();
				check = regexUtility.validateRegex(Constants.VALID_PASSWORD_REGEX, password);
				if ((passLength>=8 && passLength<=15) && (check == true)) {
					break;
				} else {
					check = false;
					LOGGER.log(Level.WARNING, "Invalid password. Enter a valid one.");
				}
			} while (check != true);

			check = false;
			
			do {
				LOGGER.log(Level.INFO, "Re-enter your password: ");
				rePass = SCANNER.next();

				if (rePass.equals(password)) {
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Passwords don't match.");
				}
			} while (check != true);

			User user = new User(userName, mail, contact, userRole, password, address, city, zipCode);
			response = sharedDelegate.register(user);
			
			if(response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Account successfully created.");
			} else {
				LOGGER.log(Level.WARNING, "Account already exists!Please login.");
			}
			
		}  catch (InputMismatchException inputMismatchException) {
			LOGGER.log(Level.SEVERE, "Invalid input type. Enter a valid one.");
		}catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Error in registering an account.");
		}
	}
	
	private void login() {
		
		String userPhone = "";
		String password = "";
		String response = "";
		RegexUtility regexUtility = null;
		SharedDelegate sharedDelegate = null;
		
		BookTaxi bookTaxi = null;
		AdminUI adminUI = null;
		DriverUI driverUI = null;
		
		boolean check = false;
		
		try {
			regexUtility = new RegexUtility();
			sharedDelegate = new SharedDelegate();
									
			do {
				LOGGER.log(Level.INFO, "Enter your registered contact number: ");
				userPhone = SCANNER.next();
			
				check = regexUtility.validateRegex(Constants.VALID_MOBILE_REGEX, userPhone);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid contact number. Enter a valid one.");
				}	
			}while(check!=true);
		
			check = false;
			
			SCANNER.nextLine();
			LOGGER.log(Level.INFO, "Enter your password:");
			password = SCANNER.nextLine();
			
			response = sharedDelegate.login(userPhone, password);
			
			if(!response.equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO, "Welcome back " + response +"!");
				
				if(response.equals(Constants.CUSTOMER)) {
					bookTaxi = new BookTaxi();
					bookTaxi.customerConsole(userPhone);
				}else if(response.equals(Constants.ADMIN)) {
					adminUI = new AdminUI();
					adminUI.adminConsole(userPhone);
				} else {
					driverUI = new DriverUI();
					driverUI.driverConsole(userPhone);
				}
				
			} else {
				LOGGER.log(Level.WARNING, "Invalid login credentials.");
			}
			
		}  catch (InputMismatchException inputMismatchException) {
			LOGGER.log(Level.SEVERE, "Invalid input type. Enter a valid one.");
		}catch (Exception exception) {
			LOGGER.log(Level.SEVERE, "Error in logging into account.");
		}
	}

}
