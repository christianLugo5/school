package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class Subject {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "subject_id")
	private int id;
	
	@NotNull
	@Size(min = 3, max = 120)
	private String subject;

	private Subject() {
	}

	private Subject(String subject) {
		this.subject = subject;
	}

	public int getId() {
		return id;
	}

	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject.strip();
	}

}
