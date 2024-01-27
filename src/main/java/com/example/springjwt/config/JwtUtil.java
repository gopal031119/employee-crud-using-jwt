package com.example.springjwt.config;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;

@Service
public class JwtUtil {
	
	private String secret;
	private int jwtExpirationInMs;
	private int refreshExpirationDateInMs;	
	
	@Value("${jwt.secret}")
	public void setSecret(String secret) {
		this.secret = secret;
	}
	
	@Value("${jwt.jwtExpirationInMs}")
	public void setJwtExpirationInMs(int jwtExpirationInMs) {
		this.jwtExpirationInMs = jwtExpirationInMs;
	}
	

	@Value("${jwt.refreshExpirationDateInMs}")
	public void setRefreshExpirationDateInMs(int refreshExpirationDateInMs) {
		this.refreshExpirationDateInMs = refreshExpirationDateInMs;
	}
	
	
	public String generateToken(UserDetails userDetails) {
		Map<String,Object> claims = new HashMap<>();
		Collection<? extends GrantedAuthority> roles = userDetails.getAuthorities();
		
		if(roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
			claims.put("isAdmin", true);
		}
		if(roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
			claims.put("isUser", true);
		}
		
//		System.out.println(userDetails.getUsername());
		
		return doGenerateToken(claims, userDetails.getUsername());

	}

	public String doGenerateToken(Map<String, Object> claims, String subject) {
		// TODO Auto-generated method stub
		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis()+jwtExpirationInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();
	}
	
	
	public String doGenerateRefreshToken(Map<String, Object> claims, String subject) {

		return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
				.setExpiration(new Date(System.currentTimeMillis() + refreshExpirationDateInMs))
				.signWith(SignatureAlgorithm.HS512, secret).compact();

	}
	
	
	public boolean validateToken(String authToken) {
		try {
			Jws<Claims> claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(authToken);
			return true;
		}
		catch (SignatureException| MalformedJwtException | UnsupportedJwtException |IllegalArgumentException ex) {
			throw new BadCredentialsException("Invalid Credntials",ex);
		}catch (ExpiredJwtException e) {
			throw e;
		}
	
	}
	
	public String getUsernameFromToken(String token) {
		Claims  claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		return claims.getSubject();
	}
	
	public List<SimpleGrantedAuthority> getRolesFromToken(String token){
		Claims  claims = Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
		List<SimpleGrantedAuthority> roles = null;
		Boolean isAdmin = claims.get("isAdmin",Boolean.class);
		Boolean isUser = claims.get("isUser",Boolean.class);
//		System.out.println("\n\n\n\n\n\n\n\n");
		if(isAdmin != null && isAdmin) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
		}
		if(isUser != null && isUser) {
			roles = Arrays.asList(new SimpleGrantedAuthority("ROLE_USER"));
//			System.out.println("\n\n\n\n\n\n\n\n");
		}
		
		return roles;
	}
	
	
}
