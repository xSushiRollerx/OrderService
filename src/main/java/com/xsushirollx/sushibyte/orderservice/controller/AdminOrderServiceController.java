
package com.xsushirollx.sushibyte.orderservice.controller;

import java.util.List;
import java.util.Map;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.xsushirollx.sushibyte.orderservice.model.Customer;
import com.xsushirollx.sushibyte.orderservice.model.Delivery;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
import com.xsushirollx.sushibyte.orderservice.security.JWTUtil;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller
@RequestMapping("/admin/order")
public class AdminOrderServiceController {

	@Autowired
	OrderService orderService;

	@Autowired
	JWTUtil util;

	private Logger log = Logger.getLogger("Order Controller");

	// DONE
	@PostMapping(value = "/update")
	public ResponseEntity<?> updateOrder(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
			}
			if (orderService.addOrUpdateOrderItem(item)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DONE
	
	@PutMapping(value ="/submit")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);

		try {
			if (!hasPermission()) {
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

	// DONE
	
	@PutMapping(value = "/delivery")
	public ResponseEntity<?> updateDelivery(@RequestBody Delivery address,
			@RequestHeader("Authorization") String token) {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
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

	// DONE
	
	@PutMapping(value = "/state")
	public ResponseEntity<?> updateOrderState(@RequestBody FoodOrder order,
			@RequestHeader("Authorization") String token) {

		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
			}
			if (orderService.updateOrderState(order)) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DONE
	
	@GetMapping(value = "/search")
	public ResponseEntity<List<FoodOrder>> searchAllOrders(@RequestHeader("Authorization") String token,
			@RequestParam Map<String, String> params) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
				return new ResponseEntity<>(headers, HttpStatus.FORBIDDEN);
			}
			return new ResponseEntity<List<FoodOrder>>(orderService.searchAllOrders(params), headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, SecurityContextHolder.getContext().getAuthentication().getClass().toString());
			log.log(Level.INFO, (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// DONE
	
	@DeleteMapping(value = "/remove")
	public ResponseEntity<?> deleteOrderItem(@RequestBody OrderItem item,
			@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
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

	// DONE
	@PutMapping(value = "/cancel")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		try {
			if (!hasPermission()) {
				return new ResponseEntity<>("Cannot Add Order Item", headers, HttpStatus.FORBIDDEN);
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

	// <------------------------------------ AUTHORIZATION ---------------------------------------------------------------------->
	private boolean hasPermission() {
		try {
			Customer customer = (Customer) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
			return customer.getRole() == 2;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
}