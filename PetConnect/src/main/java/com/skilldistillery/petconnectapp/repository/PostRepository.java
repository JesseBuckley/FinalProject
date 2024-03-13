package com.skilldistillery.petconnectapp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.skilldistillery.petconnectapp.entities.Post;

public interface PostRepository extends JpaRepository<Post, Integer> {
	Post searchById(int id);

	List<Post> findByCategories_Id(int catId);

	List<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyword, String contentKeyword);

	Post findByIdAndUser_Username(int postId, String username);

	List<Post> findByUser_Username(String username);

	@Query("SELECT p FROM Post p WHERE p.user IN (SELECT f.followedUsers FROM User f WHERE f.username = :username)")
    List<Post> findPostsFromFollowedUsers(@Param("username") String username);
}
