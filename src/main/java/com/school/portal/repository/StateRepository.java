package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.State;

public interface StateRepository extends CrudRepository<State, Integer> {
	
	public State findById(int id);
	
	public List<State> findAll();
	
	public List<State> findAllByCountryId(int id);
		
}
