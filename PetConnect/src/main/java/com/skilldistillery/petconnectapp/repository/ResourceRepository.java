package com.skilldistillery.petconnectapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Resource;

public interface ResourceRepository extends JpaRepository<Resource, Integer> {
	Resource searchById(int resourceId);

	List<Resource> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String name, String description);

	List<Resource> findByCategoryId(int categoryId);

	List<Resource> findByAddress_City(String city);

	List<Resource> findByAddress_State(String state);

	List<Resource> findByAddress_Zip(String zip);

	int countByAddressId(int count);

}
