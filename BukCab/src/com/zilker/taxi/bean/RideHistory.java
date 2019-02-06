package com.zilker.taxi.bean;

/*
 * Displays the ride history of a user.
 */

public class RideHistory {

	public RideHistory() {

	}

	private String person;
	private String cab;
	private String date;
	private String source;
	private String destintion;
	private float price;
	private int statusID;

	public RideHistory(String person, String cab, String date, String source, String destintion, float price,
			int statusID) {
		super();
		this.person = person;
		this.cab = cab;
		this.date = date;
		this.source = source;
		this.destintion = destintion;
		this.price = price;
		this.statusID = statusID;
	}

	public String getPerson() {
		return person;
	}

	public void setPerson(String person) {
		this.person = person;
	}

	public String getCab() {
		return cab;
	}

	public void setCab(String cab) {
		this.cab = cab;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getDestintion() {
		return destintion;
	}

	public void setDestintion(String destintion) {
		this.destintion = destintion;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public int getStatusID() {
		return statusID;
	}

	public void setStatusID(int statusID) {
		this.statusID = statusID;
	}

}
