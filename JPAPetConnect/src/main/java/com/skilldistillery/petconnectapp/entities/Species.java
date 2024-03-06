package com.skilldistillery.petconnectapp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Species {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String species;
	@Column(name = "image_url")
	private String imageUrl;

	@JsonIgnore
	@OneToMany(mappedBy = "species")
	private List<Pet> pets;

	public Species() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSpecies() {
		return species;
	}

	public void setSpecies(String species) {
		this.species = species;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
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

		if (pet.getSpecies() != null) {
			pet.getSpecies().removePet(pet);

		}
		pet.setSpecies(this);
	}

	public void removePet(Pet pet) {
		if (pets != null && pets.contains(pet)) {
			pets.remove(pet);
			pet.setSpecies(null);
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
		Species other = (Species) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Species [id=" + id + ", species=" + species + ", imageUrl=" + imageUrl + ", pets=" + pets.size() + "]";
	}

}
