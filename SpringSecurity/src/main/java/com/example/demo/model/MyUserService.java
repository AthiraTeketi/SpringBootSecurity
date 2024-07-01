package com.example.demo.model;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.example.demo.repository.MyUserRepo;

@Service
public class MyUserService implements UserDetailsService{
	
	@Autowired
	MyUserRepo repo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<MyUser> user = repo.findByUsername(username);
		if(user.isPresent())
		{
			var userobj = user.get();
			
			return  User.builder()
					.username(userobj.getUsername())
					.password(userobj.getUserpassword())
					.roles(getRoles(userobj))
					.build();
			
		}
		else {
			throw new UsernameNotFoundException(username);
		}
		
			}
	private String[] getRoles(MyUser userobj) {

		if(userobj.getRoles() == null)
		{
			return new String[] {"USER"};
		}
		return userobj.getRoles().split(",");
	}

}
