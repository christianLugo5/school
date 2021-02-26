package com.school.portal.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.school.portal.model.Subject;

public interface SubjectRepository extends CrudRepository<Subject, Integer> {
	
	public List<Subject> findAll();
	
	public Subject findById(int id);
	
}
