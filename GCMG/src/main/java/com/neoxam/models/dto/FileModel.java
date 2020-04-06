package com.neoxam.models.dto;

import java.util.Date;
import java.util.Set;

import com.neoxam.models.DBFile;


public class FileModel {
	private Long id;
	private String nom;
	private String type;
	private byte[]  file;
	private String tag;
	private String departement;
	private Date added_at;









	private boolean active;
	
	public FileModel() {}
	public FileModel(DBFile files) {
		this.setActive(files.isActive());

		this.setId(files.getId());
		this.nom=files.getName();
		this.tag= files.getFileTag() ;
		this.departement=files.getDepartement();
		this.type=(files.getFileType());
		this.added_at=files.getAddedAt();
		this.file=files.getFileContent();
	
		
	}
	
	
	
	
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}










	public String getType() {
		return type;
	}




	public void setType(String type) {
		this.type = type;
	}




	public  byte[]  getFile() {
		return file;
	}




	public void setFile(byte[]  file) {
		this.file = file;
	}




	public String getTag() {
		return tag;
	}




	public void setTag(String tag) {
		this.tag = tag;
	}




	public String getDepartement() {
		return departement;
	}




	public void setDepartement(String departement) {
		this.departement = departement;
	}




	public Date getAdded_at() {
		return added_at;
	}




	public void setAdded_at(Date added_at) {
		this.added_at = added_at;
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
