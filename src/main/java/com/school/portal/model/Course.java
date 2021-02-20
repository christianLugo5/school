package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum CourseType{
	SEMESTER, QUARTER;
}

@Entity
public class Course {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_course")
	private int id;
	private String course;
	private String identifier;
	private boolean available;
	
	@Column(name = "course_type")
	private CourseType courseType;
	private int capacity;
	private int price;
	
	private Course() {}
	
	private Course(String course, String identifier, boolean available, CourseType courseType, int capacity,
			int price) {
		this.course = course;
		this.identifier = identifier;
		this.available = available;
		this.courseType = courseType;
		this.capacity = capacity;
		this.price = price;
	}
	
	public int getId() {
		return id;
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
	public boolean isAvailable() {
		return available;
	}
	public void setAvailable(boolean available) {
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
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
			
}
