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
import com.xsushirollx.sushibyte.orderservice.dto.FoodOrderDTO;
import com.xsushirollx.sushibyte.orderservice.model.*;
import com.stripe.model.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

@Service
public class OrderService {

	Logger log = Logger.getLogger("OrderService");
	
	@Autowired
	FoodOrderDAO fodao;
	
	@Autowired
	ObjectMapper mapper;
	
	
	@SuppressWarnings("deprecation")
	JsonParser parser = new JsonParser();
	
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
	
	
	
	@SuppressWarnings("deprecation")
	public boolean submitOrder(Event event) throws SQLIntegrityConstraintViolationException, JsonMappingException, JsonProcessingException  {
		JsonArray description = parser.parse(parser.parse(event.getData().toJson()).getAsJsonObject().get("object").getAsJsonObject().get("description").getAsString()).getAsJsonArray();
		log.info(description.toString());

		
		List<FoodOrder> orders = new ArrayList<>();
		for (JsonElement o : description) {
			FoodOrder order = new FoodOrder(mapper.readValue(o.toString(), FoodOrderDTO.class)); 
			order.getAddress().setOrder(order);
			order.setStripe(parser.parse(event.getData().toJson()).getAsJsonObject().get("object").getAsJsonObject().get("id").getAsString());
			
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
