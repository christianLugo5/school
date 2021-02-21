package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Address {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_address")
	private int id;
	@Column(name = "fk_city")
	private int fkCity;
	private String street;
	private String neighborhood;
	@Column(name = "zip_code")
	private int zip_code;
	private String reference;

	private Address() {
	}

	private Address(int fkCity, String street, String neighborhood, int zip_code, String reference) {
		this.fkCity = fkCity;
		this.street = street;
		this.neighborhood = neighborhood;
		this.zip_code = zip_code;
		this.reference = reference;
	}

	public int getId() {
		return id;
	}

	public int getFkCity() {
		return fkCity;
	}

	public void setFkCity(int fkCity) {
		this.fkCity = fkCity;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getNeighborhood() {
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public int getZip_code() {
		return zip_code;
	}

	public void setZip_code(int zip_code) {
		this.zip_code = zip_code;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

}
