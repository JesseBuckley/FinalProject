package com.skilldistillery.petconnectapp.entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

@Entity
public class Pet {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String name;
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;
	private String breed;
	@Column(name = "profile_picture")
	private String profilePicture;
	private String description;
	private boolean enabled;

	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;

	@JsonIgnore
	@OneToMany(mappedBy = "pet")
	private List<PetPicture> petPictures;

	@ManyToOne
	@JoinColumn(name = "species_id")
	private Species species;

	public Pet() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getBreed() {
		return breed;
	}

	public void setBreed(String breed) {
		this.breed = breed;
	}

	public String getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(String profilePicture) {
		this.profilePicture = profilePicture;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Species getSpecies() {
		return species;
	}

	public void setSpecies(Species species) {
		this.species = species;
	}

	public List<PetPicture> getPetPictures() {
		return petPictures;
	}

	public void setPetPictures(List<PetPicture> petPictures) {
		this.petPictures = petPictures;
	}

	public void addPetPicture(PetPicture petPicture) {
		if (petPictures == null) {
			petPictures = new ArrayList<>();
		}

		if (!petPictures.contains(petPicture)) {
			petPictures.add(petPicture);
		}

		if (petPicture.getPet() != null) {
			petPicture.getPet().removePetPicture(petPicture);

		}
		petPicture.setPet(this);
	}

	public void removePetPicture(PetPicture petPicture) {
		if (petPictures != null && petPictures.contains(petPicture)) {
			petPictures.remove(petPicture);
			petPicture.setPet(null);
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
		Pet other = (Pet) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Pet [id=" + id + ", name=" + name + ", dateOfBirth=" + dateOfBirth + ", breed=" + breed
				+ ", profilePicture=" + profilePicture + ", description=" + description + ", enabled=" + enabled
				+ ", user=" + user + ", petPictures=" + petPictures.size() + ", species=" + species + "]";
	}

}
