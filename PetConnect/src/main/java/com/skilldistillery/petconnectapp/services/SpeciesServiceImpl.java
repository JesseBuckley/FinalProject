package com.skilldistillery.petconnectapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skilldistillery.petconnectapp.entities.Species;
import com.skilldistillery.petconnectapp.repository.SpeciesRepository;

@Service
public class SpeciesServiceImpl implements SpeciesService {

	@Autowired
	private SpeciesRepository speciesRepo;

	@Override
	public List<Species> findAllSpecies() {
		return speciesRepo.findAll();
	}
}
