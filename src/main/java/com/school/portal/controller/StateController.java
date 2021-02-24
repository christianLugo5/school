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

import com.school.portal.model.Country;
import com.school.portal.model.State;
import com.school.portal.repository.CountryRepository;
import com.school.portal.repository.StateRepository;

@RestController
public class StateController {

	@Autowired
	private StateRepository stateRepository;
	@Autowired
	private CountryRepository countryRepository;

	@GetMapping("/country/{id}/state")
	public ResponseEntity<List<State>> getAllStatesByCountryId(@PathVariable int id) {
		if(id > 0 )
			return Optional.ofNullable(stateRepository.findAllByCountryId(id)).map(state -> ResponseEntity.ok().body(state))
				.orElseGet(() -> ResponseEntity.notFound().build());
		else
			return ResponseEntity.notFound().build();
	}

	@GetMapping("/country/{countryId}/state/{stateId}")
	public ResponseEntity<State> getStateById(@PathVariable int countryId, @PathVariable int stateId) {
		if(stateId > 0)
			return Optional.ofNullable(stateRepository.findById(stateId)).map(state -> ResponseEntity.ok().body(state))
				.orElseGet(() -> ResponseEntity.notFound().build());
		else
			return ResponseEntity.notFound().build();
	}

	@PostMapping("/country/{id}/state")
	public void addState(@RequestBody State state, @PathVariable int id) {
		if (id > 0 && state != null) {
			Country country = countryRepository.findById(id);
			if (country != null) {
				state.setCountry(country);
				stateRepository.save(state);
			}
		}
	}

	@PutMapping("/country/{countryId}/state/{stateId}")
	public void updateState(@RequestBody State state, @PathVariable int countryId, @PathVariable int stateId) {
		if (countryId > 0 && stateId > 0 && state.getId() == stateId) {
			State temp = stateRepository.findById(stateId);
			if (temp != null) {
				state.setCountry(temp.getCountry());
				stateRepository.save(state);
			}
		}
	}

	@DeleteMapping("/country/{countryId}/state/{stateId}")
	public void deleteState(@PathVariable int countryId, @PathVariable int stateId) {
		if (countryId > 0 && stateId > 0)
			stateRepository.deleteById(stateId);
	}

}
