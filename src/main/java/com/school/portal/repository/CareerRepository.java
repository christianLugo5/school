package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.portal.model.Career;

public interface CareerRepository extends JpaRepository<Career, Integer> {

	@Modifying
	@Query(value = "DELETE FROM career_course WHERE career_fk = :careerId;", nativeQuery = true)
	public void deleteAllCourses(@Param(value = "careerId") int careerId);

	@Modifying
	@Query(value = "DELETE FROM career_course WHERE career_fk = :careerId AND course_fk = :courseId", nativeQuery = true)
	public void deleteCourseFromCareerById(@Param("careerId") int careerId, @Param("courseId") int courseId);

}
