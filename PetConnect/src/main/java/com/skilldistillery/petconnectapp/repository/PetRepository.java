package com.skilldistillery.petconnectapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Pet;

public interface PetRepository extends JpaRepository<Pet, Integer> {
	Pet findById(int petId);
	Pet deleteById(int petId);
	Pet findByName(String petName);
	List<Pet> findPetsByUserId(int userId);
	List<Pet> findPetsByUser_Username(String username);
}
