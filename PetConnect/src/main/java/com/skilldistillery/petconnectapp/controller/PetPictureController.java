package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

	@PostMapping("pet-pictures/{petId}")
	public PetPicture create(@RequestBody PetPicture petPicture, Principal principal, HttpServletResponse res,
			@PathVariable("petId") int petId) {
		PetPicture created = petPictureService.create(petPicture, principal.getName(), petId);

		if (created != null) {
			res.setStatus(201);
			return created;
		} else {
			res.setStatus(400);
			return null;
		}
	}

	@GetMapping("myPet-pictures")
	public List<PetPicture> getMyPetPictures(Principal principal, HttpServletResponse res) {
		if (principal == null) {
			res.setStatus(401);
			return null;
		}

		List<PetPicture> petPictures = petPictureService.findPetPicturesByUsername(principal.getName());

		if (petPictures == null || petPictures.isEmpty()) {
			res.setStatus(404);
			return null;
		}

		return petPictures;
	}

	@DeleteMapping("pet-pictures/{id}/delete")
	public boolean delete(@PathVariable("id") Integer id, HttpServletResponse res, Principal principal) {
		boolean deleted;
		try {
			deleted = petPictureService.deleteById(id);

			if (deleted) {
				res.setStatus(204);
			} else {
				res.setStatus(404);
			}
		} catch (Exception e) {
			res.setStatus(500);
			deleted = false;
		}
		return deleted;
	}
//		
}
