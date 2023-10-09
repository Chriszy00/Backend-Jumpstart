package com.jumpstart.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.entity.Membership;
import com.jumpstart.entity.Role;
import com.jumpstart.entity.RoleName;
import com.jumpstart.entity.User;
import com.jumpstart.exception.RoleNotFoundException;
import com.jumpstart.repository.MembershipRepository;
import com.jumpstart.repository.RoleRepository;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.service.MembershipService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/admin")
public class AdminController {

	@Autowired
	MembershipService membershipService;
	
	@Autowired
	MembershipRepository membershipRepository;
	
	@Autowired
	UserRepository userRepository;
	
	@Autowired
	RoleRepository roleRepository;
	
	
	@PutMapping("/{id}/approve")
	public ResponseEntity<String> approveMembership(@PathVariable Long id) {
	    Optional<Membership> applicationOptional = membershipRepository.findById(id);
	    if (applicationOptional.isPresent()) {
	        Membership application = applicationOptional.get();
	        application.setStatus("approved");
	        application.setApprovedDate(LocalDate.now());
	        
	        // Update roleId in Membership
	        application.setRoleId(3L);
	        
	        // Update user roles
	        User member = application.getMember();
	        Optional<Role> memberRoleOptional = roleRepository.findByName(RoleName.MEMBER);
	        if (memberRoleOptional.isPresent()) {
	            Role memberRole = memberRoleOptional.get();
	            member.getRoles().clear(); // Remove existing roles (if any)
	            member.getRoles().add(memberRole); // Add the MEMBER role
	        } else {
	            // Handle the case where the role was not found
	        	throw new RoleNotFoundException(RoleName.MEMBER.name());
	        }
	        
	        // Save both the Membership and User entities
	        membershipRepository.save(application);
	        userRepository.save(member);
	        
	        return ResponseEntity.ok("Member approved successfully");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}



	@PutMapping("/{id}/deny")
	public ResponseEntity<String> denyMembership(@PathVariable Long id) {
	    Optional<Membership> applicationOptional = membershipRepository.findById(id);
	    if (applicationOptional.isPresent()) {
	        Membership application = applicationOptional.get();
	        application.setStatus("denied"); // This is already in lowercase
	        application.setApprovedDate(LocalDate.now());
	        
	        application.setRoleId(2L);
	        
	        User member = application.getMember();
	        Optional<Role> memberRoleOptional = roleRepository.findByName(RoleName.USER);
	        if (memberRoleOptional.isPresent()) {
	        	Role memberRole = memberRoleOptional.get();
	        	member.getRoles().clear();
	        	member.getRoles().add(memberRole);
	        } else {
	        	throw new RoleNotFoundException(RoleName.USER.name());
	        }
	        
	        membershipRepository.save(application);
	        return ResponseEntity.ok("Membership denied.");
	    } else {
	        return ResponseEntity.notFound().build();
	    }
	}

}
