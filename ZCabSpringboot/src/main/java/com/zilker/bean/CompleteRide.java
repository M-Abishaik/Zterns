package com.zilker.bean;

public class CompleteRide {
	
	private int bookingID;
	private String userPhone;
	
	public CompleteRide() {}
	
	public CompleteRide(int bookingID, String userPhone) {
		super();
		this.bookingID = bookingID;
		this.userPhone = userPhone;
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
