package com.school.portal.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.PositiveOrZero;

enum EvaluationType {
	ORDINARY, COMPLEMENTARY;
}

@Entity
@Table(name = "report_card")
public class ReportCard {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "report_card_id")
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "course_teacher_fk" ,referencedColumnName = "link_id")
	private CourseStudent courseStudent;
	
	@PositiveOrZero
	@Column(name = "grade_average")
	private double gradeAverage;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "evaluation_type")
	private EvaluationType evaluationType;
	
	@OneToMany(mappedBy = "reportCard", cascade = CascadeType.ALL)
	private Set<ReportCardDetail> cardDetail;

	public ReportCard() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public CourseStudent getCourseStudent() {
		return courseStudent;
	}

	public void setCourseStudent(CourseStudent courseStudent) {
		this.courseStudent = courseStudent;
	}

	public double getGradeAverage() {
		return gradeAverage;
	}

	public void setGradeAverage(double gradeAverage) {
		this.gradeAverage = gradeAverage;
	}

	public EvaluationType getEvaluationType() {
		return evaluationType;
	}

	public void setEvaluationType(EvaluationType evaluationType) {
		this.evaluationType = evaluationType;
	}

	public Set<ReportCardDetail> getCardDetail() {
		return cardDetail;
	}

	public void setCardDetail(Set<ReportCardDetail> cardDetail) {
		this.cardDetail = cardDetail;
	}
	
}
