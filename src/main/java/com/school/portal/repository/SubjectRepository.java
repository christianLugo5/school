package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Subject;

public interface SubjectRepository extends JpaRepository<Subject, Integer> {

}
