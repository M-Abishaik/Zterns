package com.zilker.bean;

public class UpdateProfile {

	private String email;
	private String address;
	private String zipCode;
	private String password;
	private String phone;

	public UpdateProfile() {
	}

	public UpdateProfile(String email, String address, String zipCode, String password, String phone) {
		super();
		this.email = email;
		this.address = address;
		this.zipCode = zipCode;
		this.password = password;
		this.phone = phone;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

}
