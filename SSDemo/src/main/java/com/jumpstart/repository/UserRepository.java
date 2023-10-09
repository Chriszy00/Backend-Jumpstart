package com.jumpstart.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByUserName(String userName);
	Boolean existsByUserName(String userName);
	List<User> findByIdIn(List<Long> userIds);
	List<User> findByRoles_Id(Long roleId); 

}
