package com.school.portal.model;

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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

import com.fasterxml.jackson.annotation.JsonIgnore;

enum Status {
	NORMAL, DROPPED;
}

@Entity
@Table(name = "course_teacher_student")
public class CourseStudent {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "link_id")
	private Integer id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_teacher_id", referencedColumnName = "link_id")
	private CourseTeacher courseTeacher;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "student_id", referencedColumnName = "person_id")
	private Student student;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Status status;

	@Positive
	private byte period;
	
	@JsonIgnore
	@OneToMany(mappedBy = "courseStudent")
	private Set<ReportCard> reportCard = new HashSet<ReportCard>();
	
	@JsonIgnore
	@OneToMany(mappedBy = "courseStudent")
	private Set<PaymentDetail> paymentDetail = new HashSet<PaymentDetail>();

	public CourseStudent() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
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

	public Set<ReportCard> getReportCard() {
		return reportCard;
	}

	public void setReportCard(Set<ReportCard> reportCard) {
		this.reportCard = reportCard;
	}

	public Set<PaymentDetail> getPaymentDetail() {
		return paymentDetail;
	}

	public void setPaymentDetail(Set<PaymentDetail> paymentDetail) {
		this.paymentDetail = paymentDetail;
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
		CourseStudent other = (CourseStudent) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "CourseStudent [id=" + id + ", student=" + student + ", status=" + status + ", period=" + period
				+ ", reportCard=" + reportCard + ", paymentDetail=" + paymentDetail + "]";
	}
	
}
