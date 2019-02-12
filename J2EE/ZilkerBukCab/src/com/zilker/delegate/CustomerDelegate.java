package com.zilker.delegate;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.zilker.bean.Address;
import com.zilker.dao.CustomerDAO;

public class CustomerDelegate {
	
	private final static Logger LOGGER = Logger.getLogger(Logger.GLOBAL_LOGGER_NAME);
	
	/*
	 * Displays travel locations.
	 */

	public ArrayList<Address> displayLocations() {

		CustomerDAO customerDAO = null;
		ArrayList<Address> address = null;

		try {
			address = new ArrayList<Address>();
			customerDAO = new CustomerDAO();
			address = customerDAO.displayLocations();

			return address;
		} catch (Exception e) {
			LOGGER.log(Level.WARNING, "Error in fetching travel locations from DAO.");
			return null;
		}
	}

}
