package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

enum EducationalAttainment{
	DOCTORAL, PROFESSIONAL, MASTER, BACHELOR, HIGHSCHOOL;	
}

@Entity
public class Employee extends Person {
	
	@Id
	@Column(name = "fk_person")
	private int fkPerson;
	private int salary;
	@Column(name = "educational_attainment")
	private EducationalAttainment educationalAttainment;
	private String identifier;
	
	
	protected Employee() {}
	
	public Employee(String name, String lastname, String phoneNumber, Date dateOfBirth, Gender gender, String email,
			BloodType bloodType, int salary, EducationalAttainment educationalAttainment, String identifier) {
		super(name, lastname, phoneNumber, dateOfBirth, gender, email, bloodType);
		this.salary = salary;
		this.educationalAttainment = educationalAttainment;
		this.identifier = identifier;
	}

	public int getSalary() {
		return salary;
	}
	public void setSalary(int salary) {
		this.salary = salary;
	}
	public EducationalAttainment getEducationalAttainment() {
		return educationalAttainment;
	}
	public void setEducationalAttainment(EducationalAttainment educationalAttainment) {
		this.educationalAttainment = educationalAttainment;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
		
}
