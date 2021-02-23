package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.City;

public interface CityRepository extends CrudRepository<City, Integer> {

	public City findById(int id);

	public List<City> findAll();

	public List<City> findAllByStateId(int stateId);

}
