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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.skilldistillery.petconnectapp.entities.DirectMessage;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.services.DirectMessageService;

import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("api")
@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class DirectMessageController {

	@Autowired
	private DirectMessageService dmService;

	@GetMapping("directMessages")
	public List<DirectMessage> findall() {
		return dmService.findAll();
	}

	@GetMapping("directMessages/{dmId}")
	public DirectMessage findDmId(@PathVariable("dmId") Integer dmId, HttpServletResponse res) {
		DirectMessage dm = dmService.findById(dmId);
		if (dm == null) {
			res.setStatus(404);
		}
		res.setStatus(200);
		return dm;

	}

	@PostMapping("users/{recipientId}/directMessages")
	public DirectMessage create(@RequestBody DirectMessage directMessage, Principal principal,
			@PathVariable("recipientId") Integer recipientId) {
		try {
			directMessage = dmService.create(directMessage, recipientId, principal.getName());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return directMessage;
	}

	@PutMapping("directMessages/{id}")
	public DirectMessage update(@RequestBody DirectMessage dm, @PathVariable("id") int id, HttpServletResponse res) {
		DirectMessage updated = dmService.update(dm, id);
		if (updated != null) {
			res.setStatus(200);
		} else {
			res.setStatus(400);
		}
		return updated;

	}

	@DeleteMapping("directMessages/{id}")
	public boolean delete(@PathVariable("id") int id, HttpServletResponse res) {
		boolean deleted;
		deleted = dmService.deleteById(id);

		if (deleted) {
			res.setStatus(204);
		} else {
			res.setStatus(400);
		}

		return deleted;
	}

}
