package com.school.portal.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.Table;

import com.sun.istack.NotNull;

enum Relationship{
	FATHER, MOTHER, AUNT, UNCLE, GRANDMOTHER, GRANDFATHER, TUTOR, OTHER;
}

@Entity
@Table(name = "relative_student")
public class RelativeStudent {
	
	@EmbeddedId
	private RelativeStudentId id = new RelativeStudentId();
	
	@ManyToOne
	@MapsId("person_id")
	private Relative relative;
	
	@ManyToOne
	@MapsId("student_id")
	private Student student;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Relationship relationship;

	public Relative getRelative() {
		return relative;
	}

	public void setRelative(Relative relative) {
		this.relative = relative;
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
