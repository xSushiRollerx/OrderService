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
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsushirollx.sushibyte.orderservice.dto.*;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller
@RequestMapping("/customer/{id}")
public class OrderServiceController {

	private Logger log = Logger.getLogger("CustomerOrderServiceController");

	@Autowired
	OrderService orderService;

	@UpdatePermission
	@PostMapping(value = "/order")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrderDTO order, @PathVariable("id") Long customerId,
			@RequestHeader("Authorization") String token) {
		try {
			orderService.submitOrder(order, customerId);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (SQLIntegrityConstraintViolationException e) {
			return new ResponseEntity<>("Status 400: This Order Fields Are Not Filled Out Properly. Please Make Sure All Fields Are Complete and the User And Restaurant For This Order Exists.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.warning("Order: " + order.toString());
			e.printStackTrace();
			return new ResponseEntity<>("Status 500: Something Went Wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}
	
	@UpdatePermission
	@PostMapping(value = "/order")
	public ResponseEntity<?> submitOrder(@RequestBody EventDTO order, @PathVariable("id") String stripeId,
			@RequestHeader("Authorization") String token) {
		try {
			orderService.submitOrder(order);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (SQLIntegrityConstraintViolationException e) {
			return new ResponseEntity<>("Status 400: This Order Fields Are Not Filled Out Properly. Please Make Sure All Fields Are Complete and the User And Restaurant For This Order Exists.", HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			log.warning("Order: " + order.toString());
			e.printStackTrace();
			return new ResponseEntity<>("Status 500: Something Went Wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@PreAuthorize("hasAuthority('ADMINISTRATOR')")
	@PutMapping(value = "/order/{orderId}")
	public ResponseEntity<?> updateOrderState(@PathVariable("orderId") Integer orderId, @RequestBody FoodOrderDTO order,
			@RequestHeader("Authorization") String token) {
		try {
			if (orderService.updateOrderState(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Status 400: Either This Order Does Not Exists Or This Order Is Not Allowed To Be Updated To The Specified State.", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Status 500: Something Went Wrong", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@UpdatePermission
	@PreAuthorize("(hasAuthority('CUSTOMER') and hasAuthority('ORDER ' + #orderId)) or hasAuthority('ADMINISTRATOR')")
	@DeleteMapping(value = "/order/{orderId}")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrderDTO order, @RequestHeader("Authorization") String token,
			@PathVariable("id") Integer customerId, @PathVariable("orderId") Integer orderId) {
		try {
			log.log(Level.INFO, order.toString());
			if (orderService.cancelOrder(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>("Status 400: This Order Could Not Be Cancelled. Either this Order Does Not Exists Or This Order Is No Longer Pending And ThereFore Can No Longer Be Cancelled.", HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Status 500: Something Went Wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@UpdatePermission
	@GetMapping(value = "/orders")
	public ResponseEntity<?> getAllOrders(@PathVariable("id") Long customerId,
			@RequestHeader("Authorization") String token) {
		log.log(Level.INFO, "get Start");
		// security: check loaded user is the same as the one specified in the url path
		try {
			return new ResponseEntity<>(orderService.getAllCustomerOrders(customerId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>("Status 500: Something Went Wrong.", HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// <-------------------------------------------------- SECURITY CONFIG
	// ---------------------------------------------------------------->
	@Retention(RetentionPolicy.RUNTIME)
	@PreAuthorize("(hasAuthority('CUSTOMER') and principal.id == #customerId) or (hasAuthority('ADMINISTRATOR'))")
	private @interface UpdatePermission {
	}

}
