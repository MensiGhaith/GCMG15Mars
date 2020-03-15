package com.neoxam.models.dto;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;

import com.neoxam.models.Role;
import com.neoxam.models.User;

public class UserModel {
	private Long id;
	private String nom;
	private String prenom;
	private String email;
	private boolean active;
	private Set<String> roles=null;
	
	
	public UserModel(User user) {
		this.setActive(user.isActive());

		this.setId(user.getId());
		this.nom=user.getNom();
		this.prenom=user.getPrenom();
		this.setEmail(user.getEmail());
		if (user.getRoles().isEmpty()) 
			return;
		roles = new HashSet();
		for (Role role : user.getRoles()) {
			roles.add(role.getRoleName());
		}
		
	}
	
	
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getPrenom() {
		return prenom;
	}
	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}
	public Set<String> getRoles() {
		return roles;
	}
	public void setRoles(Set<String> roles) {
		this.roles = roles;
	}




	public String getEmail() {
		return email;
	}




	public void setEmail(String email) {
		this.email = email;
	}




	public boolean isActive() {
		return active;
	}




	public void setActive(boolean active) {
		this.active = active;
	}




	public Long getId() {
		return id;
	}




	public void setId(Long id) {
		this.id = id;
	}
	
}