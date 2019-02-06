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
import com.zilker.taxi.bean.Profile;
import com.zilker.taxi.bean.RideHistory;
import com.zilker.taxi.bean.TravelInvoice;
import com.zilker.taxi.bean.User;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.delegate.CustomerDelegate;
import com.zilker.taxi.shared.SharedDelegate;
import com.zilker.taxi.util.RegexUtility;

/*
 * Customer UI.
 */

public class Customer {

	public Customer() {
	}

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);

	/*
	 * Lists Customer functionalities.
	 */
	public void customerConsole(String userPhone) {

		int choice = 0;
		Customer customer = null;

		do {
			try {
				customer = new Customer();

				LOGGER.log(Level.INFO, Constants.CUSTOMER_MESSAGE);
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
					customer.updatePersonalDetails(userPhone);
					break;
				case 4:
					customer.updateBookingDetails();
					break;
				case 5:
					customer.cancelRide();
					break;
				case 6:
					customer.viewMyRides(userPhone);
					break;
				case 7:
					customer.rateMyTrips(userPhone);
					break;
				case 8:
					return;
				default:
					LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
					break;
				}
			} catch (InputMismatchException e) {
				LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
				SCANNER.next();
			}
		} while (choice != 8);
	}

	/*
	 * Books a ride for customer.
	 */

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
		String driver = "";
		String cab = "";
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
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid date format. Enter a valid one.");
				}
			} while (check != true);

			check = false;

			SCANNER.nextLine();

			do {
				LOGGER.log(Level.INFO, "Enter the start time of your journey[hh:mm AM/PM]: ");
				startTime = SCANNER.nextLine();

				check = isValidFormat(startTime, 1);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid time format. Enter a valid one.");
				}
			} while (check != true);

			check = false;

			startTimeDate = startDate + " " + startTime;

			LOGGER.log(Level.INFO, "Choose a source and destination location from these.");

			address = customerDelegate.displayLocations();

			size = address.size();

			for (i = 0; i < size; i++) {
				object = address.get(i);
				LOGGER.log(Level.INFO, object.getAddress() + ", " + object.getZipCode());
			}

			do {
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");
				source = SCANNER.nextLine();

				source = source.toLowerCase();

				LOGGER.log(Level.INFO, "Enter the drop location: ");
				destination = SCANNER.nextLine();

				destination = destination.toLowerCase();

				sourceID = customerDelegate.findLocationID(source);
				destinationID = customerDelegate.findLocationID(destination);

				if (sourceID != (-1) && destinationID != (-1)) {
					zipCode = customerDelegate.findZipByID(sourceID);
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			} while (check != true);

			check = false;

			LOGGER.log(Level.INFO, "Enter the number of seats: ");

			seats = SCANNER.nextInt();

			cabID = customerDelegate.findNearestCab(zipCode, 0);

			if (cabID == (-1)) {
				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");
				return;
			}

			driverID = customerDelegate.getDriverID(cabID);

			response = customerDelegate.checkCabSeats(cabID, seats);

			if (response.equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO,
						"The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
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

				cab = sharedDelegate.findCabByID(cabID);

				driver = sharedDelegate.findDriverByID(driverID);

				LOGGER.log(Level.INFO, "Driver and cab details: ");

				LOGGER.log(Level.INFO, driver + "\n" + cab);

			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in booking the ride.");
		}

	}

	/*
	 * Displays profile/booking details of customer.
	 */
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
		Profile profile = null;
		SharedDelegate sharedDelegate = null;

		try {
			sharedDelegate = new SharedDelegate();

			profile = sharedDelegate.displayProfile(userPhone);

			LOGGER.log(Level.INFO, "Name: " + profile.getUserName() + "\nE-mail: " + profile.getMail() + "\nContact: "
					+ profile.getContact() + "\nRole: " + profile.getRole() + "\nAddress: " + profile.getAddress()
					+ "\nCity: " + profile.getCity() + "\nZip-code: " + profile.getZipCode());

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
		SharedDelegate sharedDelegate = null;

		try {
			sharedDelegate = new SharedDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");

			bookingID = SCANNER.nextInt();

			bookingResponse = sharedDelegate.displayBookingDetails(bookingID);

			if (bookingResponse == null) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			LOGGER.log(Level.INFO,
					"Booking ID: " + bookingResponse.getBookingID() + "\nSource: " + bookingResponse.getSource()
							+ "\nDestination: " + bookingResponse.getDestination() + "\nPrice: "
							+ bookingResponse.getPrice() + "\nStart time: " + bookingResponse.getStartTime()
							+ "\nDriver name-contact : " + bookingResponse.getDriver() + "\nCab details : "
							+ bookingResponse.getCab());

			switch (bookingResponse.getStatusID()) {
			case 1:
				LOGGER.log(Level.INFO, "Ride booked.");
				break;
			case 2:
				LOGGER.log(Level.INFO, "Ride started.");
				break;
			case 3:
				LOGGER.log(Level.INFO, "Ride completed.");
				break;
			case 4:
				LOGGER.log(Level.INFO, "Ride cancelled.");
				break;
			}

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying booking details.");
		}
	}

	/*
	 * Updates customer profile.
	 */
	public void updatePersonalDetails(String userPhone) {

		String mail = "";
		String userName = "";
		String address = "";
		String city = "";
		String zipCode = "";
		String password = "";
		String response = "";
		String userRole = Constants.CUSTOMER;
		String rePass = "";
		boolean check = false;
		int passLength = -1;
		SharedDelegate sharedDelegate = null;
		CustomerDelegate customerDelegate = null;
		RegexUtility regexUtility = null;

		try {
			sharedDelegate = new SharedDelegate();
			customerDelegate = new CustomerDelegate();
			regexUtility = new RegexUtility();

			do {
				SCANNER.nextLine();
				LOGGER.log(Level.INFO, "Enter your username:");
				userName = SCANNER.nextLine();

				check = regexUtility.validateRegex(Constants.VALID_NAME_REGEX, userName);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid name. Enter a valid one.");
				}
			} while (check != true);

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

			SCANNER.nextLine();
			LOGGER.log(Level.INFO, "Enter your address: ");
			address = SCANNER.nextLine();

			address = address.toLowerCase();

			LOGGER.log(Level.INFO, "Enter the city: ");
			city = SCANNER.nextLine();

			city = city.toLowerCase();

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

			do {
				SCANNER.nextLine();
				LOGGER.log(Level.INFO, "Enter your password: ");
				password = SCANNER.nextLine();

				passLength = password.length();
				check = regexUtility.validateRegex(Constants.VALID_PASSWORD_REGEX, password);
				if ((passLength >= 8 && passLength <= 15) && (check == true)) {
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

			User user = new User(userName, mail, userPhone, userRole, password, address, city, zipCode);
			response = sharedDelegate.updateProfile(user);

			if (response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Account successfully updated.");
			}

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in updating profile.");
		}

	}

	/*
	 * Updates booking details of a customer.
	 */

	public void updateBookingDetails() {

		Address object = null;
		ArrayList<Address> address = null;
		CustomerDelegate customerDelegate = null;
		SharedDelegate sharedDelegate = null;
		TravelInvoice travelInvoice = null;
		boolean check = false;
		boolean elapsed = false;
		String startTime = "";
		int bookingID = -1;
		String startDate = "";
		String startTimeDate = "";
		String response = "";
		String source = "";
		String destination = "";
		String zipCode = "";
		int sourceID = 0;
		int destinationID = -1;
		int i = -1;
		int size = -1;
		int seats = -1;
		int cabID = -1;
		int driverID = -1;

		try {
			customerDelegate = new CustomerDelegate();
			sharedDelegate = new SharedDelegate();
			travelInvoice = new TravelInvoice();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();
			check = sharedDelegate.checkBookingExists(bookingID);

			if (check == false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			startTime = customerDelegate.findStartTime(bookingID);

			// If the time difference between current time of booking and start time is
			// positive, no updation is possible.

			elapsed = checkTimeElapsed(startTime);

			if (elapsed == true) {
				LOGGER.log(Level.WARNING, "You can't change your ride details now.");
				return;
			}

			do {
				LOGGER.log(Level.INFO, "Enter the date of your journey[dd-mm-yyyy]: ");
				startDate = SCANNER.next();

				check = isValidFormat(startDate, 0);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid date format. Enter a valid one.");
				}
			} while (check != true);

			check = false;

			SCANNER.nextLine();

			do {
				LOGGER.log(Level.INFO, "Enter the start time of your journey[hh:mm AM/PM]: ");
				startTime = SCANNER.nextLine();

				check = isValidFormat(startTime, 1);
				if (check == true) {
					break;
				} else {
					LOGGER.log(Level.INFO, "Invalid time format. Enter a valid one.");
				}
			} while (check != true);

			check = false;

			startTimeDate = startDate + " " + startTime;

			LOGGER.log(Level.INFO, "Choose a source and destination location from these.");

			address = customerDelegate.displayLocations();

			size = address.size();

			for (i = 0; i < size; i++) {
				object = address.get(i);
				LOGGER.log(Level.INFO, object.getAddress() + ", " + object.getZipCode());
			}

			do {
				LOGGER.log(Level.INFO, "Enter the pick-up location: ");
				source = SCANNER.nextLine();

				source = source.toLowerCase();

				LOGGER.log(Level.INFO, "Enter the drop location: ");
				destination = SCANNER.nextLine();

				destination = destination.toLowerCase();

				sourceID = customerDelegate.findLocationID(source);
				destinationID = customerDelegate.findLocationID(destination);

				if (sourceID != (-1) && destinationID != (-1)) {
					zipCode = customerDelegate.findZipByID(sourceID);
					check = true;
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid location(s). Enter a valid one.");
				}
			} while (check != true);

			check = false;

			LOGGER.log(Level.INFO, "Enter the number of seats: ");

			seats = SCANNER.nextInt();

			cabID = customerDelegate.findNearestCab(zipCode, 1);

			if (cabID == (-1)) {
				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");
				return;
			}

			driverID = customerDelegate.getDriverID(cabID);

			response = customerDelegate.checkCabSeats(cabID, seats);

			if (response.equals(Constants.FAILURE)) {
				LOGGER.log(Level.INFO,
						"The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
				return;
			} else {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is available.");
			}

			travelInvoice = new TravelInvoice(bookingID, driverID, cabID, sourceID, destinationID, startTimeDate);

			customerDelegate.calculateTravel(travelInvoice, 1);

			LOGGER.log(Level.INFO, "Booking details of " + bookingID + " successfully updated.");
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
		boolean check = false;
		SharedDelegate sharedDelegate = null;

		try {
			sharedDelegate = new SharedDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();

			check = sharedDelegate.cancelRide(bookingID);

			if (check == false) {
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

	public void viewMyRides(String userPhone) {
		int customerID = -1;
		RideHistory object = null;
		ArrayList<RideHistory> history = null;
		SharedDelegate sharedDelegate = null;
		int i = -1;
		int size = -1;

		try {
			sharedDelegate = new SharedDelegate();
			customerID = sharedDelegate.getUserID(userPhone);

			history = sharedDelegate.displayRideHistory(customerID, 0);

			if (history == null) {
				LOGGER.log(Level.INFO, "You haven't booked any rides yet.");
				return;
			}

			size = history.size();

			for (i = 0; i < size; i++) {
				object = history.get(i);

				LOGGER.log(Level.INFO,
						"Source: " + object.getSource() + "\nDestination: " + object.getDestintion() + "\nPrice: "
								+ object.getPrice() + "\nStart time: " + object.getDate() + "\nDriver name-contact : "
								+ object.getPerson() + "\nCab details : " + object.getCab());

				switch (object.getStatusID()) {
				case 1:
					LOGGER.log(Level.INFO, "Ride booked.");
					break;
				case 2:
					LOGGER.log(Level.INFO, "Ride started.");
					break;
				case 3:
					LOGGER.log(Level.INFO, "Ride completed.");
					break;
				case 4:
					LOGGER.log(Level.INFO, "Ride cancelled.");
					break;
				}
			}
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in displaying ride history.");
		}
	}

	/*
	 * Gives a rating to the ride.
	 */
	public void rateMyTrips(String userPhone) {
		SharedDelegate sharedDelegate = null;
		int customerID = -1;
		int bookingID = -1;
		float rating = 0.0f;
		String response = "";
		boolean check = false;

		try {
			sharedDelegate = new SharedDelegate();
			customerID = sharedDelegate.getUserID(userPhone);

			/*
			 * LOGGER.log(Level.INFO,
			 * "Choose a booking ID to rate from the following rides.");
			 * 
			 * completedTrip = sharedDelegate.displayCompletedTrips(customerID);
			 * 
			 * size = completedTrip.size();
			 * 
			 * for(i=0;i<size;i++) { object = completedTrip.get(i); LOGGER.log(Level.INFO,
			 * object.getAddress() + ", " + object.getZipCode()); }
			 */

			LOGGER.log(Level.INFO, "Enter your bookingID: ");

			bookingID = SCANNER.nextInt();

			check = sharedDelegate.checkBookingExistsForRating(bookingID, customerID);

			if (check == false) {
				LOGGER.log(Level.INFO, "Can't rate due to missing ID or ride incompletion.");
				return;
			}

			LOGGER.log(Level.INFO, "Enter your rating on a scale of 10: ");

			rating = SCANNER.nextFloat();

			response = sharedDelegate.rateTrip(rating, bookingID, customerID);

			if (response.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Ride successfully rated.");
			}
		} catch (InputMismatchException e) {
			LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid one.");
			SCANNER.next();
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in rating the ride.");
		}

	}

	/*
	 * Validates if the time format is valid.
	 */

	public boolean isValidFormat(String input, int flag) {
		SimpleDateFormat date = null;
		SimpleDateFormat time = null;

		try {
			if (flag == 0) {
				date = new SimpleDateFormat("dd-MM-yyyy");
				date.parse(input);
				return true;
			} else {
				time = new SimpleDateFormat("hh:mm aa");
				time.parse(input);
				return true;
			}
		} catch (ParseException e) {
			LOGGER.log(Level.WARNING, "Error in parsing time formats.");
			return false;
		}
	}

	/*
	 * Checks if the booking time has elapsed.
	 */

	public boolean checkTimeElapsed(String startTime) {

		Calendar inputTime = null;
		Calendar currentTime = null;
		DateFormat dateFormat = null;
		int checkElapsed = -1;
		Date dateObject = null;

		try {
			inputTime = Calendar.getInstance();
			currentTime = Calendar.getInstance();

			dateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm aa");

			// Converting the input String to Date
			dateObject = dateFormat.parse(startTime);

			inputTime.setTime(dateObject);

			checkElapsed = inputTime.compareTo(currentTime);

			if (checkElapsed <= 0) {
				return true;
			}

			return false;

		} catch (ParseException pe) {
			LOGGER.log(Level.WARNING, "Error in parsing calendar format.");
			return false;
		}

	}

}
