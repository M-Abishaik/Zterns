package com.zilker.contact.beanClass;

/*
 * Home bean.
 */

public class Home_Detail {
	
	private int areaCode, countryCode;
	private String mail, homeNum;
	
	public Home_Detail(String num, int area, int country, String mId) {
		this.homeNum = num;
		this.areaCode = area;
		this.countryCode = country;
		this.mail = mId;
	}
	
	public void setHomeNum(String num) {
		this.homeNum = num;
	}
	
	public void setAreaCode(int area) {
		this.areaCode = area;
	}
	
	public void setCountryCode(int country) {
		this.countryCode = country;
	}
	
	public void setMailId(String mail) {
		this.mail = mail;
	}
	
	public String getHomeNum() {
		return homeNum;
	}
	
	public int getAreaCode() {
		return areaCode;
	}
	
	public int getCountryCode() {
		return countryCode;
	}
	
	public String getMail() {
		return mail;
	}
}
