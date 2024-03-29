package com.school.portal.model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

// CourseTeacher (class) just can have one Course and one Teacher

@Entity
@Table(name = "course_teacher")
public class CourseTeacher {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private Integer id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_id", referencedColumnName = "course_id")
	private Course course;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "teacher_id", referencedColumnName = "person_id")
	private Teacher teacher;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate from;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate to;
	
	@PositiveOrZero
	private int capacity;
	
	@NotNull
	private Boolean available;
	
	@JsonIgnore
	@OneToMany(mappedBy = "courseTeacher")
	private Set<CourseStudent> courseStudent = new HashSet<CourseStudent>();

	public CourseTeacher() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public int getCapacity() {
		return capacity;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
		this.available = available;
	}

	public Set<CourseStudent> getCourseStudent() {
		return courseStudent;
	}

	public void setCourseStudent(Set<CourseStudent> courseStudent) {
		this.courseStudent = courseStudent;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
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
		CourseTeacher other = (CourseTeacher) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseTeacher [id=" + id + ", course=" + course + ", teacher=" + teacher + ", from=" + from + ", to="
				+ to + ", capacity=" + capacity + ", available=" + available + "]";
	}
		
}
