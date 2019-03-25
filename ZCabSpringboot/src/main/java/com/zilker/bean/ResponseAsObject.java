package com.zilker.bean;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResponseAsObject<T> implements Serializable {
	
	  private static final long serialVersionUID = 176336976291823368L;

      @JsonProperty("isSuccess")
      private boolean isSuccess;

      @JsonProperty("responseBody")
      private T responseBody;

      public ResponseAsObject() {
              // TODO Auto-generated constructor stub
      }

      public boolean getIsSuccess() {
              return isSuccess;
      }

      public void setIsSuccess(boolean isSuccess) {
              this.isSuccess = isSuccess;
      }

      public T getResponseBody() {
              return responseBody;
      }

      public void setResponseBody(T responseBody) {
              this.responseBody = responseBody;
      }

      public static long getSerialversionuid() {
              return serialVersionUID;
      }

      @Override
      public String toString() {
              return "ResponseAsObject [isSuccess=" + isSuccess + ", ResponseBody=" + responseBody + "]";
      }
}
