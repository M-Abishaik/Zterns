package com.zilker.constants;

public class SQLConstants {
	
	public static final String INSERT_PERSONAL_DETAILS = "INSERT INTO USER_DETAIL(USERNAME, MAIL, CONTACT, ROLE, PASS) VALUES(?,?,?,?,?)";

	public static final String CHECK_LOGIN = "SELECT ROLE FROM USER_DETAIL WHERE CONTACT=? AND PASS=?";

}
