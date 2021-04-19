package com.xsushirollx.sushibyte.orderservice.controller;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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

	private Logger log = Logger.getLogger("Order Controller");

	@PostMapping("/update")
	public ResponseEntity<?> updateOrder(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (orderService.addOrUpdateOrderItem(item, Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()))) {
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
			if (!hasPermission(order)) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
			}
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
			if (!hasPermission(address)) {
				return new ResponseEntity<>("Cannot Add Delivery Address", headers, HttpStatus.FORBIDDEN);
			}
			if (orderService.updateDeliveryAddress(address)) {
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
		try {
				return new ResponseEntity<FoodOrder>(orderService.getActiveOrder(Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getName())), headers,  HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@GetMapping("/all")
	public ResponseEntity<List<FoodOrder>> getAllOrders(@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
				return new ResponseEntity<List<FoodOrder>>(orderService.getAllOrders(Integer.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getName())), headers,  HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, SecurityContextHolder.getContext().getAuthentication().getClass().toString());
			log.log(Level.INFO, (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@DeleteMapping("/remove")
	public ResponseEntity<?> deleteOrderItem(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission(item)) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
			}
			
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
			if (!hasPermission(order)) {
				return new ResponseEntity<>("Cannot Cancel Order", headers, HttpStatus.FORBIDDEN);
			}
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
	
	//<--------------------------------------------------------- AUTHORIZATION -------------------------------------------------------------->
	
	private boolean hasPermission(FoodOrder o) {
		@SuppressWarnings("unchecked")
		List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication().getDetails();
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getId() == o.getId()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasPermission(OrderItem o) {
		@SuppressWarnings("unchecked")
		List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication().getDetails();
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getId() == o.getOrderId()) {
				return true;
			}
		}
		return false;
	}
	
	private boolean hasPermission(Delivery address) {
		@SuppressWarnings("unchecked")
		List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication().getDetails();
		for (int i = 0; i < orders.size(); i++) {
			if (orders.get(i).getId() == address.getId()) {
				return true;
			}
		}
		return false;
	}
}
