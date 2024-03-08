package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Comment;

public interface CommentService {
	List<Comment> getAllCommentsForPost(int postId);

	Comment createComment(int postId, Comment comment, String username);

	Comment createReply(int postId, int parentComId, Comment replyComment, String username);
	
	Comment updateComment(int comId, Comment comment, String username);

	boolean deleteComment(int postId, int comId, String username);
}
