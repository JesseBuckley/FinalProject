package com.skilldistillery.petconnectapp.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.DirectMessage;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.repository.DirectMessageRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class DirectMessageServiceImpl implements DirectMessageService {

	@Autowired
	private DirectMessageRepository dmRepo;

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public List<DirectMessage> findAll() {
		return dmRepo.findAll();
	}

	@Override
	public DirectMessage findById(int dmId) {
		return dmRepo.findById(dmId);
	}

	@Override
	public DirectMessage create(DirectMessage directMessage, int followerID, String userName) {
	User sender = userRepo.findByUsername(userName);
	Optional<User> opt = userRepo.findById(followerID);
	
		if(sender != null && opt.isPresent()) {
			directMessage.setUser(sender);
			directMessage.setReceivingUser(opt.get());
			return dmRepo.save(directMessage);
		}
		return null;
		
	}

	@Override
	public DirectMessage update(DirectMessage directMessage, int id, String userName) {
		User sender = userRepo.findByUsername(userName);
		if(sender != null) {
		DirectMessage original = dmRepo.findById(id);
		original.setContent(directMessage.getContent());
		return dmRepo.save(original);
		}
		return null;
		
	}

	@Override
	public boolean deleteByIdAndUserName(int dmId, String userName) {
		boolean deleted = false;
		User sender = userRepo.findByUsername(userName);
		if(sender != null) {
			
			if (dmRepo.existsById(dmId)) {
				dmRepo.deleteById(dmId);
				deleted = true;
				
				return deleted;
			}
		}
		return false;

	}

	

}
