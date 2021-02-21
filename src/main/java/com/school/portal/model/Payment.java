package com.school.portal.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

enum PaymentStatus {
	CREATED, CHECKED, PAID, CANCELLED;
}

@Entity
@Table(name = "student_course_payment")
public class Payment {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_payment")
	private int id;
	@Column(name = "total_courses")
	private byte totalCourses;
	private Date creation;
	private Date expiration;
	private PaymentStatus status;
	private Date payment;
	private String reference;
	private BigDecimal subtotal;
	private BigDecimal dicount;
	private BigDecimal total;

	private Payment() {
	}

	private Payment(byte totalCourses, Date creation, Date expiration, PaymentStatus status, Date payment,
			String reference, BigDecimal subtotal, BigDecimal dicount, BigDecimal total) {
		this.totalCourses = totalCourses;
		this.creation = creation;
		this.expiration = expiration;
		this.status = status;
		this.payment = payment;
		this.reference = reference;
		this.subtotal = subtotal;
		this.dicount = dicount;
		this.total = total;
	}

	public int getId() {
		return id;
	}

	public byte getTotal_courses() {
		return totalCourses;
	}

	public void setTotal_courses(byte total_courses) {
		this.totalCourses = total_courses;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

	public PaymentStatus getStatus() {
		return status;
	}

	public void setStatus(PaymentStatus status) {
		this.status = status;
	}

	public Date getPayment() {
		return payment;
	}

	public void setPayment(Date payment) {
		this.payment = payment;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public BigDecimal getSubtotal() {
		return subtotal;
	}

	public void setSubtotal(BigDecimal subtotal) {
		this.subtotal = subtotal;
	}

	public BigDecimal getDicount() {
		return dicount;
	}

	public void setDicount(BigDecimal dicount) {
		this.dicount = dicount;
	}

	public BigDecimal getTotal() {
		return total;
	}

	public void setTotal(BigDecimal total) {
		this.total = total;
	}

}
