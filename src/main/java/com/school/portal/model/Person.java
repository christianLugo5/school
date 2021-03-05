package com.school.portal.model;

import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

enum Gender {
	MALE, FEMALE
}

enum BloodType {
	A_POSITIVE, A_NEGATIVE, B_POSITIVE, B_NEGATIVE, AB_POSITIVE, AB_NEGATIVE, O_POSITIVE, O_NEGATIVE, NO_ANSWER;
}

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Person {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "person_id", updatable = false)
	private int id;

	@NotEmpty
	@Size(min = 2, max = 45)
	private String name;

	@NotEmpty
	@Size(min = 2, max = 45)
	@Column(name = "last_name")
	private String lastName;

	@NotEmpty
	@Size(min = 18, max = 18)
	private String curp;
	private String rfc;

	@NotEmpty
	@Size(min = 10, max = 15)
	@Column(name = "phone_number")
	private String phoneNumber;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE)
	@Past
	@Column(name = "date_of_birth")
	private LocalDate dateOfBirth;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Gender gender;
	private String email;
	private String allergy;

	@NotNull
	@Column(name = "blood_type")
	@Enumerated(EnumType.STRING)
	private BloodType bloodType;

	public Person() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.strip().replaceAll("\\s+", " ");
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName.strip().replaceAll("\\s+", " ");
	}

	public String getCurp() {
		return curp;
	}

	public void setCurp(String curp) {
		this.curp = curp.strip().replaceAll("\\s+", " ");
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc.strip().replaceAll("\\s+", " ");
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber.strip().replaceAll("\\s+", " ");
	}

	public LocalDate getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(LocalDate dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email.strip().replaceAll("\\s+", " ");
	}

	public String getAllergy() {
		return allergy;
	}

	public void setAllergy(String allergy) {
		this.allergy = allergy.strip().replaceAll("\\s+", " ");
	}

	public BloodType getBloodType() {
		return bloodType;
	}

	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}

}
