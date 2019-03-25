package com.zilker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.zilker.bean.Address;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.CompleteRating;
import com.zilker.bean.CompleteRide;
import com.zilker.bean.Location;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.RateRide;
import com.zilker.bean.User;
import com.zilker.bean.TravelInvoice;
import com.zilker.constants.Constants;
import com.zilker.customexception.ApplicationException;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;
import com.zilker.util.ResponseGenerator;

@RestController
public class RideController {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	SharedDelegate sharedDelegate;

	@Autowired
	CustomerDelegate customerDelegate;

	@Autowired
	DriverDelegate driverDelegate;

	@Autowired
	ResponseGenerator responseGenerator;

	
	@PostMapping(value = "/book/ride")
	public ResponseEntity<?> confirmbookRide(@RequestBody TravelInvoice travelInvoice) {
		int bookingID = -1;
		
		try {

			bookingID = customerDelegate.calculateTravel(travelInvoice);
		
			return responseGenerator.successResponse(bookingID);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/ride/cancel/{travelInvoiceBookingID}")
	public ResponseEntity<?> cancelRide(@PathVariable int travelInvoiceBookingID) {

		try {

			sharedDelegate.cancelRide(travelInvoiceBookingID);

			return responseGenerator.successResponse(Constants.SUCCESS);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@PostMapping(value = "/ride/complete")
	public ResponseEntity<?> completeRide(@RequestBody CompleteRide completeRide) {

		int driverID = -1;
		String rideCompleteResponse = "";
		
		try {
			System.out.println(completeRide.toString());
			System.out.println(completeRide.getUserPhone() + " " + completeRide.getBookingID());
			driverID = sharedDelegate.getUserID(completeRide.getUserPhone());

			rideCompleteResponse = driverDelegate.completeRide(completeRide.getBookingID(), driverID);

			if (rideCompleteResponse.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Ride completed successfully.");

				LOGGER.log(Level.INFO, "Location successfully updated.");
				
				return responseGenerator.successResponse(Constants.SUCCESS);

			}

			LOGGER.log(Level.INFO, "Error in updating location.");
			return responseGenerator.successResponse(Constants.FAILURE);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@PostMapping(value = "/ride/rate")
	public ResponseEntity<?> rateRide(@RequestBody RateRide rateRide) {
	
		try {
			sharedDelegate.rateTrip(rateRide.getRating(), rateRide.getBookingID(), rateRide.getUserPhone());

			return responseGenerator.successResponse(Constants.SUCCESS);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}


	@GetMapping(value = "/locations")
	public ResponseEntity<?> displayLocations() {

		ArrayList<Address> addressList = null;

		try {
			addressList = customerDelegate.displayLocations();

			return responseGenerator.successResponse(addressList);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/dashboard/rider/{bookingID}")
	public ResponseEntity<?> displayDashboardRider(@PathVariable int bookingID) {

		PostConfirm postConfirm = null;

		try {
			
			postConfirm = sharedDelegate.getBookingDetails(bookingID);
			return responseGenerator.successResponse(postConfirm);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/rides/completed/{contact}/{flag}")
	public ResponseEntity<?> completedRides(@PathVariable String contact, @PathVariable int flag) {

		ArrayList<BookingResponse> completeList = null;

		try {
			completeList = new ArrayList<BookingResponse>();
			completeList = sharedDelegate.displayCompletedRides(contact, flag);

			return responseGenerator.successResponse(completeList);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/rides/ongoing/{contact}/{flag}")
	public ResponseEntity<?> onGoingRides(@PathVariable String contact, @PathVariable int flag) {

		BookingResponse bookingResponse = null;

		try {

			bookingResponse = sharedDelegate.displayBookingDetails(contact, flag);

			
			return responseGenerator.successResponse(bookingResponse);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/rides/cancelled/{contact}/{flag}")
	public ResponseEntity<?> cancelledRides(@PathVariable String contact, @PathVariable int flag) {

		ArrayList<BookingResponse> cancelledList = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();

			cancelledList = sharedDelegate.displayCancelledRides(contact, flag);

			return responseGenerator.successResponse(cancelledList);

		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/ride/rated/{contact}/{flag}")
	public ResponseEntity<?> RatedRides(@PathVariable String contact, @PathVariable int flag) throws IOException {

		ArrayList<CompleteRating> ratingCompleteList = null;

		try {
			ratingCompleteList = new ArrayList<CompleteRating>();

			ratingCompleteList = sharedDelegate.displayCompletedRatedRides(contact, flag);

			return responseGenerator.successResponse(ratingCompleteList);

		} catch (Exception jse) {
			return null;
		}
	}

	@GetMapping(value = "/booking/status/{contact}/{flag}")
	public ResponseEntity<?> getBookingStatus(@PathVariable String contact, @PathVariable int flag) {

		int bookingID = -1;

		try {
			bookingID = sharedDelegate.checkBookingStatus(contact, flag);
			return responseGenerator.successResponse(bookingID);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/cab/{zipCode}/{flag}")
	public ResponseEntity<?> getUserID(@PathVariable String zipCode, @PathVariable int flag) {

		int cabID = -1;

		try {
			cabID = customerDelegate.findNearestCab(zipCode, flag);
			return responseGenerator.successResponse(cabID);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/cabdriver/{cabID}")
	public ResponseEntity<?> getDriverByCabID(@PathVariable int cabID) {

		int driverID = -1;
		try {
			driverID = customerDelegate.getDriverID(cabID);
			return responseGenerator.successResponse(driverID);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/check/cab/{cabID}/{seats}")
	public ResponseEntity<?> isSeatExists(@PathVariable int cabID, @PathVariable int seats) {

		try {
			customerDelegate.checkCabSeats(cabID, seats);
			return responseGenerator.successResponse(Constants.SUCCESS);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/cab/{cabID}")
	public ResponseEntity<?> getCabByID(@PathVariable int cabID) {

		String cab = "";
		try {
			cab = sharedDelegate.findCabByID(cabID);
			return responseGenerator.successResponse(cab);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

	@GetMapping(value = "/driver/{driverID}")
	public ResponseEntity<?> getDriverByID(@PathVariable int driverID) {

		String driver = "";
		try {
			driver = sharedDelegate.findDriverByID(driverID);
			return responseGenerator.successResponse(driver);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}
	
	@GetMapping(value = "/location/{locationID}")
	public ResponseEntity<?> getLocationByID(@PathVariable int locationID) {

		String location = "";
		try {
			location = sharedDelegate.findLocation(locationID);
			return responseGenerator.successResponse(location);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}
	
	@PostMapping(value = "/check/location")
	public ResponseEntity<?> getIDByLocation(@RequestBody Location location) {

		int locationID = -1;
		
		try {
			System.out.println(location.getLocation() + " " + location.getZipCode());
			locationID = customerDelegate.findLocationID(location.getLocation(), location.getZipCode());
			System.out.println(locationID);
			return responseGenerator.successResponse(locationID);
		} catch (ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}

}
