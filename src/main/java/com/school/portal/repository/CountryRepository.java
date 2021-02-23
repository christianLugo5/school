package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.Country;

public interface CountryRepository extends CrudRepository<Country, Integer> {
	
	public Country findById(int id);
	
	public List<Country> findAll();
	
}
