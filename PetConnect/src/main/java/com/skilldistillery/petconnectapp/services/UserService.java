package com.skilldistillery.petconnectapp.services;

import java.time.LocalDateTime;
import java.util.List;

import com.skilldistillery.petconnectapp.entities.User;

public interface UserService {
	List<User> index(String username);

	User show(String username, int userId);

	List<User> findUsersByUsername(String username);

	List<User> findUsersByRole(String role);

	List<User> findUsersByEnabled(boolean status);

	List<User> findUsersByCreatedAtAfter(LocalDateTime date);

	List<User> findUsersByName(String name);

	User update(int userId, User user, String username);

	boolean destroy(String username);
	
	User enableUserAccount(String username, int userId);

	User disableUserAccount(String username, int userId);
}
