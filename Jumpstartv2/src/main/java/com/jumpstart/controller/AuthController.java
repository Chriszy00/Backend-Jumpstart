package com.jumpstart.controller;

import java.util.Collections;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.DTO.ApiResponse;
import com.jumpstart.DTO.JwtAuthenticationResponse;
import com.jumpstart.DTO.LoginRequest;
import com.jumpstart.DTO.UserRegistrationDTO;
import com.jumpstart.entity.Role;
import com.jumpstart.entity.RoleName;
import com.jumpstart.entity.User;
import com.jumpstart.exception.AppException;
import com.jumpstart.exception.UserNotFoundException;
import com.jumpstart.repository.RoleRepository;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.Security.JwtTokenProvider;
import com.jumpstart.service.UserService;

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
    private UserService userService;
    
	@Autowired
	PasswordEncoder passwordEncoder;
	
	@Autowired
	JwtTokenProvider tokenProvider;
	
	@PostMapping("/sign-up")
	public ResponseEntity<ApiResponse> registerUser(@RequestBody UserRegistrationDTO userRegistrationDTO){
		
		//Check if email is already taken
		if (userRepository.existsByEmail(userRegistrationDTO.getEmail())) {
			return ResponseEntity.badRequest().body(new ApiResponse(false, "Email Address has already been taken"));
		}
		
		//Ecoded Password
		String encodedPassword = passwordEncoder.encode(userRegistrationDTO.getPassword());
		
		//Create a new user and save it to the database
		
		User user = new User(
				userRegistrationDTO.getFirstName(),
				userRegistrationDTO.getLastName(),
				userRegistrationDTO.getPhoneNumber(),
				userRegistrationDTO.getAddress(),
				userRegistrationDTO.getMemberNumber(),
				userRegistrationDTO.getEmail(),
				encodedPassword
		);
		
		 Role userRole = roleRepository.findByName(RoleName.USER)
	                .orElseThrow(() -> new AppException("User Role not set."));

	        user.setRoles(Collections.singleton(userRole));

	        userRepository.save(user);
		
		return ResponseEntity.ok(new ApiResponse(true, "User registered successfully!"));
	}
	
	@PostMapping("/sign-in")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
	    UsernamePasswordAuthenticationToken authenticationToken;
	    
	    if (StringUtils.hasText(loginRequest.getMemberNumber())) {
	        // If memberNumber is not blank, include it in the authentication
	        authenticationToken = new UsernamePasswordAuthenticationToken(
	            loginRequest.getEmail(),
	            loginRequest.getPassword(),
	            Collections.singleton(new SimpleGrantedAuthority(loginRequest.getMemberNumber()))
	        );
	    } else {
	        // If memberNumber is blank, omit it from the authentication
	        authenticationToken = new UsernamePasswordAuthenticationToken(
	            loginRequest.getEmail(),
	            loginRequest.getPassword()
	        );
	    }
	    Authentication authentication = authenticationManager.authenticate(authenticationToken);

	    SecurityContextHolder.getContext().setAuthentication(authentication);

	    String jwt = tokenProvider.generateToken(authentication);
	    UserDetails userDetails = (UserDetails) authentication.getPrincipal();
	    String username = userDetails.getUsername();
	    String roleName = userDetails.getAuthorities().iterator().next().getAuthority(); // retrieve the role name

	    User user = userRepository.findByEmail(username)
	            .orElseThrow(() -> new AppException("User not found"));
	    String firstName = user.getFirstName();
	    String lastName = user.getLastName();
	    Long userId = user.getId();
	    String email = username;
	    String phoneNumber = user.getPhoneNumber();
	    String memberNumber = user.getMemberNumber();
	    String message = "User " + username + " logged in successfully";

	    JwtAuthenticationResponse response = new JwtAuthenticationResponse(jwt, message, roleName, firstName, lastName, userId, email, phoneNumber, memberNumber);
	    System.out.println("User ID: " + response.getUserId());

	    return ResponseEntity.ok(response);
	}
	
	@PostMapping("/apply-membership")
	public ResponseEntity<ApiResponse> applyMembership(@RequestBody UserRegistrationDTO userRegistrationDTO) {
	    // Check if the user is logged in (you can add authentication checks here)

	    // Check if the user's role is currently "User" (role_id 2)
	    User loggedInUser = userService.getCurrentLoggedInUser(); // Implement a method to get the currently logged-in user
	    Set<Role> userRoles = loggedInUser.getRoles();
	    
	    boolean isUser = userRoles.stream().anyMatch(role -> role.getName() == RoleName.USER);

	    if (!isUser) {
	        return ResponseEntity.badRequest().body(new ApiResponse(false, "User role cannot apply for membership."));
	    }

	    // Apply membership logic (e.g., payment processing)

	    // Update the user's role to "Member" (role_id 3)
	    Role memberRole = roleRepository.findByName(RoleName.MEMBER)
	        .orElseThrow(() -> new AppException("Member Role not set."));

	    userRoles.clear();
	    userRoles.add(memberRole);
	    loggedInUser.setRoles(userRoles);

	    userRepository.save(loggedInUser);

	    return ResponseEntity.ok(new ApiResponse(true, "Membership applied successfully, role updated to Member!"));
	}
	
    @GetMapping("/userDetails/{id}")
//    @PreAuthorize("hasAnyRole('ADMINISTRATOR', 'USER', 'MEMBER')")
    User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException(id));
    }
	
}
