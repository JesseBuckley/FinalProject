package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.PetPicture;

public interface PetPictureService {
	

	boolean deleteById(int petPicId);
	List<PetPicture> findAllPetPics(String authenticatedUsername);
	PetPicture create(PetPicture petPicture, String name, int petId);
	List<PetPicture> findPetPicturesByUsername(String username);	
	

}
