package com.xsushirollx.sushibyte.orderservice.service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.OrderItem;

@SpringBootTest
public class OrderServiceTests {

	@Autowired
	OrderService orderService;
	
	@Test 
	public void addOrUpdateOrderItemAdd() {
		OrderItem o = new OrderItem();
		o.setFoodId(2);
		o.setOrderId(3);
		o.setQuantity(2);
		assert(orderService.addOrUpdateOrderItem(o, 1));
	}
	
	@Test 
	public void addOrUpdateOrderItemUpdate() {
		OrderItem o = new OrderItem();
		o.setFoodId(1);
		o.setOrderId(3);
		o.setQuantity(6);
		assert(orderService.addOrUpdateOrderItem(o, 1));
	}
	
	@Test
	public void createOrderHP() {
		assert(orderService.createOrder(5));
	}
	
	@Test
	public void createOrderBasketWithState0AlreadyExistsSP() {
		assert(!orderService.createOrder(1));
	}
	
	@Test
	public void updateOrderStateHP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(2);
		o.setId(5);
		o.setState(2);
		assert(orderService.updateOrderState(o));
	}
	
	@Test
	public void updateOrderStateAbove4SP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(1);
		o.setId(1);
		o.setState(5);
		assert(!orderService.updateOrderState(o));
	}
	
	@Test
	public void updateOrderStateNotMatchingSP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(1);
		o.setId(1);
		o.setState(2);
		assert(!orderService.updateOrderState(o));
	}
	
	@Test
	public void submitOrderHP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(1);
		o.setId(3);
		o.setState(0);
		assert(!orderService.submitOrder(o));
	}
	
	@Test
	public void submitOrderEmptySP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(2);
		o.setId(4);
		o.setState(0);
		assert(!orderService.submitOrder(o));
	}
	
	@Test
	public void submitOrderOrderCannotBeSubmittedSP() {
		FoodOrder o = new FoodOrder();
		o.setCustomerId(1);
		o.setId(2);
		o.setState(1);
		assert(!orderService.submitOrder(o));
	}
	
	@Test
	public void getActiveOrderHP() {
		assert(orderService.getActiveOrder(1).getCustomerId() == 1);
	}
	
	@Test
	public void getActiveOrderDNEHP() {
		assert(orderService.getActiveOrder(7).getCustomerId() == 7);
	}
	
	@Test
	public void getAllOrdersHP() {
		assert(orderService.getAllOrders(1).size() > 0);
	}
	
	@Test
	public void getAllOrdersNoneSP() {
		assert(orderService.getAllOrders(3).size() == 0);
	}
	
	@Test
	public void deleteOrderItemHP() {
		OrderItem o = new OrderItem();
		o.setFoodId(3);
		o.setOrderId(3);
		o.setId(4);
		assert(orderService.deleteOrderItem(o));
	}
	
	@Test
	public void deleteOrderItemDNESP() {
		OrderItem o = new OrderItem();
		o.setFoodId(3);
		o.setOrderId(3);
		o.setId(6);
		assert(!orderService.deleteOrderItem(o));
	}
	
	@Test
	public void cancelOrderHP() {
		FoodOrder order = new FoodOrder();
		order.setId(2);
		assert(orderService.cancelOrder(order));
	}
	
	@Test
	public void cancelOrderWrongStateSP() {
		FoodOrder order = new FoodOrder();
		order.setId(5);
		assert(!orderService.cancelOrder(order));
	}
}
