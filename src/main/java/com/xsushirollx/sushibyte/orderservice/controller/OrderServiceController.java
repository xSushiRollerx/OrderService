package com.xsushirollx.sushibyte.orderservice.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stripe.model.Event;
import com.xsushirollx.sushibyte.orderservice.dto.*;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller
public class OrderServiceController {

	private Logger log = Logger.getLogger("CustomerOrderServiceController");

	@Autowired
	OrderService orderService;

	@UpdatePermission
	@PostMapping(value = "/customer/{id}/order")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrderDTO order, @PathVariable("id") Long customerId,
			@RequestHeader("Authorization") String token) {

		return new ResponseEntity<>(HttpStatus.CREATED);

	}
	
	@PostMapping(value = "/stripe/order")
	public ResponseEntity<?> submitOrder(@RequestBody Event order, 
			@RequestHeader("Stripe-Signature") String signature) throws SQLIntegrityConstraintViolationException, JsonMappingException, JsonProcessingException {
		orderService.submitOrder(order);
		return new ResponseEntity<>(HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping(value = "/customer/{id}/order/{orderId}")
	public ResponseEntity<?> updateOrderState(@PathVariable("orderId") Integer orderId, @RequestBody FoodOrderDTO order,
			@RequestHeader("Authorization") String token) {
		
			if (orderService.updateOrderState(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Status 400: Either This Order Does Not Exists Or This Order Is Not Allowed To Be Updated To The Specified State.", HttpStatus.BAD_REQUEST);
			}
	}

	@UpdatePermission
	@PreAuthorize("(hasAuthority('CUSTOMER') and hasAuthority('ORDER ' + #orderId)) or hasAuthority('ADMINISTRATOR')")
	@DeleteMapping(value = "/customer/{id}/order/{orderId}")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrderDTO order, @RequestHeader("Authorization") String token,
			@PathVariable("id") Integer customerId, @PathVariable("orderId") Integer orderId) {
			log.log(Level.INFO, order.toString());
			if (orderService.cancelOrder(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Status 400: This Order Could Not Be Cancelled. Either this Order Does Not Exists Or This Order Is No Longer Pending And ThereFore Can No Longer Be Cancelled.", HttpStatus.BAD_REQUEST);
			}
	}

	@UpdatePermission
	@GetMapping(value = "/customer/{id}/orders")
	public ResponseEntity<?> getAllOrders(@PathVariable("id") Long customerId,
			@RequestHeader("Authorization") String token) {
		log.log(Level.INFO, "get Start");
		
		return new ResponseEntity<>(orderService.getAllCustomerOrders(customerId), HttpStatus.OK);
	}

	// <-------------------------------------------------- SECURITY CONFIG
	// ---------------------------------------------------------------->

	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("(hasAuthority('CUSTOMER') and principal.id == #customerId) or (hasAuthority('ADMINISTRATOR'))")
	private @interface UpdatePermission {
	}

}
