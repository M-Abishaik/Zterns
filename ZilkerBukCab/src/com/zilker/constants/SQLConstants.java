package com.zilker.constants;

/*
 * Describes SQL queries.
 */

public class SQLConstants {

	public static final String CHECK_CONTACT_EXISTS = "SELECT USER_ID FROM USER_DETAIL WHERE CONTACT=?";

	public static final String INSERT_PERSONAL_DETAILS = "INSERT INTO USER_DETAIL(USERNAME, MAIL, CONTACT, ROLE, PASS) VALUES(?,?,?,?,?)";

	public static final String GET_USER_ID = "SELECT USER_ID FROM USER_DETAIL WHERE CONTACT=?";

	public static final String UPDATE_USER_ID = "UPDATE USER_DETAIL SET CREATED_BY=?, UPDATED_BY=?, UPDATED_AT=current_timestamp WHERE CONTACT=?";

	public static final String INSERT_USER_ADDRESS = "INSERT INTO ADDRESS_DETAIL(USER_ID, STREET_ADDRESS, CITY, ZIP_CODE, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?,?,?)";

	public static final String CHECK_LOGIN = "SELECT ROLE FROM USER_DETAIL WHERE CONTACT=? AND PASS=?";

	public static final String INSERT_CAB_MODEL_DETAILS = "INSERT INTO CAB_MODEL(MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?,?)";

	public static final String GET_MODEL_ID = "SELECT MODEL_ID FROM CAB_MODEL WHERE MODEL_NAME=? AND MODEL_DESCRIPTION=?";

	public static final String INSERT_CAB_LAUNCH_DETAILS = "INSERT INTO CAB_DETAIL(LICENCE_PLATE, MODEL_ID, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?)";

	public static final String GET_MODEL_ID_BY_LICENCE = "SELECT MODEL_ID FROM CAB_DETAIL WHERE LICENCE_PLATE=?";

	public static final String UPDATE_CAB_MODEL = "UPDATE CAB_MODEL SET MODEL_NAME=?, MODEL_DESCRIPTION=?, NUMBER_OF_SEATS=?, UPDATED_AT=current_timestamp, UPDATED_BY=? WHERE MODEL_ID=?";

	public static final String DELETE_CAB_MODEL = "DELETE FROM CAB_MODEL WHERE MODEL_ID=?";

	public static final String DELETE_CAB_DETAILS = "DELETE FROM CAB_DETAIL WHERE MODEL_ID=?";

	public static final String DISPLAY_CAB_MODELS = "SELECT LICENCE_PLATE, MODEL_NAME, MODEL_DESCRIPTION, NUMBER_OF_SEATS FROM CAB_DETAIL, CAB_MODEL WHERE CAB_DETAIL.MODEL_ID=CAB_MODEL.MODEL_ID";

	public static final String INSERT_LICENCE_DETAILS = "INSERT INTO DRIVER_DETAIL(USER_ID, LICENCE_NUMBER, ZIP_CODE, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?,?)";

	public static final String GET_CAB_ID = "SELECT CAB_ID FROM CAB_DETAIL WHERE CAB_STATUS=5";

	public static final String INSERT_CAB_DRIVER = "INSERT INTO CAB_DRIVER(DRIVER_ID, CAB_ID, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?)";

	public static final String UPDATE_CAB_STATUS = "UPDATE CAB_DETAIL SET CAB_STATUS=? WHERE CAB_ID=?";

	public static final String DISPLAY_LOCATIONS = "SELECT STREET_ADDRESS, ZIP_CODE FROM ADDRESS_DETAIL";

	public static final String GET_ZIP_CODE = "SELECT ZIP_CODE FROM ADDRESS_DETAIL WHERE ADDRESS_ID=?";

	public static final String GET_DRIVER_ID = "SELECT USER_ID FROM DRIVER_DETAIL WHERE DRIVER_STATUS=? AND ZIP_CODE=?";

	public static final String GET_CAB_DRIVER_ID = "SELECT CAB_ID FROM CAB_DRIVER WHERE DRIVER_ID=?";

