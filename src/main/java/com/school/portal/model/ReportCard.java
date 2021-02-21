package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

enum EvaluationType {
	ORDINARY, COMPLEMENTARY;
}

@Entity
@Table(name = "report_card")
public class ReportCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_report_card")
	private int id;
	@Column(name = "fk_course_teacher")
	private int courseTeacher;
	@Column(name = "grade_average")
	private byte gradeAverage;
	@Column(name = "evaluation_type")
	private EvaluationType evaluationType;

	private ReportCard() {
	}

	private ReportCard(int courseTeacher, byte gradeAverage, EvaluationType evaluationType) {
		this.courseTeacher = courseTeacher;
		this.gradeAverage = gradeAverage;
		this.evaluationType = evaluationType;
	}

	public int getId() {
		return id;
	}

	public int getCourseTeacher() {
		return courseTeacher;
	}

	public void setCourseTeacher(int courseTeacher) {
		this.courseTeacher = courseTeacher;
	}

	public byte getGradeAverage() {
		return gradeAverage;
	}

	public void setGradeAverage(byte gradeAverage) {
		this.gradeAverage = gradeAverage;
	}

	public EvaluationType getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(EvaluationType evaluationType) {
		this.evaluationType = evaluationType;
	}

}
