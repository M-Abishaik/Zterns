package com.zilker.bean;

public class DisplayInvoice {
	
	private String driver;
	private String cab;
	private String source;
	private String destination;
	private String startTime;
	private int seats;
	
	public DisplayInvoice() {}

	public DisplayInvoice(String driver, String cab, String source, String destination, String startTime, int seats) {
		super();
		this.driver = driver;
		this.cab = cab;
		this.source = source;
		this.destination = destination;
		this.startTime = startTime;
		this.seats = seats;
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

	public int getSeats() {
		return seats;
	}

	public void setSeats(int seats) {
		this.seats = seats;
	}

	
	

}
