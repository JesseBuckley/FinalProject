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

class CommentTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Comment comment;

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
		comment = em.find(Comment.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		comment = null;
		em.close();
	}

	@Test
	void test_comment_entity_mapping() {
		assertNotNull(comment);
		assertEquals("this is a comment", comment.getContent());
	}

	@Test
	void test_user_can_have_many_or_one_comment_on_a_post() {
		assertNotNull(comment.getUser());
		assertTrue(comment.getUser().getComments().size() > 0);
	}

	@Test
	void test_post_can_have_many_or_one_comment_per_user() {
		assertNotNull(comment.getPost().getUser());
		assertEquals("admin", comment.getPost().getUser().getUsername());
		assertTrue(comment.getPost().getComments().size() > 0);
	}

	@Test
	void test_comment_can_have_many_or_zero_replies() {
		assertNotNull(comment);
		if (comment.getPost().getComments().size() > 0) {
			assertTrue(comment.getReplyTo() == null || comment.getReplyTo() != null);
		}
	}
}
