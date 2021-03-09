package com.school.portal.model;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "course_teacher")
public class CourseTeacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private int id;
	
	@NotNull
	@OneToMany
	@JoinColumn(name = "course_fk", referencedColumnName = "course_id")
	private Course course;
	
	@NotNull
	@OneToMany
	@JoinColumn(name = "teacher_fk", referencedColumnName = "teacher_id")
	private Teacher teacher;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate from;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate to;
	
	@NotNull
	private Integer capacity;
	
	@NotNull
	private Boolean available;

	public CourseTeacher() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Course getCourse() {
		return course;
	}

	public void setCourse(Course course) {
		this.course = course;
	}

	public Teacher getTeacher() {
		return teacher;
	}

	public void setTeacher(Teacher teacher) {
		this.teacher = teacher;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}	
	
}
