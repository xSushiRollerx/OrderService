package com.xsushirollx.sushibyte.orderservice.security;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.security.core.GrantedAuthority;

import com.xsushirollx.sushibyte.orderservice.model.Customer;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerAuthenticationTokenTests {
	
	CustomerAuthenticationToken token;
			
	@BeforeAll
	public void setUp() {
		Customer c =  new Customer(96, 1);
		List<FoodOrder> orders = new ArrayList<>(); 
		orders.add(new FoodOrder(1, 0));
		orders.add(new FoodOrder(87, 1));
		c.setOrders(orders);
		
		token = new CustomerAuthenticationToken(c);
	}
	
	@Test 
	public void getPrincipal() {
		assert(token.getPrincipal() == 96);
	}
	
	@Test
	public void getDetails() {
		List<FoodOrder> orders = token.getDetails();
		for (int i = 0; i < orders.size(); i++) {
			System.out.println("Orders: " + orders.get(i).getId());
		}
		assert(orders.get(0).getId() == 1);
		assert(orders.get(0).getId() == 87);
	}

	

}
