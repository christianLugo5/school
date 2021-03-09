package com.school.portal.model;

import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

enum CourseType {
	SEMESTER, QUARTER;
}

@Entity
public class Course {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "course_id")
	private int id;
	
	@NotEmpty
	@Size(min = 3, max = 120)
	private String course;
	
	@NotEmpty
	@Size(min = 3,max = 25)
	private String identifier;
	
	@NotNull
	private Boolean available;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "course_type")
	private CourseType courseType;
	
	@NotNull
	private int capacity;
	@NotNull
	private BigDecimal price;
	
	@ManyToMany
	@JoinTable(name = "course_subject", joinColumns = {@JoinColumn(name = "course_id")}, inverseJoinColumns = {@JoinColumn(name = "subject_id")})	
	private Set<Subject> subject = new HashSet<Subject>();

	public Course() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCourse() {
		return course;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public CourseType getCourseType() {
		return courseType;
	}

	public void setCourseType(CourseType courseType) {
		this.courseType = courseType;
	}

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public Set<Subject> getSubject() {
		return subject;
	}

	public void setSubject(Set<Subject> subject) {
		this.subject = subject;
	}

	public void addSubject(Subject subject) {
		this.subject.add(subject);
	}	
	
	public void removeSubject(int subjectId) {
		this.subject.removeIf(sub -> sub.getId()==subjectId);
	}
	
}
