package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.skilldistillery.petconnectapp.entities.Resource;
import com.skilldistillery.petconnectapp.services.ResourceService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost:4200" })
public class ResourceController {

	@Autowired
	private ResourceService resourceService;

	@GetMapping("resources")
	public List<Resource> index(HttpServletResponse resp) {
		List<Resource> resources = resourceService.showAllResources();
		if (resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}

	@GetMapping("resources/{resourceId}")
	public Resource showResource(@PathVariable("resourceId") int resourceId, HttpServletResponse resp) {
		Resource resource = resourceService.show(resourceId);
		if (resource == null) {
			resp.setStatus(404);
			return null;
		}
		return resource;
	}

	@PostMapping("resources")
	public Resource createResource(@RequestBody Resource resource, HttpServletResponse resp, Principal principal) {
		try {
			resource = resourceService.create(resource, principal.getName());
			resp.setStatus(201);
			return resource;
		} catch (Exception e) {
			resp.setStatus(400);
			return null;
		}
	}

	@PutMapping("resources/{resourceId}")
	public Resource updateResource(@PathVariable("resourceId") int resourceId, @RequestBody Resource resource,
			HttpServletResponse resp, Principal principal) {
		try {
			resource = resourceService.update(resourceId, resource, principal.getName());
			if (resource != null) {
				resp.setStatus(200);
				return resource;
			} else {
				resp.setStatus(404);
				return null;
			}
		} catch (Exception e) {
			resp.setStatus(403);
			return null;
		}
	}

	@DeleteMapping("resources/{resourceId}")
	public void deleteResource(@PathVariable("resourceId") int resourceId, HttpServletResponse resp,
			Principal principal) {
		try {
			boolean deleted = resourceService.destroy(resourceId, principal.getName());
			if (deleted) {
				resp.setStatus(204);
			} else {
				resp.setStatus(404);
			}
		} catch (Exception e) {
			resp.setStatus(403);
		}
	}

	@GetMapping("resources/category/{categoryId}")
	public List<Resource> getResourcesByCategory(@PathVariable("categoryId") int categoryId, HttpServletResponse resp) {
		List<Resource> resources = resourceService.getResourcesByCategory(categoryId);
		if (resources.isEmpty() || resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}

	@GetMapping("resources/search/{keyword}")
	public List<Resource> searchByKeyword(@PathVariable("keyword") String keyword, HttpServletResponse resp) {
		List<Resource> resources = resourceService.getByKeywordSearch(keyword);
		if (resources == null || resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}

	@GetMapping("resources/city/{city}")
	public List<Resource> findResourcesByCity(@PathVariable("city") String city, HttpServletResponse resp,
			Principal principal) {
		String username = principal.getName();
		List<Resource> resources = resourceService.findResourcesByUsersCity(city, username);
		if (resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}

	@GetMapping("resources/state/{state}")
	public List<Resource> findResourcesByState(@PathVariable("state") String state, Principal principal,
			HttpServletResponse resp) {

		String username = principal.getName();
		List<Resource> resources = resourceService.findResourcesByUsersState(state, username);
		if (resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}

	@GetMapping("resources/zip/{zip}")
	public List<Resource> findResourcesByZip(@PathVariable("zip") String zip, Principal principal,
			HttpServletResponse resp) {
		String username = principal.getName();

		List<Resource> resources = resourceService.findResourcesByUsersZip(zip, username);
		if (resources.isEmpty()) {
			resp.setStatus(404);
		}
		return resources;
	}
}
