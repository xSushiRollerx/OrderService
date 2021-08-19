package com.xsushirollx.sushibyte.orderservice.controller;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestParam;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.stripe.model.Event;
import com.xsushirollx.sushibyte.orderservice.dto.*;
import com.xsushirollx.sushibyte.orderservice.exception.OrderServiceException;
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
		return new ResponseEntity<>(orderService.submitOrder(order), HttpStatus.CREATED);
	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping(value = "/customer/{id}/order/{orderId}")
	public ResponseEntity<?> updateOrderState(@PathVariable("orderId") Integer orderId, @RequestBody FoodOrderDTO order,
			@RequestHeader("Authorization") String token) throws OrderServiceException {
		
		return new ResponseEntity<>(orderService.updateOrderState(order), HttpStatus.OK);
	}

	@UpdatePermission
	@PreAuthorize("(hasAuthority('CUSTOMER') and hasAuthority('ORDER ' + #orderId)) or hasAuthority('ADMINISTRATOR')")
	@DeleteMapping(value = "/customer/{id}/order/{orderId}")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrderDTO order, @RequestHeader("Authorization") String token,
			@PathVariable("id") Integer customerId, @PathVariable("orderId") Integer orderId) throws OrderServiceException {
			log.log(Level.INFO, order.toString());
			return new ResponseEntity<>(orderService.cancelOrder(order), HttpStatus.NO_CONTENT);
	}

	@UpdatePermission
	@GetMapping(value = "/customer/{id}/orders")
	public ResponseEntity<?> getAllOrders(@PathVariable("id") Long customerId, @RequestParam(defaultValue = "0", value = "page") String page, @RequestParam(defaultValue = "newest", value = "sort", required = false) String sort,
			@RequestParam(defaultValue = "10", value = "pageSize", required = false) String pageSize, @RequestParam Map<String, String> params, @RequestHeader("Authorization") String token) {
		log.log(Level.INFO, "get Start");
		
		return new ResponseEntity<>(orderService.getAllCustomerOrders(customerId, params), HttpStatus.OK);
	}
	
	@PreAuthorize("hasAnyAuthority('DRIVER', 'ADMINISTRATOR')")
	@GetMapping(value = "/driver/order")
	public ResponseEntity<?> driverOrderRequest() {
		log.log(Level.INFO, "get Start");
		
		DriverOrderDTO order = orderService.driverRequestOrder();
		if (order != null) {
			return new ResponseEntity<>(order, HttpStatus.OK);
		} else {
			return new ResponseEntity<>("No available orders at this time", HttpStatus.NO_CONTENT);
		}
		
	}
	
	@PreAuthorize("(hasAuthority('DRIVER') and principal.id == #driverId) or (hasAuthority('ADMINISTRATOR'))")
	@PutMapping(value = "/driver/{driverId}/order/{orderId}")
	public ResponseEntity<DriverOrderDTO> driverOrderAcceptance(@PathVariable("driverId") Long driverId, @PathVariable("orderId") Long orderId) throws OrderServiceException {
		log.log(Level.INFO, "get Start");
		
		return new ResponseEntity<DriverOrderDTO>(orderService.driverAcceptOrder(orderId, driverId), HttpStatus.OK);
		
	}
	
	@PreAuthorize("hasAnyAuthority('DRIVER', 'ADMINISTRATOR')")
	@PutMapping(value = "/order/{orderId}")
	public ResponseEntity<?> driverOrderDecline(@PathVariable("orderId") Long orderId) throws OrderServiceException {
		log.log(Level.INFO, "get Start");
		orderService.driverDeclineOrder(orderId);
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		
	}

	// <-------------------------------------------------- SECURITY CONFIG
	// ---------------------------------------------------------------->

	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("(hasAuthority('CUSTOMER') and principal.id == #customerId) or (hasAuthority('ADMINISTRATOR'))")
	private @interface UpdatePermission {
	}
	
	

}
