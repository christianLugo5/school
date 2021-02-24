package com.school.portal.controller;

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

import com.school.portal.model.City;
import com.school.portal.model.State;
import com.school.portal.repository.CityRepository;
import com.school.portal.repository.StateRepository;

@RestController
public class CityController {
	
	@Autowired
	CityRepository cityRepository;
	@Autowired
	StateRepository StateRepository;
	
	@GetMapping("/country/{countryId}/state/{stateId}/city")
	public ResponseEntity<List<City>> getAllCitiesByStateId(@PathVariable int countryId,@PathVariable int stateId){
		if(countryId > 0 && stateId > 0)
			return Optional.ofNullable(cityRepository.findAllByStateId(stateId)).map(cityL -> ResponseEntity.ok().body(cityL))
					.orElseGet(() -> ResponseEntity.notFound().build());
		else
			return ResponseEntity.notFound().build();
	}
	
	@GetMapping("/country/{countryId}/state/{stateId}/city/{cityId}")
	public ResponseEntity<City> getCityById(@PathVariable int countryId, @PathVariable int stateId, @PathVariable int cityId){
		if(countryId > 0 && stateId > 0 && cityId > 0) {
			return Optional.ofNullable(cityRepository.findById(cityId)).map(city -> ResponseEntity.ok().body(city))
					.orElseGet(() -> ResponseEntity.notFound().build());
		}
		return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/country/{countryId}/state/{stateId}/city")
	public void addCity(@RequestBody City city, @PathVariable int countryId, @PathVariable int stateId) {
		if(countryId > 0 && stateId > 0 && city != null) {
			State state = StateRepository.findById(stateId);
			if(state != null) {
				city.setState(state);
				cityRepository.save(city);
			}
		}
	}
	
	@PutMapping("/country/{countryId}/state/{stateId}/city/{cityId}")
	public void updateCity(@RequestBody City city, @PathVariable int countryId, @PathVariable int stateId, @PathVariable int cityId) {
		if(countryId > 0 && stateId > 0 && cityId > 0 && city.getId() == cityId) {
			City temp = cityRepository.findById(cityId);
			if(temp != null) {
				city.setState(temp.getState());
				cityRepository.save(city);
			}
		}		
	}
	
	@DeleteMapping("/country/{countryId}/state/{stateId}/city/{cityId}")
	public void deleteCity(@PathVariable int countryId, @PathVariable int stateId, @PathVariable int cityId) {
		if(countryId > 0 && stateId > 0 && cityId > 0) {
			cityRepository.deleteById(cityId);
		}
	}
	
}
