package com.weatherapp.integrate.spring.react.controller;

import javax.validation.Valid;//Was blocked by spring boot 2.3.5 release, need to check

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import com.weatherapp.integrate.spring.react.jwt.AuthTokenFilter;
import com.weatherapp.integrate.spring.react.jwt.JwtUtils;
import com.weatherapp.integrate.spring.react.model.User;
import com.weatherapp.integrate.spring.react.repository.UserRepository;
import com.weatherapp.integrate.spring.react.requestobject.LoginRequest;
import com.weatherapp.integrate.spring.react.requestobject.SignupRequest;
import com.weatherapp.integrate.spring.react.responseobject.JwtResponse;
import com.weatherapp.integrate.spring.react.responseobject.MessageResponse;
import com.weatherapp.integrate.spring.react.services.UserDetailsImpl;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	PasswordEncoder encoder;

	@Autowired
	UserRepository userRepository;

	@Autowired
	JwtUtils jwtUtils;

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@PostMapping("signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) throws Exception{
		System.out.println("Inside signing");
		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);
		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();		
		return ResponseEntity.ok(new JwtResponse(jwt, userDetails.getId(), userDetails.getEmail()));
	}

	@PostMapping("signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity
					.badRequest()
					.body(new MessageResponse("Error: Email is already in use!"));
		}
		User user = new User(signUpRequest.getEmail(), signUpRequest.getDob(),
				encoder.encode(signUpRequest.getPassword()));
		userRepository.save(user);
		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}
}

