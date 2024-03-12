package com.skilldistillery.petconnectapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Address;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.exceptions.UsernameExistsException;
import com.skilldistillery.petconnectapp.repository.AddressRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class AuthServiceImpl implements AuthService {

	@Autowired
	private PasswordEncoder pwdEncode;

	@Autowired
	private UserRepository userRepo;

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public User register(User user) {
		if (userRepo.existsByUsername(user.getUsername())) {
			throw new UsernameExistsException("Username already exists");
		}

		String encodedPwd = pwdEncode.encode(user.getPassword());
		user.setPassword(encodedPwd);
		user.setEnabled(true);
		user.setRole("standard");

		Address existingAddress = addressRepo.findByStreetAndCityAndStateAndZip(user.getAddress().getStreet(),
				user.getAddress().getCity(), user.getAddress().getState(), user.getAddress().getZip());

		if (existingAddress != null) {
			user.setAddress(existingAddress);
		} else {
			addressRepo.saveAndFlush(user.getAddress());
		}

		return userRepo.saveAndFlush(user);
	}

	@Override
	public User getUserByUsername(String username) {

		return userRepo.findByUsername(username);
	}

}