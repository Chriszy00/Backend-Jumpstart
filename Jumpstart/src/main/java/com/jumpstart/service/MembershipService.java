package com.jumpstart.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jumpstart.entity.Membership;
import com.jumpstart.repository.MembershipRepository;

@Service
public class MembershipService {
	@Autowired
	private MembershipRepository membershipRepository;


//	public Membership approveStatus(Long memberId) {
//		Membership membership = membershipRepository.findById(memberId)
//				.orElseThrow(() -> new RuntimeException("Membership not found: " + memberId));
//		membership.setStatus("Approved");
//		membership.setStatus("Denied");
//		
//		return membership;
//	}
//	
    // Method to get all membership applications
    public List<Membership> getAllApplications() {
        return membershipRepository.findAll();
    }

}
