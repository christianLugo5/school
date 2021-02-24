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
import com.school.portal.repository.CountryRepository;

@RestController
public class CountryController {

	@Autowired
	private CountryRepository countryRepository;

	@GetMapping("/country")
	public List<Country> getAllCountries() {
		return countryRepository.findAll();
	}

	@GetMapping("/country/{id}")
	public ResponseEntity<Country> getCountryById(@PathVariable int id) {
		return Optional.ofNullable(countryRepository.findById(id)).map(country -> ResponseEntity.ok().body(country)) // 200 OK
				.orElseGet(() -> ResponseEntity.notFound().build()); // 404 Not found
	}

	@PostMapping("/country/add")
	public void addCountry(@RequestBody Country country) {
		if (country != null)
			countryRepository.save(country);
	}

	@PutMapping("/country/{id}")
	public void updateCountryById(@RequestBody Country country, @PathVariable int id) {
		if (id > 0 && id == country.getId()) {
			if (countryRepository.existsById(id)) {
				countryRepository.save(country);
			}
		}
	}

	@DeleteMapping("/country/{id}")
	public void deleteCountryById(@PathVariable int id) {
		if (id > 0)
			countryRepository.deleteById(id);
	}

}
