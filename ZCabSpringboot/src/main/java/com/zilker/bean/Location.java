package com.zilker.bean;

public class Location {

	private String location;
	private String zipCode;
	
	public Location() {}
	
	public Location(String location, String zipCode) {
		super();
		this.location = location;
		this.zipCode = zipCode;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
	
}
