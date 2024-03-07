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

public class AddressTest {

	private static EntityManagerFactory emf;
	private EntityManager em;
	private Address address;

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
		address = em.find(Address.class, 1);
	}

	@AfterEach
	void tearDown() throws Exception {
		address = null;
		em.close();
	}

//  1 | test street 123 | fakecity   | fakestate | 12345
	@Test
	void test_Address_entity_mapping() {
		assertNotNull(address);
		assertEquals(1, address.getId());
		assertEquals("test street 123", address.getStreet());
		assertEquals("fakecity", address.getCity());
		assertEquals("fakestate", address.getState());
		assertEquals("12345", address.getZip());

	}

	@Test
	void test_Address_User_entity_mapping() {
		assertNotNull(address);
		assertEquals("admin", address.getUsers().get(0).getUsername());

	}
	@Test
	void test_Address_Resource_entity_mapping() {
		assertNotNull(address);
		assertEquals("dog clinic", address.getResources().get(0).getName());
		
	}

}