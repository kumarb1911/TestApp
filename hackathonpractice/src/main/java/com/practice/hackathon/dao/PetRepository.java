package com.practice.hackathon.dao;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.practice.hackathon.model.Pet;
import com.practice.hackathon.model.User;

public interface PetRepository extends JpaRepository<Pet, Long> {
	public Page<Pet> findByUser(User user,Pageable pageable);
	public List<Pet> findByUser(User user);
}
