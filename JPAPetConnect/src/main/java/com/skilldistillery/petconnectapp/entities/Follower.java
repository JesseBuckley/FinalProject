package com.skilldistillery.petconnectapp.entities;

import java.util.Objects;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Follower {
	
	@Id
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	@ManyToOne
	@JoinColumn(name = "followed_user_id")
	private User followedUser;

	public Follower() {
		super();
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public User getFollowedUser() {
		return followedUser;
	}

	public void setFollowedUser(User followedUser) {
		this.followedUser = followedUser;
	}

	@Override
	public int hashCode() {
		return Objects.hash(user);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Follower other = (Follower) obj;
		return Objects.equals(user, other.user);
	}


	
}
