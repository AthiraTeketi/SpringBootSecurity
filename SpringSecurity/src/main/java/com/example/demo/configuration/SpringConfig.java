package com.example.demo.configuration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.model.MyUserService;

@Configuration
@EnableWebSecurity
public class SpringConfig {
	
	@Autowired
	MyUserService uerserviceObj;
	
	@Bean
	public SecurityFilterChain SecurityFilterChain (HttpSecurity httpsecurity) throws Exception{
		return httpsecurity
				.csrf(HttpSecuritycsrfConfigure->HttpSecuritycsrfConfigure.disable())
				.authorizeHttpRequests(registry->{
					registry.requestMatchers("/home","/Register/**").permitAll();
					registry.requestMatchers("/user/**").hasRole("USER");
					registry.requestMatchers("/admin/**").hasRole("ADMIN");
					registry.anyRequest().authenticated();
				})
				.formLogin(formLogin->formLogin.permitAll())
				.build();
	}
	//write this bean below security chain
	@Bean
	public UserDetailsService userDetailService() {
		
		UserDetails normalUser =User.builder()
				.username("athira")
				.password("$2a$12$IvSOMEoE5tuFfbVP/BAs3OoUkHHmaNl6WkXNfXhr0eqwQoSnJC15O")//Daddy@143
				.roles("USER")
				.build();
		
		UserDetails adminuser = User.builder()
				.username("Pyda")
				.password("$2a$12$2O6Cq80FlbG9V1K5xBLFKuUJcfIdKSl1uX2LRY7D2K/n7zKmESV4")//Mummy
				.roles("USER", "ADMIN")
				.build();
				
		return  new InMemoryUserDetailsManager(normalUser,adminuser);
	}
	
	@Bean
	public UserDetailsService userDetailsService() {

		return uerserviceObj;
		
	}
	
	@Bean
	public AuthenticationProvider authProvider() {
		DaoAuthenticationProvider daoAuthProvider = new DaoAuthenticationProvider();// Dao means data access object.
		daoAuthProvider.setUserDetailsService(uerserviceObj);
		daoAuthProvider.setPasswordEncoder(passwordEncoder());
		return daoAuthProvider;
		
	}
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		
		return new BCryptPasswordEncoder();
		
	}

	



}