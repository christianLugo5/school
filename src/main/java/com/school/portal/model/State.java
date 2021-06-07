package com.school.portal.model;

import java.util.HashSet;
import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "state_country")
public class State {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "state_id")
	private Integer id;

	@NotEmpty @Size(min = 2, max = 45)
	private String state;

	@NotEmpty @Size(min = 2, max = 5)
	private String abbreviation;

	@NotNull
	@ManyToOne @JoinColumn(name = "country_id", referencedColumnName = "country_id")
	private Country country;

	@JsonIgnore
	@OneToMany(mappedBy = "state")
	private Set<City> city = new HashSet<>();

	public State() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state.strip().replaceAll("\\s+", " ");
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation.strip().replaceAll("\\s+", " ");
	}

	public Country getCountry() {
		return country;
	}

	public void setCountry(Country country) {
		this.country = country;
	}

	public Set<City> getCity() {
		return city;
	}

	public void setCity(Set<City> city) {
		this.city = city;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		State other = (State) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "State {id=" + id + ", state=" + state + ", abbreviation=" + abbreviation + ", country=" + country
				+ ", city=" + city + "}";
	}

}
