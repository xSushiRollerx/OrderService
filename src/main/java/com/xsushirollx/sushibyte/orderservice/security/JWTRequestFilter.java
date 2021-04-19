package com.xsushirollx.sushibyte.orderservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xsushirollx.sushibyte.orderservice.dao.CustomerDAO;
import com.xsushirollx.sushibyte.orderservice.model.Customer;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil util;

	@Autowired
	CustomerDAO cdao;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// assume authentication so valid jwt which starts w/ "Bearer "
		try {
			String token = request.getHeader("Authorization").substring(7);
			int userId = Integer.parseInt(util.extractUserId(request.getHeader("Authorization").substring(7)));

			if (util.validateToken(token)) {
				Customer customer = cdao.findById(userId).get();
				CustomerAuthenticationToken customerAuthentication = new CustomerAuthenticationToken(customer);
				SecurityContextHolder.getContext().setAuthentication(customerAuthentication);
			}

		} catch (Exception e) {

		} finally {
			filterChain.doFilter(request, response);
		}
	}

}
