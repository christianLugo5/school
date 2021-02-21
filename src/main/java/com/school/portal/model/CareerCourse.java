package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "career_course")
public class CareerCourse {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_link")
	private int id;
	@Column(name = "fk_career")
	private int career;
	@Column(name = "fk_course")
	private int course;
	@Column(name = "previous_fk_course")
	private int previousCourse;
	
}
