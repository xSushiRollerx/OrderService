package com.xsushirollx.sushibyte.orderservice.controller;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller
@RequestMapping("/customer/{id}")
public class CustomerOrderServiceController {
	
	private Logger log = Logger.getLogger("CustomerOrderServiceController");

	@Autowired
	OrderService orderService;

	@PostMapping(value = "/order")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrderDTO order, @PathVariable("id") Integer customerId, @RequestHeader("Authorization") String token) {
		try {
			orderService.submitOrder(order, customerId);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
	}
	
	@PutMapping(value = "/order/{orderId}")
	public ResponseEntity<?> updateOrderState(@RequestBody FoodOrderDTO order, @RequestHeader("Authorization") String token) {
		try {
			if (orderService.updateOrderState(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping(value = "/order/{orderId}")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrderDTO order, @RequestHeader("Authorization") String token) {
		try {
			if (orderService.cancelOrder(order)) {
				return new ResponseEntity<>(HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping(value = "/orders")
	public ResponseEntity<?> getAllOrders(@PathVariable("id") Integer customerId, @RequestHeader("Authorization") String token) {
		log.log(Level.INFO, "");
		//security: check loaded user is the same as the one specified in the url path
		try {
			return new ResponseEntity<>(orderService.getAllCustomerOrders(customerId), HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	


}
