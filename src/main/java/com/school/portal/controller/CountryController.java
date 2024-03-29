package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.slf4j.Logger;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.school.portal.model.Country;
import com.school.portal.model.assembler.CountryAssembler;
import com.school.portal.repository.CountryRepository;

@RestController
@Validated
@RequestMapping("/countries")
public class CountryController {

	private final CountryRepository repository;
	private final CountryAssembler assembler;
	
	private final static Logger logger = org.slf4j.LoggerFactory.getLogger(CountryController.class);

	public CountryController(CountryRepository countryRepository, CountryAssembler assembler) {
		this.repository = countryRepository;
		this.assembler = assembler;
	}

	@GetMapping
	public CollectionModel<EntityModel<Country>> all() {
		List<EntityModel<Country>> countries = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());

		return CollectionModel.of(countries,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CountryController.class).all()).withSelfRel());
	}

	@GetMapping("/{id}")
	public EntityModel<Country> one(@Positive @PathVariable int id) {
		return repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not found " + id));
	}

	@PostMapping
	public ResponseEntity<?> newCountry(@Valid @RequestBody Country country) {
		EntityModel<Country> entityModel = assembler.toModel(repository.save(country));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> replaceCountry(@Valid @RequestBody Country newCountry, @Positive @PathVariable int id) {
		Country updatedCountry = repository.findById(id).map(country -> {
			country = newCountry;
			return repository.save(country);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));

		EntityModel<Country> entityModel = assembler.toModel(updatedCountry);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(updatedCountry);
	}

	@DeleteMapping("/{id}")
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
