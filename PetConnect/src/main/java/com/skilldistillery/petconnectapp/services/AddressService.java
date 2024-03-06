package com.skilldistillery.petconnectapp.services;

import com.skilldistillery.petconnectapp.entities.Address;

public interface AddressService {

	Address show(int id);

	Address create(Address address);

	Address update(Address address, int id);

	boolean delete(int id);

}
