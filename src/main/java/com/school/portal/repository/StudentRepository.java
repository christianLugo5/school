package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public List<Student> findAllByOrderByNameAsc();
	
}
