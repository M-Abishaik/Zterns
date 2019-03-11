package com.zilker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.bean.Address;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.CompleteRating;
import com.zilker.bean.DisplayInvoice;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.TravelInvoice;
import com.zilker.bean.User;
import com.zilker.constants.Constants;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;

@Controller
public class RideController {

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@Autowired
	SharedDelegate sharedDelegate;

	@Autowired
	CustomerDelegate customerDelegate;

	@Autowired
	DriverDelegate driverDelegate;

	@RequestMapping(value = "/book/ride", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView bookRide(HttpSession session, @RequestParam("startDate") String startDate,
			@RequestParam("startTime") String startTime, @RequestParam("source") String source,
			@RequestParam("destination") String destination, @RequestParam("seats") String seats) {

		int cabID = -1;
		int sourceID = -1;
		int destinationID = -1;
		int customerID = -1;
		int driverID = -1;
		int numSeats = -1;
		String cab = "";
		String driver = "";
		String startTimeDate = "";
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		String availabilityResponse = "";
		String userPhone = "";
		ArrayList<Address> address = null;

		TravelInvoice travelInvoice = null;
		DisplayInvoice displayInvoice = null;
		ModelAndView mav = null;

		try {
			numSeats = Integer.parseInt(seats);

			travelInvoice = new TravelInvoice();
			displayInvoice = new DisplayInvoice();

			userPhone = (String) session.getAttribute("userPhone");

			customerID = sharedDelegate.getUserID(userPhone);
			startTimeDate = startDate + " " + startTime;

			sourceExtract = customerDelegate.extractLocation(source, 0);
			destinationExtract = customerDelegate.extractLocation(destination, 0);

			zipCodeSource = customerDelegate.extractLocation(source, 1);
			zipCodeDestination = customerDelegate.extractLocation(destination, 1);

			sourceID = customerDelegate.findLocationID(sourceExtract, zipCodeSource);
			destinationID = customerDelegate.findLocationID(destinationExtract, zipCodeDestination);

			cabID = customerDelegate.findNearestCab(zipCodeSource, 0);

			address = customerDelegate.displayLocations();

			if (cabID == (-1)) {

				LOGGER.log(Level.INFO, "No nearest cab found. Please try later.");

				mav = new ModelAndView("customer");
				mav.addObject("errorMessage", "No nearest cab found. Please try later.");
				mav.addObject("addressList", address);

				return mav;
			}

			driverID = customerDelegate.getDriverID(cabID);
			availabilityResponse = customerDelegate.checkCabSeats(cabID, numSeats);

			if (availabilityResponse.equals(Constants.FAILURE)) {

				LOGGER.log(Level.INFO,
						"The cab with the requested number of seats is not yet available. Please try later or wait for some time.");

				mav = new ModelAndView("customer");
				mav.addObject("errorMessage",
						"The cab with the requested number of seats is not yet available. Please try later or wait for some time.");
				mav.addObject("addressList", address);

				return mav;
			} else {
				LOGGER.log(Level.INFO, "The cab with the requested number of seats is available.");
			}

			if (driverID != (-1) && cabID != (-1)) {

				cab = sharedDelegate.findCabByID(cabID);

				driver = sharedDelegate.findDriverByID(driverID);

				displayInvoice = new DisplayInvoice(driver, cab, source, destination, startTimeDate, numSeats);

				travelInvoice = new TravelInvoice(customerID, driverID, cabID, sourceID, destinationID, startTimeDate,
						0.0f, 0.0f);

				mav = new ModelAndView("confirm_ride");
				mav.addObject("travelInvoice", travelInvoice);
				mav.addObject("displayInvoice", displayInvoice);
				mav.addObject("source", zipCodeSource);
				mav.addObject("destination", zipCodeDestination);

				return mav;
			}

			return null;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/ride/confirm", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView confirmbookRide(HttpSession session,
			@RequestParam("travelInvoiceCustomerID") int travelInvoiceCustomerID,
			@RequestParam("travelInvoiceDriverID") int travelInvoiceDriverID,
			@RequestParam("travelInvoiceCabID") int travelInvoiceCabID,
			@RequestParam("travelInvoiceSourceID") int travelInvoiceSourceID,
			@RequestParam("travelInvoiceDestinationID") int travelInvoiceDestinationID,
			@RequestParam("travelInvoiceStartTimeDate") String travelInvoiceStartTimeDate,
			@RequestParam("travelInvoicePrice") float travelInvoicePrice,
			@RequestParam("travelInvoiceDistance") float travelInvoiceDistance) {

		TravelInvoice travelInvoice = null;
		PostConfirm postConfirm = null;
		int bookingID = -1;
		String source = "";
		String destination = "";
		String cab = "";
		String driver = "";
		String sourceZipCode = "";
		String destinationZipCode = "";
		ModelAndView mav = null;

		try {

			travelInvoice = new TravelInvoice(travelInvoiceCustomerID, travelInvoiceDriverID, travelInvoiceCabID,
					travelInvoiceSourceID, travelInvoiceDestinationID, travelInvoiceStartTimeDate, travelInvoicePrice,
					travelInvoiceDistance);

			bookingID = customerDelegate.calculateTravel(travelInvoice, 0);

			source = sharedDelegate.findLocation(travelInvoiceSourceID);
			sourceZipCode = customerDelegate.extractLocation(source, 1);

			destination = sharedDelegate.findLocation(travelInvoiceDestinationID);
			destinationZipCode = customerDelegate.extractLocation(destination, 1);

			cab = sharedDelegate.findCabByID(travelInvoiceCabID);

			driver = sharedDelegate.findDriverByID(travelInvoiceDriverID);

			postConfirm = new PostConfirm(bookingID, travelInvoiceStartTimeDate, source, destination, driver, cab,
					travelInvoicePrice);

			mav = new ModelAndView("rider_dashboard");
			mav.addObject("postConfirmInvoice", postConfirm);
			mav.addObject("sourceZip", sourceZipCode);
			mav.addObject("destinationZip", destinationZipCode);

			return mav;

		} catch (Exception exception) {
			return null;
		}

	}

	@RequestMapping(value = "/ride/cancel", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView cancelRide(HttpSession session,
			@RequestParam("travelInvoiceBookingID") int travelInvoiceBookingID) {

		boolean check = false;
		ArrayList<Address> address = null;
		ModelAndView mav = null;

		try {

			check = sharedDelegate.cancelRide(travelInvoiceBookingID);

			address = customerDelegate.displayLocations();

			mav = new ModelAndView("rider");
			mav.addObject("addressList", address);

			return mav;

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
			return null;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
			return null;
		}

	}

	@RequestMapping(value = "/ride/complete", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView completeRide(HttpSession session,
			@RequestParam("travelInvoiceBookingID") int travelInvoiceBookingID) {

		ArrayList<BookingResponse> completeList = null;

		String userPhone = "";
		String rideCompleteResponse = "";
		int driverID = -1;
		boolean check = false;
		ModelAndView mav = null;

		try {
			completeList = new ArrayList<BookingResponse>();

			userPhone = (String) session.getAttribute("userPhone");

			driverID = sharedDelegate.getUserID(userPhone);

			check = sharedDelegate.checkBookingExists(travelInvoiceBookingID, driverID);

			if (check == true) {

				rideCompleteResponse = driverDelegate.completeRide(travelInvoiceBookingID, driverID);

				if (rideCompleteResponse.equals(Constants.SUCCESS)) {
					LOGGER.log(Level.INFO, "Location successfully updated.");

					completeList = sharedDelegate.displayCompletedRides(userPhone, 1);

					mav = new ModelAndView("mytrips_driver");
					mav.addObject("onCompleteResponse", completeList);

					return mav;

				}

				LOGGER.log(Level.INFO, "Ride completed successfully.");

			}
			LOGGER.log(Level.INFO, "Error in updating location.");
			return null;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/ride/rate", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView rateRide(HttpSession session,
			@RequestParam("travelInvoiceBookingID") int travelInvoiceBookingID, @RequestParam("rating") float rating) {

		String ratingResponse = "";
		String userPhone = "";
		ArrayList<BookingResponse> completeList = null;
		ModelAndView mav = null;

		int flag = -1;

		try {
			completeList = new ArrayList<BookingResponse>();

			userPhone = (String) session.getAttribute("userPhone");

			ratingResponse = sharedDelegate.rateTrip(rating, travelInvoiceBookingID, userPhone);

			travelInvoiceBookingID = -1;
			if (ratingResponse.equals(Constants.SUCCESS)) {

				completeList = sharedDelegate.displayCompletedRides(userPhone, 0);

				travelInvoiceBookingID = sharedDelegate.checkBookingStatus(userPhone, 0);

				if (travelInvoiceBookingID != (-1)) {
					mav = new ModelAndView("mytrips_rider_dashboard");
				} else {
					mav = new ModelAndView("mytrips_rider");
				}
				mav.addObject("onCompleteResponse", completeList);
				return mav;
			}
			return null;
		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/ride/cancel", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView cancelRideRedirect(HttpSession session) {

		ArrayList<Address> address = null;
		ModelAndView mav = null;

		try {
			address = customerDelegate.displayLocations();

			mav = new ModelAndView("rider");
			mav.addObject("addressList", address);

			return mav;

		} catch (InputMismatchException e) {
			LOGGER.log(Level.INFO, "Enter a valid integer.");
			return null;
		} catch (Exception e) {
			LOGGER.log(Level.SEVERE, "Error in cancelling the ride.");
			return null;
		}

	}

	@RequestMapping(value = "/locations", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayLocations(HttpSession session) {

		ArrayList<Address> addressList = null;
		ModelAndView mav = null;

		try {
			addressList = customerDelegate.displayLocations();

			mav = new ModelAndView("rider");
			mav.addObject("addressList", addressList);

			return mav;

		} catch (Exception exception) {
			return null;
		}

	}

	@RequestMapping(value = "/dashboard/rider", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayDashboardRider(HttpSession session) {

		User user = null;

		ModelAndView mav = null;
		int bookingID = -1;
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		String userPhone = "";

		PostConfirm postConfirm = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");
			user = sharedDelegate.displayProfile(userPhone);

			mav = new ModelAndView("rider_dashboard");

			mav.addObject("userProfile", user);

			bookingID = sharedDelegate.checkBookingStatus(userPhone, 0);
			postConfirm = sharedDelegate.getBookingDetails(bookingID);

			sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
			destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

			zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
			zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

			mav.addObject("postConfirmInvoice", postConfirm);
			mav.addObject("sourceZip", zipCodeSource);
			mav.addObject("destinationZip", zipCodeDestination);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/driver", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayDashboardDriver(HttpSession session) {

		ModelAndView mav = null;

		int bookingID = -1;
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		String userPhone = "";
		User user = null;

		PostConfirm postConfirm = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");
			user = sharedDelegate.displayProfile(userPhone);

			mav = new ModelAndView("driver_dashboard");
			mav.addObject("userProfile", user);

			bookingID = sharedDelegate.checkBookingStatus(userPhone, 1);
			postConfirm = sharedDelegate.getBookingDetails(bookingID);

			sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
			destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

			zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
			zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

			mav.addObject("postConfirmInvoice", postConfirm);
			mav.addObject("sourceZip", zipCodeSource);
			mav.addObject("destinationZip", zipCodeDestination);

			return mav;

		} catch (Exception exception) {
			return null;
		}

	}

	@RequestMapping(value = "/rider/rides/completed", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView completedRides(HttpSession session) {

		ArrayList<BookingResponse> completeList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			completeList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			completeList = sharedDelegate.displayCompletedRides(userPhone, 0);

			mav = new ModelAndView("mytrips_rider");
			mav.addObject("onCompleteResponse", completeList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/driver/rides/completed", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView driverCompletedRides(HttpSession session) {

		ArrayList<BookingResponse> completeList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			completeList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			completeList = sharedDelegate.displayCompletedRides(userPhone, 1);

			mav = new ModelAndView("mytrips_driver");
			mav.addObject("onCompleteResponse", completeList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/rider/rides/ongoing", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView onGoingRides(HttpSession session) {

		BookingResponse bookingResponse = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			userPhone = (String) session.getAttribute("userPhone");

			bookingResponse = sharedDelegate.displayBookingDetails(userPhone, 0);

			mav = new ModelAndView("mytrips_rider");
			mav.addObject("onGoingResponse", bookingResponse);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/driver/rides/ongoing", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView onGoingDriverRides(HttpSession session) {

		BookingResponse bookingResponse = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			userPhone = (String) session.getAttribute("userPhone");

			bookingResponse = sharedDelegate.displayBookingDetails(userPhone, 1);

			mav = new ModelAndView("mytrips_driver");
			mav.addObject("onGoingResponse", bookingResponse);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/rider/rides/ongoing", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView onGoingDashboardRides(HttpSession session) {

		BookingResponse bookingResponse = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			userPhone = (String) session.getAttribute("userPhone");

			bookingResponse = sharedDelegate.displayBookingDetails(userPhone, 0);

			mav = new ModelAndView("mytrips_rider_dashboard");
			mav.addObject("onGoingResponse", bookingResponse);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/driver/rides/ongoing", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView onGoingDriverDashboardRides(HttpSession session) {

		BookingResponse bookingResponse = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			userPhone = (String) session.getAttribute("userPhone");

			bookingResponse = sharedDelegate.displayBookingDetails(userPhone, 1);

			mav = new ModelAndView("mytrips_driver_dashboard");
			mav.addObject("onGoingResponse", bookingResponse);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/rider/rides/completed", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView CompletedDashboardRides(HttpSession session) {

		ArrayList<BookingResponse> completeList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			completeList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			completeList = sharedDelegate.displayCompletedRides(userPhone, 0);

			mav = new ModelAndView("mytrips_rider_dashboard");
			mav.addObject("onCompleteResponse", completeList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/driver/rides/completed", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView CompletedDriverDashboardRides(HttpSession session) {

		ArrayList<BookingResponse> completeList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			completeList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			completeList = sharedDelegate.displayCompletedRides(userPhone, 1);

			mav = new ModelAndView("mytrips_driver_dashboard");
			mav.addObject("onCompleteResponse", completeList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/rider/rides/cancelled", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView CancelledDashboardRides(HttpSession session) {

		ArrayList<BookingResponse> cancelledList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			cancelledList = sharedDelegate.displayCancelledRides(userPhone, 0);

			mav = new ModelAndView("mytrips_rider_dashboard");
			mav.addObject("onCancelResponse", cancelledList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/driver/rides/cancelled", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView CancelledDriverDashboardRides(HttpSession session) {

		ArrayList<BookingResponse> cancelledList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			cancelledList = sharedDelegate.displayCancelledRides(userPhone, 1);

			mav = new ModelAndView("mytrips_driver_dashboard");
			mav.addObject("onCancelResponse", cancelledList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/rider/rides/cancelled", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView cancelledRides(HttpSession session) {

		ArrayList<BookingResponse> cancelledList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			cancelledList = sharedDelegate.displayCancelledRides(userPhone, 0);

			mav = new ModelAndView("mytrips_rider");
			mav.addObject("onCancelResponse", cancelledList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/driver/rides/cancelled", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView driverCancelledRides(HttpSession session) {

		ArrayList<BookingResponse> cancelledList = null;

		String userPhone = "";

		ModelAndView mav = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();
			userPhone = (String) session.getAttribute("userPhone");

			cancelledList = sharedDelegate.displayCancelledRides(userPhone, 1);

			mav = new ModelAndView("mytrips_driver");
			mav.addObject("onCancelResponse", cancelledList);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/driverratedrides", method = RequestMethod.GET)
	@ResponseBody
	public void DriverRatedRides(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ArrayList<CompleteRating> ratingCompleteList = null;
		CompleteRating completeRating = null;
		String userPhone = "";

		JSONObject json = new JSONObject();
		JSONArray bookingID = new JSONArray();
		JSONObject ratedBookingID;

		int size = -1;
		int i = 0;
		int flag = 1;

		try {
			ratingCompleteList = new ArrayList<CompleteRating>();

			userPhone = (String) session.getAttribute("userPhone");
			ratingCompleteList = sharedDelegate.displayCompletedRatedRides(userPhone, flag);

			size = ratingCompleteList.size();

			for (i = 0; i < size; i++) {
				completeRating = ratingCompleteList.get(i);

				ratedBookingID = new JSONObject();

				ratedBookingID.put("bookingID", completeRating.getBookingID());
				ratedBookingID.put("rating", completeRating.getRating());
				bookingID.put(ratedBookingID);
			}

			json.put("bookingid", bookingID);

		} catch (Exception jse) {

		}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());

	}

	@RequestMapping(value = "/riderratedrides", method = RequestMethod.GET)
	@ResponseBody
	public void RiderRatedRides(HttpSession session, HttpServletRequest request, HttpServletResponse response)
			throws IOException {

		ArrayList<CompleteRating> ratingCompleteList = null;
		CompleteRating completeRating = null;
		String userPhone = "";

		JSONObject json = new JSONObject();
		JSONArray bookingID = new JSONArray();
		JSONObject ratedBookingID;

		int size = -1;
		int i = 0;
		int flag = 0;

		try {
			ratingCompleteList = new ArrayList<CompleteRating>();

			userPhone = (String) session.getAttribute("userPhone");
			ratingCompleteList = sharedDelegate.displayCompletedRatedRides(userPhone, flag);

			size = ratingCompleteList.size();

			for (i = 0; i < size; i++) {
				completeRating = ratingCompleteList.get(i);

				ratedBookingID = new JSONObject();

				ratedBookingID.put("bookingID", completeRating.getBookingID());
				ratedBookingID.put("rating", completeRating.getRating());
				bookingID.put(ratedBookingID);
			}

			json.put("bookingid", bookingID);

		} catch (Exception jse) {

		}
		response.setContentType("application/json");
		response.getWriter().write(json.toString());

	}

}
