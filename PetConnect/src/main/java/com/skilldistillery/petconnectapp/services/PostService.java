package com.skilldistillery.petconnectapp.services;

import java.util.List;

import com.skilldistillery.petconnectapp.entities.Post;

public interface PostService {

	Post show(int postId, String username);

	List<Post> getPostsByCategory(int catId);

	List<Post> keywordSearch(String keyword);

	Post create(Post post, String username);

	Post update(int postId, Post post, String username);

	boolean softDeletePost(int postId, String username);

	List<Post> findPostsByUsersCity(String city, String username);

	List<Post> findPostsByUsersState(String state, String username);

	List<Post> findPostsByUsersZip(String zip, String username);

	List<Post> findPostsByUsername(String username);

	List<Post> showAllPosts();

}
