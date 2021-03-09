package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.AccessPassword;

public interface AccessPasswordRepository extends JpaRepository<AccessPassword, Integer> {

}
