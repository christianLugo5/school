package com.school.portal.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.school.portal.model.Address;

public interface AddressRepository extends JpaRepository<Address, Integer> {

	public List<Address> findAllByCityId(int cityId);

}
