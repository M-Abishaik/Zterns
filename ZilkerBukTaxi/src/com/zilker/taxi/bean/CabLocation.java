package com.zilker.taxi.bean;

public class CabLocation {
	
	private int cabID;
	private float cabLatitude;
	private float cabLongitude;
	
	public CabLocation() {}
	
	public CabLocation(int cabID, float cabLatitude, float cabLongitude) {
		super();
		this.cabID = cabID;
		this.cabLatitude = cabLatitude;
		this.cabLongitude = cabLongitude;
	}

	public int getCabID() {
		return cabID;
	}

	public void setCabID(int cabID) {
		this.cabID = cabID;
	}

	public float getCabLatitude() {
		return cabLatitude;
	}

	public void setCabLatitude(float cabLatitude) {
		this.cabLatitude = cabLatitude;
	}

	public float getCabLongitude() {
		return cabLongitude;
	}

	public void setCabLongitude(float cabLongitude) {
		this.cabLongitude = cabLongitude;
	}

}
