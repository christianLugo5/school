package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.CourseStudent;

public interface CourseStudentRepository extends JpaRepository<CourseStudent, Integer> {

}
