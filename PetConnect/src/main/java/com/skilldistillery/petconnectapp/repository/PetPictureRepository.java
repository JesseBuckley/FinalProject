package com.skilldistillery.petconnectapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.PetPicture;

public interface PetPictureRepository extends JpaRepository<PetPicture, Integer> {
	Pet findById(int petId);
	Pet deleteById(int petId);
    List<PetPicture> findByPet_User_Username(String username);


}
