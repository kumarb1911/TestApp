package com.practice.hackathon.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;

@Entity
@Table(name="users")
public class User {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="userid")
	private long id;
	
	
	@Column(unique=true,name="username") 
	@NotNull
	private String username;
	
	@NotNull
	private String password;
	/*@OneToMany(cascade=CascadeType.ALL)
	@JoinColumn(name="PET_OwnerId")
	private Set<Pet> myPets;*/
	
	
	public User(){}
	public User(User user){
		this.password = user.password;
		this.username = user.username;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setName(String name) {
		this.username = name;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	/*public Set<Pet> getMyPets() {
		return myPets;
	}
	public void setMyPets(Set<Pet> myPets) {
		this.myPets = myPets;
	}*/
	
}
