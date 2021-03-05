package com.school.portal.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Positive;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
@Validated
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
	public EntityModel<Country> one(@Positive @PathVariable int id) {
		Country country = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found " + id));
		return assembler.toModel(country);
	}

	@PostMapping("/countries")
	public ResponseEntity<?> newCountry(@RequestBody Country country) {
		EntityModel<Country> entityModel = assembler.toModel(repository.save(country));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/countries/{id}")
	public ResponseEntity<?> replaceCountry(@RequestBody Country newCountry, @Positive @PathVariable int id) {
		Country updatedCountry = repository.findById(id).map(country -> {
			country = newCountry;
			return repository.save(country);
		}).orElseThrow(() -> new RuntimeException("Not found " + id));

		EntityModel<Country> entityModel = assembler.toModel(updatedCountry);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(updatedCountry);
	}

	@DeleteMapping("/countries/{id}")
	public ResponseEntity<?> deleteCountry(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ExceptionHandler
	public String constraintViolationHandler(ConstraintViolationException ex){
		return ex.getConstraintViolations().iterator().next()
                .getMessage();
	}

}
