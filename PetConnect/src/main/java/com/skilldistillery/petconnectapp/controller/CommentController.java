package com.skilldistillery.petconnectapp.controller;

import java.security.Principal;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.skilldistillery.petconnectapp.entities.Comment;
import com.skilldistillery.petconnectapp.services.CommentService;
import jakarta.servlet.http.HttpServletResponse;

@RequestMapping("api")
@RestController
@CrossOrigin({ "*", "http://localhost/" })
public class CommentController {

	@Autowired
	private CommentService comService;

	@GetMapping("{postId}/comments")
	public List<Comment> showCommentsForPost(@PathVariable("postId") int postId, HttpServletResponse rsp) {
		List<Comment> comments = comService.getAllCommentsForPost(postId);
		if (comments == null || comments.isEmpty()) {
			rsp.setStatus(404);
		}
		return comments;
	}

	@PostMapping("{postId}/comments")
	public Comment addComment(@PathVariable("postId") int postId, @RequestBody Comment comment, HttpServletResponse rsp,
			Principal principal) {
		Comment createdComment = comService.createComment(postId, comment, principal.getName());
		if (createdComment != null) {
			rsp.setStatus(201);
			return createdComment;
		} else {
			rsp.setStatus(400);
			return null;
		}
	}

	@PostMapping("{postId}/comments/{commentId}/reply")
	public Comment addReply(@PathVariable("postId") int postId, @PathVariable("commentId") int parentCommentId,
			@RequestBody Comment replyComment, HttpServletResponse rsp, Principal principal) {
		Comment createdReply = comService.createReply(postId, parentCommentId, replyComment, principal.getName());
		if (createdReply != null) {
			rsp.setStatus(201);
			return createdReply;
		} else {
			rsp.setStatus(400);
			return null;
		}
	}

	@PutMapping("{postId}/comments/{commentId}")
	public Comment updateComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
			@RequestBody Comment comment, HttpServletResponse rsp, Principal principal) {
		Comment updatedComment = comService.updateComment(commentId, comment, principal.getName());
		if (updatedComment != null) {
			rsp.setStatus(200);
			return updatedComment;
		} else {
			rsp.setStatus(404);
			return null;
		}
	}

	@DeleteMapping("{postId}/comments/{commentId}")
	public void deleteComment(@PathVariable("postId") int postId, @PathVariable("commentId") int commentId,
			HttpServletResponse rsp, Principal principal) {
		boolean deleted = comService.deleteComment(postId, commentId, principal.getName());
		if (deleted) {
			rsp.setStatus(204);
		} else {
			rsp.setStatus(404);
		}
	}
}
