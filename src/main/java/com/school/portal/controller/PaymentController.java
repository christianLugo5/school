package com.school.portal.controller;

import java.util.List;
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

import com.school.portal.model.Payment;
import com.school.portal.model.assembler.PaymentAssembler;
import com.school.portal.repository.PaymentRepository;

@RestController
@Validated
public class PaymentController {

	@Autowired
	PaymentRepository repository;
	@Autowired
	PaymentAssembler assembler;

	@GetMapping("/payments")
	public ResponseEntity<CollectionModel<EntityModel<Payment>>> all() {
		List<EntityModel<Payment>> entityModels = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModels,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(PaymentController.class).all()).withSelfRel()));
	}

	@GetMapping("/payments/{id}")
	public ResponseEntity<EntityModel<Payment>> one(@Positive @PathVariable int id) {
		EntityModel<Payment> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new RuntimeException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}
	
	

	@ExceptionHandler
	public String constraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
