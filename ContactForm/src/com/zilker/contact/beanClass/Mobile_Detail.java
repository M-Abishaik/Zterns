package com.zilker.contact.beanClass;

/*
 * Mobile bean.
 */

public class Mobile_Detail {
	
	private int mobileExt;
	private String mail, mobileNum;
	
	public Mobile_Detail(String num, int ext, String mId) {
		this.mobileNum = num;
		this.mobileExt = ext;
		this.mail = mId;
	}
	
	public void setMobileNum(String num) {
		this.mobileNum = num;
	}
	
	public void setMobileExt(int ext) {
		this.mobileExt = ext;
	}
	
	public void setMailId(String mail) {
		this.mail = mail;
	}
	
	public String getMobileNum() {
		return mobileNum;
	}
	
	public int getMobileExt() {
		return mobileExt;
	}
	
	public String getMail() {
		return mail;
	}

}
