package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.PetPicture;

public interface PetPictureService {
	
	PetPicture create(PetPicture petPicture, String name);
	boolean deleteById(int petPicId);
	List<PetPicture> findAllPetPics(String authenticatedUsername);
	

}
