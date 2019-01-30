package com.zilker.taxi.bean;

public class CabModel {
	private String modelName;
	private String modelDescription;
	private int numberSeats;
	
	public CabModel() {

	}
	
	public CabModel(String modelName, String modelDescription, int numberSeats) {
		super();
		this.modelName = modelName;
		this.modelDescription = modelDescription;
		this.numberSeats = numberSeats;
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
}
