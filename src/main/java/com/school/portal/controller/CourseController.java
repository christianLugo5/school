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

import com.school.portal.model.Course;
import com.school.portal.model.Subject;
import com.school.portal.model.assembler.CourseAssembler;
import com.school.portal.repository.CourseRepository;

@RestController
@Validated
public class CourseController {
	
	@Autowired
	CourseRepository repository;
	@Autowired
	CourseAssembler assembler;
	
	@GetMapping("/courses")
	public ResponseEntity<CollectionModel<EntityModel<Course>>> all(){
		List<EntityModel<Course>> courses = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(courses, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseController.class).all()).withSelfRel()));
	}
	
	@GetMapping("/courses/{id}")
	public ResponseEntity<EntityModel<Course>> one(@Positive @PathVariable int id){
		EntityModel<Course> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}
	
	@PostMapping("/courses")
	public ResponseEntity<?> newCourse(@Valid @RequestBody Course course){
		EntityModel<Course> entityModel = assembler.toModel(repository.save(course));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/courses/{id}")
	public ResponseEntity<?> replaceCourse(@Valid @RequestBody Course newCourse, @Positive @PathVariable int id){
		if(newCourse.getId() != id)
			return ResponseEntity.badRequest().build();
		Course updatedCourse = repository.findById(id).map(course -> {
			newCourse.setSubject(newCourse.getSubject() == null ? course.getSubject() : newCourse.getSubject());
			course = newCourse;
			return repository.save(course);
		})
				.orElseThrow(() -> new NoSuchElementException("Not found" + id));
		
		EntityModel<Course> entityModel = assembler.toModel(updatedCourse);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/courses/{id}/subjects")
	public ResponseEntity<?> newSubject(@Valid @RequestBody Subject subject ,@Positive @PathVariable int id){
		Course course = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found " + id));
		course.addSubject(subject);
		EntityModel<Course> entityModel = assembler.toModel(repository.save(course));		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/courses/{id}")
	public ResponseEntity<?> deleteCourse(@Positive @PathVariable int id){
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@DeleteMapping("/courses/{courseId}/subjects/{subjectId}")
	public ResponseEntity<?> deleteSubject(@Positive @PathVariable int courseId, @Positive @PathVariable int subjectId){
		try {
			Course course = repository.findById(courseId).orElseThrow(() -> new NoSuchElementException("Not found"));
			course.removeSubject(subjectId);
			repository.save(course);
			return ResponseEntity.ok().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ExceptionHandler
	public String constraintViolationException(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}
	
}
