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

import com.skilldistillery.petconnectapp.entities.Post;
import com.skilldistillery.petconnectapp.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api")
@CrossOrigin({ "*", "http://localhost/" })
public class PostController {

	@Autowired
	private PostService postService;

	@GetMapping("posts")
	public List<Post> index(HttpServletResponse resp) {
		List<Post> posts = postService.showAllPosts();
		if (posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/{postId}")
	public Post showPost(Principal principal, @PathVariable("postId") int postId, HttpServletResponse rsp) {
		Post post = postService.show(postId, principal.getName());

		if (post == null) {
			rsp.setStatus(404);
		}
		return post;
	}
	
	@GetMapping("posts/followed")
    public List<Post> getPostsFromFollowedUsers(Principal principal, HttpServletResponse response) {
        String username = principal.getName();
        List<Post> posts = postService.findPostsFromFollowedUsers(username);
        if (posts.isEmpty()) {
            response.setStatus(204); 
            return null; 
        }
        return posts;
    }

	@PostMapping("posts")
	public Post createPost(@RequestBody Post post, HttpServletResponse resp, Principal principal) {
		try {
			Post createdPost = postService.create(post, principal.getName());
			resp.setStatus(201);
			return createdPost;
		} catch (Exception e) {
			e.printStackTrace();
			resp.setStatus(503);
			return null;
		}
	}

	@PutMapping("posts/{postId}")
	public Post updatePost(@PathVariable("postId") int postId, @RequestBody Post post, HttpServletResponse resp,
			Principal principal) {
		try {
			Post updatedPost = postService.update(postId, post, principal.getName());
			resp.setStatus(202);
			return updatedPost;
		} catch (Exception e) {
			resp.setStatus(403);
			return null;
		}
	}

	@DeleteMapping("posts/{postId}")
	public void deletePost(@PathVariable("postId") int postId, HttpServletResponse resp, Principal principal) {
		try {
			boolean deleted = postService.softDeletePost(postId, principal.getName());
			if (deleted) {
				resp.setStatus(204);
			} else {
				resp.setStatus(400);
			}
		} catch (Exception e) {
			resp.setStatus(403);
		}
	}

	@GetMapping("categories/{catId}/posts")
	public List<Post> getPostsForCategory(@PathVariable("catId") int catId, HttpServletResponse resp) {
		List<Post> posts = postService.getPostsByCategory(catId);
		if (posts == null || posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/search/{keyword}")
	public List<Post> searchByKeyword(@PathVariable("keyword") String keyword, HttpServletResponse resp) {
		List<Post> posts = postService.keywordSearch(keyword);
		if (posts == null || posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/city/{city}")
	public List<Post> getPostsByUsersInSameCity(@PathVariable("city") String city, HttpServletResponse resp,
			Principal principal) {
		String username = principal.getName();
		List<Post> posts = postService.findPostsByUsersCity(city, username);
		if (posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/state/{state}")
	public List<Post> getPostsByUsersInSameState(@PathVariable("state") String state, HttpServletResponse resp,
			Principal principal) {
		String username = principal.getName();
		List<Post> posts = postService.findPostsByUsersState(state, username);
		if (posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/zip/{zip}")
	public List<Post> getPostsByUsersInSameZip(@PathVariable("zip") String zip, HttpServletResponse resp,
			Principal principal) {
		String username = principal.getName();
		List<Post> posts = postService.findPostsByUsersZip(zip, username);
		if (posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

	@GetMapping("posts/username/{username}")
	public List<Post> getPostsByUsername(@PathVariable("username") String username, HttpServletResponse resp) {
		List<Post> posts = postService.findPostsByUsername(username);
		if (posts == null || posts.isEmpty()) {
			resp.setStatus(404);
		}
		return posts;
	}

}
