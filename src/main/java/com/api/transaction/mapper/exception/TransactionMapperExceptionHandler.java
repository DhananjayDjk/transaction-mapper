package com.api.transaction.mapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionMapperExceptionHandler {
	   
	   @ExceptionHandler(value = Exception.class)
	   public ResponseEntity<Object> handleException(Exception exception) {
		  System.out.println("Inside the generic exception handler...");
	      return new ResponseEntity<>("Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR);
	   }

}
