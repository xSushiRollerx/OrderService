package com.xsushirollx.sushibyte.orderservice.service;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.xsushirollx.sushibyte.orderservice.dao.FoodOrderDAO;
import com.xsushirollx.sushibyte.orderservice.dto.DeliveryDTO;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.dto.OrderItemDTO;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;


@SpringBootTest
public class OrderServiceTests {
	
	Logger log = Logger.getLogger("OrderServiceTests");
	
	@MockBean
	FoodOrderDAO fdao;

	@Autowired
	OrderService orderService;
	
	
	FoodOrderDTO order;
	
	@BeforeEach
	public void setUp() {
		List<OrderItemDTO> items = new ArrayList<OrderItemDTO>();
		items.add(new OrderItemDTO((long)2, 4, (float) 3.99, "Miso Soup", (long) 5));
		items.add(new OrderItemDTO((long) 5, 2, (float) 8.99, "California Roll", (long) 5));
		items.add(new OrderItemDTO((long) 13, 1, (float) 11.99, "Teriyaki Shrimp", (long) 5));
		
		order = new FoodOrderDTO( null, 0, (long) 1, items, new DeliveryDTO());
	}
	
	
	@Test
	public void sumbitOrderHP() throws SQLIntegrityConstraintViolationException {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		assert(orderService.submitOrder(order, 1));
	}
	
	@Test
	public void getAllCustomerHP() {
		when(fdao.findByCustomerId(Mockito.anyLong())).thenReturn(new ArrayList<FoodOrder>());
		orderService.getAllCustomerOrders((long) 1);
	}
	
	@Test
	public void updateOrderStateHP() {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		when(fdao.save(Mockito.any(FoodOrder.class))).thenReturn(new FoodOrder());
		assert(orderService.updateOrderState(new FoodOrderDTO((long) 2, 2, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void updateOrderStateSPWrongStateSpecifiedToUpdateTo() {
		assert(!orderService.updateOrderState(new FoodOrderDTO((long) 3, 2, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void updateOrderStateSPStateGreaterThan5() {
		assert(!orderService.updateOrderState(new FoodOrderDTO((long) 6, 5, (long) 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void cancelOrderHP() {
		when(fdao.existsByIdAndState(Mockito.anyLong(), Mockito.anyInt())).thenReturn(true);
		when(fdao.save(Mockito.any(FoodOrder.class))).thenReturn(new FoodOrder());
		assert(orderService.cancelOrder(new FoodOrderDTO((long) 3, 0, (long) 1, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
	@Test
	public void cancelOrderSP() {
		assert(!orderService.cancelOrder(new FoodOrderDTO((long) 5, 2, (long) 2, new ArrayList<OrderItemDTO>(), new DeliveryDTO())));
	}
	
}
