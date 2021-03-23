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
import com.school.portal.model.Teacher;
import com.school.portal.model.assembler.TeacherAssembler;
import com.school.portal.repository.TeacherRepository;

@RestController
@Validated
public class TeacherController {

	@Autowired
	TeacherRepository repository;
	@Autowired
	TeacherAssembler assembler;

	@GetMapping("/teachers")
	public ResponseEntity<CollectionModel<EntityModel<Teacher>>> all() {
		List<EntityModel<Teacher>> teachers = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(teachers,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(TeacherController.class).all()).withSelfRel()));
	}

	@GetMapping("/teachers/{id}")
	public ResponseEntity<EntityModel<Teacher>> one(@Positive @PathVariable int id) {
		Teacher teacher = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(assembler.toModel(teacher));
	}

	@PostMapping("/teachers")
	public ResponseEntity<?> newTeacher(@Valid @RequestBody Teacher teacher) {
		EntityModel<Teacher> entityModel = assembler.toModel(repository.save(teacher));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/teachers/{id}")
	public ResponseEntity<?> replaceTeacher(@Valid @RequestBody Teacher newTeacher, @Positive @PathVariable int id) {
		if (newTeacher.getId() != id)
			return ResponseEntity.badRequest().build();
		Teacher updatedTeacher = repository.findById(id).map(teacher -> {
			newTeacher.setAddress(teacher.getAddress());
			teacher = newTeacher;
			return repository.save(teacher);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));

		EntityModel<Teacher> entityModel = assembler.toModel(updatedTeacher);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/teachers/{id}/address")
	public ResponseEntity<?> replaceEmployee(@Valid @RequestBody Address address, @Positive @PathVariable int id) {
		Teacher updatedTeacher = repository.findById(id).map(teacher -> {
			address.setId(teacher.getAddress() == null ? null : teacher.getAddress().getId());
			teacher.setAddress(address);
			return repository.save(teacher);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));

		EntityModel<Teacher> entityModel = assembler.toModel(updatedTeacher);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/teachers/{id}")
	public ResponseEntity<?> deleteTeacher(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}

	@ExceptionHandler
	public String constrainViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
