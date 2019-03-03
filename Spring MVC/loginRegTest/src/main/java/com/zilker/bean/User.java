package com.zilker.bean;

public class User {
	
	private String username;
	private String email;
	private String contact;
	private String role;
	private String password;
	
	public User() {}
	
	public User(String username, String email, String contact, String role, String password) {
		super();
		this.username = username;
		this.email = email;
		this.contact = contact;
		this.role = role;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
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
	
	

	
}
