package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Subject;
import com.school.portal.model.assembler.SubjectAssembler;
import com.school.portal.repository.SubjectRepository;

@RestController
@Validated
@RequestMapping("/subjects")
public class SubjectController {

	private final SubjectRepository repository;
	private final SubjectAssembler assembler;
	
	public SubjectController(SubjectRepository repository, SubjectAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}

	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Subject>>> all() {
		List<EntityModel<Subject>> subjects = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(subjects,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(SubjectController.class).all()).withSelfRel()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Subject>> one(@Positive @PathVariable int id) {
		Subject subject = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(assembler.toModel(subject));
	}

	@PostMapping
	public ResponseEntity<?> newSubject(@Valid @RequestBody Subject subject) {
		EntityModel<Subject> entityModel = assembler.toModel(repository.save(subject));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> replaceSubject(@Valid @RequestBody Subject subject, @Positive @PathVariable int id) {
		if (id != subject.getId())
			return ResponseEntity.badRequest().build();
		EntityModel<Subject> entityModel = assembler.toModel(repository.save(subject));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteSubject(@Positive @PathVariable int id) {
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
