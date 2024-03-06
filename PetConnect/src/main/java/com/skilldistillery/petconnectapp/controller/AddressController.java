package com.skilldistillery.petconnectapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.entities.Address;
import com.skilldistillery.petconnectapp.services.AddressService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class AddressController {

	@Autowired
	private AddressService addressService;

	
	@GetMapping("address/{id}")
	public Address show(@PathVariable("id") int id, HttpServletResponse res) {
		Address address = addressService.show(id);
		if (address == null) {
			res.setStatus(404);
		}
		return address;
	}

	@PostMapping("address")
	public Address create(@RequestBody Address address, HttpServletResponse res) {
		Address created = addressService.create(address);
		if (created != null) {
			res.setStatus(201);
		} else if (created == null) {
			res.setStatus(400);
		}
		return created;
	}

	@PutMapping("address/{id}")
	public Address update(@RequestBody Address address, @PathVariable("id") int id, HttpServletResponse res) {
		Address updatedAddress = addressService.update(address, id);
		
		if(updatedAddress == null) {
			res.setStatus(404);
		} else if (updatedAddress.getId() != id) {
			res.setStatus(400);
		}
		
		return updatedAddress;
	}

	@DeleteMapping("address/{id}")
	public ResponseEntity<Void> delete(@PathVariable("id") int id) {
	    boolean deleted = addressService.delete(id);
	    if (!deleted) {
	        return ResponseEntity.notFound().build(); // Return 404
	    } else {
	        return ResponseEntity.noContent().build(); // Return 204
	    }
	}
	}

