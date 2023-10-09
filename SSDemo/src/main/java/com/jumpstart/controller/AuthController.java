package com.jumpstart.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.DTO.JwtAuthenticationResponse;
import com.jumpstart.DTO.LoginRequest;
import com.jumpstart.entity.User;
import com.jumpstart.exception.AppException;
import com.jumpstart.repository.RoleRepository;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.security.JwtTokenProvider;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/user")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;
	
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;
	
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	
	@PostMapping("/sign-in")
	public ResponseEntity<?> authenicateEmployee(@Valid @RequestBody LoginRequest loginRequest){
		  Authentication authentication = authenticationManager.authenticate(
		            new UsernamePasswordAuthenticationToken(
		                loginRequest.getUserName(),
		                loginRequest.getPassword()
		            )
		        );
		  SecurityContextHolder.getContext().setAuthentication(authentication);
		  String jwt = tokenProvider.generateToken(authentication);
	        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	        String username = userDetails.getUsername();
	        String roleName = userDetails.getAuthorities().iterator().next().getAuthority();

	        User user = userRepository.findByUserName(username)
	                .orElseThrow(() -> new AppException("User not found."));
	        
	        Long userId = user.getId(); // Retrieve the user ID
	        String userName = username;
	        long storeId = user.getStore_id();
	        String storeName = user.getStore().getName();
	        String storeAddress = user.getStore().getAddress();
	        String message = "User '" + userName + "' logged in successfully.";

	        JwtAuthenticationResponse response = new JwtAuthenticationResponse(
	        	    jwt, message, roleName, userName, userId, storeId, storeName, storeAddress
	        	);
	        System.out.println("User ID: " + response.getUserId());
	        
		return ResponseEntity.ok(response);
	}	
}
