package com.school.portal.model;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Country {

	@Id @GeneratedValue(strategy = GenerationType.IDENTITY) @Column(name = "country_id")
	private Integer id;

	@NotEmpty @Size(min = 3, max = 45)
	private String name;
	
	@NotEmpty @Size(min = 1, max = 2)
	private String code;

	@JsonIgnore
	@OneToMany(mappedBy = "country")
	private Set<State> state = new HashSet<>();

	public Country() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name.strip().replaceAll("\\s+", " ");
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code.strip().replaceAll("\\s+", " ");
	}

	public Set<State> getState() {
		return state;
	}

	public void setState(Set<State> state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "Country {id=" + id + ", name=" + name + ", code=" + code + ", state=" + state + "}";
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
		Country other = (Country) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
