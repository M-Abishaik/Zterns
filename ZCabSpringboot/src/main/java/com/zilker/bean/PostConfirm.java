package com.zilker.bean;

public class PostConfirm {

	private int bookingID;
	private String startTime;
	private String source;
	private String destination;
	private String driver;
	private String cab;
	private float price;

	public PostConfirm() {
	}

	public PostConfirm(int bookingID, String startTime, String source, String destination, String driver, String cab,
			float price) {
		super();
		this.bookingID = bookingID;
		this.startTime = startTime;
		this.source = source;
		this.destination = destination;
		this.driver = driver;
		this.cab = cab;
		this.price = price;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
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

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

}
