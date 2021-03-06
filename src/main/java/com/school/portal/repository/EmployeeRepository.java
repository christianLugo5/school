package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Integer> {

}
