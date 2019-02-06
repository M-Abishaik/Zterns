package com.zilker.taxi.constant;

import java.util.regex.Pattern;

/*
 * Constant declarations. 
 */

public class Constants {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$",
			Pattern.CASE_INSENSITIVE);

	public static final Pattern VALID_NAME_REGEX = Pattern.compile("^[\\p{L} .'-]+$");

	public static final Pattern VALID_MOBILE_REGEX = Pattern.compile("[6-9][0-9]{9}");

	public static final Pattern VALID_PASSWORD_REGEX = Pattern
			.compile("^(?=.*[0-9])(?=[a-zA-Z]{2,})([@$!A-Za-z0-9_-]+)");

	public static final Pattern VALID_ZIPCODE_REGEX = Pattern.compile("^[1-9][0-9]{5}$");

	public static final Pattern VALID_LICENCE_PLATE_REGEX = Pattern.compile("[A-Z][A-Z]([A-Z]|\\d)\\d\\d");

	public static final Pattern VALID_LICENCE_REGEX = Pattern.compile("/^[0-3][0-9]{7}$/");

	public static final Pattern VALID_STRING_REGEX = Pattern.compile("^[a-zA-Z]+$");

	public static final String CUSTOMER_MESSAGE = "1.Book a ride." + "\n" + "2. Display details." + "\n"
			+ "3. Update personal details." + "\n" + "4. Update booking details. " + "\n" + "5. Cance a ride." + "\n"
			+ "6. View my rides." + "\n" + "7. Rate my trips." + "\n" + "8. Logout.";

	public static final String ADMIN_MESSAGE = "1. Add a cab model." + "\n" + "2. Display cab models." + "\n"
			+ "3. Update a cab model." + "\n" + "4. Delete a cab model." + "\n" + "5. Logout.";

	public static final String DRIVER_MESSAGE = "1. Add Licence number." + "\n" + "2. Complete a ride." + "\n"
			+ "3. View my rides." + "\n" + "4. View rides by location." + "\n" + "5. Logout.";

	public static final String CONNECTION = "jdbc:mysql://localhost:3306/TAXI";
	public static final String USERNAME = "newuser";
	public static final String PASSWORD = "tester";
	public static final String AVAILABLE = "available";
	public static final String NOT_AVAILABLE = "unavailable";
	public static final String CUSTOMER = "customer";
	public static final String DRIVER = "driver";
	public static final String ADMIN = "admin";
	public static final String SUCCESS = "success";
	public static final String FAILURE = "failure";

}
