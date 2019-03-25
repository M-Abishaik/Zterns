package com.zilker.customexception;

public class ContactAlreadyExistsException extends ApplicationException {
	
	private String errorCode="CONTACT ERROR EXISTS";
	private String errorDescription="CONTACT ALREADY REGISTERED";
	private Object errorData;
	
	public String getErrorCode() {
		return errorCode;
	}
	
	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}
	
	public String getErrorDescription() {
		return errorDescription;
	}
	
	public void setErrorDescription(String errorDescription) {
		this.errorDescription = errorDescription;
	}
	
	public Object getErrorData() {
		return errorData;
	}
	public void setErrorData(Object errorData) {
		this.errorData = errorData;
	}

}
