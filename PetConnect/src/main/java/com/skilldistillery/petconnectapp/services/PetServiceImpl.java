package com.skilldistillery.petconnectapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.repository.PetRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class PetServiceImpl implements PetService {

	@Autowired
	private PetRepository petRepo;

	@Autowired
	private UserRepository userRepo;

	 @Override
	    public List<Pet> findAllOwnedPets(int userId, String authenticatedUsername) {
	        Optional<User> owner = userRepo.findById(userId);

	        if (owner.isPresent()) {
	           
	            if (!owner.get().getUsername().equals(authenticatedUsername)) {
	            	return null;
	            }
	            return petRepo.findPetsByUserId(userId);
	        } else {
	            return null;
	        }
	    }

	@Override
	public Pet findById(int petId) {
		return petRepo.findById(petId);
	}

	@Override
	public Pet findByName(String petName) {
		return petRepo.findByName(petName);
	}

	@Override
	public Pet create(Pet pet, String name) {
		User user = userRepo.findByUsername(name);
		if( user != null) {
			pet.setUser(user);
			return petRepo.save(pet);
		}
		
		
		
		return null;
	}

	@Override
	public Pet update(Pet pet, int petId, String PetName) {
		Pet original = petRepo.findById(petId);
		original.setSpecies(pet.getSpecies());
		original.setBreed(pet.getBreed());
		original.setName(pet.getName());
		original.setDescription(pet.getDescription());
		;
		original.setDateOfBirth(pet.getDateOfBirth());
		original.setEnabled(pet.isEnabled());
//		original.setPetPictures(pet.getPetPictures());
		return petRepo.save(original);
	}

	@Override
	public boolean deleteById(int petId) {
		boolean deleted = false;
		if (petRepo.existsById(petId)) {
			petRepo.deleteById(petId);
			deleted = true;

			return deleted;
		}
		return false;
	}

}
