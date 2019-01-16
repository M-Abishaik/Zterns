package com.zilker.taxi.bean;

/*
 *	Ride details of a customer. 
 */

public class Invoice {
	
	private int customerID, driverID, cabID, sourceID, destinationID;
	private String rideStartTime, rideEndTime;
	private float price;
	
	public Invoice(int customerID, int driverID, int cabID, int sourceID, int destinationID, String rideStartTime, 
			String rideEndTime, float price) {
		super();
		this.customerID = customerID;
		this.driverID = driverID;
		this.cabID = cabID;
		this.sourceID = sourceID;
		this.destinationID = destinationID;
		this.rideStartTime = rideStartTime;
		this.rideEndTime = rideEndTime;
		this.price = price;
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

	public String getRideEndTime() {
		return rideEndTime;
	}

	public void setRideEndTime(String rideEndTime) {
		this.rideEndTime = rideEndTime;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getCustomerID() {
		return customerID;
	}

	public void setCustomerID(int customerID) {
		this.customerID = customerID;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public int getCabID() {
		return cabID;
	}

	public void setCabID(int cabID) {
		this.cabID = cabID;
	}
	
	public String getRideStartTime() {
		return rideStartTime;
	}

	public void setRideStartTime(String rideStartTime) {
		this.rideStartTime = rideStartTime;
	}
}
