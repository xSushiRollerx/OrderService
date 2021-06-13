package com.xsushirollx.sushibyte.orderservice.exception;

public class OrderServiceException extends Exception {

	private String message;
	
	private static final long serialVersionUID = 1L;

	public OrderServiceException(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
	
}
