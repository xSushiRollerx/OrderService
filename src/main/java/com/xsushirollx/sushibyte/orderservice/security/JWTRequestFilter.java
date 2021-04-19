package com.xsushirollx.sushibyte.orderservice.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
<<<<<<< Upstream, based on OrderService/develop
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

public class JWTRequestFilter extends OncePerRequestFilter {
	
	@Autowired 
	CustomUserDetailsService userDetailsService;
	
	@Autowired
	JWTUtil util;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String header = request.getHeader("Authorization");
		
		String userId = null;
		String jwt = null;
		
		if (header != null && header.startsWith("Bearer ")) {
			jwt = header.substring(7);
			userId = util.extractUserId(jwt);
		}
		
		if (userId != null && SecurityContextHolder.getContext() == null) {
			
			UserDetails userDetails = this.userDetailsService.loadUserByUsername(userId);
			
			if (util.validateToken(jwt, userDetails)) {
				UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
				authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authenticationToken);		
			}
		}
		filterChain.doFilter(request, response);
=======
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.xsushirollx.sushibyte.orderservice.dao.CustomerDAO;
import com.xsushirollx.sushibyte.orderservice.model.Customer;

@Component
public class JWTRequestFilter extends OncePerRequestFilter {

	@Autowired
	JWTAuthenticationProvider jwtAuthenticantionProvider;

	@Autowired
	JWTUtil util;
	
	@Autowired
	CustomerDAO cdao;
	
	@Autowired
	ObjectMapper mapper;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		//assume authentication so valid jwt which starts w/ "Bearer "
		try {
			String token  = request.getHeader("Authorization").substring(7);
			int userId = Integer.parseInt(util.extractUserId(request.getHeader("Authorization").substring(7)));
			
			if (util.validateToken(token) && SecurityContextHolder.getContext() == null) {
				Customer customer = cdao.findById(userId).get();
				CustomerAuthenticationToken authentication = new CustomerAuthenticationToken(customer);
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		
		} catch (Exception e) {
			
		} finally {
			filterChain.doFilter(request, response);
		}
		
		
		
		
>>>>>>> b295d39 Added Spring Security Layer
	}

}
