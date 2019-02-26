package com.zilker.bean;

public class CompleteRating {
	
	private int bookingID;
	private String driver;
	private String cab;
	private String source;
	private String destination;
	private String startTime;
	private float price;
	private float rating;
	
	public CompleteRating() {}
	
	public CompleteRating(int bookingID, String driver, String cab, String source, String destination, String startTime,
			float price, float rating) {
		super();
		this.bookingID = bookingID;
		this.driver = driver;
		this.cab = cab;
		this.source = source;
		this.destination = destination;
		this.startTime = startTime;
		this.price = price;
		this.rating = rating;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public String getDriver() {
		return driver;
	}

	public void setDriver(String driver) {
		this.driver = driver;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestination() {
		return destination;
	}

	public void setDestination(String destination) {
		this.destination = destination;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public float getRating() {
		return rating;
	}

	public void setRating(float rating) {
		this.rating = rating;
	}
	
	
	
	

}
