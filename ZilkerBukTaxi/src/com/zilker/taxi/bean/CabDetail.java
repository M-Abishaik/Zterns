package com.zilker.taxi.bean;

public class CabDetail {
	private String LicencePlate;
	private float latitude;
	private float longitude;
	private int modelID;
	
	public CabDetail() {}
	
	public CabDetail(String licencePlate, float latitude, float longitude, int modelID) {
		super();
		this.LicencePlate = licencePlate;
		this.latitude = latitude;
		this.longitude = longitude;
		this.modelID = modelID;
	}

	public String getLicencePlate() {
		return LicencePlate;
	}

	public void setLicencePlate(String licencePlate) {
		LicencePlate = licencePlate;
	}

	public float getLatitude() {
		return latitude;
	}

	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}

	public float getLongitude() {
		return longitude;
	}

	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}
}
