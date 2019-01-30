package com.zilker.taxi.bean;

public class CabModelID {

	private String modelName;
	private String modelDescription;
	private int numberSeats;
	private int modelID;
	
	
	public CabModelID() {}
	
	public CabModelID(String modelName, String modelDescription, int numberSeats, int modelID) {
		super();
		this.modelName = modelName;
		this.modelDescription = modelDescription;
		this.numberSeats = numberSeats;
		this.modelID = modelID;
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

	public int getNumberSeats() {
		return numberSeats;
	}

	public void setNumberSeats(int numberSeats) {
		this.numberSeats = numberSeats;
	}

	public int getModelID() {
		return modelID;
	}

	public void setModelID(int modelID) {
		this.modelID = modelID;
	}
	
	
	
	
}
