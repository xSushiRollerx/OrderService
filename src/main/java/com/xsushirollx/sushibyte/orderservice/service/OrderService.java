package com.xsushirollx.sushibyte.orderservice.service;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xsushirollx.sushibyte.orderservice.dao.*;
import com.xsushirollx.sushibyte.orderservice.model.*;


@Service
public class OrderService {

	@Autowired
	OrderItemDAO odao;

	@Autowired
	FoodOrderDAO fodao;

	@Autowired
	MenuItemDAO mdao;
	
	@Autowired
	DeliveryDAO ddao;
	
	@Autowired
	CustomerDAO cdao;
	
	public boolean submitOrder(FoodOrder o, int customerId) {
		o.setCustomerId(customerId);
		o.getAddress().setOrder(o);
		
		for (int i = 0; i < o.getOrderItems().size(); i++) {
			o.getOrderItems().get(i).setOrder(o);
		}
		fodao.save(o);
		return true;
	}
	
	public List<FoodOrder> getAllCustomerOrders(int customerId) {
		return fodao.findByCustomerId(customerId);
	}
	
	
	public boolean updateOrderState(FoodOrder order, int orderState) {
		if (fodao.existsByIdAndState(order.getId(), orderState - 1)) {
			order.setState(orderState);
			fodao.save(order);
			return true;
		} else {
			return false;
		}
		
	}

	
}
