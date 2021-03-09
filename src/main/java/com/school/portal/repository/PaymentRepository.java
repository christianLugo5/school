package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Payment;

public interface PaymentRepository extends JpaRepository<Payment, Integer> {

}
