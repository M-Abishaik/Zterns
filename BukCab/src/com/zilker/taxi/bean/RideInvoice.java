package com.zilker.taxi.bean;

/*
 *	Ride details of a customer. 
 */

public class RideInvoice {

	private int customerID;
	private int driverID;
	private int cabID;
	private int sourceID;
	private int destinationID;
	private String rideStartTime;
	private float price;

	public RideInvoice(int customerID, int driverID, int cabID, int sourceID, int destinationID, String rideStartTime,
			float price) {
		super();
		this.customerID = customerID;
		this.driverID = driverID;
		this.cabID = cabID;
		this.sourceID = sourceID;
		this.destinationID = destinationID;
		this.rideStartTime = rideStartTime;
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
