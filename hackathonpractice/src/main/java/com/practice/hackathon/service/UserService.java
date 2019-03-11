package com.practice.hackathon.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.practice.hackathon.dao.UserRepository;
import com.practice.hackathon.model.Pet;
import com.practice.hackathon.model.User;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepo;
	@Autowired
	PetService petService;
	@Autowired
	PasswordEncoder passwordencoder;
	
	public User addUser(User user){
		user.setPassword(passwordencoder.encode(user.getPassword()));
		return userRepo.save(user);
	}
	
	public Optional<User> getUserById(Long userId){
		return userRepo.findById(userId);
	}
	
	public Optional<User> getUserByName(String userName){
		return userRepo.findByUsername(userName);
	}
	
	public List<Pet> getMyPets(String username){
		User user = userRepo.findByUsername(username).get();
		return petService.getByUser(user);
	}
	
	public Page<Pet> getMyPets(String username, int page, int size, Optional<Map<String,String>> sortMap){
		User user = userRepo.findByUsername(username).get();
		return petService.getByUser(user,page,size,sortMap);
	}
}
