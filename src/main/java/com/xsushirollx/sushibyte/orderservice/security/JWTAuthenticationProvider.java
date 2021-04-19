package com.xsushirollx.sushibyte.orderservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;

import com.xsushirollx.sushibyte.orderservice.dao.CustomerDAO;

@Component
public class JWTAuthenticationProvider implements AuthenticationProvider {

	@Autowired
	CustomerDAO cdao;

	@Override
	public Authentication authenticate(Authentication authentication) throws AuthenticationException {
		try {
			if (cdao.existsById(Integer.parseInt(authentication.getName()))) {
				return authentication;
			} else {
				throw new Exception();
			}
		} catch (Exception e) {
			throw new AuthenticationException("Bad Credentials") {
				private static final long serialVersionUID = 1L;
			};
		}
	}

	@Override
	public boolean supports(Class<?> authentication) {
		// TODO Auto-generated method stub
		return authentication.equals(CustomerAuthenticationToken.class);
	}

}
