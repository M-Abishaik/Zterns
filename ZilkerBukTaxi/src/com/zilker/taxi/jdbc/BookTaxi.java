package com.zilker.taxi.jdbc;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.InputMismatchException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.taxi.bean.BookingResponse;
import com.zilker.taxi.bean.Customer;
import com.zilker.taxi.bean.Invoice;
import com.zilker.taxi.bean.UpdateRide;
import com.zilker.taxi.constant.Constants;
import com.zilker.taxi.util.RegexUtility;
import com.zilker.taxi.delegate.TaxiDelegate;

/*
 *	Inputs the personal details and booking details of a customer. 
 */

public class BookTaxi {

	public BookTaxi() {}

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	private final static Scanner SCANNER = new Scanner(System.in);

	public static void main(String[] args) {

		int choice = 0;
		BookTaxi bookTaxi = new BookTaxi();

		do {
			LOGGER.log(Level.INFO, "Taxi Booking Application.");
			LOGGER.log(Level.INFO, "1. Register account." + "\n" + "2. Book a ride." + "\n" + "3. Display details." + "\n"
							+ "4. Update personal details." + "\n" + "5. Update booking details. " + "\n"
							+ "6. Cance a ride." + "\n" + "7. Delete account." + "\n" + "8. Exit");
			LOGGER.log(Level.INFO, "Enter your choice: ");

			try {
				choice = SCANNER.nextInt();
			} catch(InputMismatchException e) {
				LOGGER.log(Level.WARNING, "Invalid input. Please enter a valid number.");
				SCANNER.next();
			}

			
			switch (choice) {
			case 1:
				bookTaxi.registerAccount();
				break;
			case 2:
				bookTaxi.bookRide();
				break;
			case 3:
				bookTaxi.displayDetails();
				break;
			case 4:
				bookTaxi.updatePersonalDetails();
				break;
			case 5:
				bookTaxi.updateBookingDetails();
				break;
			case 6:
				bookTaxi.cancelRide();
				break;
			case 7:
				bookTaxi.deleteAccount();
				break;
			case 8:
				System.exit(0);
			default:
				LOGGER.log(Level.WARNING, "Invalid choice. Enter a valid one.");
				break;

			}
		} while (choice != 8);
	}

	/*
	 * Creates a new customer account.
	 */

