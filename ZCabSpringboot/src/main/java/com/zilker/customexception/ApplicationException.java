package com.zilker.customexception;

public class ApplicationException extends Exception{
	
	private String errorCode;
	private String errorDescription;
	private Object errorData;
	
	public ApplicationException() {}
	
	public ApplicationException(String errorCode, String errorDescription) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
	}

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
