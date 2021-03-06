package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Course;

public interface CourseRepository extends JpaRepository<Course, Integer> {

}
