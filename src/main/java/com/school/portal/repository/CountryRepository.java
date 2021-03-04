package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Country;

public interface CountryRepository extends JpaRepository<Country, Integer> {
	
}
