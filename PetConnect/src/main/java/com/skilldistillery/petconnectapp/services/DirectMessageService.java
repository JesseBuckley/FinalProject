package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.DirectMessage;
import com.skilldistillery.petconnectapp.entities.User;

public interface DirectMessageService {
	List<DirectMessage> findAll();
	DirectMessage findById(int dmId);
	DirectMessage create(DirectMessage directMessage, int followerId, String userName);
	DirectMessage update(DirectMessage directMessage, int id, String userName);
	boolean deleteByIdAndUserName(int dmId,  String userName);
}
