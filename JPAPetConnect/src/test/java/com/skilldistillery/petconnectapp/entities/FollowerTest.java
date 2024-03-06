package com.skilldistillery.petconnectapp.entities;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

class FollowerTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Follower follower;
	
	
	
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
	    follower = em.find(Follower.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
	   follower = null;
		em.close();
	}
	
	@Test
	void test_todo_entity_mapping() {
		assertNotNull(follower);
		assertEquals(1, follower.getUser().getId());
	    assertEquals(2, follower.getFollowedUser().getId());  
				
		}

	
}
