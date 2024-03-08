package com.skilldistillery.petconnectapp.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.entities.Category;
import com.skilldistillery.petconnectapp.services.CategoryService;

@RequestMapping("api")
@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class CategoryController {

	@Autowired
	private CategoryService catService;

	@GetMapping(path = { "categories", "categories/" })
	public List<Category> index() {
		return catService.getAllCategories();
	}

}
