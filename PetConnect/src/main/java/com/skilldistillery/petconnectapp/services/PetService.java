package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Pet;

public interface PetService{
	 List<Pet> findAllOwnedPets(int userId, String authenticatedUsername);
	Pet findById(int petId);
	Pet findByName(String petName);
	Pet create(Pet pet, String name);
	Pet update(Pet pet, int petId, String PetName);
	boolean deleteById(int petId);
}
