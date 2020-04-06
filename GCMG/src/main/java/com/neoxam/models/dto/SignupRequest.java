package com.neoxam.models.dto;


import java.util.Set;

import javax.validation.constraints.*;
 
public class SignupRequest {
  
	private String nom;

    private String prenom;
 
    public String getPrenom() {
		return prenom;
	}

	public void setPrenom(String prenom) {
		this.prenom = prenom;
	}


    private String email;
    
    private Set<String> roles;
    private String username;
    

    public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}


	private String password;
    private boolean active;
  
    public String getNom() {
        return nom;
    }
 
    public void setNom(String username) {
        this.nom = username;
    }
 
    public String getEmail() {
        return email;
    }
 
    public void setEmail(String email) {
        this.email = email;
    }
 
    public String getPassword() {
        return password;
    }
 
    public void setPassword(String password) {
        this.password = password;
    }
    
    public Set<String> getRoles() {
      return this.roles;
    }
    
    public void setRoles(Set<String> role) {
      this.roles = role;
    }
    public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
}