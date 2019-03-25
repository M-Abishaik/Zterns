package com.zilker.customexception;

public class NearestCabNotFoundException extends ApplicationException{
	
	private String errorCode="NEAREST CAB ERROR EXISTS";
	private String errorDescription="NEAREST CAB NOT FOUND";
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
