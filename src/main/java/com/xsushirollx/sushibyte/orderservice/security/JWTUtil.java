//package com.xsushirollx.sushibyte.orderservice.security;
//
//import java.util.Date;
//import java.util.function.Function;
//
//import org.springframework.security.core.userdetails.UserDetails;
//
//import io.jsonwebtoken.Claims;
//import io.jsonwebtoken.Jwts;
//
//public class JWTUtil {
//	
//	private String SECRET_KEY = "secret";
//	
//	public String extractUserId(String token) {
//		return (extractClaim(token, Claims::getSubject));
//	}
//	
//	public Date extractExpiration(String token) {
//		return extractClaim(token, Claims::getExpiration);
//	}
//	
//	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
//		final Claims claims = extractAllClaims(token);
//		return claimsResolver.apply(claims);
//	}
//	
//	private Claims extractAllClaims(String token) {
//		return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
//	}
//
//	private boolean isTokenExpired(String token) {
//		return extractExpiration(token).before(new Date());
//	}
//	
//	
//	public boolean validateToken(String token, UserDetails user) {
//		final String userId = extractUserId(token);
//		return (!isTokenExpired(token));
//	}
//	
//}
