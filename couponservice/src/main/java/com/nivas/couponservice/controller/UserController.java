package com.nivas.couponservice.controller;

import java.util.HashSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.nivas.couponservice.model.Role;
import com.nivas.couponservice.model.User;
import com.nivas.couponservice.model.security.SecurityService;
import com.nivas.couponservice.repository.UserRepo;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Controller
public class UserController {
	
	@Autowired
	private SecurityService securityService;
	
	@Autowired
	UserRepo userRepo;
	
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
	
	@GetMapping("/showReg")
	public String showRegistrationPage() {
		return "registerUser";
	}
	
	@PostMapping("/registerUser")
	public String registerUser(User user) {
		user.setPassword(encoder.encode(user.getPassword()));
		HashSet<Role> roles = new HashSet<>();
		Role role = new Role();
		role.setId(2l);
		roles.add(role);
		user.setRoles(roles);
		userRepo.save(user);
		return "login";
	}

}
