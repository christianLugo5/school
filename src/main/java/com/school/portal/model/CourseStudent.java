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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

enum Status {
	NORMAL, DROPPED;
}

@Entity
@Table(name = "course_teacher_student")
public class CourseStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private int id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_teacher_fk", referencedColumnName = "teacher_fk")
	private CourseTeacher courseTeacher;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_fk", referencedColumnName = "person_id")
	private Student student;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	@Positive
	private byte period;

	public CourseStudent() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CourseTeacher getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(CourseTeacher courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public Student getStudent() {
		return student;
	}

	public void setStudent(Student student) {
		this.student = student;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	public byte getPeriod() {
		return period;
	}

	public void setPeriod(byte period) {
		this.period = period;
	}

}
