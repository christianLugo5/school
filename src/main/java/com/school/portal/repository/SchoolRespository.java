package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.School;

public interface SchoolRespository extends CrudRepository<School, Integer> {
	
	public List<School> findAll();
	
	public School findById(int id);
	
	public List<School> findAllByAddressCityStateCountryId(int id);
	
	public List<School> findAllByAddressCityStateId(int id);
	
	public List<School> findAllByAddressCityId(int id);
	
}
