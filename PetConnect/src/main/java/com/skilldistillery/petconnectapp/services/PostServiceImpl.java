package com.skilldistillery.petconnectapp.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Category;
import com.skilldistillery.petconnectapp.entities.Comment;
import com.skilldistillery.petconnectapp.entities.Post;
import com.skilldistillery.petconnectapp.entities.User;
import com.skilldistillery.petconnectapp.exceptions.CustomSecurityException;
import com.skilldistillery.petconnectapp.exceptions.ResourceNotFoundException;
import com.skilldistillery.petconnectapp.repository.CategoryRepository;
import com.skilldistillery.petconnectapp.repository.CommentRepository;
import com.skilldistillery.petconnectapp.repository.PostRepository;
import com.skilldistillery.petconnectapp.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepository postRepo;
	@Autowired
	private CategoryRepository catRepo;
	@Autowired
	private CommentRepository comRepo;
	@Autowired
	private UserRepository userRepo;

	@Override
	public Post show(int postId, String username) {
		return postRepo.findByIdAndUser_Username(postId, username);
	}

	@Override
	public List<Post> getPostsByCategory(int catId) {

		if (!catRepo.existsById(catId)) {
			return null;
		}
		return postRepo.findByCategories_Id(catId);
	}

	@Override
	public List<Post> keywordSearch(String keyword) {

		return postRepo.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword);
	}

	@Override
	public List<Post> findPostsFromFollowedUsers(String username) {

		return postRepo.findPostsFromFollowedUsers(username);
	}

	@Override
	public Post create(Post post, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new ResourceNotFoundException("User not found");
		}

		post.setUser(user);
		post.setEnabled(true);

		List<Category> categoryList = post.getCategories();
		if (categoryList != null) {
			post.setCategories(new ArrayList<>());
			for (Category category : categoryList) {
				Category c = catRepo.searchById(category.getId());
				if (c != null) {
					post.addCategory(c);
				}
			}
		} else {
			post.setCategories(new ArrayList<>());
		}

		return postRepo.saveAndFlush(post);
	}

	@Override
	public Post update(int postId, Post post, String username) {
		Optional<Post> optionalPost = postRepo.findById(postId);
		if (!optionalPost.isPresent()) {
			throw new ResourceNotFoundException("Post with id " + postId + " not found.");
		}

		Post existingPost = optionalPost.get();
		if (!existingPost.getUser().getUsername().equals(username)) {
			throw new CustomSecurityException("Not authorized to update this post.");
		}

		existingPost.setContent(post.getContent());
		existingPost.setImageUrl(post.getImageUrl());
		existingPost.setTitle(post.getTitle());
		existingPost.setPinned(post.isPinned());
		existingPost.setEnabled(post.isEnabled());

		existingPost.getCategories().clear();

		List<Category> categoryList = existingPost.getCategories();
		existingPost.setCategories(new ArrayList<>());

		for (Category category : categoryList) {
			Category c = catRepo.searchById(category.getId());
			if (c != null) {
				existingPost.addCategory(c);
			}
		}

		return postRepo.saveAndFlush(existingPost);
	}

	@Override
	@Transactional
	public boolean softDeletePost(int postId, String username) {
		return postRepo.findById(postId).filter(post -> post.getUser().getUsername().equals(username)).map(post -> {
			post.setEnabled(false);
			if (post.getComments() != null) {
				post.getComments().forEach(comment -> {
					disableCommentAndSubComments(comment);
				});
			}
			postRepo.save(post);
			return true;
		}).orElse(false);
	}

	@Transactional
	public void disableCommentAndSubComments(Comment comment) {
		while (comment != null) {
			comment.setEnabled(false);
			comRepo.save(comment);
			comment = comment.getReplyTo();
		}
	}

	@Override
	public List<Post> findPostsByUsersCity(String city, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null || user.getAddress() == null) {
			throw new ResourceNotFoundException("User or user address not found");
		}

		List<Post> allPosts = postRepo.findAll();

		List<Post> filteredPosts = allPosts.stream().filter(post -> post.getUser() != null
				&& post.getUser().getAddress() != null && city.equals(post.getUser().getAddress().getCity()))
				.collect(Collectors.toList());

		return filteredPosts;
	}

	@Override
	public List<Post> findPostsByUsersState(String state, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null || user.getAddress() == null) {
			throw new ResourceNotFoundException("User or user address not found");
		}

		List<Post> allPosts = postRepo.findAll();

		List<Post> filteredPosts = allPosts.stream().filter(post -> post.getUser() != null
				&& post.getUser().getAddress() != null && state.equals(post.getUser().getAddress().getState()))
				.collect(Collectors.toList());

		return filteredPosts;
	}

	@Override
	public List<Post> findPostsByUsersZip(String zip, String username) {
		User user = userRepo.findByUsername(username);
		if (user == null || user.getAddress() == null) {
			throw new ResourceNotFoundException("User or user address not found");
		}

		List<Post> allPosts = postRepo.findAll();

		List<Post> filteredPosts = allPosts.stream().filter(post -> post.getUser() != null
				&& post.getUser().getAddress() != null && zip.equals(post.getUser().getAddress().getZip()))
				.collect(Collectors.toList());

		return filteredPosts;
	}

	@Override
	public List<Post> findPostsByUsername(String username) {
		return postRepo.findByUser_Username(username);
	}

	@Override
	public List<Post> showAllPosts() {
		return postRepo.findAll();
	}

}
