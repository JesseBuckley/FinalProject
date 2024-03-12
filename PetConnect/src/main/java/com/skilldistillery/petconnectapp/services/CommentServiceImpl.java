package com.skilldistillery.petconnectapp.services;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.skilldistillery.petconnectapp.entities.Comment;
import com.skilldistillery.petconnectapp.entities.Post;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.exceptions.CustomSecurityException;
import com.skilldistillery.petconnectapp.exceptions.ResourceNotFoundException;
import com.skilldistillery.petconnectapp.repository.CommentRepository;
import com.skilldistillery.petconnectapp.repository.PostRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private CommentRepository comRepo;

	@Autowired
	private PostRepository postRepo;

	@Autowired
	private UserRepository userRepo;

	@Override
	public List<Comment> getAllCommentsForPost(int postId) {
		if (!postRepo.existsById(postId)) {
			return null;
		}
		return comRepo.findByPostId(postId);
	}

	@Override
	public Comment createComment(int postId, Comment comment, String username) {
		Post post = postRepo.findById(postId).orElse(null);
		User user = userRepo.findByUsername(username);

		if (post == null || user == null) {
			return null;
		}

		comment.setPost(post);
		comment.setUser(user);
		comment.setEnabled(true);

		return comRepo.save(comment);
	}

	public Comment createReply(int postId, int parentCommentId, Comment replyComment, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new CustomSecurityException("User not found");
		}

		Post post = postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post with id " + postId + " not found"));

		Comment parentComment = comRepo.findById(parentCommentId)
				.orElseThrow(() -> new ResourceNotFoundException("Comment with id " + parentCommentId + " not found"));

		replyComment.setUser(user);
		replyComment.setPost(post);
		replyComment.setReplyTo(parentComment);
		replyComment.setEnabled(true);

		return comRepo.save(replyComment);
	}

	@Override
	public Comment updateComment(int comId, Comment updatedComment, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			return null;
		}

		return comRepo.findById(comId).filter(comment -> comment.getUser().getId() == user.getId()).map(comment -> {
			comment.setContent(updatedComment.getContent());
			comment.setEnabled(true);
			return comRepo.save(comment);
		}).orElse(null);
	}

	@Override
	public boolean deleteComment(int postId, int commentId, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			return false;
		}

		return comRepo.findById(commentId)
				.filter(comment -> comment.getUser().getId() == user.getId() && comment.getPost().getId() == postId)
				.map(comment -> {
					comRepo.delete(comment);
					return true;
				}).orElse(false);
	}

}
