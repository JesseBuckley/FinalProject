package com.skilldistillery.petconnectapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Comment;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
	Comment searchById(int id);

	List<Comment> findByPostId(int postId);

	Comment findByIdAndPostId(int postId, int commentId);
	
	Comment deleteByPostId(int postId);
}
