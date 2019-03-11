package com.practice.hackathon.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.practice.hackathon.dao.PetRepository;
import com.practice.hackathon.model.Pet;
import com.practice.hackathon.model.User;

@Service
public class PetService {
	@Autowired
	PetRepository petRepo;
	
	@Autowired
	UserService userService;
	
	@Autowired
	PagingAndSortingService pagingAndSortingService;
	
	public Pet addPet(Pet pet){
		return petRepo.save(pet);
	}
	
	public List<Pet> getAllPets(){
		return petRepo.findAll();
	}
	
	public Optional<Pet> getById(Long petId){
		return petRepo.findById(petId);
	}
	
	public List<Pet> getByUser(User user){
		return petRepo.findByUser(user);
	}
	
	public Page<Pet> getByUser(User user,int page, int size, Optional<Map<String,String>> sortMap){
		return petRepo.findByUser(user,pagingAndSortingService.getPageRequest(page, size, sortMap));
	}
	
	public Pet buyPet(String userName, Long petId){
		User user = userService.getUserByName(userName).orElseThrow(
				()->new UsernameNotFoundException("User not found with username : "+userName));
		Pet pet = petRepo.findById(petId).orElseThrow(
				()->new UsernameNotFoundException("Pet not found with PetID : "+petId));
		pet.setUser(user);
		
		return petRepo.save(pet);		
		
	}
}
