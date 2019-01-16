package com.zilker.contact.constants;

import java.util.regex.Pattern;

/*
 * Constant declarations.
 */

public class Constants {

	public static final Pattern VALID_EMAIL_ADDRESS_REGEX = 
		    Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);
	
	public static final Pattern VALID_MOBILE_EXTENSION_REGEX = 
		    Pattern.compile("^\\+[1-9]{1}[0-9]{1,3}$");
	
	public static final Pattern VALID_OFFICE_EXTENSION_REGEX = 
		    Pattern.compile("[1-9]{3,5}");
	
	public static final Pattern VALID_AREA_CODE = 
		    Pattern.compile("[1-9]{2,3}");
	
	public static final Pattern VALID_COUNTRY_CODE = 
		    Pattern.compile("^\\+[1-9]{1,3}");
}
