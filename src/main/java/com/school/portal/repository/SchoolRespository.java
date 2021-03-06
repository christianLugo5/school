package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.School;

public interface SchoolRespository extends JpaRepository<School, Integer> {	
	
	public List<School> findAllByAddressCityStateCountryId(int id);
	
	public List<School> findAllByAddressCityStateId(int id);
	
	public List<School> findAllByAddressCityId(int id);
	
}
