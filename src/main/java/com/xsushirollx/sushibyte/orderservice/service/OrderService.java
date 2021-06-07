package com.xsushirollx.sushibyte.orderservice.service;


import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.xsushirollx.sushibyte.orderservice.dao.*;
import com.xsushirollx.sushibyte.orderservice.dto.EventDTO;
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.model.*;


@Service
public class OrderService {

	Logger log = Logger.getLogger("OrerService");

	@Autowired
	FoodOrderDAO fodao;
	
	
	public boolean submitOrder(FoodOrderDTO order, long customerId) throws SQLIntegrityConstraintViolationException  {
		FoodOrder o = new FoodOrder(order);
		o.setCustomerId(customerId);
		o.getAddress().setOrder(o);
		
		for (int i = 0; i < o.getOrderItems().size(); i++) {
			o.getOrderItems().get(i).setOrder(o);
		}
		fodao.save(o);
		return true;
	}
	
	
	public boolean submitOrder(EventDTO event) throws SQLIntegrityConstraintViolationException  {
		List<FoodOrder> orders = new ArrayList<>();
		for (FoodOrderDTO o : event.getDescription()) {
			FoodOrder order = new FoodOrder(o); 
			order.getAddress().setOrder(order);
			order.setStripe(event.getClient_secret());
			
			for (OrderItem i : order.getOrderItems()) {
				i.setOrder(order);
			}
			orders.add(order);
		}
		
		fodao.saveAll(orders);
		return true;
	}
	
	
	public List<FoodOrderDTO> getAllCustomerOrders(Long customerId) {
		
		return Arrays.asList(fodao.findByCustomerId(customerId).stream().map(o -> new FoodOrderDTO(o)).toArray(FoodOrderDTO[]::new));
	}
	
	
	public boolean updateOrderState(FoodOrderDTO o) {
		FoodOrder order = new FoodOrder(o);
		log.log(Level.INFO, "state: " + order.getState());
		if (fodao.existsByIdAndState(order.getId(), order.getState() - 1) && order.getState() < 5) {
			order.getAddress().setOrder(order);
			order.getAddress().setId(order.getId());
			order.setRefunded(0);
			fodao.save(order);
			return true;
		} else {
			return false;
		}
		
	}
	
	public boolean cancelOrder(FoodOrderDTO o) {
		FoodOrder order = new FoodOrder(o);
		if (fodao.existsByIdAndState(order.getId(), 0)) {
			order.setState(5);
			order.getAddress().setOrder(order);
			order.getAddress().setId(order.getId());
			order.setRefunded(1);
			fodao.save(order);
			return true;
		} else {
			return false;
		}
		
	}

	
}
