package com.xsushirollx.sushibyte.orderservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.xsushirollx.sushibyte.orderservice.model.Customer;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;

public class CustomerAuthenticationToken implements Authentication {

	private static final long serialVersionUID = 1L;

	private final Customer customer;

	private Boolean isAuthenticated = true;

	public CustomerAuthenticationToken(Customer customer) {
		this.customer = customer;
	}

	@Override
	public String getName() {
		return customer.getId().toString();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		if (customer == null) {
			authorities.add(new SimpleGrantedAuthority("NONE"));
			return authorities;
		}
		switch (customer.getRole()) {
		case 1:
			authorities.add(new SimpleGrantedAuthority("CUSTOMER"));
			break;
		case 2:
			authorities.add(new SimpleGrantedAuthority("ADMINISTRATOR"));
			break;
		case 3:
			authorities.add(new SimpleGrantedAuthority("DRIVER"));
			break;
		default:
			authorities.add(new SimpleGrantedAuthority("NONE"));
			break;
		}
		return authorities;

	}

	@Override
	public Object getCredentials() {
		return null;
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