	public static final String GET_MODEL_BY_CAB_ID = "SELECT MODEL_ID FROM CAB_DETAIL WHERE CAB_ID=?";

	public static final String GET_CAB_SEATS = "SELECT NUMBER_OF_SEATS FROM CAB_MODEL WHERE MODEL_ID=?";

	public static final String GET_DRIVER_BY_CAB_ID = "SELECT DRIVER_ID FROM CAB_DRIVER WHERE CAB_ID=?";

	public static final String INSERT_RIDE_DETAILS = "INSERT INTO CAB_RIDE_DETAIL(USER_ID, DRIVER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?,?,?,?,?,?)";

	public static final String GET_BOOKING_ID = "SELECT CAB_RIDE_ID FROM CAB_RIDE_DETAIL WHERE USER_ID=? AND DRIVER_ID=? AND CAB_ID=? AND START_TIME=?";

	public static final String UPDATE_DRIVER_STATUS = "UPDATE DRIVER_DETAIL SET DRIVER_STATUS=? WHERE USER_ID=?";

	public static final String GET_LOCATION = "SELECT STREET_ADDRESS, ZIP_CODE FROM ADDRESS_DETAIL WHERE ADDRESS_ID=?";

	public static final String GET_DRIVER_BY_ID = "SELECT USERNAME, CONTACT FROM USER_DETAIL WHERE USER_ID=?";

	public static final String GET_CAB_BY_ID = "SELECT MODEL_NAME, MODEL_DESCRIPTION FROM CAB_MODEL, CAB_DETAIL WHERE CAB_MODEL.MODEL_ID=CAB_DETAIL.MODEL_ID AND CAB_DETAIL.CAB_ID=?";

	public static final String CHECK_BOOKING_EXISTS = "SELECT CAB_RIDE_ID FROM CAB_RIDE_DETAIL WHERE CAB_RIDE_ID=? AND DRIVER_ID=? AND STATUS_ID=1";

	public static final String UPDATE_PROFILE = "UPDATE USER_DETAIL SET MAIL=?, PASS=?, UPDATED_AT=current_timestamp WHERE CONTACT=?";

	public static final String CANCEL_RIDE = "UPDATE CAB_RIDE_DETAIL SET UPDATED_AT=current_timestamp, STATUS_ID=? WHERE CAB_RIDE_ID=?";

	public static final String GET_START_TIME = "SELECT START_TIME FROM CAB_RIDE_DETAIL WHERE CAB_RIDE_ID=?";

	public static final String UPDATE_RIDE_DETAILS = "UPDATE CAB_RIDE_DETAIL SET DRIVER_ID=?, START_TIME=?, SOURCE_ADDRESS_ID=?, DESTINATION_ADDRESS_ID=?, CAB_ID=?, PRICE=?, UPDATED_AT=current_timestamp WHERE CAB_RIDE_ID=?";

	public static final String DISPLAY_CUSTOMER_RIDE_HISTORY = "SELECT DRIVER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE, STATUS_ID FROM CAB_RIDE_DETAIL WHERE USER_ID=?";

	public static final String DISPLAY_DRIVER_RIDE_HISTORY = "SELECT USER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE, STATUS_ID FROM CAB_RIDE_DETAIL WHERE DRIVER_ID=?";

	public static final String DISPLAY_RIDE_HISTORY_BY_LOCATION = "SELECT DISTINCT CAB_RIDE_DETAIL.USER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE, STATUS_ID FROM CAB_RIDE_DETAIL, ADDRESS_DETAIL WHERE DESTINATION_ADDRESS_ID=ADDRESS_ID AND ZIP_CODE=? AND CAB_RIDE_DETAIL.DRIVER_ID=?";

	public static final String COMPLETE_RIDE = "SELECT DISTINCT CAB_RIDE_ID FROM CAB_RIDE_DETAIL, ADDRESS_DETAIL WHERE ZIP_CODE=? AND CAB_RIDE_DETAIL.DRIVER_ID=? AND STATUS_ID=?";

