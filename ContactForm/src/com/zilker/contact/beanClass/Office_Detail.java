package com.zilker.contact.beanClass;

/*
 * Office bean.
 */

public class Office_Detail {
	
	private int officeExt;
	private String mail, officeNum;
	
	public Office_Detail(String num, int ext, String mId) {
		this.officeNum = num;
		this.officeExt = ext;
		this.mail = mId;
	}
	
	public void setOfficeNum(String num) {
		this.officeNum = num;
	}
	
	public void setMobileExt(int ext) {
		this.officeExt = ext;
	}
	
	public void setMailId(String mail) {
		this.mail = mail;
	}
	
	public String getOfficeNum() {
		return officeNum;
	}
	
	public int getOfficeExt() {
		return officeExt;
	}
	
	public String getMail() {
		return mail;
	}


}
