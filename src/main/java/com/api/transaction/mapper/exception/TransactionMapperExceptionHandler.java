package com.api.transaction.mapper.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TransactionMapperExceptionHandler {
	
	private static final Logger logger = LogManager.getLogger(TransactionMapperExceptionHandler.class);
	   
	   @ExceptionHandler(value = Exception.class)
	   public ResponseEntity<Object> handleException(Exception exception) {
		  logger.error("Inside the generic exception handler...");
	      return new ResponseEntity<>("Exception Occured", HttpStatus.INTERNAL_SERVER_ERROR);
	   }

}
