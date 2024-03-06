package com.skilldistillery.petconnectapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.repository.DirectMessageRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

	@Autowired
	private DirectMessageRepository dmRepo;

}
