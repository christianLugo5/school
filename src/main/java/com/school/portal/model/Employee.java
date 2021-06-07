package com.school.portal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.DiscriminatorColumn;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

enum EducationalAttainment {
	DOCTORAL, PROFESSIONAL, MASTER, BACHELOR, HIGHSCHOOL;
}

@Entity
@DiscriminatorColumn(name = "person_type")
@DiscriminatorValue("E")
public class Employee extends Person {

	@PositiveOrZero
	private int salary;
	
	@NotNull
	@Column(name = "educational_attainment")
	@Enumerated(EnumType.STRING)
	private EducationalAttainment educationalAttainment;
	
	@NotEmpty
	@Size(min = 10)
	private String identifier;
	
	@Column(name = "person_type")
	protected char PERSON_TYPE;
	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id", referencedColumnName = "address_id")
	private Address address;

	public Employee() {
		PERSON_TYPE = 'E';
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
		this.identifier = identifier.strip();
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Employee [" + super.toString() + "salary=" + salary + ", educationalAttainment=" + educationalAttainment + ", identifier="
				+ identifier + "]";
	}

}
