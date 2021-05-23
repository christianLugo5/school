package com.school.portal.controller;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import javax.transaction.Transactional;
import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;

import org.springframework.dao.EmptyResultDataAccessException;
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

import com.school.portal.model.Career;
import com.school.portal.model.Course;
import com.school.portal.model.assembler.CareerAssembler;
import com.school.portal.repository.CareerRepository;

@RestController
@Validated
public class CareerController {

	private final CareerRepository repository;
	private final CareerAssembler assembler;
	
	public CareerController(CareerRepository repository, CareerAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}

	@GetMapping("/careers")
	public ResponseEntity<CollectionModel<EntityModel<Career>>> all() {
		List<EntityModel<Career>> careers = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(careers,
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CareerController.class).all()).withSelfRel()));
	}

	@GetMapping("/careers/{id}")
	public ResponseEntity<EntityModel<Career>> one(@Positive @PathVariable int id) {
		EntityModel<Career> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}

	@PostMapping("/careers")
	public ResponseEntity<?> newCareer(@Valid @RequestBody Career newCareer) {
		EntityModel<Career> entityModel = assembler.toModel(repository.save(newCareer));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/careers/{id}")
	public ResponseEntity<?> replaceCareer(@Valid @RequestBody Career newCareer, @Positive @PathVariable int id) {
		if (newCareer.getId() != id)
			return ResponseEntity.badRequest().build();
		Career updatedCareer = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		updatedCareer = repository.save(newCareer);

		EntityModel<Career> entityModel = assembler.toModel(updatedCareer);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/careers/{id}/courses")
	public ResponseEntity<?> addCourseToCareer(@Valid @RequestBody Course course, @Positive @PathVariable int id){
		Career career = repository.findById(id).orElseThrow();
		career.addCourse(course);
		repository.save(career);
		
		EntityModel<Career> entityModel = assembler.toModel(career);		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@Transactional
	@DeleteMapping("/careers/{id}")
	public ResponseEntity<?> deleteCareer(@Positive @PathVariable int id) throws EmptyResultDataAccessException {
		repository.deleteById(id);
		return ResponseEntity.noContent().build();
	}

	@Transactional
	@DeleteMapping("/careers/{id}/courses/{courseId}")
	public ResponseEntity<?> deleteCourseFromCareer(@Positive @PathVariable int id,
			@Positive @PathVariable int courseId) {
		repository.deleteCourseFromCareerById(id, courseId);
		return ResponseEntity.ok().build();
	}

	@ExceptionHandler
	public String constraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}

}
