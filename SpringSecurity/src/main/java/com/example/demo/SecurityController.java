package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.MyUser;
import com.example.demo.repository.MyUserRepo;

@RestController
public class SecurityController {
	
@Autowired
MyUserRepo repo;
@Autowired
PasswordEncoder passwordEncoder;
	
	@GetMapping("/home")
	public String login() {
		return "hello you are in home";
	}
	
	@GetMapping("/user")
	public String userLogin()
	{
		return"hello you are in user";
	}
	
	@GetMapping("/admin")
	public String adminLogin() {
		return "hello you are in admin";
		
	}
	
	@PostMapping("/Register")
	public MyUser Registration(@RequestBody MyUser user)
	{
		user.setUserpassword(passwordEncoder.encode(user.getUserpassword()));
		MyUser data = repo.save(user);
		return data ;
		
	}

}
