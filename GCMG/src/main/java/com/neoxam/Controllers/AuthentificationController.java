package com.neoxam.Controllers;

import java.util.HashSet;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import org.springframework.http.HttpStatus;


import com.neoxam.models.ResponseMessage;
import com.neoxam.models.Role;
import com.neoxam.models.User;
import com.neoxam.models.dto.JwtResponse;
import com.neoxam.models.dto.LoginRequest;
import com.neoxam.models.dto.SignupRequest;
import com.neoxam.repository.RoleRepository;
import com.neoxam.repository.UserRepository;
import com.neoxam.security.JWT.JwtUtils;
import com.neoxam.security.services.UserDetailsImpl;
@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping(value = "/auth")
public class AuthentificationController {

	@Autowired
	AuthenticationManager authenticationManager;

	



	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
	@Autowired
	RoleRepository roleRepository;

	@Autowired
	private UserRepository userrep;
	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		List<String> roles = userDetails.getAuthorities().stream()
				.map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(new JwtResponse(jwt, 
												 userDetails.getId(), 
												 userDetails.getUsername(), 
												 userDetails.getEmail(), 
												 roles));
	}
	

	@PostMapping(value = "signup")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignupRequest userUP) {
		if (userrep.existsByEmail(userUP.getEmail())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Email is already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		if (userrep.existsByUsername(userUP.getUsername())) {
			return new ResponseEntity<>(new ResponseMessage("Fail -> Username is already in use!"),
					HttpStatus.BAD_REQUEST);
		}
		User user = new User(userUP.getNom(), userUP.getPrenom(), encoder.encode(userUP.getPassword()), userUP.getEmail(),userUP.getUsername());
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
	

	
}
