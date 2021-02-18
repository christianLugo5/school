package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum Gender{
	MALE,FEMALE
}

enum BloodType{
	APOSITIVE("A+"),ANEGATIVE("A-"), BPOSITIVE("B+"), BNEGATIVE("B-"), ABPOSITIVE("AB+"),ABNEGATIVE("AB-"),OPOSITIVE("O+"),ONEGATIVE("O-");
	String type;
	
	private BloodType(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}

@Entity
public class Person {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_person")
	private int id;
		
	private String name;
	
	@Column(name = "last_name")
	private String lastname;
	private String curp;
	private String rfc;
	
	@Column(name = "phone_number")
	private String phoneNumber;
	
	@Column(name = "date_of_birth")
	private Date dateOfBirth;
	private Gender gender;
	private String email;
	private String allergy;
	
	@Column(name = "blood_type")
	private BloodType bloodType;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getCurp() {
		return curp;
	}
	public void setCurp(String curp) {
		this.curp = curp;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	public Date getDateOfBirth() {
		return dateOfBirth;
	}
	public void setDateOfBirth(Date dateOfBirth) {
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
		this.email = email;
	}
	public String getAllergies() {
		return allergy;
	}
	public void setAllergies(String allergy) {
		this.allergy = allergy;
	}
	public BloodType getBloodType() {
		return bloodType;
	}
	public void setBloodType(BloodType bloodType) {
		this.bloodType = bloodType;
	}
	public String getBloodTypeGroup() {
		return bloodType.type;
	}
		
}
