package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Teacher extends Employee {
	
	@Id
	@Column(name = "fk_employee")
	private int fkEmployee;
	@Column(name = "experience_year")
	private byte experienceYear;
	@Column(name = "previous_job")
	private String previousJob;
	private boolean active;
	
	private Teacher() {}
	
	private Teacher(String name, String lastname, String phoneNumber, Date dateOfBirth, Gender gender, String email,
			BloodType bloodType, int salary, EducationalAttainment educationalAttainment, String identifier, 
			byte experienceYear, String previousJob, boolean active) {
		super(name, lastname, phoneNumber, dateOfBirth, gender, email, bloodType, salary, educationalAttainment, identifier);
		this.experienceYear = experienceYear;
		this.previousJob = previousJob;
		this.active = active;
	}
	
	private Teacher(int fkEmployee, byte experienceYear, String previousJob, boolean active) {
		this.fkEmployee = fkEmployee;
		this.experienceYear = experienceYear;
		this.previousJob = previousJob;
		this.active = active;
	}
	
	public byte getExperienceYear() {
		return experienceYear;
	}
	public void setExperienceYear(byte experienceYear) {
		this.experienceYear = experienceYear;
	}
	public String getPreviousJob() {
		return previousJob;
	}
	public void setPreviousJob(String previousJob) {
		this.previousJob = previousJob;
	}
	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
		
}
