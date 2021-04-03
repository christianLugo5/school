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
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.CourseTeacher;
import com.school.portal.model.assembler.CourseTeacherAssembler;
import com.school.portal.repository.CourseTeacherRepository;

@RestController
@Validated
public class CourseTeacherController {
	
	private final CourseTeacherRepository repository;
	private final CourseTeacherAssembler assembler;
	
	public CourseTeacherController(CourseTeacherRepository repository, CourseTeacherAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}
	
	@GetMapping("/classes")
	public ResponseEntity<CollectionModel<EntityModel<CourseTeacher>>> all(){
		List<EntityModel<CourseTeacher>> entityModels = repository.findAll().stream().map(assembler::toModel).collect(Collectors.toList()); 
		return ResponseEntity.ok(CollectionModel.of(entityModels, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(CourseTeacherController.class).all()).withSelfRel()));
	}
	
	@GetMapping("/classes/{id}")
	public ResponseEntity<EntityModel<CourseTeacher>> one(@Positive @PathVariable int id){
		EntityModel<CourseTeacher> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}
	
	@PostMapping("/classes")
	public ResponseEntity<?> newCourseTeacher(@Valid @RequestBody CourseTeacher courseTeacher){
		EntityModel<CourseTeacher> entityModel = assembler.toModel(repository.save(courseTeacher));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/classes/{id}")
	public ResponseEntity<?> replaceCourseTeacher(@Valid @RequestBody CourseTeacher newCourseTeacher, @Positive @PathVariable int id){
		if(newCourseTeacher.getId() != id)
			return ResponseEntity.badRequest().build();
		CourseTeacher updatedClass = repository.findById(id).map(courseT -> {
			courseT = newCourseTeacher;
			return repository.save(courseT);
		})
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		
		EntityModel<CourseTeacher> entityModel = assembler.toModel(updatedClass);				
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/classes/{id}")
	public ResponseEntity<?> deleteCourseTeacher(@Positive @PathVariable int id){
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
