package com.school.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Address;
import com.school.portal.model.City;
import com.school.portal.repository.AddressRepository;
import com.school.portal.repository.CityRepository;

@RestController
public class AddressController {

	@Autowired
	AddressRepository addressRepository;
	@Autowired
	CityRepository cityRepository;

	@GetMapping("/country/{countryId}/state/{stateId}/city/{cityId}/address")
	public List<Address> getAllAddressByCityId(@PathVariable int countryId, @PathVariable int stateId,@PathVariable int cityId) {
		if(countryId > 0 && stateId > 0 && cityId > 0)
			return addressRepository.findAllByCityId(cityId); 
		return new ArrayList<Address>();
	};

	@GetMapping("/country/{countryId}/state/{stateId}/city/{cityId}/address/{id}")
	public ResponseEntity<Address> getAddressById(@PathVariable int countryId, @PathVariable int stateId,@PathVariable int cityId, 
			@PathVariable int id) {
		if(countryId > 0 && stateId > 0 && cityId > 0 && cityId > 0)
			return Optional.ofNullable(addressRepository.findById(id)).map(address -> ResponseEntity.ok().body(address))
				.orElseGet(() -> ResponseEntity.notFound().build());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/country/{countryId}/state/{stateId}/city/{cityId}/address")
	public void addAddress(@PathVariable int countryId, @PathVariable int stateId,@PathVariable int cityId, 
			@RequestBody Address address) {
		if (countryId > 0 && stateId > 0 && cityId > 0 && address != null) {
			address.setCity(cityRepository.findById(cityId));
			addressRepository.save(address);
		}			
	}

	@PutMapping("/country/{countryId}/state/{stateId}/city/{cityId}/address/{id}")
	public void updateAddress(@RequestBody Address address, @PathVariable int countryId, @PathVariable int stateId, 
			@PathVariable int cityId, @PathVariable int id) {
		if (countryId > 0 && stateId > 0 && cityId > 0 && id > 0 && id == address.getId()) {
			City city = cityRepository.findById(cityId);
			if (city != null) {
				address.setCity(city);
				addressRepository.save(address);
			}				
		}			
	}

	@DeleteMapping("/country/{countryId}/state/{stateId}/city/{cityId}/address/{id}")
	public void deleteAddress(@PathVariable int countryId, @PathVariable int stateId, @PathVariable int cityId, 
			@PathVariable int id) {
		if (countryId > 0 && stateId > 0 && cityId > 0 && id > 0)
			addressRepository.deleteById(id);
	}

}
