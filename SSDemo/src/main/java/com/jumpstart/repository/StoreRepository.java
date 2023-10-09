package com.jumpstart.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.jumpstart.entity.Store;

public interface StoreRepository extends JpaRepository<Store, Long>{
	@Query(value ="SELECT * from Store WHERE name LIKE :name", nativeQuery = true)
	public Store getStore(String name);
}
