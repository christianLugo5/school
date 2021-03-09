package com.school.portal.model;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

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
	
	@Positive
	@Column(name = "total_courses")
	private byte totalCourses;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private LocalDate creation;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	private Date expiration;
	
	@NotNull
	@Enumerated(EnumType.STRING)
	private PaymentStatus status;
	
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime payment;
	
	private String reference;
	
	@PositiveOrZero
	private BigDecimal subtotal;
	
	@PositiveOrZero
	private BigDecimal dicount;
	
	@PositiveOrZero
	private BigDecimal total;
	
	@OneToMany(mappedBy = "payment")
	private PaymentDetail detail;

	public Payment() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public byte getTotalCourses() {
		return totalCourses;
	}

	public void setTotalCourses(byte totalCourses) {
		this.totalCourses = totalCourses;
	}

	public LocalDate getCreation() {
		return creation;
	}

	public void setCreation(LocalDate creation) {
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

	public LocalDateTime getPayment() {
		return payment;
	}

	public void setPayment(LocalDateTime payment) {
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

	public PaymentDetail getDetail() {
		return detail;
	}

	public void setDetail(PaymentDetail detail) {
		this.detail = detail;
	}
		
}
