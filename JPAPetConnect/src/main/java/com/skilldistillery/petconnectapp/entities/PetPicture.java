package com.skilldistillery.petconnectapp.entities;

import java.time.LocalDateTime;
import java.util.Objects;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "pet_picture")
public class PetPicture {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "image_url")
	private String imageUrl;
	private String caption;
	@Column(name = "date_posted")
	@CreationTimestamp
	private LocalDateTime datePosted;

	@ManyToOne
	@JoinColumn(name = "pet_id")
	private Pet pet;

	public PetPicture() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public String getCaption() {
		return caption;
	}

	public void setCaption(String caption) {
		this.caption = caption;
	}

	public LocalDateTime getDatePosted() {
		return datePosted;
	}

	public void setDatePosted(LocalDateTime datePosted) {
		this.datePosted = datePosted;
	}

	public Pet getPet() {
		return pet;
	}

	public void setPet(Pet pet) {
		this.pet = pet;
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
		PetPicture other = (PetPicture) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "PetPicture [id=" + id + ", imageUrl=" + imageUrl + ", caption=" + caption + ", datePosted=" + datePosted
				+ ", pet=" + pet + "]";
	}

}
