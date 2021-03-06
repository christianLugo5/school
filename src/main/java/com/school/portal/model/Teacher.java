package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

@Entity
public class Teacher extends Employee {

	@PositiveOrZero
	@Column(name = "experience_year")
	private byte experienceYear;
	
	@NotEmpty
	@Size(min = 3, max = 90)
	@Column(name = "previous_job")
	private String previousJob;
	
	@NotNull
	private Boolean active;

	public Teacher() {
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
		this.previousJob = previousJob.strip();
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}
	
}
