package com.school.portal.model;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

enum Relationship{
	FATHER, MOTHER, AUNT, UNCLE, GRANDMOTHER, GRANDFATHER, TUTOR, OTHER;
}

@Entity
public class Relative extends Person {
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "student_fk", referencedColumnName = "person_id")
	private Student student;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Relationship relationship;

	public Relative() {
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Relationship getRelationship() {
		return relationship;
	}

	public void setRelationship(Relationship relationship) {
		this.relationship = relationship;
	}
		
}
