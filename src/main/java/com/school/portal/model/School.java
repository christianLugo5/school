package com.school.portal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class School {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "school_id")
	private Integer id;

	@NotEmpty
	@Size(min = 2, max = 15)
	private String identifier;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String name;

	@Column(name = "is_headquarters")
	@NotNull
	private Boolean isHeadquarters;

	@NotEmpty
	@Size(min = 12, max = 12)
	private String rfc;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_fk", referencedColumnName = "address_id")
	private Address address;

	public School() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier.strip();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.strip().replaceAll("\\s+", " ");
	}

	public Boolean getIsHeadquarters() {
		return isHeadquarters;
	}

	public void setIsHeadquarters(Boolean isHeadquarters) {
		this.isHeadquarters = isHeadquarters;
	}

	public String getRfc() {
		return rfc;
	}

	public void setRfc(String rfc) {
		this.rfc = rfc.strip().replaceAll("\\s+", "");
	}

	public Address getAddress() {
		return address;
	}

	public void setAddress(Address address) {
		this.address = address;
	}

}
