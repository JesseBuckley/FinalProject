package com.skilldistillery.petconnectapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.User;

public interface DirectMessageRepository extends JpaRepository<User, Integer> {

}
