package com.school.portal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "student_course_payment_detail")
public class PaymentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_id")
	private Integer id;
	
	@NotNull
	@ManyToOne
	@JoinColumn(name = "course_teacher_student_fk")
	private CourseStudent courseStudent;
	
	@JsonIgnore
	@NotNull
	@ManyToOne
	@JoinColumn(name = "payment_fk", referencedColumnName = "payment_id")
	private Payment payment;
	
	@PositiveOrZero
	private BigDecimal price;
	
	@PositiveOrZero
	private BigDecimal discount;
	
	@PositiveOrZero
	private BigDecimal total;

	public PaymentDetail() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public CourseStudent getCourseStudent() {
		return courseStudent;
	}

	public void setCourseStudent(CourseStudent courseStudent) {
		this.courseStudent = courseStudent;
	}

	public Payment getPayment() {
		return payment;
	}

	public void setPayment(Payment payment) {
		this.payment = payment;
	}

	public BigDecimal getPrice() {
		return price;
	}

	public void setPrice(BigDecimal price) {
		this.price = price;
	}

	public BigDecimal getDiscount() {
		return discount;
	}

	public void setDiscount(BigDecimal discount) {
		this.discount = discount;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