	public void registerAccount() {

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();
			RegexUtility regexUtility = new RegexUtility();
			
			String mail = "", firstName = "", lastName = "", fullName = "";
			boolean check = false;
			int customerID = 0;

			SCANNER.nextLine();

			do {
				LOGGER.log(Level.INFO, "Enter first name: ");
				firstName = SCANNER.nextLine();

				LOGGER.log(Level.INFO, "Enter Last name: ");
				lastName = SCANNER.nextLine();
				
				fullName = firstName + " " + lastName;
		
				check = regexUtility.validateRegex(Constants.VALID_NAME_REGEX, fullName);
				if(check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid name. Enter a valid one.");
				}
				
			}while(check != true);
			
			check = false;

			do {
				LOGGER.log(Level.INFO, "Enter your mail: ");
				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if(check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			customerID = taxiDelegate.checkMailExists(mail);
			
			if (customerID != (-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "already exists.");
				return;
			}

			Customer customer = new Customer(firstName, lastName, mail);
			taxiDelegate.insertPersonalDetails(customer);
			
			LOGGER.log(Level.INFO, "Account successfully created.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Books a new ride for the customer.
	 */

	public void bookRide() {

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();
			RegexUtility regexUtility = new RegexUtility();

			String source = "", destination = "", rideStartTime = "", rideEndTime = "", mail = "", formattedTime = "";
			boolean check = false;
			int customerID = 0, driverID = 0, cabID = 0, sourceID = 0, destinationID = 0, bookingID = 0;
			Date date = null;
			float price = 0.0f;
			HashMap<String, Float> hashMap = new HashMap<String, Float>();
			DateFormat dateFormat = null, outputFormat = null;

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();

				mail = SCANNER.nextLine();

				check = regexUtility.validateMail(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if(check == true) {
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

			do {
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

			driverID = taxiDelegate.checkDriverExists();
			cabID = taxiDelegate.checkCabExists();
			
			
			if (driverID != (-1) && cabID != (-1)) {
				
				LOGGER.log(Level.INFO, "At what time do you wish to start the ride?(Example: 11:30 PM)");
				rideStartTime = SCANNER.nextLine();

				dateFormat = new SimpleDateFormat("hh:mm aa");
				outputFormat = new SimpleDateFormat("HH:mm");

				
				date = dateFormat.parse(rideStartTime);
				formattedTime = outputFormat.format(date);
		
				// Computes the estimated finish time and price corresponding to the distance
				// between source and destination.

				hashMap = taxiDelegate.calculateTravel(sourceID, destinationID, formattedTime);
				if (hashMap == null) {
					LOGGER.log(Level.WARNING, "Please enter a valid set of source and destination.");
					return;
				}

				rideEndTime = (String) hashMap.keySet().toArray()[0];
				price = hashMap.get(rideEndTime);

				Invoice invoice = new Invoice(customerID, driverID, cabID, sourceID, destinationID, formattedTime,
						rideEndTime, price);
				
				bookingID = taxiDelegate.insertRideDetails(invoice);
				
				LOGGER.log(Level.INFO, "Please note down the booking ID: " + bookingID);

				// Updates the driver and cab status to be unavailable until the current ride
				// has been completed.
				
				taxiDelegate.updateDriverStatus(driverID, 0);
				taxiDelegate.updateCabStatus(cabID, 0);

				LOGGER.log(Level.INFO, "Ride successfully booked.");

			} else {
				LOGGER.log(Level.INFO, "All rides are busy. Please try after sometime.");
			}

		} catch (ParseException pe) {
			LOGGER.log(Level.INFO, "Invalid time. Please enter a valid one.");

		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
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
		int customerID = 0;

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();
			RegexUtility regexUtility = new RegexUtility();

			Customer customer = new Customer();
			
			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();

				mail = SCANNER.nextLine();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if(check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid mail. Enter a valid one.");
				}
			} while (check != true);

			customerID = taxiDelegate.checkMailExists(mail);
			if (customerID == (-1)) {
				LOGGER.log(Level.WARNING, mail + " " + "doesn't exist. Please register.");
				return;
			}
			
			customer = taxiDelegate.displayProfile(mail);
			LOGGER.log(Level.INFO, "First name: " + customer.getFirstName() + "\nLast name: " + customer.getLastName() + 
					"\nMail: " + customer.getMailId());
			LOGGER.log(Level.INFO, "Profile displayed.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Displays customer booking details.
	 */

	public void displayBookingDetails() {
		int bookingID = 0;
		boolean check = false;
		BookingResponse bookingResponse = null;

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();
			check = taxiDelegate.checkBookingExists(bookingID);

			if (check == false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			bookingResponse = taxiDelegate.displayBookingDetails(bookingID);
			LOGGER.log(Level.INFO, "Booking ID: " + bookingResponse.getBookingID() + "\nSource: " + bookingResponse.getSource()
					+ "\nDestination: " + bookingResponse.getDestination() + "\nPrice: " + bookingResponse.getPrice()
					+ "\nStart time: " + bookingResponse.getStartTime() + "\nEnd time: " + bookingResponse.getEndTime());
			LOGGER.log(Level.INFO, "Booking details displayed.");
		}catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Updates customer profile.
	 */

	public void updatePersonalDetails() {

		String mail = "", firstName = "", lastName = "", fullName = "";
		boolean check = false;
		int customerID = 0;

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();
			RegexUtility regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();

				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if(check == true) {
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
				if(check == true) {
					break;
				} else {
					LOGGER.log(Level.WARNING, "Invalid name. Enter a valid one.");
				}				
			}while(check != true);

			Customer customer = new Customer(firstName, lastName, mail);
			taxiDelegate.updatePersonalDetails(customer);

			LOGGER.log(Level.INFO, "Profile successfully updated.");

		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Updates the booking details of the customer.
	 */

	public void updateBookingDetails() {

		int bookingID = 0, sourceID = 0, destinationID = 0;
		boolean check = false;
		String startTime = "", currentTime = "", rideStartTime = "", rideEndTime = "", source = "", destination = "",
				formattedTime = "";
		Calendar calendar = null;
		SimpleDateFormat simpleDateFormat = null;
		Date startDate = null, currentDate = null, date = null;
		long elapsed = 0, remaining = 0;
		DateFormat dateFormat = null, outputFormat = null;
		float price = 0.0f;
		HashMap<String, Float> hashMap = new HashMap<String, Float>();

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();
			check = taxiDelegate.checkBookingExists(bookingID);

			if (check == false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			check = false;

			startTime = taxiDelegate.findStartTime(bookingID);

			calendar = Calendar.getInstance();
			simpleDateFormat = new SimpleDateFormat("HH:mm");

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

				dateFormat = new SimpleDateFormat("hh:mm aa");
				outputFormat = new SimpleDateFormat("HH:mm");

				
				date = dateFormat.parse(rideStartTime);
				formattedTime = outputFormat.format(date);			
			

			// Recomputes the new estimated finish time and price corresponding to the
			// distance between source and destination.
			
			hashMap = taxiDelegate.calculateTravel(sourceID, destinationID, formattedTime);
			if (hashMap == null) {
				LOGGER.log(Level.WARNING, "Please enter a valid set of source and destination.");
				return;
			}

			rideEndTime = (String) hashMap.keySet().toArray()[0];
			price = hashMap.get(rideEndTime);

			UpdateRide updateRide = new UpdateRide(formattedTime, rideEndTime, sourceID, destinationID, bookingID, price);
			taxiDelegate.updateRideDetails(updateRide);
			
			LOGGER.log(Level.INFO, "Booking details successfully updated.");
		} catch (ParseException pe) {
			LOGGER.log(Level.INFO, "Invalid time. Please enter a valid one.");
		}catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Cancels the ride booked by the customer.
	 */

	public void cancelRide() {

		int bookingID = 0, driverID = 0, cabID = 0;
		boolean check = false;
		HashMap<Integer, Integer> hashMap = new HashMap<Integer, Integer>();

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();

			LOGGER.log(Level.INFO, "Enter your bookingID: ");
			SCANNER.nextLine();

			bookingID = SCANNER.nextInt();
			check = taxiDelegate.checkBookingExists(bookingID);


			if (check == false) {
				LOGGER.log(Level.WARNING, bookingID + " " + "doesn't exist. Please book a ride.");
				return;
			}

			// Fetches corresponding driver and cab ID's.

			hashMap = taxiDelegate.cancelRide(bookingID);

			driverID = (int) hashMap.keySet().toArray()[0];
			cabID = hashMap.get(driverID);

			// Updates the driver and cab statuses to be available now.
			
			taxiDelegate.updateDriverStatus(driverID, 1);
			taxiDelegate.updateCabStatus(cabID, 1);

			LOGGER.log(Level.INFO, "Ride cancelled successfully.");
		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
		}catch (Exception e) {
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}

	/*
	 * Removes customer account and its corresponding ride details.
	 */

	public void deleteAccount() {
		String mail = "";
		boolean check = false;
		int customerID = 0;

		try {
			TaxiDelegate taxiDelegate = new TaxiDelegate();
			RegexUtility regexUtility = new RegexUtility();

			do {
				LOGGER.log(Level.INFO, "Enter your registered mail-ID: ");
				SCANNER.nextLine();
				mail = SCANNER.next();

				check = regexUtility.validateRegex(Constants.VALID_EMAIL_ADDRESS_REGEX, mail);
				if(check == true) {
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
			LOGGER.log(Level.SEVERE, e.getMessage());
		}
	}
}
