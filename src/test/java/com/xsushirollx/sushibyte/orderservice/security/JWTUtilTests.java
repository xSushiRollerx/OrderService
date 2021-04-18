package com.xsushirollx.sushibyte.orderservice.security;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

//@SpringBootTest
public class JWTUtilTests {
	
//	@Autowired
//	JWTUtil util;
	
	JWTUtil util = new JWTUtil();
	
	@Test
	public void extractUserId() {
		System.out.println(util.generateToken("96"));
		assert(Integer.parseInt(util.extractUserId(util.generateToken("96"))) == 96);
	}
}
