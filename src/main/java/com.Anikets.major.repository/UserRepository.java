package com.Anikets.major.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Anikets.major.model.User;

public interface UserRepository extends JpaRepository<User, Integer>{
	
	
	  Optional<User> findUserByEmail(String email);
	  
	  
	 

}
