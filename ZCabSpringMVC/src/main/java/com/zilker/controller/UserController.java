package com.zilker.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;
import com.zilker.bean.Address;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.UpdateProfile;
import com.zilker.bean.User;
import com.zilker.constants.Constants;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;

@Controller
public class UserController {

	@Autowired
	SharedDelegate sharedDelegate;

	@Autowired
	CustomerDelegate customerDelegate;

	@Autowired
	DriverDelegate driverDelegate;

	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);

	@RequestMapping(value = "/users/login", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView loginUser(HttpSession session, @RequestParam("loginMobile") String userPhone,
			@RequestParam("loginPassword") String userPassword) {

		String loginResponse = "";
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";
		ArrayList<Address> address = null;
		ArrayList<BookingResponse> cancelledList = null;

		int bookingID = -1;
		PostConfirm postConfirm = null;

		ModelAndView mav = null;

		try {
			cancelledList = new ArrayList<BookingResponse>();

			loginResponse = sharedDelegate.login(userPhone, userPassword);

			if (!loginResponse.equals(Constants.FAILURE)) {
				session.setAttribute("userPhone", userPhone);
				if (loginResponse.equals(Constants.CUSTOMER)) {

					bookingID = sharedDelegate.checkBookingStatus(userPhone, 0);

					if (bookingID != (-1)) {

						postConfirm = sharedDelegate.getBookingDetails(bookingID);

						sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
						destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

						zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
						zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

						mav = new ModelAndView("rider_dashboard");
						mav.addObject("postConfirmInvoice", postConfirm);
						mav.addObject("sourceZip", zipCodeSource);
						mav.addObject("destinationZip", zipCodeDestination);
						return mav;
					} else {

						address = customerDelegate.displayLocations();

						mav = new ModelAndView("rider");
						mav.addObject("addressList", address);
						return mav;
					}
				} else if (loginResponse.equals(Constants.DRIVER)) {

					bookingID = sharedDelegate.checkBookingStatus(userPhone, 1);

					if (bookingID != (-1)) {

						postConfirm = sharedDelegate.getBookingDetails(bookingID);

						sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
						destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

						zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
						zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

						mav = new ModelAndView("driver_dashboard");
						mav.addObject("postConfirmInvoice", postConfirm);
						mav.addObject("sourceZip", zipCodeSource);
						mav.addObject("destinationZip", zipCodeDestination);
						return mav;
					} else {
						cancelledList = sharedDelegate.displayCancelledRides(userPhone, 1);

						mav = new ModelAndView("mytrips_driver");
						mav.addObject("onCancelResponse", cancelledList);
						return mav;
					}
				}
				LOGGER.log(Level.INFO, "Invalid login credentials.");

				mav = new ModelAndView("index");
				mav.addObject("errorMessage", "Invalid login credentials.");
				return mav;
			} else {
				LOGGER.log(Level.INFO, "Invalid login credentials.");

				mav = new ModelAndView("index");
				mav.addObject("errorMessage", "Invalid login credentials.");
				return mav;
			}

		} catch (Exception exception) {
			LOGGER.log(Level.WARNING, "Exception in login credentials.");

			mav = new ModelAndView("index");
			mav.addObject("errorMessage", "Invalid login credentials.");
			return mav;
		}
	}

	@RequestMapping(value = "/users/update", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView updateUser(HttpSession session, @RequestParam("profileType") String profileType,
			@RequestParam("password") String password, @RequestParam("email") String email,
			@RequestParam("address") String address, @RequestParam("zipCode") String zipCode) {

		User user = null;
		UpdateProfile updateProfile = null;
		String updateResponse = "";
		String phone = "";
		ArrayList<Address> addressList = null;
		ModelAndView mav = null;

		try {
			sharedDelegate = new SharedDelegate();
			updateProfile = new UpdateProfile();
			phone = (String) session.getAttribute("userPhone");

			addressList = customerDelegate.displayLocations();

			updateProfile = new UpdateProfile(email, address, zipCode, password, phone);

			updateResponse = sharedDelegate.updateProfile(updateProfile);

			user = sharedDelegate.displayProfile(phone);

			if (updateResponse.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Account successfully updated.");

				if (profileType.equals("0")) {
					mav = new ModelAndView("rider");
					mav.addObject("addressList", addressList);
					mav.addObject("userProfile", user);

					return mav;
				}

				mav = new ModelAndView("driver");
				mav.addObject("userProfile", user);
				return mav;
			}
			return null;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/users", method = RequestMethod.POST)
	@ResponseBody
	public ModelAndView registerUser(HttpSession session, HttpServletResponse response,
			@RequestParam("registerUsername") String registerUsername,
			@RequestParam("registerContact") String registerContact, @RequestParam("registerMail") String registerMail,
			@RequestParam("registerAddress") String registerAddress,
			@RequestParam("registerZipcode") String registerZipcode,
			@RequestParam("registerPassword") String registerPassword,
			@RequestParam("registerRole") String registerRole, @RequestParam("licenceNumber") String licenceNumber)
			throws IOException {

		int driverID = -1;
		ArrayList<Address> addressList = null;
		String addLicenceResponse = "";
		String registerResponse = "";
		User user = null;
		ModelAndView mav = null;

		try {
			addressList = customerDelegate.displayLocations();
			user = new User();

			registerMail = registerMail.toLowerCase();

			registerAddress = registerAddress.toLowerCase();

			user = new User(registerUsername, registerMail, registerContact, registerRole, registerPassword,
					registerAddress, registerZipcode);
			registerResponse = sharedDelegate.register(user);

			if (registerResponse.equals(Constants.SUCCESS)) {
				session.setAttribute("userPhone", registerContact);

				if (registerRole.equals(Constants.CUSTOMER)) {
					addressList = customerDelegate.displayLocations();
					mav = new ModelAndView("rider");
					mav.addObject("addressList", addressList);

					return mav;

				} else if (registerRole.equals(Constants.DRIVER)) {
					driverID = sharedDelegate.getUserID(registerContact);
					addLicenceResponse = driverDelegate.addLicenceDetails(licenceNumber, registerContact,
							registerZipcode);
					user = sharedDelegate.displayProfile(registerContact);

					if (addLicenceResponse.equals(Constants.SUCCESS)) {
						mav = new ModelAndView("driver");
						mav.addObject("userProfile", user);
						return mav;
					}
				}
			}
			response.sendRedirect("index.jsp");
			return null;
		} catch (Exception exception) {
			response.sendRedirect("index.jsp");
			return null;
		}
	}

	@RequestMapping(value = "/riders", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayRider(HttpSession session) {

		User user = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");

			user = sharedDelegate.displayProfile(userPhone);
			mav = new ModelAndView("rider");
			mav.addObject("userProfile", user);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/drivers", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayDriver(HttpSession session) {

		User user = null;
		String userPhone = "";
		ModelAndView mav = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");

			user = sharedDelegate.displayProfile(userPhone);
			mav = new ModelAndView("driver");
			mav.addObject("userProfile", user);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/rider/profile", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayDashboardRider(HttpSession session) {

		User user = null;
		String userPhone = "";
		ModelAndView mav = null;
		int bookingID = -1;
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";

		PostConfirm postConfirm = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");
			bookingID = sharedDelegate.checkBookingStatus(userPhone, 0);
			postConfirm = sharedDelegate.getBookingDetails(bookingID);

			sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
			destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

			zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
			zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

			user = sharedDelegate.displayProfile(userPhone);
			mav = new ModelAndView("rider_dashboard");
			mav.addObject("userProfile", user);
			mav.addObject("postConfirmInvoice", postConfirm);
			mav.addObject("sourceZip", zipCodeSource);
			mav.addObject("destinationZip", zipCodeDestination);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/dashboard/driver/profile", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView displayDashboardDriver(HttpSession session) {

		User user = null;
		String userPhone = "";
		ModelAndView mav = null;
		int bookingID = -1;
		String sourceExtract = "";
		String destinationExtract = "";
		String zipCodeSource = "";
		String zipCodeDestination = "";

		PostConfirm postConfirm = null;

		try {
			userPhone = (String) session.getAttribute("userPhone");
			bookingID = sharedDelegate.checkBookingStatus(userPhone, 1);
			postConfirm = sharedDelegate.getBookingDetails(bookingID);

			sourceExtract = customerDelegate.extractLocation(postConfirm.getSource(), 0);
			destinationExtract = customerDelegate.extractLocation(postConfirm.getDestination(), 0);

			zipCodeSource = customerDelegate.extractLocation(postConfirm.getSource(), 1);
			zipCodeDestination = customerDelegate.extractLocation(postConfirm.getDestination(), 1);

			user = sharedDelegate.displayProfile(userPhone);
			mav = new ModelAndView("driver_dashboard");
			mav.addObject("userProfile", user);

			mav.addObject("postConfirmInvoice", postConfirm);
			mav.addObject("sourceZip", zipCodeSource);
			mav.addObject("destinationZip", zipCodeDestination);

			return mav;

		} catch (Exception exception) {
			return null;
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	@ResponseBody
	public void logoutUser(HttpSession session, HttpServletResponse response) {

		try {
			session.invalidate();

			response.sendRedirect("index.jsp");
		} catch (Exception exception) {
		}
	}
}
