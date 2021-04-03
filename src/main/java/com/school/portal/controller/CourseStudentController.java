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

import com.school.portal.model.CourseStudent;
import com.school.portal.model.assembler.CourseStudentAssembler;
import com.school.portal.repository.CourseStudentRepository;

@RestController
@Validated
public class CourseStudentController {

	private final CourseStudentRepository repository;
	private final CourseStudentAssembler assembler;
	
	public CourseStudentController(CourseStudentRepository repository, CourseStudentAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}

	@GetMapping("/classes/{classId}/students/{studentId}")
	public ResponseEntity<CollectionModel<EntityModel<CourseStudent>>> all(@Positive @PathVariable int classId,
			@Positive @PathVariable int studentId) {
		List<EntityModel<CourseStudent>> entityModels = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(entityModels,
				WebMvcLinkBuilder
						.linkTo(WebMvcLinkBuilder.methodOn(CourseStudentController.class).all(classId, studentId))
						.withSelfRel()));
	}

	@GetMapping("/classes/{classId}/students/{studentId}/{id}")
	public ResponseEntity<EntityModel<CourseStudent>> one(@Positive @PathVariable int classId,
			@Positive @PathVariable int studentId, @Positive @PathVariable int id) {
		EntityModel<CourseStudent> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(entityModel);
	}

	@PostMapping("/classes/{classId}/students/{studentId}")
	public ResponseEntity<?> newCourseStudent(@Valid @RequestBody CourseStudent newCourseStudent,
			@Positive @PathVariable int classId, @Positive @PathVariable int studentId) {
		EntityModel<CourseStudent> entityModel = assembler.toModel(repository.save(newCourseStudent));
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@PutMapping("/classes/{classId}/students/{studentId}/{id}")
	public ResponseEntity<?> replaceCourseStudent(@Valid @RequestBody CourseStudent newCourseStudent,
			@Positive @PathVariable int classId, @Positive @PathVariable int studentId,
			@Positive @PathVariable int id) {
		CourseStudent updatedCourseStudent = repository.findById(id).map(courseS -> {
			newCourseStudent.setCourseTeacher(courseS.getCourseTeacher());
			newCourseStudent.setStudent(courseS.getStudent());
			courseS = newCourseStudent;
			return repository.save(courseS);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		EntityModel<CourseStudent> entityModel = assembler.toModel(updatedCourseStudent);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}

	@DeleteMapping("/classes/{classId}/students/{studentId}/{id}")
	public ResponseEntity<?> deleteCourseStudent(@Positive @PathVariable int classId,
			@Positive @PathVariable int studentId, @Positive @PathVariable int id) {
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
