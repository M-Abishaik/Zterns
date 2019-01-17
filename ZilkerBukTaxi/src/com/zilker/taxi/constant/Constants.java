package com.zilker.taxi.constant;

import java.util.logging.Logger;
import java.util.regex.Pattern;

/*
 * Constant declarations. 
 */

public class Constants {
	
	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
			Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

	public static final String CONNECTION = "jdbc:mysql://localhost:3306/TAXI";
	public static final String USERNAME = "root";
	public static final String PASSWORD = "";
	public static final String AVAILABLE = "Available";
	public static final String NOT_AVAILABLE = "Unavailable";
}
