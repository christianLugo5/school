package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.school.portal.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {
	
	@Modifying
	@Query(value = "DELETE FROM course_subject WHERE course_id = :courseId", nativeQuery = true)
	public void deleteAllCoursesSubjects(@Param("courseId") int courseId);
	
	@Modifying
	@Query(value = "DELETE FROM course_subject WHERE course_id = :courseId AND subject_id = :subjectId", nativeQuery = true)
	public void deleteCourseSubjectById(@Param("courseId") int courseId, @Param("subjectId") int subjectId);

}
