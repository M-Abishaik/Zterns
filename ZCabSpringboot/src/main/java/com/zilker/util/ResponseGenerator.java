package com.zilker.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.zilker.bean.ErrorResponse;
import com.zilker.bean.ResponseAsArray;
import com.zilker.bean.ResponseAsObject;
import com.zilker.bean.ResponseMessage;
import com.zilker.customexception.ApplicationException;

@Service
public class ResponseGenerator {
	
	 // Generates the successResponse and returns the ResponseEntity object
    public <T> ResponseEntity<?> successResponse(T body) {
            ResponseAsObject<T> response = new ResponseAsObject<>();
            response.setIsSuccess(true);
            response.setResponseBody(body);
            return ResponseEntity.ok().body(response);
    }

    // Generates the successResponse and returns the ResponseEntity object
    public <T> ResponseEntity<?> successResponse(ArrayList<?> body) {
            ResponseAsArray response = new ResponseAsArray();
            response.setIsSuccess(true);
            response.setResponseBody(body);
            return ResponseEntity.ok().body(response);
    }

    // Generates the errorResponse and returns the ResponseEntity object
    public ResponseEntity<?> errorResponse(String errorCode, String errorDescription) {
            ResponseAsObject<ErrorResponse> response = new ResponseAsObject<>();
            ErrorResponse errorBody = new ErrorResponse(errorCode, errorDescription);
            response.setIsSuccess(false);
            response.setResponseBody(errorBody);
            return ResponseEntity.ok().body(response);
    }
    
    public ResponseEntity<?> errorResponse(ApplicationException applicationException) {
		ResponseAsObject<ErrorResponse> response = new ResponseAsObject<>();
		ErrorResponse errorBody = new ErrorResponse(applicationException.getErrorCode(), applicationException.getErrorDescription(), applicationException.getErrorData());
		response.setIsSuccess(false);
		response.setResponseBody(errorBody);
		return ResponseEntity.ok().body(response);
	}

    public ResponseEntity<?> generateMessage(String message) {
            ResponseAsObject<ResponseMessage> response = new ResponseAsObject<>();
            ResponseMessage responseMessage = new ResponseMessage(message);
            response.setIsSuccess(true);
            response.setResponseBody(responseMessage);
            return ResponseEntity.ok().body(response);
    }
}
