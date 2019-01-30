package com.zilker.taxi.ui;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.Address;
import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.CustomerDelegate;
import com.zilker.taxi.delegate.TaxiDelegate;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.RegexUtility;

/*
 *	Inputs the personal details and booking details of a customer. 
 */

public class BookTaxi {

	public BookTaxi() {
	}

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);

	public void customerConsole(String userPhone) {

		int choice = 0;
		BookTaxi bookTaxi = null;

		try {
			bookTaxi = new BookTaxi();
			do {
				LOGGER.log(Level.INFO, Constants.DISPLAY_CUSTOMER);
				LOGGER.log(Level.INFO, "Enter your choice: ");

				choice = SCANNER.nextInt();

				switch (choice) {
				
				case 1:
					bookTaxi.bookRide(userPhone);
					break;
				case 2:
					bookTaxi.displayDetails();
					break;
				case 3:
					bookTaxi.updatePersonalDetails();
					break;
				case 4:
					bookTaxi.updateBookingDetails();
					break;
				case 5:
					bookTaxi.cancelRide();
					break;
				case 6:
					bookTaxi.deleteAccount();
					break;
				case 7: //bookTaxi.viewMyRides();
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

	/*
	 * Books a new ride for the customer.
	 */

	public void bookRide(String userPhone) {
		TaxiDelegate taxiDelegate = null;
		SharedDelegate sharedDelegate = null;
		RegexUtility regexUtility = null;
		String source = ""; 
		String destination = ""; 
		String rideStartTime = ""; 
		String mail = ""; 
		String formattedTime = "";
		boolean check = false;
		int customerID = 0; 
		int driverID = 0; 
		int cabID = 0; 
		int sourceID = 0; 
		int destinationID = 0; 
		int bookingID = 0;
		Date date = null;
		DateFormat dateFormat = null; 
		DateFormat outputFormat = null;
		TravelInvoice travelInvoice = null;
		String startDate = "";
		String startTime = "";
		String startTimeDate = "";
		ArrayList<Address> address = null;
		String response = "";
		Address object = null;
		int i = 0; 
		int size = 0;
		int seats = 0;
		int nearestCab = -1;

		try {
			taxiDelegate = new TaxiDelegate();
			sharedDelegate = new SharedDelegate();
			regexUtility = new RegexUtility();
			address = new ArrayList<Address>();
			object = new Address();
			
			
			dateFormat = new SimpleDateFormat("hh:mm aa");
			outputFormat = new SimpleDateFormat("HH:mm");
			
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
				
			do {
				LOGGER.log(Level.INFO, "Enter the start time of your journey[hh:mm AM/PM]: ");
				startTime = SCANNER.next();
				
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
			
			address = taxiDelegate.displayLocations();
			
			size = address.size();
			
			for(i=0;i<size;i++) {
				object = address.get(i);
				LOGGER.log(Level.INFO, object.getAddress() + ", " + object.getZipCode());
			}
			
			
			SCANNER.nextLine();
			
			do {	
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");
				source = SCANNER.nextLine();

				LOGGER.log(Level.INFO, "Enter the drop location: ");
				destination = SCANNER.nextLine();

				sourceID = taxiDelegate.findLocationID(source);
				destinationID = taxiDelegate.findLocationID(destination);

				if (sourceID != (-1) && destinationID != (-1)) {
					System.out.println("success");
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			}while (check != true);

			check = false;

			nearestCab = taxiDelegate.findNearestCab(source);
			
			if(nearestCab==(-1)) {
				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");
				return;
			}
			
			LOGGER.log(Level.INFO, "Enter the number of seats: ");

			seats = SCANNER.nextInt();
					
			driverID = taxiDelegate.getDriverID(nearestCab);
			
			response = taxiDelegate.checkCabSeats(nearestCab, seats);

			if(response.equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
				return;
			} else {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is available.");
			}
			
			/*if (driverID != (-1) && cabID != (-1)) {

				// Computes the estimated finish time and price corresponding to the distance
				// between source and destination.

				travelInvoice = new TravelInvoice(customerID, driverID, nearestCab, sourceID, destinationID, startTimeDate);

				bookingID = taxiDelegate.calculateTravel(travelInvoice, 0);

				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				LOGGER.log(Level.INFO, "Ride successfully booked.");

			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}*/
			
			
			
			
			
			
			/*if (driverID != (-1) && cabID != (-1)) {

				LOGGER.log(Level.INFO, "At what time do you wish to start the ride?(Example: 11:30 PM)");
				rideStartTime = SCANNER.nextLine();

				date = dateFormat.parse(rideStartTime);
				formattedTime = outputFormat.format(date);

				// Computes the estimated finish time and price corresponding to the distance
				// between source and destination.

				travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, formattedTime);

				bookingID = taxiDelegate.calculateTravel(travelInvoice, 0);

				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				LOGGER.log(Level.INFO, "Ride successfully booked.");

			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}*/

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in booking the ride.");
		}
}


	/*
	 * Displays customer profile and booking details.
	 */

	public void displayDetails() {

		int choice = 0;

		LOGGER.log(Level.INFO, "1. Display profile?" + "\n" + "2. Display booking details?" + "\n" + "3. Exit.");
		LOGGER.log(Level.INFO, "Enter your choice: ");

		choice = SCANNER.nextInt();

		switch (choice) {
		case 1:
			displayProfile();
			break;
		case 2:
			displayBookingDetails();
			break;
		case 3:
			return;
		default:
			LOGGER.log(Level.WARNING, "Invalid choice.");
			break;
		}
	}

	/*
	 * Displays customer profile.
	 */

	public void displayProfile() {
		String mail = "";
		boolean check = false;
		Customer customer = null;
		CustomerDelegate customerDelegate = null;

		try {
			customerDelegate = new CustomerDelegate();
			RegexUtility regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();

				mail = SCANNER.nextLine();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			//customer = customerDelegate.displayProfile(mail);

			if (customer == null) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}

			LOGGER.log(Level.INFO, "First name: " + customer.getFirstName() + "\nLast name: " + customer.getLastName()
					+ "\nMail: " + customer.getMailId());

			LOGGER.log(Level.INFO, "Profile displayed.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying the profile.");
		}
	}

	/*
	 * Displays customer booking details.
	 */

	public void displayBookingDetails() {
		int bookingID = -1;
		BookingResponse bookingResponse = null;
		TaxiDelegate taxiDelegate = null;
		
		try {
			taxiDelegate = new TaxiDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();

			bookingResponse = taxiDelegate.displayBookingDetails(bookingID);

			if (bookingResponse == null) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			LOGGER.log(Level.INFO,
					"Booking ID: " + bookingResponse.getBookingID() + "\nSource: " + bookingResponse.getSource()
							+ "\nDestination: " + bookingResponse.getDestination() + "\nPrice: "
							+ bookingResponse.getPrice() + "\nStart time: " + bookingResponse.getStartTime()
							+ "\nEnd time: " + bookingResponse.getEndTime());

			LOGGER.log(Level.INFO, "Booking details displayed.");

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
		}
	}

	/*
	 * Updates customer profile.
	 */

	public void updatePersonalDetails() {

		String mail = ""; 
		String firstName = ""; 
		String lastName = ""; 
		String fullName = "";
		boolean check = false;
		int customerID = 0;
		TaxiDelegate taxiDelegate = null;
		CustomerDelegate customerDelegate = null;
		RegexUtility regexUtility = null;
		Customer customer = null;

		try {
			taxiDelegate = new TaxiDelegate();
			customerDelegate = new CustomerDelegate();
			regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();

				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			check = false;

			customerID = taxiDelegate.checkMailExists(mail);
			if (customerID == (-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}

			SCANNER.nextLine();

			do {
				LOGGER.log(Level.INFO, "Enter first name: ");
				firstName = SCANNER.nextLine();

				LOGGER.log(Level.INFO, "Enter Last name: ");
				lastName = SCANNER.nextLine();

				fullName = firstName + " " + lastName;

				check = regexUtility.validateRegex(Constants.VALID_NAME_REGEX, fullName);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid name. Enter a valid one.");
				}
			} while (check != true);

			customer = new Customer(firstName, lastName, mail);
			customerDelegate.updatePersonalDetails(customer);

			LOGGER.log(Level.INFO, "Profile successfully updated.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in updating profile.");
		}
	}

	/*
	 * Updates the booking details of the customer.
	 */

	public void updateBookingDetails() {

		int bookingID = 0, bID = 0, sourceID = 0, destinationID = 0;
		boolean check = false;
		String startTime = ""; 
		String currentTime = ""; 
		String rideStartTime = ""; 
		String source = ""; 
		String destination = ""; 
		String formattedTime = "";
		Calendar calendar = null;
		SimpleDateFormat simpleDateFormat = null;
		Date startDate = null;
		Date currentDate = null; 
		Date date = null;
		long elapsed = 0; 
		long remaining = 0;
		DateFormat dateFormat = null; 
		DateFormat outputFormat = null;
		TaxiDelegate taxiDelegate = null;
		TravelInvoice travelInvoice = null;
		
		try {
			taxiDelegate = new TaxiDelegate();
			dateFormat = new SimpleDateFormat("hh:mm aa");
			outputFormat = new SimpleDateFormat("HH:mm");
			simpleDateFormat = new SimpleDateFormat("HH:mm");
			
			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();
			bID = taxiDelegate.checkBookingExists(bookingID);

			if (bID == (-1)) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			startTime = taxiDelegate.findStartTime(bookingID);

			calendar = Calendar.getInstance();
			

			currentTime = simpleDateFormat.format(calendar.getTime());
			startDate = simpleDateFormat.parse(startTime);
			currentDate = simpleDateFormat.parse(currentTime);

			elapsed = currentDate.getTime() - startDate.getTime();

			// If the time difference between current time of booking and start time is
			// positive, no updation is possible.

			remaining = elapsed / 3600;

			if (remaining >= 0) {
				LOGGER.log(Level.WARNING, "You can't change your ride details now.");
				return;
			}

			do {
				SCANNER.nextLine();
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");
				source = SCANNER.nextLine();

				LOGGER.log(Level.INFO, "Enter the drop location: ");
				destination = SCANNER.nextLine();

				sourceID = taxiDelegate.findLocationID(source);
				destinationID = taxiDelegate.findLocationID(destination);

				if (sourceID != (-1) && destinationID != (-1)) {
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			} while (check != true);

			check = false;

			LOGGER.log(Level.INFO, "At what time do you wish to start the ride?(Example: 11:30 PM)");
			rideStartTime = SCANNER.nextLine();


			date = dateFormat.parse(rideStartTime);
			formattedTime = outputFormat.format(date);

			travelInvoice = new TravelInvoice(bookingID, 0, 0, sourceID, destinationID, formattedTime);

			bookingID = taxiDelegate.calculateTravel(travelInvoice, 1);

			LOGGER.log(Level.INFO, "Booking details of " + bookingID + " successfully updated.");
		} catch (ParseException pe) {
			LOGGER.log(Level.INFO, "Invalid time. Please enter a valid one.");
		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying ride details.");
		}
	}

	/*
	 * Cancels the ride booked by the customer.
	 */

	public void cancelRide() {

		int bookingID = 0;
		TaxiDelegate taxiDelegate = null;

		try {
			taxiDelegate = new TaxiDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();

			bookingID = taxiDelegate.cancelRide(bookingID);

			if (bookingID == (-1)) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			LOGGER.log(Level.INFO, "Ride cancelled successfully.");

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
		}
	}

	/*
	 * Removes customer account and its corresponding ride details.
	 */

	public void deleteAccount() {
		String mail = "";
		boolean check = false;
		int customerID = 0;
		TaxiDelegate taxiDelegate = null;
		RegexUtility regexUtility = null;

		try {
			taxiDelegate = new TaxiDelegate();
			regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			customerID = taxiDelegate.checkMailExists(mail);

			if (customerID == (-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist.");
				return;
			}

			taxiDelegate.deleteAccount(mail, customerID);

			LOGGER.log(Level.INFO, "Account deleted successfully.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in deleting the account.");
		}
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
	
