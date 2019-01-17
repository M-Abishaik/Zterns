package com.zilker.taxi.bean;

public class UpdateRide {
	
	private String rideStartTime, rideEndTime;
	int sourceID, destinationID, bookingID;
	float price;
	
	public UpdateRide(String rideStartTime, String rideEndTime, int sourceID, int destinationID, int bookingID,
			float price) {
		super();
		this.rideStartTime = rideStartTime;
		this.rideEndTime = rideEndTime;
		this.sourceID = sourceID;
		this.destinationID = destinationID;
		this.bookingID = bookingID;
		this.price = price;
	}

	public String getRideStartTime() {
		return rideStartTime;
	}

	public void setRideStartTime(String rideStartTime) {
		this.rideStartTime = rideStartTime;
	}

	public String getRideEndTime() {
		return rideEndTime;
	}

	public void setRideEndTime(String rideEndTime) {
		this.rideEndTime = rideEndTime;
	}

	public int getSourceID() {
		return sourceID;
	}

	public void setSourceID(int sourceID) {
		this.sourceID = sourceID;
	}

	public int getDestinationID() {
		return destinationID;
	}

	public void setDestinationID(int destinationID) {
		this.destinationID = destinationID;
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

}
