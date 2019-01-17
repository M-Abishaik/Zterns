package com.zilker.taxi.bean;

/*
 *	Personal details of customer. 
 */

public class Customer {

	private String firstName, lastName, mailId;
	
	public Customer() {}
	
	public Customer(String firstName, String lastName, String mailId) {
		super();
		this.firstName = firstName;
		this.lastName = lastName;
		this.mailId = mailId;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getMailId() {
		return mailId;
	}

	public void setMailId(String mailId) {
		this.mailId = mailId;
	}
}
