package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sun.istack.NotNull;

enum Relationship{
	FATHER, MOTHER, AUNT, UNCLE, GRANDMOTHER, GRANDFATHER, TUTOR, OTHER;
}

@Entity
@Table(name = "relative_student")
public class RelativeStudent {
	
	@JsonIgnore
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private Integer linkId;
	
	@ManyToOne
	@JoinColumn(name = "relative_id", referencedColumnName = "person_id")
	private Relative relative;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "person_id")
	private Student student;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private Relationship relationship;

	
	public RelativeStudent() {
	}

	public RelativeStudent(Relative relative, Student student) {
		this.relative = relative;
		this.student = student;
	}

	public Integer getLinkId() {
		return linkId;
	}

	public void setLinkId(Integer linkId) {
		this.linkId = linkId;
	}

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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relative == null) ? 0 : relative.hashCode());
		result = prime * result + ((student == null) ? 0 : student.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelativeStudent other = (RelativeStudent) obj;
		if (relative == null) {
			if (other.relative != null)
				return false;
		} else if (!relative.equals(other.relative))
			return false;
		if (student == null) {
			if (other.student != null)
				return false;
		} else if (!student.equals(other.student))
			return false;
		return true;
	}
	
}
