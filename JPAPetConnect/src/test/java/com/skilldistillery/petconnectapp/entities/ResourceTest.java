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

public class ResourceTest {
	private static EntityManagerFactory emf;
	private EntityManager em;
	private Resource resource;

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
		resource = em.find(Resource.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		em.close();
	}

	@Test
	void test_Resource_entity_mapping() {
		assertNotNull(resource);
		assertEquals("dog clinic", resource.getName());

	}
	@Test
	void test_Resource_Category_entity_mapping() {
		assertNotNull(resource);
		assertEquals("Pet Health", resource.getCategory().getType());
		
	}
	@Test
	void test_Resource_Address_entity_mapping() {
		assertNotNull(resource);
		assertEquals("12345", resource.getAddress().getZip());
		
	}

}
