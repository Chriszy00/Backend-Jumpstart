package com.jumpstart.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jumpstart.entity.Role;
import com.jumpstart.entity.RoleName;

public interface RoleRepository extends JpaRepository<Role, Long>{
	
	Role findByName(String name);
	Optional<Role> findByName(RoleName roleName);

}
