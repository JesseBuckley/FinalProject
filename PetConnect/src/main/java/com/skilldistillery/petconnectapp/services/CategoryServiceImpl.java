package com.skilldistillery.petconnectapp.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Category;
import com.skilldistillery.petconnectapp.repository.CategoryRepository;

@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository catRepo;

	@Override
	public List<Category> getAllCategories() {
		return catRepo.findAll();
	}

}
