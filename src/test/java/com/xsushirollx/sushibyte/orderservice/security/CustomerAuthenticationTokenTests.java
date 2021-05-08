package com.xsushirollx.sushibyte.orderservice.security;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xsushirollx.sushibyte.orderservice.model.User;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

@TestInstance(Lifecycle.PER_CLASS)
public class CustomerAuthenticationTokenTests {
	Logger log = Logger.getLogger("CustomerAuthenticationTokenTests");		
	UserAuthenticationToken token;
	User c;
	
	@BeforeAll
	public void setUp() {
		c =  new User(96, 1);
		List<FoodOrder> orders = new ArrayList<>(); 
		orders.add(new FoodOrder(1, 0));
		orders.add(new FoodOrder(87, 1));
		c.setOrders(orders);
		
		token = new UserAuthenticationToken(c,"jwttoken");
	}
	
	@Test 
	public void getName() {
		assert(Integer.parseInt(token.getName()) == 96);
	}
	
	@Test 
	public void getPrincipal() {
		assert(token.getPrincipal().equals(c));
	}
	
	@Test 
	public void getAuthoritiesOrders() {
		assert(token.getAuthorities().contains(new SimpleGrantedAuthority("ORDER 87")));
		assert(token.getAuthorities().contains(new SimpleGrantedAuthority("ORDER 1")));
	}
	
	@Test 
	public void getAuthoritiesRoles() {
		
		assert(token.getAuthorities().contains(new SimpleGrantedAuthority("CUSTOMER")));
		assert(new UserAuthenticationToken(new User(0, 0), "credentials").getAuthorities().contains(new SimpleGrantedAuthority("NONE")));
		assert(new UserAuthenticationToken(new User(0, 2), "credentials").getAuthorities().contains(new SimpleGrantedAuthority("ADMINISTRATOR")));
		assert(new UserAuthenticationToken(new User(0, 3), "credentials").getAuthorities().contains(new SimpleGrantedAuthority("DRIVER")));
		assert(new UserAuthenticationToken(new User(0, 4), "credentials").getAuthorities().contains(new SimpleGrantedAuthority("NONE")));
	}
	
	
	@Test
	public void getDetails() {
		List<FoodOrder> orders = token.getDetails();
		for (int i = 0; i < orders.size(); i++) {
			System.out.println("Orders: " + orders.get(i).getId());
		}
		assert(orders.get(0).getId() == 1);
		assert(orders.get(1).getId() == 87);
	}
	
	@Test
	public void getsetAuthentication() {
		assert(token.isAuthenticated() == true);
		token.setAuthenticated(false);
		assert(token.isAuthenticated() == false);
	}

	@Test
	public void getCredentials() {
		assert(token.getCredentials().equals("jwttoken"));
	}
	

}
