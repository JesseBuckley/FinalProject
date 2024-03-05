package com.skilldistillery.petconnectapp.services;

import com.skilldistillery.petconnectapp.entities.User;

public interface AuthService {

	User register(User user);

	User getUserByUsername(String username);
}
