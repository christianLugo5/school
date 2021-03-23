package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.School;
import com.school.portal.model.assembler.SchoolAssembler;
import com.school.portal.repository.SchoolRespository;

@RestController
@Validated
public class SchoolController {

	@Autowired
	SchoolRespository repository;
	@Autowired
	SchoolAssembler assembler;

	@GetMapping("/schools")
	public ResponseEntity<CollectionModel<EntityModel<School>>> all(@RequestParam(required = false) Integer countryId,
			@RequestParam(required = false) Integer stateId, @RequestParam(required = false) Integer cityId) {
		if (countryId != null && stateId == null && cityId == null) {
			if (countryId < 1)
				return ResponseEntity.noContent().build();
			List<EntityModel<School>> entityModel = repository.findAllByAddressCityStateCountryId(countryId).stream()
					.map(assembler::toModel).collect(Collectors.toList());
			return ResponseEntity.ok(CollectionModel.of(entityModel,
					WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(SchoolController.class).all(countryId, null, null))
							.withSelfRel()));
		}
		if (stateId != null && cityId == null) {
			if (stateId < 1)
				return ResponseEntity.noContent().build();
			List<EntityModel<School>> entityModel = repository.findAllByAddressCityStateId(stateId).stream()
					.map(assembler::toModel).collect(Collectors.toList());
			return ResponseEntity.ok(CollectionModel.of(entityModel,
					WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(SchoolController.class).all(countryId, stateId, null))
							.withSelfRel()));
		}
		if (cityId != null) {
			if (cityId < 1)
				return ResponseEntity.noContent().build();
			List<EntityModel<School>> entityModel = repository.findAllByAddressCityId(cityId).stream()
					.map(assembler::toModel).collect(Collectors.toList());
			return ResponseEntity.ok(CollectionModel.of(entityModel,
					WebMvcLinkBuilder
							.linkTo(WebMvcLinkBuilder.methodOn(SchoolController.class).all(countryId, stateId, cityId))
							.withSelfRel()));
		}
		List<EntityModel<School>> entityModel = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModel, WebMvcLinkBuilder
				.linkTo(WebMvcLinkBuilder.methodOn(SchoolController.class).all(null, null, null)).withSelfRel()));
	}

	@GetMapping("/schools/{id}")
	public EntityModel<School> one(@Positive @PathVariable int id) {
		School school = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return assembler.toModel(school);
	}

	@PostMapping("/schools")
	public ResponseEntity<?> newSchool(@Valid @RequestBody School school) {
		EntityModel<School> entityModel = assembler.toModel(repository.save(school));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/schools/{id}")
	public ResponseEntity<?> replaceSchool(@Valid @RequestBody School newSchool, @Positive @PathVariable int id) {
		if(newSchool.getId() != id)
			return ResponseEntity.badRequest().build();
		School updatedSchool = repository.findById(id).map(school -> {
			newSchool.getAddress().setId(school.getAddress().getId());
			school = newSchool;
			return repository.save(school);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		EntityModel<School> entityModel = assembler.toModel(updatedSchool);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/schools/{id}")
	public ResponseEntity<?> deleteSchoolById(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ExceptionHandler
	public String constraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
