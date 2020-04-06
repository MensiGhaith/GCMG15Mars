package com.neoxam.models;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.annotations.NaturalId;

@Entity
@Table(name="User")
public class User {
	@Id
	@Column(name="Id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long  id;
	@Column(nullable = false,name="Nom")
	private String nom;
	@Column(nullable = false,name="Prenom")
	private String prenom;
	@Column(nullable = false,name="username")
	private String username;
	
	@Column(nullable = false,name="Password")
	private String password;
	@NaturalId(mutable = false)
	@Column(nullable = false,name="Email",unique = true)
	private String email;
	@Column(nullable = false,name="Role")
	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role= new HashSet();
	@Column(name="Active")
	private boolean active=false;
	public User() {}
	
	public User(String nom, String prenom, String password, String email,String username) {
		super();
		this.nom = nom;
		this.prenom = prenom;
		this.password = password;
		this.email = email;
		this.username=username;
	
	}

	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
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
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	public Set<Role> getRoles() {
		return role;
	}
	public void setRole(Set<Role> role) {
		this.role = role;
	}
	
	
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}
}