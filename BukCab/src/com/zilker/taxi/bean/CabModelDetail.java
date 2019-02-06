package com.zilker.taxi.bean;

public class CabModelDetail {

	private String licencePlate;
	private String modelName;
	private String modelDescription;
	private int numSeats;

	public CabModelDetail() {

	}

	public CabModelDetail(String licencePlate, String modelName, String modelDescription, int numSeats) {
		super();
		this.licencePlate = licencePlate;
		this.modelName = modelName;
		this.modelDescription = modelDescription;
		this.numSeats = numSeats;
	}

	public String getLicencePlate() {
		return licencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		this.licencePlate = licencePlate;
	}

	public String getModelName() {
		return modelName;
	}

	public void setModelName(String modelName) {
		this.modelName = modelName;
	}

	public String getModelDescription() {
		return modelDescription;
	}

	public void setModelDescription(String modelDescription) {
		this.modelDescription = modelDescription;
	}

	public int getNumSeats() {
		return numSeats;
	}

	public void setNumSeats(int numSeats) {
		this.numSeats = numSeats;
	}

}
