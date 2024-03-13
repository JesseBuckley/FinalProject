package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.entities.Pet;
import com.skilldistillery.petconnectapp.entities.PetPicture;
import com.skilldistillery.petconnectapp.services.PetPictureService;

import jakarta.servlet.http.HttpServletResponse;

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
	
		@PostMapping("create/pet-pictures")
		public PetPicture create(@RequestBody PetPicture petPic, Principal principal, HttpServletResponse res) {
			PetPicture created = petPictureService.create(petPic, principal.getName());
			
			if (created != null) {
				res.setStatus(201);
			} else if (created == null) {
				res.setStatus(400);
			}
			return petPic;
		}
		
//		@DeleteMapping("pets/{id}/delete")
//		public boolean delete(@PathVariable("id") Integer id, HttpServletResponse res, Principal principal) {
//			boolean deleted;
//			deleted = petService.deleteById(id);
//
//			if (deleted) {
//				res.setStatus(204);
//			} else {
//				res.setStatus(400);
//			}
//
//			return deleted;
//		}
//		
}
