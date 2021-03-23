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
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Address;
import com.school.portal.model.Employee;
import com.school.portal.model.assembler.EmployeeAssembler;
import com.school.portal.repository.EmployeeRepository;

@RestController
@Validated
public class EmployeeController {

	@Autowired
	EmployeeRepository repository;
	@Autowired
	EmployeeAssembler assembler;

	@GetMapping("/employees")
	public ResponseEntity<CollectionModel<EntityModel<Employee>>> all() {
		List<EntityModel<Employee>> employees = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(employees,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(EmployeeController.class).all()).withSelfRel()));
	}

	@GetMapping("/employees/{id}")
	public ResponseEntity<EntityModel<Employee>> one(@Positive @PathVariable int id) {
		Employee employee = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(assembler.toModel(employee));
	}

	@PostMapping("/employees")
	public ResponseEntity<?> newEmployee(@Valid @RequestBody Employee employee) {
		EntityModel<Employee> entityModel = assembler.toModel(repository.save(employee));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/employees/{id}")
	public ResponseEntity<?> replaceEmployee(@Valid @RequestBody Employee newEmployee, @Positive @PathVariable int id) {
		if (newEmployee.getId() != id)
			return ResponseEntity.badRequest().build();
		Employee updatedEmployee = repository.findById(id).map(employee -> {
			newEmployee.setAddress(employee.getAddress());
			employee = newEmployee;
			return repository.save(employee);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));

		EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/employees/{id}/address")
	public ResponseEntity<?> replaceEmployee(@Valid @RequestBody Address address, @Positive @PathVariable int id) {
		Employee updatedEmployee = repository.findById(id).map(employee -> {
			address.setId(employee.getAddress() == null ? null : employee.getAddress().getId());
			employee.setAddress(address);
			return repository.save(employee);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));

		EntityModel<Employee> entityModel = assembler.toModel(updatedEmployee);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/employees/{id}")
	public ResponseEntity<?> deleteEmployee(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ExceptionHandler
	public String constraintExceptionHandler(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
