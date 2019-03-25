package com.zilker.bean;

import java.io.Serializable;

public class ErrorResponse implements Serializable {
	
	 private static final long serialVersionUID = 6810404937862261501L;

     private String errorCode;
     private String errorDescription;
     private Object errorData;

     public ErrorResponse() {
             super();
     }
     
     public ErrorResponse(String errorCode, String errorDescription) {
 		super();
 		this.errorCode = errorCode;
 		this.errorDescription = errorDescription;
 	}

	public ErrorResponse(String errorCode, String errorDescription, Object errorData) {
		super();
		this.errorCode = errorCode;
		this.errorDescription = errorDescription;
		this.errorData = errorData;
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

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

     
}
