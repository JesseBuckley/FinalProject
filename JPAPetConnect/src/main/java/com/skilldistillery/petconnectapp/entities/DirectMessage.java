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
@Table(name="direct_message")
public class DirectMessage {


	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String content;
	
	@CreationTimestamp
	@Column(name = "create_at")
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn(name = "user_id")
	private User user;
	

	@ManyToOne
	@JoinColumn(name = "receiving_user_id")
	private User receivingUser;


	public DirectMessage() {
		super();
	}


	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getContent() {
		return content;
	}


	public void setContent(String content) {
		this.content = content;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public User getReceivingUser() {
		return receivingUser;
	}


	public void setReceivingUser(User receivingUser) {
		this.receivingUser = receivingUser;
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
		DirectMessage other = (DirectMessage) obj;
		return id == other.id;
	}


	@Override
	public String toString() {
		return "DirectMessage [id=" + id + ", content=" + content + ", createdAt=" + createdAt + ", user=" + user
				+ ", receivingUser=" + receivingUser + "]";
	}

	
	
}
