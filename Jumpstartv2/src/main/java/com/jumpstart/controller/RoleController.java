package com.jumpstart.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.entity.Role;
import com.jumpstart.repository.RoleRepository;

@RestController
@RequestMapping("/user/roles")
@CrossOrigin(origins = "http://localhost:3000")
public class RoleController {

	@Autowired
	private RoleRepository roleRepository;
	
	@GetMapping
	public ResponseEntity<?> getAllRoles() {
		List<Role> roles = roleRepository.findAll();
		return ResponseEntity.ok(roles);
	}
}
