package com.ignek.ecommerce.apis.controller;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

import com.ignek.ecommerce.apis.config.jwt.JwtUtils;
import com.ignek.ecommerce.apis.config.service.UserDetailsImpl;
import com.ignek.ecommerce.apis.dto.request.LoginRequest;
import com.ignek.ecommerce.apis.dto.request.SignupRequest;
import com.ignek.ecommerce.apis.dto.response.JwtResponse;
import com.ignek.ecommerce.apis.dto.response.MessageResponse;
import com.ignek.ecommerce.apis.entity.ERole;
import com.ignek.ecommerce.apis.entity.Role;
import com.ignek.ecommerce.apis.entity.User;
import com.ignek.ecommerce.apis.repository.RoleRepo;
import com.ignek.ecommerce.apis.repository.UserRepo;

import jakarta.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepo userRepository;

	@Autowired
	RoleRepo roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	JwtUtils jwtUtils;
//
//	@Autowired
//	private LoginOtpService loginOtpService;
//
//	@PostMapping("/getOtp")
//	public ResponseEntity<?> getOtp(@Valid @RequestBody OtpRequest otpRequest) {
//		try {
//			loginOtpService.handleOtpRequest(otpRequest);
//			return ResponseEntity.ok().build();
//		} catch (BadRequestException e) {
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
//		}
//	}
//
//	@PostMapping("/otpLogin")
//	public ResponseEntity<?> authenticateUserByEmail(@Valid @RequestBody OtpRequest otpRequest) {
//		try {
//			loginOtpService.verifyEmail(otpRequest.getEmail(), otpRequest.getOtp());
//
//			User user = userService.loadUserByEmail(otpRequest.getEmail());
//			String jwt = jwtUtils.generateJwtToken(user.getUsername());
//			List<String> roles = user.getRoles().stream().map(role -> role.getName().name())
//					.collect(Collectors.toList());
//
//			return ResponseEntity.ok(new JwtResponse(jwt, user.getId(), user.getUsername(), roles));
//		} catch (BadRequestException e) {
//			return ResponseEntity.badRequest().body(new MessageResponse(e.getMessage()));
//		}
//	}

	@PostMapping("/signin")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);
		String jwt = jwtUtils.generateJwtToken(authentication);

		UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
		List<String> roles = userDetails.getAuthorities().stream().map(item -> item.getAuthority())
				.collect(Collectors.toList());

		return ResponseEntity.ok(
				new JwtResponse(jwt, userDetails.getId(), userDetails.getUsername(), userDetails.getEmail(), roles));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Username is already taken!"));
		}

		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
		}

		// Create new user's account
		User user = new User(signUpRequest.getUsername(), signUpRequest.getEmail(),
				encoder.encode(signUpRequest.getPassword()));

		Set<String> strRoles = signUpRequest.getRole();
		Set<Role> roles = new HashSet<>();

		if (strRoles == null) {
			Role userRole = roleRepository.findByName(ERole.ROLE_USER)
					.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
			roles.add(userRole);
		} else {
			strRoles.forEach(role -> {
				switch (role) {
				case "admin":
					Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(adminRole);

					break;
				case "mod":
					Role modRole = roleRepository.findByName(ERole.ROLE_MODERATOR)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(modRole);

					break;
				default:
					Role userRole = roleRepository.findByName(ERole.ROLE_USER)
							.orElseThrow(() -> new RuntimeException("Error: Role is not found."));
					roles.add(userRole);
				}
			});
		}

		user.setRoles(roles);
		userRepository.save(user);

		return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
	}

}
