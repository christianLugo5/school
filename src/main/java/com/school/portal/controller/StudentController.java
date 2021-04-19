package com.school.portal.controller;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import javax.validation.ConstraintViolationException;
import javax.validation.Valid;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.PageRequest;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Address;
import com.school.portal.model.Student;
import com.school.portal.model.assembler.StudentAssembler;
import com.school.portal.repository.StudentRepository;

@RestController
@RequestMapping("/students")
@Validated
public class StudentController {
	
	private final StudentRepository repository;
	private final StudentAssembler assembler;
	private final static Logger logger = LoggerFactory.getLogger(StudentController.class);
	private static AtomicInteger total = new AtomicInteger(-1);
	
	
	public StudentController(StudentRepository repository, StudentAssembler assembler) {
		this.repository = repository;
		this.assembler = assembler;		
	}
	
	@GetMapping("/total")
	public Integer getTotal() {
		if(total.get() == -1) {
			total.set(repository.findAllTotal());
		}
		return total.intValue();
	}
	
	@GetMapping
	public ResponseEntity<CollectionModel<EntityModel<Student>>> all(@RequestParam(required = false, defaultValue = "0") Integer pageNumber, 
			@RequestParam(required = false, defaultValue = "50") Integer elements){
		List<EntityModel<Student>> students = repository.findByOrderByNameAsc(PageRequest.of(pageNumber, elements))
				.stream().map(obj -> (Student) obj).map(assembler::toModel).collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(students, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
						.all(pageNumber, elements)).withSelfRel()));
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<Student>> one(@Positive @PathVariable int id) {
		Student student = repository.findById(id).orElseThrow(() -> new NoSuchElementException("Not found " + id));
		return ResponseEntity.ok(assembler.toModel(student));		
	}
	
	@GetMapping(params = "name")
	public ResponseEntity<CollectionModel<EntityModel<Student>>> allLikeByName(@RequestParam @Size(min = 2) String name){
		List<EntityModel<Student>> students = repository.findByNameContaining(name)
				.stream().map(assembler::toModel).collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(students, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
						.allLikeByName(name)).withSelfRel()));
	}
	
	@GetMapping(params = "lastName")
	public ResponseEntity<CollectionModel<EntityModel<Student>>> allByContainingLastName(@RequestParam @Size(min = 2) String lastName){
		List<EntityModel<Student>> students = repository.findByLastName(lastName)
				.stream().map(assembler::toModel).collect(Collectors.toList());
		return ResponseEntity.ok(CollectionModel.of(students, 
				WebMvcLinkBuilder.linkTo(WebMvcLinkBuilder.methodOn(StudentController.class)
						.allLikeByName(lastName)).withSelfRel()));
	}
	
	@PostMapping
	public ResponseEntity<?> newStudent(@Valid @RequestBody Student newStudent) {
		EntityModel<Student> student = assembler.toModel(repository.save(newStudent));
		total.incrementAndGet();
		return ResponseEntity.created(student.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(student);
	}
	
	
	
	@PutMapping("/{id}")
	public ResponseEntity<?> replaceStudent(@Valid @RequestBody Student newStudent, @Positive @PathVariable int id) {
		if(id != newStudent.getId())
			return ResponseEntity.badRequest().build();
		
		Student updatedStudent = repository.findById(id).map(student -> {
			newStudent.setAddress(student.getAddress());
			newStudent.setRelative(student.getRelative());
			student = newStudent;
			return repository.save(student);
		}).orElseThrow(() -> new NoSuchElementException("Not Found " + id));
		
		EntityModel<Student> entityModel = assembler.toModel(updatedStudent);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);		
	}
	
	@PutMapping("/{id}/address")
	public ResponseEntity<?> replaceAddress(@Valid @RequestBody Address address, @Positive @PathVariable int id){				
		Student updatedStudent = repository.findById(id).map(student -> {
			address.setId(student.getAddress() == null ? null : student.getAddress().getId());
			student.setAddress(address);
			return repository.save(student);
		}).orElseThrow(() -> new NoSuchElementException("Not Found " + id));
		
		EntityModel<Student> entityModel = assembler.toModel(updatedStudent);
		return ResponseEntity.created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri()).body(entityModel);
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteStudent(@Positive @PathVariable int id) {
		try {
			repository.deleteById(id);
			total.decrementAndGet();
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
