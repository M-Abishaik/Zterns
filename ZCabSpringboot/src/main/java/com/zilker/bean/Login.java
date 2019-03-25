package com.zilker.bean;

public class Login {
	
	private String contact;
	private String password;
	
	public Login() {}

	public Login(String contact, String password) {
		super();
		this.contact = contact;
		this.password = password;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

}
