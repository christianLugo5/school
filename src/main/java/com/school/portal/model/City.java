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
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "city_state")
public class City {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "city_id")
	private Integer id;

	@NotEmpty @Size(min = 2, max = 62)
	private String city;

	@NotEmpty @Size(min = 2, max = 25)
	private String abbreviation;

	@ManyToOne
	@JoinColumn(name = "state_fk", referencedColumnName = "state_id", nullable = false)
	private State state;

	@JsonIgnore
	@OneToMany(mappedBy = "city")
	private Set<Address> address = new HashSet<>();

	public City() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city.strip().replaceAll("\\s+", " ");
	}

	public String getAbbreviation() {
		return abbreviation;
	}

	public void setAbbreviation(String abbreviation) {
		this.abbreviation = abbreviation.strip().replaceAll("\\s+", " ");
	}

	public State getState() {
		return state;
	}

	public void setState(State state) {
		this.state = state;
	}

	public Set<Address> getAddress() {
		return address;
	}

	public void setAddress(Set<Address> address) {
		this.address = address;
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
		City other = (City) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "City [id=" + id + ", city=" + city + ", abbreviation=" + abbreviation + ", address=" + address + "]";
	}

}
