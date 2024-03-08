package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.services.UserService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })
public class UserController {

	@Autowired
	private UserService userService;

	@GetMapping(path = { "users", "users/" })
	public List<User> findAllUsers(Principal principal, HttpServletResponse rsp) {
		List<User> users = userService.index(principal.getName());

		if (users == null) {
			rsp.setStatus(404);
		}

		return users;
	}

	@GetMapping("users/{userId}")
	public User findUser(@PathVariable("userId") int userId, Principal principal, @RequestBody User user,
			HttpServletResponse rsp) {
		User updatedUser;
		try {
			updatedUser = userService.update(userId, user, principal.getName());
			if (updatedUser == null) {
				rsp.setStatus(404);
			}
		} catch (Exception e) {
			rsp.setStatus(400);
			updatedUser = null;
		}
		return updatedUser;
	}

	@PutMapping("users/{userId}")
	public User updateUser(Principal principal, @PathVariable("userId") int userId, @RequestBody User user,
			HttpServletResponse rsp) {
		User updatedUser;
		try {
			updatedUser = userService.update(userId, user, principal.getName());
			if (updatedUser == null) {
				rsp.setStatus(404);
			}
		} catch (Exception e) {
			rsp.setStatus(400);
			updatedUser = null;
		}
		return updatedUser;
	}

	@DeleteMapping("users")
	public void deleteUser(Principal principal, HttpServletResponse rsp) {
		if (userService.destroy(principal.getName())) {
			rsp.setStatus(204);
		} else {
			rsp.setStatus(404);
		}

	}

	@PutMapping("users/{userId}/enable")
	public User enableUserAccount(@PathVariable("userId") int userId, Principal principal, HttpServletResponse rsp) {
		User disabledUser = userService.enableUserAccount(principal.getName(), userId);

		if (disabledUser == null) {
			rsp.setStatus(404);
		} else {
			rsp.setStatus(202);

		}

		return disabledUser;
	}

	@PutMapping("users/{userId}/disable")
	public User disableUserAccount(@PathVariable("userId") int userId, Principal principal, HttpServletResponse rsp) {
		User disabledUser = userService.disableUserAccount(principal.getName(), userId);

		if (disabledUser == null) {
			rsp.setStatus(404);
		} else {
			rsp.setStatus(202);

		}

		return disabledUser;
	}

}
