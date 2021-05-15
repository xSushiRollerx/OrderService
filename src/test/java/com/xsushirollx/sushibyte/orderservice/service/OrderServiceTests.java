package com.xsushirollx.sushibyte.orderservice.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.orderservice.dao.FoodOrderDAO;
import com.xsushirollx.sushibyte.orderservice.dto.DeliveryDTO;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.dto.OrderItemDTO;


@SpringBootTest
public class OrderServiceTests {
	
	Logger log = Logger.getLogger("OrderServiceTests");

	@Autowired
	OrderService orderService;
	
	@Autowired
	FoodOrderDAO fodao;
	
	FoodOrderDTO order;
	
	@BeforeEach
	public void setUp() {
		List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
		items.add(new OrderItemDTO(2, 4, (float) 3.99, "Miso Soup", 5));
		items.add(new OrderItemDTO(5, 2, (float) 8.99, "California Roll", 5));
		items.add(new OrderItemDTO(13, 1, (float) 11.99, "Teriyaki Shrimp", 5));
		
		order = new FoodOrderDTO( null, 0, 1, items, new DeliveryDTO());
	}
	
	
	@Test
	public void sumbitOrderHP() {
		assert(orderService.submitOrder(order,1));
	}
	
	@Test
	public void getAllCustomerHP() {
		assertEquals(orderService.getAllCustomerOrders(1).size(), 4);
	}
	
	
	
	@Test
	public void getAllCustomerSPCustomerDNE() {
		assert(orderService.getAllCustomerOrders(222).size() == 0);
	}
	
	@Test
	public void updateOrderStateHP() {
		assert(orderService.updateOrderState(new FoodOrderDTO(2, 2, 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void updateOrderStateSPWrongStateSpecifiedToUpdateTo() {
		assert(!orderService.updateOrderState(new FoodOrderDTO(3, 2, 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void updateOrderStateSPStateGreaterThan5() {
		assert(!orderService.updateOrderState(new FoodOrderDTO(6, 5, 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void cancelOrderHP() {
		assert(orderService.cancelOrder(new FoodOrderDTO(3, 0, 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void cancelOrderSP() {
		assert(!orderService.cancelOrder(new FoodOrderDTO(5, 2, 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
}
