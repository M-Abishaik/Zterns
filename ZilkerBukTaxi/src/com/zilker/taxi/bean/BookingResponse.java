package com.zilker.taxi.bean;

/*
 *  The booking response when a ride has been booked.  
 */

public class BookingResponse {

	private int bookingID;
	private float price;
	private String source; 
	private String destination; 
	private String startTime; 
	private String endTime;
	
	public BookingResponse(int bookingID, float price, String source, String destination, String startTime,
			String endTime) {
		super();
		this.bookingID = bookingID;
		this.price = price;
		this.source = source;
		this.destination = destination;
		this.startTime = startTime;
		this.endTime = endTime;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
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

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}
}
