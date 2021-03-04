package com.school.portal.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Country;
import com.school.portal.model.assembler.CountryAssembler;
import com.school.portal.repository.CountryRepository;

@RestController
public class CountryController {

	private final CountryRepository repository;
	private final CountryAssembler assembler;

	public CountryController(CountryRepository countryRepository, CountryAssembler assembler) {
		this.repository = countryRepository;
		this.assembler = assembler;
	}

	@GetMapping("/countries")
	public CollectionModel<EntityModel<Country>> all() {
		List<EntityModel<Country>> countries = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(countries,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CountryController.class).all()).withSelfRel());
	}

	@GetMapping("/countries/{id}")
	public EntityModel<Country> one(@PathVariable int id) {
		Country country = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found " + id));
		return assembler.toModel(country);
	}

	@PostMapping("/countries")
	public ResponseEntity<?> newCountry(@RequestBody Country country) {
		EntityModel<Country> entityModel = assembler.toModel(repository.save(country));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/countries/{id}")
	public ResponseEntity<?> replaceCountry(@RequestBody Country newCountry, @PathVariable int id) {
		Country updatedCountry = repository.findById(id).map(country -> {
			country = newCountry;
			return repository.save(country);
		}).orElseThrow(() -> new RuntimeException("Not found " + id));

		EntityModel<Country> entityModel = assembler.toModel(updatedCountry);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(updatedCountry);
	}

	@DeleteMapping("/countries/{id}")
	public ResponseEntity<?> deleteCountry(@PathVariable int id) {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

}
