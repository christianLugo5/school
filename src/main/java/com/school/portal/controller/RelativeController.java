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

import com.school.portal.model.Relative;
import com.school.portal.model.Student;
import com.school.portal.model.assembler.RelativeAssembler;
import com.school.portal.model.assembler.StudentAssembler;
import com.school.portal.repository.RelativeRepository;

@RestController
@Validated
@RequestMapping("/relatives")
public class RelativeController {
	
	private final RelativeRepository repository;
	private final RelativeAssembler assembler;
	private final StudentAssembler studentAssembler;
	
	public RelativeController(RelativeRepository repository, RelativeAssembler assembler, StudentAssembler studentAssembler) {
		this.repository = repository;
		this.assembler = assembler;
		this.studentAssembler = studentAssembler;		
	}
		
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Relative>>> all(){
		List<EntityModel<Relative>> relatives = repository.findAll().stream().map(assembler::toModel)
				.collect(Collectors.toList());		
		return ResponseEntity.ok(CollectionModel.of(relatives, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(RelativeController.class).all()).withSelfRel()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Relative>> one(@Positive @PathVariable Integer id) {
		EntityModel<Relative> entityModel = repository.findById(id).map(assembler::toModel)
				.orElseThrow(() -> new NoSuchElementException("Not found " + id));		
		return ResponseEntity.ok(entityModel);
	}
	
	@GetMapping("/{id}/students")
	public ResponseEntity<CollectionModel<EntityModel<Student>>> allStudents(@Positive @PathVariable int id){
		List<EntityModel<Student>> students = repository.findAllStudentById(id).stream()
				.map(studentAssembler::toModel).collect(Collectors.toList());		
		return ResponseEntity.ok(CollectionModel.of(students, 
				WebMvcLinkBuilder.linkTo(RelativeController.class).withSelfRel()));
	}
	
	@PostMapping
	public ResponseEntity<?> newRelative(@Valid @RequestBody Relative relative) {
		EntityModel<Relative> entityModel = assembler.toModel(repository.save(relative));		
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceRelative(@Valid @RequestBody Relative newRelative, @Positive @PathVariable int id) {
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
	
	@DeleteMapping("/{id}/students/{studentId}")
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
