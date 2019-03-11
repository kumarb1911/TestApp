package com.practice.hackathon.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.stereotype.Service;

import com.practice.hackathon.model.User;

import java.util.Optional;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;



@Service
public class Authenticationservice {
	@Autowired
	UserService userService;
	
	private Authentication getAuthentication(){
		return SecurityContextHolder.getContext().getAuthentication();
	}
	private Optional<UserDetails> getPrinciple(){
		Optional<UserDetails> result = Optional.empty();
		Object principle = getAuthentication().getPrincipal();
		if(principle instanceof UserDetails)
			principle = Optional.of((UserDetails) principle);
			return result;
	}
	
	public User getCurrentUser(){
		return getPrinciple().flatMap((p)->{return userService.getUserByName(p.getUsername());}).orElseGet(()-> null);
	}
	
	public void logOut(HttpServletRequest request, HttpServletResponse response){
		Authentication authentication = getAuthentication();
		if(authentication != null)
			new SecurityContextLogoutHandler().logout(request, response, authentication);
		request.getSession().invalidate();
	}
	
}
