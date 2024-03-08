package com.skilldistillery.petconnectapp.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.skilldistillery.petconnectapp.entities.Address;
import com.skilldistillery.petconnectapp.repository.AddressRepository;

@Service
public class AddressServiceImpl implements AddressService {

	@Autowired
	private AddressRepository addressRepo;

	@Override
	public Address show(int id) {
		return addressRepo.searchById(id);

	}

	@Override
	public Address create(Address address) {
		return addressRepo.save(address);
	}

	@Override
	public Address update(Address address, int id) {
		Address original = addressRepo.searchById(id);

		original.setStreet(address.getStreet());
		original.setCity(address.getCity());
		original.setState(address.getState());
		original.setZip(address.getZip());

		return addressRepo.save(address);
	}

	@Override
	public boolean delete(int id) {
		addressRepo.deleteById(id);
		return !addressRepo.existsById(id);
	}

}
