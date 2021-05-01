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
		fodao.save(o);
		return true;
		
	}
	
	public List<FoodOrder> getAllCustomerOrders(int customerId) {
		return fodao.findByCustomerId(customerId);
	}

	
}
