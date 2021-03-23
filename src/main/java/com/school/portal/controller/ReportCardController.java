package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.constraints.Positive;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.ReportCard;
import com.school.portal.model.assembler.ReportCardAssembler;
import com.school.portal.repository.ReportCardRepository;

@RestController
@Validated
public class ReportCardController {

	@Autowired
	ReportCardRepository repository;
	@Autowired
	ReportCardAssembler assembler;

	@GetMapping("/reports")
	public ResponseEntity<CollectionModel<EntityModel<ReportCard>>> all() {
		List<EntityModel<ReportCard>> entityModels = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModels,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(ReportCardController.class).all()).withSelfRel()));
	}

	@GetMapping("/reports/{id}")
	public ResponseEntity<EntityModel<ReportCard>> one(@Positive @PathVariable int id) {
		EntityModel<ReportCard> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}

	@ExceptionHandler
	public String constraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
