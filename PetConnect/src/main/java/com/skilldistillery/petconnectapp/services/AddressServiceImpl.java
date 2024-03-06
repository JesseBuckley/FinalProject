package com.skilldistillery.petconnectapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.repository.AddressRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepo;

}
