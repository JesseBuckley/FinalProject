package com.skilldistillery.petconnectapp.services;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.skilldistillery.petconnectapp.entities.Address;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.exceptions.UsernameExistsException;
import com.skilldistillery.petconnectapp.repository.AddressRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Transactional
@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;

	@Override
	public List<User> index(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null && user.getRole().equalsIgnoreCase("ADMIN")) {
			return userRepo.findAll();
		}
		return List.of();
	}

	@Override
	public User show(String username, int userId) {
		return userRepo.findByIdAndUsername(userId, username);
	}

	@Override
	public List<User> findUsersByUsername(String username) {
		return userRepo.findByUsernameContainingIgnoreCase(username);
	}

	@Override
	public List<User> findUsersByRole(String role) {
		return userRepo.findByRole(role);
	}

	@Override
	public List<User> findUsersByEnabled(boolean status) {
		return userRepo.findByEnabled(status);
	}

	@Override
	public List<User> findUsersByCreatedAtAfter(LocalDateTime date) {
		return userRepo.findByCreatedAtAfter(date);
	}

	@Override
	public List<User> findUsersByName(String name) {
		return userRepo.findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(name, name);
	}

	@Override
	public User update(int userId, User user, String username) {
		User managedUser = userRepo.findByUsername(username);

		if (managedUser != null) {

			if (!user.getUsername().equals(username) && userRepo.existsByUsername(user.getUsername())) {
				throw new UsernameExistsException("Username already exists");
			} else {
				managedUser.setUsername(user.getUsername());
			}

			managedUser.setProfilePicture(user.getProfilePicture());
			managedUser.setBiography(user.getBiography());
			managedUser.setBackgroundPicture(user.getBackgroundPicture());
			managedUser.setFirstName(user.getFirstName());
			managedUser.setLastName(user.getLastName());
			managedUser.setUpdatedAt(LocalDateTime.now());

			Address existingAddress = addressRepo.findByStreetAndCityAndStateAndZip(user.getAddress().getStreet(),
					user.getAddress().getCity(), user.getAddress().getState(), user.getAddress().getZip());

			if (existingAddress != null) {
				managedUser.setAddress(existingAddress);
			} else {
				addressRepo.saveAndFlush(user.getAddress());
			}

			return userRepo.save(managedUser);
		}

		return null;
	}

	@Override
	public boolean destroy(String username) {
		User user = userRepo.findByUsername(username);
		if (user != null) {
			user.setEnabled(false);
			userRepo.save(user);
			return true;
		}
		return false;
	}

	@Override
	public User enableUserAccount(String username, int userId) {
		User user = userRepo.findByUsername(username);
		User disabledUser = userRepo.searchById(userId);
		if (disabledUser != null) {
			disabledUser.setEnabled(true);
			return userRepo.save(disabledUser);
		}
		return null;
	}

	@Override
	public User disableUserAccount(String username, int userId) {
		User user = userRepo.findByUsername(username);
		User enabledUser = userRepo.searchById(userId);
		if (enabledUser != null) {
			enabledUser.setEnabled(false);
			return userRepo.save(enabledUser);
		}
		return null;
	}

}