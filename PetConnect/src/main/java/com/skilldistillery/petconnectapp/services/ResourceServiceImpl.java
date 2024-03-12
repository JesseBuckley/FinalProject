package com.skilldistillery.petconnectapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Address;
import com.skilldistillery.petconnectapp.entities.Category;
import com.skilldistillery.petconnectapp.entities.Resource;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.exceptions.CustomSecurityException;
import com.skilldistillery.petconnectapp.exceptions.ResourceNotFoundException;
import com.skilldistillery.petconnectapp.repository.AddressRepository;
import com.skilldistillery.petconnectapp.repository.CategoryRepository;
import com.skilldistillery.petconnectapp.repository.ResourceRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class ResourceServiceImpl implements ResourceService {

	@Autowired
	private ResourceRepository resourceRepo;
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private AddressRepository addressRepo;
	@Autowired
	private CategoryRepository catRepo;

	@Override
	public Resource show(int resourceId) {
		Optional<Resource> resourceOpt = resourceRepo.findById(resourceId);
		if (resourceOpt.isPresent()) {
			return resourceOpt.get();
		}
		throw new ResourceNotFoundException("Resource with id " + resourceId + " not found.");
	}

	@Override
	public List<Resource> getResourcesByCategory(int categoryId) {
		return resourceRepo.findByCategoryId(categoryId);
	}

	@Override
	public List<Resource> getByKeywordSearch(String keyword) {
		return resourceRepo.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword);
	}

	@Override
	public Resource create(Resource resource, String username) {
	    User user = userRepo.findByUsername(username);
	    if (user == null) {
	        throw new ResourceNotFoundException("User not found");
	    }
	    Category category = catRepo.findById(resource.getCategory().getId())
	            .orElseThrow(() -> new ResourceNotFoundException("Category not found"));
	    resource.setCategory(category);

	    Address existingAddress = addressRepo.findByStreetAndCityAndStateAndZip(
	        resource.getAddress().getStreet(),
	        resource.getAddress().getCity(),
	        resource.getAddress().getState(),
	        resource.getAddress().getZip());

	    if (existingAddress != null) {
	        resource.setAddress(existingAddress);
	    } else {
	        addressRepo.saveAndFlush(resource.getAddress());
	    }

	    return resourceRepo.saveAndFlush(resource);
	}

	@Override
	public Resource update(int resourceId, Resource resource, String username) {
	    Optional<Resource> optionalResource = resourceRepo.findById(resourceId);
	    if (!optionalResource.isPresent()) {
	        throw new ResourceNotFoundException("Resource with id " + resourceId + " not found.");
	    }

	    Resource managedResource = optionalResource.get();
	    User user = userRepo.findByUsername(username);
	    if (!user.getUsername().equals(username)) {
	        throw new CustomSecurityException("Not authorized to update this resource.");
	    }

	    managedResource.setName(resource.getName());
	    managedResource.setDescription(resource.getDescription());
	    managedResource.setImageUrl(resource.getImageUrl());
	    managedResource.setCategory(resource.getCategory());

	    Address existingAddress = addressRepo.findByStreetAndCityAndStateAndZip(
	        resource.getAddress().getStreet(),
	        resource.getAddress().getCity(),
	        resource.getAddress().getState(),
	        resource.getAddress().getZip());

	    if (existingAddress != null) {
	        managedResource.setAddress(existingAddress);
	    } else {
	        addressRepo.saveAndFlush(resource.getAddress());
	    }

	    catRepo.saveAndFlush(managedResource.getCategory());

	    return resourceRepo.saveAndFlush(managedResource);
	}


	@Override
	@Transactional
	public boolean destroy(int resourceId, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}

		return resourceRepo.findById(resourceId).map(resource -> {
			Address address = resource.getAddress();
			boolean isAddressShared = isAddressShared(address.getId());

			if (!isAddressShared) {
				addressRepo.deleteById(address.getId());
			} else {
				resource.setAddress(null);
				resourceRepo.save(resource);
			}

			resourceRepo.deleteById(resourceId);
			return true;
		}).orElse(false);
	}

	private boolean isAddressShared(int addressId) {
		return resourceRepo.countByAddressId(addressId) > 1;
	}

	@Override
	public List<Resource> findResourcesByUsersCity(String city, String username) {

		return resourceRepo.findByAddress_City(city);
	}

	@Override
	public List<Resource> findResourcesByUsersState(String state, String username) {
		return resourceRepo.findByAddress_State(state);
	}

	@Override
	public List<Resource> findResourcesByUsersZip(String zip, String username) {
		return resourceRepo.findByAddress_Zip(zip);
	}

	@Override
	public List<Resource> showAllResources() {
		return resourceRepo.findAll();
	}

}
