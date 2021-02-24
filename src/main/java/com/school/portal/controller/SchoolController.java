package com.school.portal.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;
import com.school.portal.model.Address;
import com.school.portal.model.School;
import com.school.portal.repository.AddressRepository;
import com.school.portal.repository.SchoolRespository;
import com.school.portal.util.Views;

@RestController
public class SchoolController {
	
	@Autowired
	SchoolRespository schoolRepository;
	@Autowired
	AddressRepository addressRepository;
	
	@GetMapping("/school-filter")
	public MappingJacksonValue getAllSchoolsUsingFilter(){
		List<School> schools = schoolRepository.findAll();		
		SimpleBeanPropertyFilter filter = SimpleBeanPropertyFilter.filterOutAllExcept("address");
		FilterProvider filters = new SimpleFilterProvider().addFilter("SchoolFilter", filter);
		MappingJacksonValue mapping = new MappingJacksonValue(schools);
	    mapping.setFilters(filters);
	    
		return mapping;
	}	
	
	@JsonView(Views.Simple.class)
	@GetMapping("/school")
	public List<School> getAllSchools(@RequestParam(required = false) Integer countryId, 
			@RequestParam(required = false) Integer stateId, @RequestParam(required = false) Integer cityId){
		if(countryId != null && stateId == null && cityId == null)
			return countryId > 0 ? schoolRepository.findAllByAddressCityStateCountryId(countryId) : new ArrayList<School>();
		if(stateId != null && cityId == null)
			return stateId > 0 ? schoolRepository.findAllByAddressCityStateId(stateId) : new ArrayList<School>();
		if(cityId != null)
			return cityId > 0 ? schoolRepository.findAllByAddressCityId(cityId) : new ArrayList<School>();
		return schoolRepository.findAll(); 		
	}
	
	@JsonView(Views.Detailed.class)
	@GetMapping("/school/{id}")
	public ResponseEntity<School> getSchoolById(@PathVariable int id){
		if(id > 0)
			return Optional.ofNullable(schoolRepository.findById(id)).map(school -> ResponseEntity.ok().body(school))
					.orElseGet(() -> ResponseEntity.notFound().build());
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/school")
	public void addSchool(@RequestBody School school, @RequestParam int addressId) {
		if(school != null) {
			Address address = addressRepository.findById(addressId);
			school.setAddress(address);
			schoolRepository.save(school);
		}			
	}
	
	@PutMapping("/school/{id}")
	public void updateSchool(@RequestBody School school, @PathVariable int id) {
		if(id > 0 && id == school.getId()) {
			School temp = schoolRepository.findById(id);
			if(temp != null) {
				school.setAddress(temp.getAddress());
				schoolRepository.save(school);
			}			
		}			
	}
	
	@DeleteMapping("/school/{id}")
	public void deleteSchoolById(@PathVariable int id) {
		if(id > 0)
			schoolRepository.deleteById(id);
	}
	
}
