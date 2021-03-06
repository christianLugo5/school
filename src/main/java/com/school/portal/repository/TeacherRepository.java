package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Teacher;

public interface TeacherRepository extends JpaRepository<Teacher, Integer> {

}
