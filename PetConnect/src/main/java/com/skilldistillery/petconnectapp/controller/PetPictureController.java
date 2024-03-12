package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.entities.PetPicture;
import com.skilldistillery.petconnectapp.services.PetPictureService;

@RequestMapping("api")
@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class PetPictureController {

	@Autowired
	private PetPictureService petPictureService;
	
	
	 @GetMapping("pet-pictures")
	 public List<PetPicture> findAllPetPicsOwnedByUser(Principal principal) {
	     return petPictureService.findAllPetPics(principal.getName());
	    }
	

}
