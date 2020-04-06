package com.neoxam.Controllers;

import java.io.IOException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.neoxam.exception.ResourceNotFoundException;
import com.neoxam.models.DBFile;
import com.neoxam.models.Role;
import com.neoxam.models.User;
import com.neoxam.models.dto.FileModel;
import com.neoxam.models.dto.SignupRequest;
import com.neoxam.models.dto.UserModel;
import com.neoxam.repository.DBFileRepository;
import com.neoxam.security.services.FileUtils;

@RestController
@CrossOrigin("http://localhost:4200")
@RequestMapping(value = "/gp")

public class FilesController {

	@Autowired
	FileUtils fileUtils;
	@Autowired
	DBFileRepository fileRepo;


	@RequestMapping(value = "/do", method = RequestMethod.POST, consumes = { "multipart/form-data" })
	public boolean upload(@RequestParam("file") MultipartFile file,@RequestParam("tag") String tag,@RequestParam("dep") String dep) {
		
		
		return fileUtils.uploadImage(file,tag,dep);
	}
	
	@GetMapping(path = { "/get/{imageName}" })
	public DBFile recupFile(@PathVariable("imageName") String fileName) throws IOException {

		return fileUtils.getFile(fileName);
	}
	
	
	@GetMapping(value = "/getfiles")
	public List<FileModel> getAllUsers() {
		List<DBFile> files = fileRepo.findAll();
		if (files.isEmpty())
			return null;
		List<FileModel> usersModel = new ArrayList();
		for (DBFile object : files) {
			usersModel.add(new FileModel(object));
		}
		return usersModel;
	}
	

	@PutMapping("/filesUpdate/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")

	public ResponseEntity<?> updateFile(@PathVariable(value="id") Long id,
			@Valid @RequestBody FileModel fileDetails) throws ResourceNotFoundException {
		DBFile file = fileRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File not found for this id :: " + id));
		if (!(fileDetails.getDepartement()== null)) {
			file.setDepartement(fileDetails.getDepartement());
		}
		if (!(fileDetails.getTag()== null)) {
			file.setFileTag(fileDetails.getTag());
		}
		if (!(fileDetails.getNom()== null)) {
			file.setName(fileDetails.getNom());
		}
		if (fileDetails.isActive() != file.isActive()) {
			file.setActive(fileDetails.isActive());
		}


		final DBFile updatedUser = fileRepo.save(file);
		
		return ResponseEntity.ok(updatedUser);
	}
	@DeleteMapping("/fileDelete/{id}")
	@PreAuthorize("hasAuthority('ADMIN')")

	public Map<String, Boolean> deleteFile(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		DBFile file = fileRepo.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("File not found for this id :: " + id));

		fileRepo.delete(file);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	  @GetMapping("/downloadFile/{fileId}")
	    public ResponseEntity<?> downloadFile(@PathVariable Long fileId) {
	        // Load file from database
	        Optional<DBFile> dbFile = fileRepo.findById(fileId);

	        return ResponseEntity.ok()
	                .contentType(MediaType.parseMediaType("application/octet-stream"))
	                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.get().getName() + "\"")
	                .body(new ByteArrayResource(dbFile.get().getFileContent()));
	    }

	
	
}