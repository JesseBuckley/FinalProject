package com.skilldistillery.petconnectapp.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class DirectMessageTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private DirectMessage dm;

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
		dm = em.find(DirectMessage.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		dm = null;
		em.close();
	}
	
	/*  +----+---------+-----------+---------+-------------------+
		| id | content | create_at | user_id | receiving_user_id |
		+----+---------+-----------+---------+-------------------+
		|  1 | hi      | NULL      |       1 |                 2 |
		+----+---------+-----------+---------+-------------------+
	 */

	@Test
	void test_todo_entity_mapping() {
		assertNotNull(dm);
		assertEquals(1, dm.getUser().getId());
		assertEquals("hi", dm.getContent());
		assertEquals(2, dm.getReceivingUser().getId());

	}
}
