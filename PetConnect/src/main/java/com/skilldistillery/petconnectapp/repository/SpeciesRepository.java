package com.skilldistillery.petconnectapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Species;

public interface SpeciesRepository extends JpaRepository<Species, Integer> {

}
