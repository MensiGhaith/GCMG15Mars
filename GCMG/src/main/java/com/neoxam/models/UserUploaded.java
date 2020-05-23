package com.neoxam.models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


public class UserUploaded {
	//@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long upload;
	
	
	private long id_User;
	private long id_File;
	
	public UserUploaded(long id_User, long id_File) {
		this.id_User = id_User;
		this.id_File = id_File;
	}
	
	public long getId_User() {
		return id_User;
	}
	public void setId_User(long id_User) {
		this.id_User = id_User;
	}
	public long getId_File() {
		return id_File;
	}
	public void setId_File(long id_File) {
		this.id_File = id_File;
	}
}
