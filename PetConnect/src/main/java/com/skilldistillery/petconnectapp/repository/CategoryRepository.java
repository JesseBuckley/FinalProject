package com.skilldistillery.petconnectapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Category;

public interface CategoryRepository extends JpaRepository<Category, Integer> {
	Category searchById(int id);

	Category searchByType(String type);

	Category findByType(String type);

}
