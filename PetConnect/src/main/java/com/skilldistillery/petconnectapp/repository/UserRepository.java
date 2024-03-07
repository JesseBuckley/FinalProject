package com.skilldistillery.petconnectapp.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	boolean existsByUsername(String username);

	List<User> findByUsernameContainingIgnoreCase(String username);

	List<User> findByRole(String role);

	List<User> findByEnabled(boolean status);

	List<User> findByCreatedAtAfter(LocalDateTime date);

	List<User> findByFirstNameContainingIgnoreCaseOrLastNameContainingIgnoreCase(String firstName, String lastName);

	User findByUsername(String username);

	User findByIdAndUsername(int userId, String username);
	
	User searchById(int userId);

}
