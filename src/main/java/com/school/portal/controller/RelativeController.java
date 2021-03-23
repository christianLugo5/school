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

import com.school.portal.model.Relative;
import com.school.portal.model.Student;
import com.school.portal.model.assembler.RelativeAssembler;
import com.school.portal.repository.RelativeRepository;
import com.school.portal.repository.StudentRepository;

@RestController
@Validated
public class RelativeController {
	
	@Autowired
	RelativeRepository repository;
	@Autowired
	RelativeAssembler assembler;
	@Autowired
	StudentRepository studentRepo;
		
	@GetMapping("/students/{studentId}/relatives")
	public ResponseEntity<CollectionModel<EntityModel<Relative>>> all(@Positive @PathVariable int studentId){
		List<EntityModel<Relative>> relatives = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());		
		return ResponseEntity.ok(CollectionModel.of(relatives, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelativeController.class).all(studentId)).withSelfRel()));
	}
	
	@GetMapping("/students/{studentId}/relatives/{id}")
	public ResponseEntity<EntityModel<Relative>> one(@Positive @PathVariable int studentId, @Positive @PathVariable int id) {
		EntityModel<Relative> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));		
		return ResponseEntity.ok(entityModel);
	}
	
	@PostMapping("/students/{studentId}/relatives")
	public ResponseEntity<?> newRelative(@Valid @RequestBody Relative relative, @Positive @PathVariable int studentId) {
		Student student = studentRepo.findById(studentId).orElseThrow(() -> new RuntimeException("Not found " + studentId));
		relative.setStudent(student);
		EntityModel<Relative> entityModel = assembler.toModel(repository.save(relative));		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/students/{studentId}/relatives/{id}")
	public ResponseEntity<?> replaceRelative(@Valid @RequestBody Relative newRelative,@Positive @PathVariable int studentId, 
			@Positive @PathVariable int id) {
		if(newRelative.getId() != id)
			return ResponseEntity.badRequest().build();
		
		Relative updatedRelative = repository.findById(id).map(relative -> {
			newRelative.setStudent(relative.getStudent());
			relative = newRelative;
			return repository.save(relative);
		}).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		
		EntityModel<Relative> entityModel = assembler.toModel(updatedRelative);		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/students/{studentId}/relatives/{id}")
	public ResponseEntity<?> deleteRelative(@Positive @PathVariable int studentId, @Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			return ResponseEntity.noContent().build();
		} catch (Exception e) {
			return ResponseEntity.notFound().build();
		}
	}
	
	@ExceptionHandler
	public String constraintViolationHandler(ConstraintViolationException ex) {
		return ex.getConstraintViolations().iterator().next().getMessage();
	}
	
}
