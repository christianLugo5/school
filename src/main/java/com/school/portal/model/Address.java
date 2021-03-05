package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

@Entity
public class Address {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "address_id")
	private Integer id;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "city_fk", referencedColumnName = "city_id", nullable = false)
	private City city;	
	
	@NotEmpty @Size(min = 3, max = 45)
	private String street;
	
	@NotEmpty @Size(min = 3, max = 45)
	private String neighborhood;
	
	@NotNull @Positive
	@Column(name = "zip_code")	
	private Integer zipCode;
	
	@NotEmpty @Size(min = 5, max = 120)
	private String reference;
	
	@NotNull @PositiveOrZero
	@Column(name = "outdoor_number")	
	private Integer outdoorNumber;
	
	@PositiveOrZero
	@Column(name = "interior_number")
	private Integer interiorMumber;
	
	protected Address() {
	}

	public int getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
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

	public Integer getZipCode() {
		return zipCode;
	}

	public void setZipCode(Integer zipCode) {
		this.zipCode = zipCode;
	}

	public String getReference() {
		return reference;
	}

	public void setReference(String reference) {
		this.reference = reference;
	}

	public Integer getOutdoorNumber() {
		return outdoorNumber;
	}

	public void setOutdoorNumber(Integer outdoorNumber) {
		this.outdoorNumber = outdoorNumber;
	}

	public Integer getInteriorMumber() {
		return interiorMumber;
	}

	public void setInteriorMumber(Integer interiorMumber) {
		this.interiorMumber = interiorMumber;
	}
	
}
