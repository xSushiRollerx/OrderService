package com.xsushirollx.sushibyte.orderservice.security;

import org.junit.jupiter.api.Test;

public class JWTUtilTests {
	
	JWTUtil util = new JWTUtil();
	
	@Test
	public void extractUserId() {
		System.out.println(util.generateToken("99"));
		assert(Integer.parseInt(util.extractUserId(util.generateToken("98"))) == 98);
	}
}
