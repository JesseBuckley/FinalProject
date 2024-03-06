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

public class PetPictureTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private PetPicture pp;

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
		pp = em.find(PetPicture.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}

	@Test
	void test_PetPicture_entity_mapping() {
		assertNotNull(pp);
		assertEquals("i have a dog", pp.getCaption());

	}
	@Test
	void test_PetPicture_Pet_entity_mapping() {
		assertNotNull(pp);
		assertEquals("butterball", pp.getPet().getName());
		
	}

}
