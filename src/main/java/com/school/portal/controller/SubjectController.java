package com.school.portal.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.school.portal.model.Subject;
import com.school.portal.repository.SubjectRepository;

@RestController
public class SubjectController {
	
	@Autowired
	SubjectRepository subjectRepo;
	
	@GetMapping("/subject")
	public ResponseEntity<List<Subject>> getAllSubjects(){
		List<Subject> subjects = subjectRepo.findAll();
		if(subjects.size() > 0)
			return ResponseEntity.ok().body(subjects);
		else
			return ResponseEntity.noContent().build();				
	}
	
	@GetMapping("/subject/{id}")
	public ResponseEntity<Subject> getSubject(@PathVariable int id) {
		if(id > 0)
			return Optional.ofNullable(subjectRepo.findById(id)).map(subject -> ResponseEntity.ok().body(subject))
				.orElseGet(() -> ResponseEntity.notFound().build());
		else
			return ResponseEntity.notFound().build();
	}
	
	@PostMapping("/subject")
	public void addSubject(@RequestBody Subject subject) {
		subjectRepo.save(subject);
	}
	
	@PutMapping("/subject/{id}")
	public void updateSubject(@RequestBody Subject subject, @PathVariable int id) {
		if(id > 0 && id == subject.getId()) 
			subjectRepo.save(subject);		
	}
	
	@DeleteMapping("/subject/{id}")
	public void deleteSubject(@PathVariable int id) {
		if(id > 0)
			subjectRepo.deleteById(id);
	}
	
}
