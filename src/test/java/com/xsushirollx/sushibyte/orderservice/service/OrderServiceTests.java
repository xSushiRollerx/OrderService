package com.xsushirollx.sushibyte.orderservice.service;

import java.util.ArrayList;
import java.util.logging.Logger;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.orderservice.dto.DeliveryDTO;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.dto.OrderItemDTO;

@SpringBootTest
public class OrderServiceTests {
	
	Logger log = Logger.getLogger("OrderServiceTests");

	@Autowired
	OrderService orderService;
	
	@Test
	public void sumbitOrderHP() {
		assert(orderService.submitOrder(new FoodOrderDTO( null, 0, 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO()), 1));
	}
	
	@Test
	public void getAllCustomerHP() {
		assert(orderService.getAllCustomerOrders(1) != null);
	}
	
	@Test
	public void getAllCustomerSPCustomerDNE() {
		assert(orderService.getAllCustomerOrders(222).size() == 0);
	}
	
}
