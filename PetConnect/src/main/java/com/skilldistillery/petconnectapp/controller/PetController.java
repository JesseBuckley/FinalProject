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
import com.skilldistillery.petconnectapp.services.PetService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("api")
@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class PetController {

	@Autowired
	private PetService petService;

	 @GetMapping("{userId}/pets")
	 public List<Pet> findAllPetsOwnedByUser(@PathVariable("userId") Integer userId, Principal principal) {
	     return petService.findAllOwnedPets(userId, principal.getName());
	    }

	@GetMapping("pets/{petId}")
	public Pet findPetId(@PathVariable("petId") Integer petId, HttpServletResponse res) {
		Pet pet = petService.findById(petId);
		if (pet == null) {
			res.setStatus(404);
		}
		res.setStatus(200);
		return pet;

	}
	
	@GetMapping("pets/name/{petName}")
	public Pet findPetName(@PathVariable("petName") String petName, HttpServletResponse res) {
		Pet pet = petService.findByName(petName);
		if (pet == null) {
			res.setStatus(404);
		}
		res.setStatus(200);
		return pet;
		
	}
	
	
	@PostMapping("pets")
	public Pet create(@RequestBody Pet pet, Principal principal) {
		try {
			pet = petService.create(pet, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return pet;
	}
	
	@PutMapping("pets/{id}/update")
	public Pet update(@RequestBody Pet pet, @PathVariable("id") int id, HttpServletResponse res,
			Principal principal) {
		Pet updated = petService.update(pet, id, principal.getName());
		if (updated != null) {
			res.setStatus(200);
		} else {
			res.setStatus(400);
		}
		return updated;

	}

	@DeleteMapping("pets/{id}/delete")
	public boolean delete(@PathVariable("id") Integer id, HttpServletResponse res, Principal principal) {
		boolean deleted;
		deleted = petService.deleteById(id);

		if (deleted) {
			res.setStatus(204);
		} else {
			res.setStatus(400);
		}

		return deleted;
	}
	
	
	

}
