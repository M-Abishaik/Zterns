package com.zilker.bean;

public class BookRide {
	
	private String startDate;
	private String startTime;
	private String source;
	private String destination;
	private String seats;
	
	public BookRide() {}

	public BookRide(String startDate, String startTime, String source, String destination, String seats) {
		super();
		this.startDate = startDate;
		this.startTime = startTime;
		this.source = source;
		this.destination = destination;
		this.seats = seats;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
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

	public String getSeats() {
		return seats;
	}

	public void setSeats(String seats) {
		this.seats = seats;
	}
}
