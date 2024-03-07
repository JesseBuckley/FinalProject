package com.skilldistillery.petconnectapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class UserTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private User user;

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
		user = em.find(User.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		user = null;
		em.close();
	}

	@Test
	void test_todo_entity_mapping() {
		assertNotNull(user);
		assertNotNull(user.getAddress());
		assertEquals(1, user.getAddress().getId());
		assertEquals(1, user.getId());
		assertEquals("admin", user.getUsername());
		assertEquals("$2a$10$nShOi5/f0bKNvHB8x0u3qOpeivazbuN0NE4TO0LGvQiTMafaBxLJS", user.getPassword());
		assertEquals(true, user.isEnabled());

	}

	@Test
	void test_comment_has_one_user_per_comment() {
		assertNotNull(user.getComments());
		assertTrue(user.getUsername() != null && user.getComments().size() > 0);
	}

	@Test
	void test_post_has_one_user_per_post() {
		assertNotNull(user.getPosts());
		assertTrue(user.getUsername() != null && user.getPosts().size() > 0);
	}
	@Test
	void test_User_has_Followers() {
		assertNotNull(user.getFollowedUsers());
		assertEquals("userone", user.getFollowers().get(0).getUsername());
	}
}
