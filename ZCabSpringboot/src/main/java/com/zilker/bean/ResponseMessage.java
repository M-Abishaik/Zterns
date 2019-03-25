package com.zilker.bean;

import java.io.Serializable;

public class ResponseMessage implements Serializable {
	
	private static final long serialVersionUID = 850263151479066283L;

    private String message;

    public ResponseMessage() {

    }

    public ResponseMessage(String message) {
            super();
            this.message = message;
    }

    public String getMessage() {
            return message;
    }

    public void setMessage(String message) {
            this.message = message;
    }

    @Override
    public String toString() {
            return "ResponseMessage [message=" + message + "]";
    }

}
