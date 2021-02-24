package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.Address;

public interface AddressRepository extends CrudRepository<Address, Integer> {
	
	public Address findById(int id);
	
	public List<Address> findAll();

	public List<Address> findAllByCityId(int cityId);

}
