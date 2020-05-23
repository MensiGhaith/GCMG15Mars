package com.neoxam.models;




import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

@Entity
public class DBFile {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;
	@Lob
	@Column(name = "file")
	private byte[] fileContent;
	private String fileType;
	private String name;
	private Date addedAt = new java.sql.Date(new Date().getTime());
	private String fileTag;
	public String departement;
	private boolean active=true;
	public String getDepartement() {
		return departement;
	}

	public void setDepartement(String departement) {
		this.departement = departement;
	}

	public DBFile(){}

	public DBFile(String name, String fileType, byte[] fileContent, Date addedAt, String tag,String departement, Boolean active) {
		this.fileContent = fileContent;
		this.fileType = fileType;
		this.name = name;
		this.addedAt = addedAt;
		this.fileTag = tag;
		this.departement=departement;
		this.active=active;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public String getFileType() {
		return fileType;
	}

	public void setFileType(String fileType) {
		this.fileType = fileType;
	}

	

	public String getFileTag() {
		return fileTag;
	}

	public void setFileTag(String fileTag) {
		this.fileTag = fileTag;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	

	

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getAddedAt() {
		return addedAt;
	}

	public void setAddedAt(Date addedAt) {
		this.addedAt = addedAt;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	
}
