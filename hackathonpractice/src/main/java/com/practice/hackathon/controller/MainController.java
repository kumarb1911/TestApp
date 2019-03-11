package com.practice.hackathon.controller;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.practice.hackathon.model.Pet;
import com.practice.hackathon.model.User;
import com.practice.hackathon.service.Authenticationservice;
import com.practice.hackathon.service.PetService;
import com.practice.hackathon.service.UserService;

@RestController
@RequestMapping("/api/v1")
public class MainController {
	@Autowired
	UserService userService;
	@Autowired
	PetService petService;
	@Autowired
	Authenticationservice authService;
	
	@PostMapping("/register")
	public User registerUser(@RequestBody User user){
		return userService.addUser(user);
	}
	
	@PostMapping("/pets")
	public Pet addPet(@RequestBody Pet pet){
		return petService.addPet(pet);
	}
	
	@GetMapping("/users/{userName}")
	public ResponseEntity<User> getUserByName(@PathVariable("userName") String userName){
		Optional<User> optionalUser = userService.getUserByName(userName);
		User user = optionalUser.orElseThrow(()->new UsernameNotFoundException("User not found with username : "+userName));
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@GetMapping("/pets/{userName}/{petId}")
	public Pet buyPet(@PathVariable("userName") String userName, @PathVariable("petId") Long petId){
		return petService.buyPet(userName, petId);
	}
	
	@GetMapping("/users/pets/{userName}")
	public List<Pet> myPets(@PathVariable("userName") String userName){
		return userService.getMyPets(userName);
	}
	
	@GetMapping("/logout")
	public ResponseEntity<String> logOut(HttpServletRequest request, HttpServletResponse response,HttpSession session){
		authService.logOut(request, response);
		Cookie[] cookies = request.getCookies();
	    if (cookies != null)
	        for (Cookie cookie : cookies) {
	            cookie.setValue("");
	            cookie.setPath("/");
	            cookie.setMaxAge(0);
	            response.addCookie(cookie);
	        }
		return new ResponseEntity<String>("Successfully loggedout",HttpStatus.OK);
	}
	
	  @RequestMapping(value = "/test", method = RequestMethod.GET)
	    //@ResponseStatus(HttpStatus.NO_CONTENT)
	    public String logout() {
	        return "";
	    }

}
