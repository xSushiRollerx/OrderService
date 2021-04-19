package com.xsushirollx.sushibyte.orderservice.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;

@EnableWebSecurity
public class SecurityConfigurer extends WebSecurityConfigurerAdapter {

//	@Autowired
//	private JWTAuthenticationProvider jwtTokenAuthentication;
	
	@Autowired
	private JWTRequestFilter jwtFilter;

	//asssume authenticated
//	@Override
//	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//		auth.authenticationProvider(jwtTokenAuthentication);
//	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
	}

}
