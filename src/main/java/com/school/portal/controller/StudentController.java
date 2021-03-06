package com.school.portal.controller;

import java.util.List;
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
import com.school.portal.model.Student;
import com.school.portal.model.assembler.StudentAssembler;
import com.school.portal.repository.StudentRepository;

@RestController
@Validated
public class StudentController {
	
	@Autowired
	StudentRepository repository;
	@Autowired
	StudentAssembler assembler;
	
	@GetMapping("/students")
	public ResponseEntity<CollectionModel<EntityModel<Student>>> all(){
		List<EntityModel<Student>> students = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());			
		return ResponseEntity.ok(CollectionModel.of(students, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
						.all()).withSelfRel()));
	}
	
	@GetMapping("/students/{id}")
	public ResponseEntity<EntityModel<Student>> one(@Positive @PathVariable int id) {
		Student student = repository.findById(id).orElseThrow(() -> new RuntimeException("Not found " + id));
		return ResponseEntity.ok(assembler.toModel(student));		
	}
	
	@PostMapping("/students")
	public ResponseEntity<?> newStudent(@Valid @RequestBody Student newStudent) {
		EntityModel<Student> student = assembler.toModel(repository.save(newStudent));
		return ResponseEntity.created(student.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(student);
	}
	
	@PutMapping("/students/{id}")
	public ResponseEntity<?> replaceStudent(@Valid @RequestBody Student newStudent, @Positive @PathVariable int id) {
		if(id != newStudent.getId())
			return ResponseEntity.badRequest().build();
		
		Student updatedStudent = repository.findById(id).map(student -> {
			newStudent.setAddress(student.getAddress());
			newStudent.setRelative(student.getRelative());
			student = newStudent;
			return repository.save(student);
		}).orElseThrow(() -> new RuntimeException("Not Found " + id));
		
		EntityModel<Student> entityModel = assembler.toModel(updatedStudent);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);		
	}
	
	@PutMapping("/students/{id}/address")
	public ResponseEntity<?> replaceAddress(@Valid @RequestBody Address address, @Positive @PathVariable int id){				
		Student updatedStudent = repository.findById(id).map(student -> {
			address.setId(student.getAddress() == null ? null : student.getAddress().getId());
			student.setAddress(address);
			return repository.save(student);
		}).orElseThrow(() -> new RuntimeException("Not Found " + id));
		
		EntityModel<Student> entityModel = assembler.toModel(updatedStudent);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/students/{id}")
	public ResponseEntity<?> deleteStudent(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ExceptionHandler
	public String constraintViolationHandler(ConstraintViolationException ex){
		return ex.getConstraintViolations().iterator().next().getMessage();
	}
	
}
