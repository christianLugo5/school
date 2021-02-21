package com.school.portal.model;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "student_course_payment_detail")
public class PaymentDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detail")
	private int id;
	@Column(name = "fk_course_teacher_student")
	private int teacherStudent;
	@Column(name = "fk_payment")
	private int payment;
	private BigDecimal price;
	private BigDecimal discount;
	private BigDecimal total;

	private PaymentDetail() {
	}

	private PaymentDetail(int teacherStudent, int payment, BigDecimal price, BigDecimal discount, BigDecimal total) {
		this.teacherStudent = teacherStudent;
		this.payment = payment;
		this.price = price;
		this.discount = discount;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public int getTeacherStudent() {
		return teacherStudent;
	}

	public void setTeacherStudent(int teacherStudent) {
		this.teacherStudent = teacherStudent;
	}

	public int getPayment() {
		return payment;
	}

	public void setPayment(int payment) {
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
