package com.jumpstart.controller;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jumpstart.entity.Membership;
import com.jumpstart.entity.Role;
import com.jumpstart.entity.User;
import com.jumpstart.exception.AppException;
import com.jumpstart.exception.UserNotFoundException;
import com.jumpstart.repository.MembershipRepository;
import com.jumpstart.repository.UserRepository;
import com.jumpstart.service.MembershipService;

@RestController
@CrossOrigin(origins = "http://localhost:3000/")
@RequestMapping("/membership")
public class MembershipController {

	private final MembershipRepository membershipRepository;

	@Autowired
	UserRepository userRepository;
	
	@Autowired
	MembershipService membershipService;

	public MembershipController(MembershipRepository membershipRepository) {
		this.membershipRepository = membershipRepository;
	}

	@PostMapping("/apply")
	public Membership applyMembership(@RequestBody Membership membershipData) {
		// Set the status to "pending" before saving to the database
		membershipData.setStatus("pending");

		Long userId = membershipData.getMember().getId();

		if (userId != null) {
			User member = userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId)); // Throw
																												
																								
			// Get the user's roles
			Set<Role> roles = member.getRoles();

			// Check if the user has any roles
			if (!roles.isEmpty()) {
				// Assuming the user has only one role, retrieve it
				Role userRole = roles.iterator().next();

				// Set the role ID from the user's role
				membershipData.setRoleId(userRole.getId());
			} else {
				// Handle the case where the user has no roles
				throw new AppException("User does not have a role.");
			}

			// Generate the membership number
			String membershipNumber = String.format("%06d", membershipRepository.count() + 1);
			membershipData.setMembershipNumber(membershipNumber);

			// Save the membership to the database using the repository
			return membershipRepository.save(membershipData);
		}

		throw new UserNotFoundException(userId); // Throw the exception with a custom error message
	}
	
    @GetMapping("/applications")
//    @PreAuthorize("hasRole('ADMINISTRATOR')")
    public List<Membership> getAllApplications() {
        return membershipService.getAllApplications();
    }

}
