package com.xsushirollx.sushibyte.orderservice.controller;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Logger;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.xsushirollx.sushibyte.orderservice.exception.OrderServiceException;

@RestControllerAdvice
public class OrderControllerAdvice {
	
	Logger log = Logger.getLogger("OrderControllerAdvice");
	
	@ExceptionHandler(value = {SQLIntegrityConstraintViolationException.class, JsonMappingException.class, JsonProcessingException.class})
	public ResponseEntity<?> sqlException() {
		return new ResponseEntity<>("Status 400: This Order Fields Are Not Filled Out Properly. Please Make Sure All Fields Are Complete and the User And The Restaurant For This Order Exists.", HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value =  OrderServiceException.class)
	public ResponseEntity<?> orderException(OrderServiceException e) {
		return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<?> exception(Exception e) {
		log.info(e.getMessage());
		if (e.getMessage().equalsIgnoreCase("Access is denied")) {
			return new ResponseEntity<>("Status 403: Access Denied", HttpStatus.FORBIDDEN);
		}
		return new ResponseEntity<>("Status 500: Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
