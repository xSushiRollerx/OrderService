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
import org.springframework.web.bind.annotation.RequestMapping;

import com.xsushirollx.sushibyte.orderservice.model.Delivery;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;
import com.xsushirollx.sushibyte.orderservice.service.OrderService;

@Controller
@RequestMapping("/customer/order")
public class CustomerOrderServiceController {

	@Autowired
	OrderService orderService;

	private Logger log = Logger.getLogger("Order Controller");

	@PostMapping(value = "/update")
	public ResponseEntity<?> updateOrder(@RequestBody OrderItem item, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);
	
		try {
			if (orderService.addOrUpdateOrderItem(item,
					Integer.parseInt(SecurityContextHolder.getContext().getAuthentication().getName()))) {
				return new ResponseEntity<>(headers, HttpStatus.NO_CONTENT);
			} else {
				return new ResponseEntity<>(headers, HttpStatus.BAD_REQUEST);
			}
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@PutMapping(value = "/submit")
	public ResponseEntity<?> submitOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);

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

	@PutMapping(value = "/delivery")
	public ResponseEntity<?> updateDelivery(@RequestBody Delivery address,
			@RequestHeader("Authorization") String token) {

		MultiValueMap<String, String> headers = getHeaders(token);
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

	@GetMapping(value = "/active")
	public ResponseEntity<FoodOrder> getActiveOrder(@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);
		try {
			return new ResponseEntity<FoodOrder>(
					orderService.getActiveOrder(Integer
							.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getName())),
					headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@GetMapping(value = "/all")
	public ResponseEntity<List<FoodOrder>> getAllOrders(@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);
		try {
			return new ResponseEntity<List<FoodOrder>>(
					orderService.getAllOrders(Integer
							.parseInt((String) SecurityContextHolder.getContext().getAuthentication().getName())),
					headers, HttpStatus.OK);
		} catch (Exception e) {
			e.printStackTrace();
			log.log(Level.INFO, SecurityContextHolder.getContext().getAuthentication().getClass().toString());
			log.log(Level.INFO, (String) SecurityContextHolder.getContext().getAuthentication().getPrincipal());
			return new ResponseEntity<>(headers, HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	@DeleteMapping(value = "/remove")
	public ResponseEntity<?> deleteOrderItem(@RequestBody OrderItem item,
			@RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);
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

	@PutMapping(value = "/cancel")
	public ResponseEntity<?> cancelOrder(@RequestBody FoodOrder order, @RequestHeader("Authorization") String token) {
		MultiValueMap<String, String> headers = getHeaders(token);
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
	
	// <--------------------------------------------------------- HEADERS -------------------------------------------------------------->
	private MultiValueMap<String, String> getHeaders(String token) {
		MultiValueMap<String, String> headers = new LinkedMultiValueMap<>();
		headers.add("Authorization", token);
		headers.add("Access-Control-Allow-Origin", "*");
		headers.add("Access-Control-Allow-Methods", "DELETE, POST, GET, OPTIONS");
		headers.add("Access-Control-Allow-Headers", "Content-Type, Access-Control-Allow-Headers, Authorization, X-Requested-With");
		return headers;
	}

	// <--------------------------------------------------------- AUTHORIZATION -------------------------------------------------------------->

	private boolean hasPermission(FoodOrder o) {

		try {
			@SuppressWarnings("unchecked")
			List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication()
					.getDetails();
			for (int i = 0; i < orders.size(); i++) {
				if (orders.get(i).getId() == o.getId()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean hasPermission(OrderItem o) {
		try {
			@SuppressWarnings("unchecked")
			List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication()
					.getDetails();
			for (int i = 0; i < orders.size(); i++) {
				if (orders.get(i).getId() == o.getOrderId()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}

	private boolean hasPermission(Delivery address) {
		try {
			@SuppressWarnings("unchecked")
			List<FoodOrder> orders = (List<FoodOrder>) SecurityContextHolder.getContext().getAuthentication()
					.getDetails();
			for (int i = 0; i < orders.size(); i++) {
				if (orders.get(i).getId() == address.getId()) {
					return true;
				}
			}
			return false;
		} catch (Exception e) {
			return false;
		}
	}
}
