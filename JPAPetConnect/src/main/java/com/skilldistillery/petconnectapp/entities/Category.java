package com.skilldistillery.petconnectapp.entities;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;

@Entity
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String type;

	@JsonIgnore
	@OneToMany(mappedBy = "category")
	private List<Resource> resources;
	
	@JsonIgnore
	@ManyToMany
	@JoinTable(name = "post_has_category", 
	joinColumns = @JoinColumn(name = "category_id"), 
	inverseJoinColumns = @JoinColumn(name = "post_id"))
	private List<Post> posts;

	public Category() {

	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public List<Resource> getResources() {
		return resources;
	}

	public void setResources(List<Resource> resources) {
		this.resources = resources;
	}

	public List<Post> getPosts() {
		return posts;
	}

	public void setPosts(List<Post> posts) {
		this.posts = posts;
	}

	public void addResource(Resource resource) {
		if (resources == null) {
			resources = new ArrayList<>();
		}

		if (!resources.contains(resource)) {
			resources.add(resource);
			if (resource.getCategory() != null) {
				resource.getCategory().removeResource(resource);
			}
			resource.setCategory(this);
		}
	}

	public void removeResource(Resource resource) {
		if (resources != null && resources.contains(resource)) {
			resources.remove(resource);
			resource.setCategory(null);
		}
	}

	public void addPost(Post post) {
		if(posts == null) {
			posts = new ArrayList<>();
		}
		
		if(!posts.contains(post)) {
			posts.add(post);
			post.addCategory(this);
		}
	}
	
	public void removePost(Post post) {
		if(posts != null && posts.contains(post)) {
			posts.remove(post);
			post.removeCategory(this);
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
		Category other = (Category) obj;
		return id == other.id;
	}

	@Override
	public String toString() {
		return "Category [id=" + id + ", type=" + type + ", resources=" + resources.size() + ", posts=" + posts.size() + "]";
	}

}
