package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.portal.model.Career;
import com.school.portal.model.Course;

public interface CareerRepository extends JpaRepository<Career, Integer> {

	@Modifying
	@Query(value = "DELETE FROM career_course WHERE career_id = :careerId;", nativeQuery = true)
	public void deleteAllCourses(@Param(value = "careerId") int careerId);

	@Modifying
	@Query(value = "DELETE FROM career_course WHERE career_id = :careerId AND course_id = :courseId", nativeQuery = true)
	public void deleteCourseFromCareerById(@Param("careerId") int careerId, @Param("courseId") int courseId);

	public List<Course> findAllCourseById(int id);
	
	public List<Career> findAllCareerByStudentId(int id);

}
