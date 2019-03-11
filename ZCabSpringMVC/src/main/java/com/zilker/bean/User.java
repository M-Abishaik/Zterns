package com.zilker.bean;

/*
 * Displays user details.
 */
public class User {

	private String userName;
	private String mail;
	private String contact;
	private String role;
	private String password;
	private String address;
	private String zipCode;

	public User() {
	}

	public User(String userName, String mail, String contact, String role, String password, String address,
			String zipCode) {
		super();
		this.userName = userName;
		this.mail = mail;
		this.contact = contact;
		this.role = role;
		this.password = password;
		this.address = address;
		this.zipCode = zipCode;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getMail() {
		return mail;
	}

	public void setMail(String mail) {
		this.mail = mail;
	}

	public String getContact() {
		return contact;
	}

	public void setContact(String contact) {
		this.contact = contact;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getZipCode() {
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
	}
}
