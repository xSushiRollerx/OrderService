package com.xsushirollx.sushibyte.orderservice.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

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
		return null;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {

		Collection<GrantedAuthority> authorities = new ArrayList<>();
		authorities.add(new GrantedAuthority() {

			private static final long serialVersionUID = 1L;

			@Override
			public String getAuthority() {
				switch (customer.getRole()) {
				case 1:
					return "CUSTOMER";
				case 2:
					return "ADMINISTRATOR";
				case 3:
					return "DRIVER";
				default:
					return "NONE";
				}
			}
		});
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
	public Integer getPrincipal() {
		return customer.getId();
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
