package com.xsushirollx.sushibyte.orderservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import com.xsushirollx.sushibyte.orderservice.dao.CustomUserDAO;
import com.xsushirollx.sushibyte.orderservice.model.CustomUser;

public class CustomUserDetailsService implements UserDetailsService {
	@Autowired
	CustomUserDAO udao;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		try {
			final CustomUser user = udao.findById(Integer.parseInt(username)).get();
			String authority = null;
			
			switch (user.getRole()) {
				case 1: 
					authority = "USER";
					break;
				case 2:
					authority = "ADMIN";
					break;
				case 3:
					authority = "DRIVER";
					break;
				default:
					throw new UsernameNotFoundException("User Does Not Have Any Authorities Set");
			}
			
			UserDetails details = User.withUsername(user.getUserId().toString()).authorities(authority).build();
			return details;
			
		} catch (Exception e) {
			throw new UsernameNotFoundException("Could Not Find User By Id");
		}
	}

}