	public static final String UPDATE_RIDE_COMPLETION = "UPDATE CAB_RIDE_DETAIL SET STATUS_ID=3, UPDATED_AT=current_timestamp, UPDATED_BY=? WHERE CAB_RIDE_ID=?";

	public static final String UPDATE_DRIVER_LOCATION = "UPDATE DRIVER_DETAIL SET DRIVER_STATUS=5, ZIP_CODE=?, UPDATED_BY=?, UPDATED_AT=current_timestamp WHERE USER_ID=?";

	public static final String CHECK_COMPLETED_BOOKING = "SELECT CAB_RIDE_ID FROM CAB_RIDE_DETAIL WHERE CAB_RIDE_ID=? AND USER_ID=? AND STATUS_ID=3";

	public static final String INSERT_TRIP_RATING = "INSERT INTO RATING(CAB_RIDE_ID, RATING, CREATED_BY, UPDATED_BY) VALUES(?,?,?,?)";

	public static final String INSERT_ROUTE_DETAILS = "INSERT INTO ROUTE_DETAIL(SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, DISTANCE) VALUES(?,?,?)";

	public static final String GET_ADDRESS_ID = "SELECT ADDRESS_ID FROM ADDRESS_DETAIL WHERE STREET_ADDRESS=? AND ZIP_CODE=?";
	
	public static final String UPDATE_USER_ADDRESS = "UPDATE ADDRESS_DETAIL SET STREET_ADDRESS=?, CITY=?, ZIP_CODE=?, UPDATED_AT=current_timestamp WHERE USER_ID=?";

	public static final String DISPLAY_CUSTOMER_BOOKING_DETAILS = "SELECT CAB_RIDE_ID, DRIVER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE FROM CAB_RIDE_DETAIL WHERE USER_ID=? AND STATUS_ID=?";

	public static final String DISPLAY_DRIVER_BOOKING_DETAILS = "SELECT CAB_RIDE_ID, DRIVER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE FROM CAB_RIDE_DETAIL WHERE DRIVER_ID=? AND STATUS_ID=?";

	public static final String GET_CUSTOMER_BOOKING_STATUS = "SELECT CAB_RIDE_ID FROM CAB_RIDE_DETAIL WHERE USER_ID=? AND STATUS_ID=1";
	
	public static final String GET_DRIVER_BOOKING_STATUS = "SELECT CAB_RIDE_ID FROM CAB_RIDE_DETAIL WHERE DRIVER_ID=? AND STATUS_ID=1";

	public static final String GET_DRIVER_BY_BOOKING_ID = "SELECT DRIVER_ID FROM CAB_RIDE_DETAIL WHERE CAB_RIDE_ID=?";
	
	public static final String DISPLAY_COMPLETED_RATED_RIDES = "SELECT CAB_RIDE_DETAIL.CAB_RIDE_ID, DRIVER_ID, START_TIME, SOURCE_ADDRESS_ID, DESTINATION_ADDRESS_ID, CAB_ID, PRICE, RATING FROM CAB_RIDE_DETAIL, RATING WHERE CAB_RIDE_DETAIL.CAB_RIDE_ID=RATING.CAB_RIDE_ID AND USER_ID=? AND STATUS_ID=?";

	public static final String GET_DESTINATION_ID = "SELECT DESTINATION_ADDRESS_ID FROM CAB_RIDE_DETAIL WHERE CAB_RIDE_ID=?";

	public static final String GET_ZIP_BY_DESTINATION_ID = "SELECT ZIP_CODE FROM ADDRESS_DETAIL JOIN CAB_RIDE_DETAIL ON DESTINATION_ADDRESS_ID=ADDRESS_ID WHERE CAB_RIDE_ID=?";

	public static final String GET_ZIP_BY_SOURCE_ID = "SELECT ZIP_CODE FROM ADDRESS_DETAIL JOIN CAB_RIDE_DETAIL ON SOURCE_ADDRESS_ID=ADDRESS_ID WHERE CAB_RIDE_ID=?";

}
