package com.school.portal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Relative extends Person {

	@NotEmpty
	@Size(min = 10, max = 18)
	private String identifier;

	@JsonIgnore
	@OneToMany(mappedBy = "relative")
	private Set<RelativeStudent> student = new HashSet<>();

	public Relative() {
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Set<RelativeStudent> getStudent() {
		return student;
	}

	public void setStudent(Set<RelativeStudent> student) {
		this.student = student;
	}

	public void addStudent(RelativeStudent student) {
		this.student.add(student);
	}

}
