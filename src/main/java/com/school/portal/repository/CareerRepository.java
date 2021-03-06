package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Career;

public interface CareerRepository extends JpaRepository<Career, Integer> {

}
