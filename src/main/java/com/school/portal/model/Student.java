package com.school.portal.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Student extends Person {

	@NotEmpty
	@Size(min = 10)
	private String identifier;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private final LocalDateTime registration;
	
	@OneToMany(mappedBy = "student", cascade = CascadeType.REMOVE)
	private Set<Relative> relative = new HashSet<>();		
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_fk", referencedColumnName = "address_id")
	private Address address;
	
	@JsonIgnore
	@OneToMany(mappedBy = "student")
	private CourseStudent courseStudent;
	
	@ManyToMany
	@JoinTable(name = "student_career", joinColumns = {@JoinColumn(name = "student_fk")}, inverseJoinColumns = {@JoinColumn(name = "career_fk")})
	private Set<Career> career;
	
	public Student() {
		registration = LocalDateTime.now();
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier.strip();
	}

	public LocalDateTime getRegistration() {
		return registration;
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	public Set<Relative> getRelative() {
		return relative;
	}

	public void setRelative(Set<Relative> relative) {
		this.relative = relative;
	}

	public CourseStudent getCourseStudent() {
		return courseStudent;
	}

	public void setCourseStudent(CourseStudent courseStudent) {
		this.courseStudent = courseStudent;
	}

	public Set<Career> getCareer() {
		return career;
	}

	public void setCareer(Set<Career> career) {
		this.career = career;
	}
	
	public void addCareer(Career career) {
		this.career.add(career);
	}
	
	public void removeCareer(int careerId) {
		this.career.removeIf(career -> career.getId() == careerId);
	}
	
}
