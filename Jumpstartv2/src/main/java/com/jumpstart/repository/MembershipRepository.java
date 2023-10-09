package com.jumpstart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.jumpstart.entity.Membership;

public interface MembershipRepository extends JpaRepository<Membership, Long>{

//	List<Membership> findByMembershipType(String membershipType);

	Optional<Membership> findById(Long id);

}
