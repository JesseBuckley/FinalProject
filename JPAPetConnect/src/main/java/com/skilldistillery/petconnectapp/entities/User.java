package com.skilldistillery.petconnectapp.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class User {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String username;
	private String password;
	private boolean enabled;
	private String role;
	
	@Column(name = "profile_picture")
	private String profilePicture;
	
	
	private String biography;
	
	
	@Column(name = "background_picture")
	private String backgroundPicture;
	
	
	@Column(name = "first_name")
	private String firstName;
	
	
	@Column(name = "last_name")
	private String lastName;
	
	@Column(name = "created_at")
	@CreationTimestamp
	private LocalDateTime createdAt;
	
	@Column(name = "updated_at")
	@UpdateTimestamp
	private String updatedAt;
	
	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address addressId;
	
	@OneToMany(mappedBy = "user")
	private List<Follower> user;
	
	@OneToMany(mappedBy = "followedUser")
	private List<Follower> followedUser;
	
	
	public User() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getUsername() {
		return username;
	}


	public void setUsername(String username) {
		this.username = username;
	}


	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}


	public boolean isEnabled() {
		return enabled;
	}


	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}


	public String getRole() {
		return role;
	}


	public void setRole(String role) {
		this.role = role;
	}
	

	public String getProfilePicture() {
		return profilePicture;
	}


	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}


	public String getBiography() {
		return biography;
	}


	public void setBiography(String biography) {
		this.biography = biography;
	}


	public String getBackgroundPicture() {
		return backgroundPicture;
	}


	public void setBackgroundPicture(String backgroundPicture) {
		this.backgroundPicture = backgroundPicture;
	}


	public String getFirstName() {
		return firstName;
	}


	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}


	public String getLastName() {
		return lastName;
	}


	public void setLastName(String lastName) {
		this.lastName = lastName;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public String getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}


	public Address getAddressId() {
		return addressId;
	}


	public void setAddressId(Address addressId) {
		this.addressId = addressId;
	}


	public List<Follower> getUser() {
		return user;
	}


	public void setUser(List<Follower> user) {
		this.user = user;
	}


	public List<Follower> getFollowedUser() {
		return followedUser;
	}


	public void setFollowedUser(List<Follower> followedUser) {
		this.followedUser = followedUser;
	}


	@Override
	public int hashCode() {
		return Objects.hash(id);
	}


	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		User other = (User) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", enabled=" + enabled
				+ ", role=" + role + ", profilePicture=" + profilePicture + ", biography=" + biography
				+ ", backgroundPicture=" + backgroundPicture + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + "]";
	}
	
	
}
