package com.nivas.productservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;


import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {

	@Autowired
	private com.nivas.productservice.security.SecurityService securityService;
	
	@Autowired
	com.nivas.productservice.repository.UserRepo userRepo;
	
	@Autowired
	PasswordEncoder encoder;
	
	@GetMapping("/")
	public String  showLoginPage() {
		return "login";
	}
	
	@PostMapping("/login")
	public String login(String email,String password, HttpServletRequest request, HttpServletResponse response) {
		boolean loginResponse = securityService.login(email, password,request,response);
		if(loginResponse) {
			return "index";
			
		}
		return "login";
		
	}
}
