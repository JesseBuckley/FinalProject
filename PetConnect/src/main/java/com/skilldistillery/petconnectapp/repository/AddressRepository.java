package com.skilldistillery.petconnectapp.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.skilldistillery.petconnectapp.entities.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	Address searchById(int id);
	
	Address findByStreetAndCityAndStateAndZip(String street, String city, String state, String zip);


}
