package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Resource;

public interface ResourceService {

	Resource show(int resourceId);

	List<Resource> getResourcesByCategory(int resourceId);

	List<Resource> getByKeywordSearch(String keyword);

	Resource create(Resource resource, String username);

	Resource update(int resourceId, Resource resource, String username);

	boolean destroy(int resourceId, String username);

	List<Resource> findResourcesByUsersCity(String city, String username);

	List<Resource> findResourcesByUsersState(String state, String username);

	List<Resource> findResourcesByUsersZip(String zip, String username);

	List<Resource> showAllResources();

}
