package com.example.springjwt.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.http.HttpHeaders;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.example.springjwt.config.CustomUserDetailsService;
import com.example.springjwt.config.JwtUtil;
import com.example.springjwt.model.AuthenticationRequest;
import com.example.springjwt.model.AuthenticationResponse;
import com.example.springjwt.model.UserDTO;

import io.jsonwebtoken.impl.DefaultClaims;

@RestController
public class AuthenticationController {
	
	
	
	@Autowired
	private AuthenticationManager manager;
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Autowired
	private PasswordEncoder bcryptEncoder;	
	
	@Autowired
	private JwtUtil jwtUtil;
	
	
	public String token;
	
	@RequestMapping(value = "/authenticate" , method= RequestMethod.POST )
	public /*ResponseEntity<AuthenticationResponse>*/ void createAuthenticationToken(@ModelAttribute("newlogin") AuthenticationRequest req
			, HttpServletResponse httpres) throws Exception{
		try{
			
			
			manager.authenticate(new UsernamePasswordAuthenticationToken(req.getUsername(), req.getPassword()));
		}catch (DisabledException e) {
			throw new Exception("USER_DISABLED",e);
		}catch (BadCredentialsException e) {
			throw new Exception("Invalid Credaantials",e);
		}
		
		final UserDetails userDetails = service.loadUserByUsername(req.getUsername());
		token = "Bearer "+jwtUtil.generateToken(userDetails);
		
		
//		
		System.out.println(token);
//		
		httpres.sendRedirect("/home");
//		return ResponseEntity.ok(new AuthenticationResponse(token));
		
		
//		return ResponseEntity.ok(new AuthenticationResponse(token));
//		return ResponseEntity.ok(new AuthenticationResponse(token));
//		return "redirect:/home"; 
	}
	
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public ResponseEntity<?> saveUser(@RequestBody UserDTO user) throws Exception {
		return ResponseEntity.ok(service.save(user));
	}
	
	@RequestMapping(value = "/refreshtoken", method = RequestMethod.GET)
	public ResponseEntity<?> refreshtoken(HttpServletRequest request) throws Exception {
		// From the HttpRequest get the claims
		DefaultClaims claims = (io.jsonwebtoken.impl.DefaultClaims) request.getAttribute("claims");

		Map<String, Object> expectedMap = getMapFromIoJsonwebtokenClaims(claims);
		String token = jwtUtil.doGenerateRefreshToken(expectedMap, expectedMap.get("sub").toString());
		return ResponseEntity.ok(new AuthenticationResponse(token));
	}

	public Map<String, Object> getMapFromIoJsonwebtokenClaims(DefaultClaims claims) {
		Map<String, Object> expectedMap = new HashMap<String, Object>();
		for (Entry<String, Object> entry : claims.entrySet()) {
			expectedMap.put(entry.getKey(), entry.getValue());
		}
		return expectedMap;
	}
	
}
