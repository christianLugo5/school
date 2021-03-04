package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.State;

public interface StateRepository extends JpaRepository<State, Integer> {
	
	List<State> findAllByCountryId(int id);
		
}
