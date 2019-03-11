package com.practice.hackathon.security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.hackathon.dao.UserRepository;
import com.practice.hackathon.model.CustomUserDetail;
import com.practice.hackathon.model.User;
@Service
public class CustomUserDetailsService implements UserDetailsService {
	
	@Autowired
	UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		Optional<User> user = userRepository.findByUsername(userName);
		user.orElseThrow(()->new UsernameNotFoundException("User not found with username : "+userName));
		return new CustomUserDetail(user.get());
	}

}
