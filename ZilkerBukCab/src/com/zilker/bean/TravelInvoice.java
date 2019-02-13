package com.zilker.bean;

/*
 * Displays the booking details of a ride.
 */
public class TravelInvoice {

	private int customerID;
	private int driverID;
	private int cabID;
	private int sourceID;
	private int destinationID;
	private String formattedTime;

	public TravelInvoice() {

	}

	public TravelInvoice(int customerID, int driverID, int cabID, int sourceID, int destinationID,
			String formattedTime) {
		super();
		this.customerID = customerID;
		this.driverID = driverID;
		this.cabID = cabID;
		this.sourceID = sourceID;
		this.destinationID = destinationID;
		this.formattedTime = formattedTime;
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

	public String getFormattedTime() {
		return formattedTime;
	}

	public void setFormattedTime(String formattedTime) {
		this.formattedTime = formattedTime;
	}
}
