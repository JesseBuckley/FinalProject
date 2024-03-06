package com.skilldistillery.petconnectapp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.services.AddressService;

@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class AddressController {

	@Autowired
	private AddressService addressService;

	}

