package com.school.portal.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.hateoas.PagedModel;

import com.school.portal.model.Student;

public interface StudentRepository extends JpaRepository<Student, Integer> {
	
	public List<PagedModel<Student>> findAllByOrderByNameAsc();

	public List<Student> findAllByOrderByNameAsc(Pageable page);
	
	@Query("SELECT s FROM Student s WHERE s.name LIKE %:name%")
	public List<Student> findByNameContaining(@Param("name") String name);
	
	public List<Student> findTop10ByNameContaining(String name);
	
	public List<Student> findFirst10ByNameContaining(String name);
	
	@Query("SELECT s FROM Student s WHERE s.lastName LIKE %?1%")
	public List<Student> findByLastName(String lastName);
	
	@Query("SELECT COUNT(s.id) FROM Student s")
	public Integer findAllTotal();
	
	@Query("SELECT s FROM Student s LEFT JOIN FETCH s.address LEFT JOIN FETCH s.relative LEFT JOIN FETCH s.career WHERE s.id = ?1")
	public Optional<Student> findById(Integer id);
	
	public List<Student> findAllStudentByCourseStudentCourseTeacherTeacherId(int id);
	
}
