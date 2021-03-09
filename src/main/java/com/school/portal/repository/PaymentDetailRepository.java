package com.school.portal.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.PaymentDetail;

public interface PaymentDetailRepository extends JpaRepository<PaymentDetail, Integer> {

}
