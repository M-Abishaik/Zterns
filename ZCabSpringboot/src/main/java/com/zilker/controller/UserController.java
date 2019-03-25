package com.zilker.controller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.zilker.bean.AddLicence;
import com.zilker.bean.Address;
import com.zilker.bean.BookingResponse;
import com.zilker.bean.Login;
import com.zilker.bean.PostConfirm;
import com.zilker.bean.User;
import com.zilker.bean.UpdateProfile;
import com.zilker.constants.Constants;
import com.zilker.customexception.ApplicationException;
import com.zilker.delegate.CustomerDelegate;
import com.zilker.delegate.DriverDelegate;
import com.zilker.delegate.SharedDelegate;
import com.zilker.util.ResponseGenerator;

@RestController
public class UserController {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	@Autowired
	SharedDelegate sharedDelegate;
	
	@Autowired
	CustomerDelegate customerDelegate;

	@Autowired
	DriverDelegate driverDelegate;
	
	@Autowired
	ResponseGenerator responseGenerator;
	
	@PostMapping(value="/users")
	public ResponseEntity<?> registerUser(@RequestBody User user) throws IOException {
		
		String registerResponse = "";

		try {
			
			registerResponse = sharedDelegate.register(user);

			return responseGenerator.successResponse(Constants.SUCCESS);
					
		}catch(ApplicationException applicationException){	
			return responseGenerator.errorResponse(applicationException);
		}
	}
	
	@PostMapping(value = "/users/login")
	public ResponseEntity<?> loginUser(@RequestBody Login user) {
			
		String loginResponse = "";
		
		try {
			loginResponse = sharedDelegate.login(user.getContact(), user.getPassword());
			
			if (!loginResponse.equals(Constants.FAILURE)) {
				if (loginResponse.equals(Constants.CUSTOMER)) {
					return responseGenerator.successResponse(Constants.CUSTOMER); 
				} else {
					return responseGenerator.successResponse(Constants.DRIVER); 
				}	
			}
			return null;
		}catch(ApplicationException applicationException){	
			return responseGenerator.errorResponse(applicationException);
		}
	}
	
	@PostMapping(value="/driver")
	public ResponseEntity<?> addLicenceDetails(@RequestBody AddLicence addLicence) {
		
		try {
			driverDelegate.addLicenceDetails(addLicence.getDriverID(), addLicence.getLicenceNumber(), addLicence.getZipCode());
			return responseGenerator.successResponse(Constants.SUCCESS); 
			
		}catch(ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}		
	}

	@GetMapping(value = "/user/{contact}")
	public ResponseEntity<?> displayProfile(@PathVariable String contact) {

		User user = null;

		try {
			user = sharedDelegate.displayProfile(contact);
			return responseGenerator.successResponse(user);

		} catch(ApplicationException applicationException){	
			return responseGenerator.errorResponse(applicationException);
		}
	}
	
	@GetMapping(value = "/check/user/{contact}")
	public ResponseEntity<?> isContactExists(@PathVariable String contact) {

		boolean check = false;
		
		try {
			check = sharedDelegate.isContactExists(contact);
			return responseGenerator.successResponse(check);

		} catch(ApplicationException applicationException){	
			return responseGenerator.errorResponse(applicationException);
		}
	}


	@PostMapping(value = "/users/update")
	public ResponseEntity<?> updateUser(@RequestBody UpdateProfile updateProfile) {

		String updateResponse = "";

		try {
			updateResponse = sharedDelegate.updateProfile(updateProfile);

			if (updateResponse.equals(Constants.SUCCESS)) {
				LOGGER.log(Level.INFO, "Account successfully updated.");

				return responseGenerator.successResponse(Constants.SUCCESS);
				
			}
			return responseGenerator.successResponse(Constants.FAILURE);

		} catch(ApplicationException applicationException){	
			return responseGenerator.errorResponse(applicationException);
		}
	}


	@GetMapping(value = "/users/{contact}")
	public ResponseEntity<?> getUserID(@PathVariable String contact) {

		int userID = -1;
		
		try {
			userID = sharedDelegate.getUserID(contact);
			return responseGenerator.successResponse(userID);
		}catch(ApplicationException applicationException) {
			return responseGenerator.errorResponse(applicationException);
		}
	}	
	
}
