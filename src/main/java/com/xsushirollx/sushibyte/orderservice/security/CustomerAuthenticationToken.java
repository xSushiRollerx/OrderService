package com.xsushirollx.sushibyte.orderservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xsushirollx.sushibyte.orderservice.model.Customer;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

public class CustomerAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 1L;

	private final Customer customer;
	private final String jwtToken;
	
	private Logger log = Logger.getLogger("CustomerAuthenticationToken");

	private Boolean isAuthenticated = true;
	

	public CustomerAuthenticationToken(Customer customer, String jwtToken) {
		log.log(Level.INFO, "role: " + customer.getRole());
		this.customer = customer;
		this.jwtToken = jwtToken;
	}

	@Override
	public String getName() {
		return customer.getId().toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		log.log(Level.INFO, "Authentication Authorities SET");
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (customer.getRole() == 0) {
			authorities.add(new SimpleGrantedAuthority("NONE"));
			log.log(Level.INFO, "Customer null so token null");
			return authorities;
		}
		switch (customer.getRole()) {
		case 1:
			authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
			log.log(Level.INFO, "Customer customer");
			break;
		case 2:
			authorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
			log.log(Level.INFO, "Customer administrator");
			break;
		case 3:
			authorities.add(new SimpleGrantedAuthority("DRIVER"));
			log.log(Level.INFO, "Customer driver");
			break;
		default:
			authorities.add(new SimpleGrantedAuthority("NONE"));
			log.log(Level.INFO, "Customer none");
			break;
		}
		return authorities;

	}

	@Override
	public String getCredentials() {
		return jwtToken;
	}

	@Override
	public List<FoodOrder> getDetails() {
		return customer.getOrders();
	}

	@Override
	public Customer getPrincipal() {
		return customer;
	}

	@Override
	public boolean isAuthenticated() {
		return isAuthenticated;
	}

	@Override
	public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
		this.isAuthenticated = isAuthenticated;

	}
}
