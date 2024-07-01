package com.example.demo.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.demo.model.MyUser;

public interface MyUserRepo extends JpaRepository<MyUser, Integer>{
	
	Optional<MyUser> findByUsername(String username);

}
