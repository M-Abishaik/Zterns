package com.zilker.customexception;

public class SeatNotExistsException extends ApplicationException{
	
	private String errorCode="SEAT ERROR EXISTS";
	private String errorDescription="REQUIRED SEATS NOT FOUND";
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
