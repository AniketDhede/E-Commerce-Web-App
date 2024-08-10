package com.Anikets.major.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.Anikets.major.model.CustomerUserDetails;
import com.Anikets.major.model.User;
import com.Anikets.major.repository.UserRepository;

@Service
public class CustomUserDetailService  implements UserDetailsService{
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// TODO Auto-generated method stub
		Optional<User> user=userRepository.findUserByEmail(username);
		user.orElseThrow(()->new UsernameNotFoundException("USER tar nahi milala"));
		return user.map(CustomerUserDetails::new).get();
	}

}
