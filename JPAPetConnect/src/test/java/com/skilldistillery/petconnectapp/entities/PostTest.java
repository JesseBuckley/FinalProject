package com.skilldistillery.petconnectapp.entities;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class PostTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Post post;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
		emf = Persistence.createEntityManagerFactory("JPAPetConnect");
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
		emf.close();
	}

	@BeforeEach
	void setUp() throws Exception {
		em = emf.createEntityManager();
		post = em.find(Post.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		post = null;
		em.close();
	}

	@Test
	void test_post_entity_mapping() {
		assertNotNull(post);
		assertEquals("this is a post", post.getContent());
		assertEquals("newtitle", post.getTitle());
	}

	@Test
	void test_post_has_one_user() {
		assertNotNull(post.getUser());
		assertEquals("admin", post.getUser().getUsername());
	}

	@Test
	void test_post_has_one_or_many_comments() {
		assertNotNull(post);
		assertEquals("this is a post", post.getContent());
	}
}
