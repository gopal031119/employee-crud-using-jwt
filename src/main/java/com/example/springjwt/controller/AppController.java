package com.example.springjwt.controller;


import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.example.springjwt.config.CustomUserDetailsService;
import com.example.springjwt.config.JwtUtil;
import com.example.springjwt.model.AuthenticationRequest;
import com.example.springjwt.model.DAOUser;
import com.example.springjwt.model.UserDTO;
import com.example.springjwt.repository.UserRepository;
import com.example.springjwt.service.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;

@Controller
public class AppController {
	
	@Autowired
	private AuthenticationController auth;
	
	@Autowired
	private CustomUserDetailsService service;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String viewHomePage() {
		
		
		return "index";
		
	}
	
	@GetMapping("/register")
	public ModelAndView showSignPage() {
		
		return new ModelAndView("signup_form","user",new DAOUser());
		
	}
	
	@PostMapping("/registered")
	public String registrationSuccess(@ModelAttribute("user")UserDTO user) {
		service.save(user);
		return "register_success";
	}
	
	
	@GetMapping("/home")
	public String showHomePage() {
		return "home";
	}
	
	
	  @GetMapping("/login") 
	  public String showLoginPage(Model model) { 
		  auth.token="";
		  model.addAttribute("newlogin",new AuthenticationRequest());  
		  return "login";
	  }
	
	
	@GetMapping("list_users")
	public ModelAndView viewUserList(ModelAndView model , Principal principal , Model model1) {
		
		List<DAOUser> listUser = repository.findAll();
		model1.addAttribute("username", principal.getName());
		model.addObject("userList", listUser);
		model.setViewName("users");
		return model;
	}
	
	@GetMapping("/update")
	public ModelAndView updateUser(@RequestParam long id) {
		
		ModelAndView mav = new ModelAndView("update_users");
	    Optional<DAOUser> user = userService.getUserById(id);
	    mav.addObject("user", user);
	 
	    return mav;
	}
	
	@PostMapping("/saveUpdated")
	public String updateSuccess(@ModelAttribute("user")DAOUser user) {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		user.setPassword( passwordEncoder.encode(user.getPassword()));
		userService.save(user);
		return "home";
	}
	
	@GetMapping("/delete")
	public String deleteUser(@RequestParam long id) {
		userService.delete(id);
		return "home";
		
	}
	


	
	
}
