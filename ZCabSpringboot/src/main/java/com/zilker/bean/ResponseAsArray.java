package com.zilker.bean;

import java.io.Serializable;
import java.util.ArrayList;

public class ResponseAsArray implements Serializable{
	
	 private static final long serialVersionUID = -3040172141745893308L;

     private boolean isSuccess;
     private ArrayList<?> responseBody;

     public ResponseAsArray() {
             super();
     }

     public boolean getIsSuccess() {
             return isSuccess;
     }

     public void setIsSuccess(boolean isSuccess) {
             this.isSuccess = isSuccess;
     }

     public ArrayList<?> getResponseBody() {
             return responseBody;
     }

     public void setResponseBody(ArrayList<?> responseBody) {
             this.responseBody = responseBody;
     }	

}
