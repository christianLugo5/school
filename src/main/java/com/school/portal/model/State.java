package com.school.portal.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "country_state")
public class State {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_state")
	private int id;
	private String state;
	private String abbreviation;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "country_fk", referencedColumnName = "country_id")
	private Country country;

	@OneToOne(mappedBy = "state")
	private City city;

	private State() {
	}

	private State(String state, String abbreviation) {
		this.state = state;
		this.abbreviation = abbreviation;
	}

	public int getId() {
		return id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation;
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public City getCity() {
		return city;
	}

	public void setCity(City city) {
		this.city = city;
	}

}
