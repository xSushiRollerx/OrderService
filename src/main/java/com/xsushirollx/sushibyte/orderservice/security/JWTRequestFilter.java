package com.xsushirollx.sushibyte.orderservice.security;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.xsushirollx.sushibyte.orderservice.dao.UserDAO;
import com.xsushirollx.sushibyte.orderservice.model.FoodOrder;
import com.xsushirollx.sushibyte.orderservice.model.User;
import com.xsushirollx.sushibyte.orderservice.security.JWTUtil;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTUtil util;

	@Autowired
	UserDAO cdao;



	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {

		// assume authentication so valid jwt which starts w/ "Bearer "
		try {
			String token = request.getHeader("Authorization").substring(7);
			Long userId = Long.parseLong(util.extractUserId(request.getHeader("Authorization").substring(7)));
			
			User customer = null;
			
			if (util.validateToken(token)) {
				customer = cdao.findById(userId).get();
			} else {
				customer = new User((long) 0,0);
				List<FoodOrder> orders = new ArrayList<>(); 
				customer.setOrders(orders);
			}
			
			UserAuthenticationToken customerAuthentication = new UserAuthenticationToken(customer, token);
			SecurityContextHolder.getContext().setAuthentication(customerAuthentication);
		} catch (Exception e) {

		} finally {
			filterChain.doFilter(request, response);
		}
	}


}

