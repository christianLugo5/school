package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.City;

public interface CityRepository extends JpaRepository<City, Integer> {

	public List<City> findAllByStateId(int stateId);

}
