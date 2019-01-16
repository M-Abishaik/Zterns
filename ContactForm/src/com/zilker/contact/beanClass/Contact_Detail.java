package com.zilker.contact.beanClass;

/*
 * Contact Bean.
 */

public class Contact_Detail {
	
	private String firstName, lastName, mailId;
	
	public Contact_Detail(String fName, String sName, String mId) {
		this.firstName = fName;
		this.lastName = sName;
		this.mailId = mId;
	}
	
	public void setFirstName(String fName) {
		this.firstName = fName;
	}
	
	public void setLastName(String sName) {
		this.lastName = sName;
	}

	public void setMailId(String mail) {
		this.mailId = mail;
	}
	
	public String getFirstName() {
		return firstName;
	}
	
	public String getLastName() {
		return lastName;
	}
	
	public String getMail() {
		return mailId;
	}
}
