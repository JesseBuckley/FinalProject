package com.skilldistillery.petconnectapp.entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
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
	private LocalDateTime updatedAt;

	@ManyToOne
	@JoinColumn(name = "address_id")
	private Address address;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "follower", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "followed_user_id"))
	private List<User> followers;

	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "follower", joinColumns = @JoinColumn(name = "followed_user_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	private List<User> followedUsers;

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Post> posts = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Comment> comments = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<DirectMessage> messagesSent = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "receivingUser")
	private List<DirectMessage> receivedMessages = new ArrayList<>();

	@JsonIgnore
	@OneToMany(mappedBy = "user")
	private List<Pet> pets;

	public User() {
		super();
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
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

	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public List<User> getFollowers() {
		return followers;
	}

	public void setFollowers(List<User> followers) {
		this.followers = followers;
	}

	public List<User> getFollowedUsers() {
		return followedUsers;
	}

	public void setFollowedUsers(List<User> followedUsers) {
		this.followedUsers = followedUsers;
	}

	public List<DirectMessage> getMessagesSent() {
		return messagesSent;
	}

	public void setMessagesSent(List<DirectMessage> messagesSent) {
		this.messagesSent = messagesSent;
	}

	public List<DirectMessage> getReceivedMessages() {
		return receivedMessages;
	}

	public void setReceivedMessages(List<DirectMessage> receivedMessages) {
		this.receivedMessages = receivedMessages;
	}

	public List<Pet> getPets() {
		return pets;
	}

	public void setPets(List<Pet> pets) {
		this.pets = pets;
	}

	public void addPet(Pet pet) {
		if (pets == null) {
			pets = new ArrayList<>();
		}

		if (!pets.contains(pet)) {
			pets.add(pet);
		}

		if (pet.getUser() != null) {
			pet.getUser().removePet(pet);

		}
		pet.setUser(this);
	}

	public void removePet(Pet pet) {
		if (pets != null && pets.contains(pet)) {
			pets.remove(pet);
			pet.setUser(null);
		}
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
				+ ", createdAt=" + createdAt + ", updatedAt=" + updatedAt + ", address=" + address + ", followers="
				+ followers + ", followedUsers=" + followedUsers + ", posts=" + posts + ", comments=" + comments
				+ ", messagesSent=" + messagesSent + ", receivedMessages=" + receivedMessages + ", pets=" + pets + "]";
	}

}
