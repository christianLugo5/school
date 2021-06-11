package com.school.portal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Relative extends Person {

	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private Set<RelativeStudent> student = new HashSet<>();

	public Relative() {
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
