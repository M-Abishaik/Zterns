package com.zilker.bean;

public class RateRide {
	
	private int rating;
	private int bookingID;
	private String userPhone;
	
	public RateRide() {}
	
	public RateRide(int rating, int bookingID, String userPhone) {
		super();
		this.rating = rating;
		this.bookingID = bookingID;
		this.userPhone = userPhone;
	}

	public int getRating() {
		return rating;
	}

	public void setRating(int rating) {
		this.rating = rating;
	}

	public int getBookingID() {
		return bookingID;
	}

	public void setBookingID(int bookingID) {
		this.bookingID = bookingID;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	
}
