package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Relative;
import com.school.portal.model.Student;

public interface RelativeRepository extends JpaRepository<Relative, Integer> {
	
	public List<Student> findAllStudentById(Integer id);
	
}
