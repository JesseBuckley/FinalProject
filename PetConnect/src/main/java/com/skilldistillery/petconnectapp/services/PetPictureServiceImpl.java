package com.skilldistillery.petconnectapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.PetPicture;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.repository.PetPictureRepository;
import com.skilldistillery.petconnectapp.repository.PetRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class PetPictureServiceImpl implements PetPictureService {
	@Autowired
	private PetPictureRepository petPictureRepo;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private PetRepository petRepo;

	@Override
	public PetPicture create(PetPicture petPicture, String name, int petId) {
		User user = userRepo.findByUsername(name);
		Pet pet = petRepo.findById(petId);
		if (user == null || pet == null) {
			return null;
		}

		petPicture.setPet(pet);

		return petPictureRepo.saveAndFlush(petPicture);
	}

	public List<PetPicture> findPetPicturesByUsername(String username) {
		User user = userRepo.findByUsername(username);
		List<Pet> pets = petRepo.findPetsByUser_Username(username);
		if (user == null || pets == null) {
			return null;
		}
		
		return petPictureRepo.findByPet_User_Username(username);
    }

	@Override
	public boolean deleteById(int petPicId) {
		boolean deleted = false;
		try {
			if (petPictureRepo.existsById(petPicId)) {
				petPictureRepo.deleteById(petPicId);
				deleted = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return deleted;
	}

	@Override
	public List<PetPicture> findAllPetPics(String authenticatedUsername) {
		User owner = userRepo.findByUsername(authenticatedUsername);

		if (owner != null) {

			return petPictureRepo.findAll();
		} else {
			return null;
		}
	}

}
