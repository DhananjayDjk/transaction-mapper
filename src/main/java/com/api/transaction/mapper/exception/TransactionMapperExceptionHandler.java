package com.api.transaction.mapper.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionMapperExceptionHandler {
	
	   @ExceptionHandler(value = RecordNotFoundException.class)
	   public ResponseEntity<Object> exception(RecordNotFoundException exception) {
		  System.out.println("Inside exception handler...");
	      return new ResponseEntity<>("No Transactions Found", HttpStatus.INTERNAL_SERVER_ERROR);
	   }

}
