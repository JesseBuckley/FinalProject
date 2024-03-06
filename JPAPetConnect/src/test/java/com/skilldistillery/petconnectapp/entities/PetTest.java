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

public class PetTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Pet pet;

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
		pet = em.find(Pet.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}

	@Test
	void test_Pet_entity_mapping() {
		assertNotNull(pet);
		assertEquals("butterball", pet.getName());

	}

	@Test
	void test_Pet_User_entity_mapping() {
		assertNotNull(pet);
		assertEquals("admin", pet.getUser().getUsername());

	}

	@Test
	void test_Pet_PetPicture_entity_mapping() {
		assertNotNull(pet);
		assertEquals("i have a dog", pet.getPetPictures().get(0).getCaption());

	}

	@Test
	void test_Pet_Species_entity_mapping() {
		assertNotNull(pet);
		assertEquals("dog", pet.getSpecies().getSpecies());

	}

}
