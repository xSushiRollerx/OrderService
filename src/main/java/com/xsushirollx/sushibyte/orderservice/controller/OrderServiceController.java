package com.xsushirollx.sushibyte.orderservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.xsushirollx.sushibyte.orderservice.model.Delivery;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
import com.xsushirollx.sushibyte.orderservice.security.JWTUtil;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller("/customer")
public class OrderServiceController {

	@Autowired
	OrderService orderService;
	
	@Autowired
	JWTUtil util;

	@PostMapping("/update")
	public ResponseEntity<?> updateOrder(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			
			if (orderService.addOrUpdateOrderItem(item, Integer.parseInt(util.extractUserId(token.substring(7).trim())))) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/submit")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (orderService.submitOrder(order)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/delivery")
	public ResponseEntity<?> updateDelivery(@RequestBody Delivery address, @RequestHeader("Authorization") String token) {
		
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (orderService.updateDeliveryAdress(address)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/active")
	public ResponseEntity<FoodOrder> getActiveOrder(@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		int customerId = 96;
		try {
				return new ResponseEntity<FoodOrder>(orderService.getActiveOrder(customerId), headers,  HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FoodOrder>> getAllOrders(@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		int customerId = 96;
		try {
				return new ResponseEntity<>(orderService.getAllOrders(customerId), headers,  HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<?> deleteOrderItem(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (orderService.deleteOrderItem(item)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PutMapping("/cancel")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (orderService.cancelOrder(order)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
}
