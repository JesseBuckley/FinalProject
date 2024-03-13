package com.skilldistillery.petconnectapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.PetPicture;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.repository.PetPictureRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class PetPictureServiceImpl implements PetPictureService {
	@Autowired
	private PetPictureRepository petPictureRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public PetPicture create(PetPicture petPicture, String name) {
		
		return null;
	}

	@Override
	public boolean deleteById(int petPicId) {
		return false;
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
