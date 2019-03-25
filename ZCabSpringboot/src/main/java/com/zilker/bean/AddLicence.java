package com.zilker.bean;

public class AddLicence {
	
	private int driverID;
	private String licenceNumber;
	private String zipCode;
	
	public AddLicence() {}

	public AddLicence(int driverID, String licenceNumber, String zipCode) {
		super();
		this.driverID = driverID;
		this.licenceNumber = licenceNumber;
		this.zipCode = zipCode;
	}

	public int getDriverID() {
		return driverID;
	}

	public void setDriverID(int driverID) {
		this.driverID = driverID;
	}

	public String getLicenceNumber() {
		return licenceNumber;
	}

	public void setLicenceNumber(String licenceNumber) {
		this.licenceNumber = licenceNumber;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
