package com.school.portal.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.school.portal.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public List<Student> findAllByOrderByNameAsc();

	public List<Student> findAllByOrderByNameAsc(Pageable page);
	
	public List<Student> findByNameContaining(String name);
	
	@Query("SELECT s FROM Student s WHERE s.lastName LIKE %?1%")
	public List<Student> findByLastName(String lastName);
	
	@Query("SELECT COUNT(s.id) FROM Student s")
	public Integer findAllTotal();
	
}
