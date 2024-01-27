 package com.example.springjwt.config;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import com.example.springjwt.controller.AuthenticationController;

import io.jsonwebtoken.ExpiredJwtException;


@Component
public class CustomJwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private AuthenticationController auth;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
	
		
		try {
			
			
			//JWT token is in the form of "Bearer Token" Remove the bearer word and get only token
			
//			String jwtToken = extractJwtFromRequest(request);
			
			String jwtToken = null;
			
			
			
			CloseableHttpClient client = HttpClients.custom().build();
			HttpUriRequest request1 = RequestBuilder.post()
					  .setUri("")
					  .setHeader(HttpHeaders.AUTHORIZATION, auth.token)
					  .build();
			
			String bearerToken = request1.getAllHeaders()[0].getValue();
			
			
			
			if(StringUtils.hasText(bearerToken) &&  bearerToken.startsWith("Bearer ")) {
				jwtToken = bearerToken.substring(7,bearerToken.length());
				
				System.out.println(jwtToken);
			}
			
			
			
			
			if(StringUtils.hasText(jwtToken) && jwtUtil.validateToken(jwtToken)) {
				
				UserDetails userDetails = new User(jwtUtil.getUsernameFromToken(jwtToken), "" , jwtUtil.getRolesFromToken(jwtToken));	
				
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken
						(userDetails, "", jwtUtil.getRolesFromToken(jwtToken));
				
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			else {
				System.out.println("can not set the security context");
			}
	
		}catch (ExpiredJwtException e) { 
			request.setAttribute("exception", e);
			
		}catch (BadCredentialsException e) {
			request.setAttribute("exception", e);
		 }
		filterChain.doFilter(request, response);
		
		
	}
	
	
	private void allowForRefreshToken(ExpiredJwtException ex, HttpServletRequest request) {

		// create a UsernamePasswordAuthenticationToken with null values.
		UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
				null, null, null);
		// After setting the Authentication in the context, we specify
		// that the current user is authenticated. So it passes the
		// Spring Security Configurations successfully.
		SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
		// Set the claims so that in controller we will be using it to create
		// new JWT
		request.setAttribute("claims", ex.getClaims());

	}
	
	
//	private String extractJwtFromRequest(HttpServletRequest request ) {
//		
//		CloseableHttpClient client = HttpClients.custom().build();
//		HttpUriRequest request1 = RequestBuilder.post()
//				  .setUri("http://localhost:8080/home")
//				  .setHeader(HttpHeaders.AUTHORIZATION, auth.token)
//				  .build();
//		
//		String bearerToken = request1.getAllHeaders()[0].getValue();
//		
//		if(StringUtils.hasText(bearerToken) &&  bearerToken.startsWith("Bearer ")) {
//			return bearerToken.substring(7,bearerToken.length());
//		}
//		
//		return null;
//	}
}
