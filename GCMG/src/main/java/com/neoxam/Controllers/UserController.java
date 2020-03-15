package com.neoxam.Controllers;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import javax.mail.MessagingException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.neoxam.SendingEmail.SmtpEmailSender;
import com.neoxam.exception.ResourceNotFoundException;
import com.neoxam.models.*;
import com.neoxam.models.dto.SignupRequest;
import com.neoxam.models.dto.UserModel;
import com.neoxam.repository.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/gp")
public class UserController {

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserRepository userrep;
	@Autowired
	private SmtpEmailSender smtpMailSender;

	@GetMapping(value = "/employees")
	public List<UserModel> getAllUsers() {
		List<User> users = userrep.findAll();
		if (users.isEmpty())
			return null;
		List<UserModel> usersModel = new ArrayList();
		for (User object : users) {
			usersModel.add(new UserModel(object));
		}
		return usersModel;
	}

	@GetMapping(value = "/employees/{id}")
	public ResponseEntity<User> getUserById(@PathVariable(value = "id") Long idU) throws ResourceNotFoundException {
		User user = userrep.findById(idU)

				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + idU));
		return ResponseEntity.ok().body(user);
	}

	@PostMapping(value = "/employees")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest userUP) {
		if (userrep.existsByEmail(userUP.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}

		User user = new User(userUP.getNom(), userUP.getPrenom(), userUP.getPassword(), userUP.getEmail());
		Set<String> strRoles = userUP.getRoles();
		Set<Role> roles = new HashSet<>();
		System.out.println("/************************************************************/");
		System.out.println(strRoles);
		System.out.println("/************************************************************/");

		if (strRoles == null) {
			Role userRole = roleRepository.findByRoleName("USER")
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "ADMIN":
					Role adminRole = roleRepository.findByRoleName("ADMIN")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);
					Role modRole1 = roleRepository.findByRoleName("MODERATOR")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole1);
					Role DHRole1 = roleRepository.findByRoleName("DATAHUB")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(DHRole1);
					Role CRole1 = roleRepository.findByRoleName("COMET")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(CRole1);
					Role GPRole1 = roleRepository.findByRoleName("GP")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(GPRole1);
					Role userRole1 = roleRepository.findByRoleName("USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole1);

					break;
				case "MODERATOR":
					Role modRole = roleRepository.findByRoleName("MODERATOR")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);
					break;

				case "DATAHUB":
					Role DHRole = roleRepository.findByRoleName("DATAHUB")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(DHRole);

					break;
				case "COMET":
					Role CRole = roleRepository.findByRoleName("COMET")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(CRole);

					break;
				case "GP":
					Role GPRole = roleRepository.findByRoleName("GP")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(GPRole);

					break;
				default:
					Role userRole = roleRepository.findByRoleName("USER")
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRole(roles);
		userrep.save(user);
		return new ResponseEntity<>(new ResponseMessage("User registered successfully!"), HttpStatus.OK);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> updateUser(@PathVariable(value="id") Long id,
			@Valid @RequestBody SignupRequest userDetails) throws ResourceNotFoundException {
		User user = userrep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		if (!(userDetails.getEmail() == null)) {
			user.setEmail(userDetails.getEmail());
		}
		if (!(userDetails.getPrenom() == null)) {
			user.setPrenom(userDetails.getPrenom());
		}
		if (!(userDetails.getRoles() == null)) {

			Set<String> strRoles = userDetails.getRoles();
			Set<Role> roles = new HashSet<Role>();
			user.getRoles().forEach(action -> {
				roles.add(action);
			});
			if (strRoles == null) {
				Role userRole = roleRepository.findByRoleName("USER")
						.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
				roles.add(userRole);
			} else {
				strRoles.forEach(role -> {
					switch (role) {
					case "ADMIN":
						Role adminRole = roleRepository.findByRoleName("ADMIN")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(adminRole);
						Role modRole1 = roleRepository.findByRoleName("MODERATOR")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole1);
						Role DHRole1 = roleRepository.findByRoleName("DATAHUB")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(DHRole1);
						Role CRole1 = roleRepository.findByRoleName("COMET")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(CRole1);
						Role GPRole1 = roleRepository.findByRoleName("GP")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(GPRole1);
						Role userRole1 = roleRepository.findByRoleName("USER")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole1);

						break;
					case "MODERATOR":
						Role modRole = roleRepository.findByRoleName("MODERATOR")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(modRole);
						break;

					case "DATAHUB":
						Role DHRole = roleRepository.findByRoleName("DATAHUB")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(DHRole);

						break;
					case "COMET":
						Role CRole = roleRepository.findByRoleName("COMET")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(CRole);

						break;
					case "GP":
						Role GPRole = roleRepository.findByRoleName("GP")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(GPRole);

						break;
					default:
						Role userRole = roleRepository.findByRoleName("USER")
								.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
						roles.add(userRole);
					}
				});
			}
			user.setRole(roles);
		}
		if (!(userDetails.getPassword() == null)) {
			user.setPassword(userDetails.getPassword());
		}
		if (!(userDetails.getNom() == null)) {
			user.setNom(userDetails.getNom());
		}
		if (userDetails.isActive() != user.isActive()) {
			user.setActive(userDetails.isActive());
		}

		final User updatedUser = userrep.save(user);
		System.out.println("/*****************************/");
		System.out.println("/**************kammaaalettt***************/");
		System.out.println("/*****************************/");
		return ResponseEntity.ok(updatedUser);
	}

	@PutMapping("/employees/accepter/{id}")
	public ResponseEntity<User> Accept(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		User user = userrep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		user.setActive(true);
		User updatedUser = userrep.save(user);
		final String username = updatedUser.getNom();
		try {
			smtpMailSender.send(updatedUser.getEmail(), "Neoxam Account",
					"Bienvenue sur notre plateforme , vous pouvez maintenant vous connecter ");
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return ResponseEntity.ok(updatedUser);
	}

	@DeleteMapping("/employees/{id}")
	public Map<String, Boolean> deleteUser(@PathVariable(value = "id") Long id) throws ResourceNotFoundException {
		User user = userrep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));

		userrep.delete(user);
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
	@DeleteMapping("/DeleteRole/{id}/{UrRole}")
	public Map<String, Boolean> deleteRole(@PathVariable(value = "id") Long id, @PathVariable(value = "UrRole") String UrRole) throws ResourceNotFoundException {
		User user = userrep.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("User not found for this id :: " + id));
		

		Set<Role> UserRoles=user.getRoles();
		Optional<Role> myRole = roleRepository.findByRoleName(UrRole);
		Role RoleToDelete=myRole.get();
		UserRoles.remove(RoleToDelete);
		user.setRole(null);
		user.setRole(UserRoles);
		
		userrep.save(user);
		
		Map<String, Boolean> response = new HashMap<>();
		response.put("deleted", Boolean.TRUE);
		return response;
	}
}