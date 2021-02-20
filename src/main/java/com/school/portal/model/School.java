package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class School {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_school")	
	private int id;
	private String identifier;
	private String name;
	@Column(name = "is_headquarters")
	private boolean isHeadquarters;
	@Column(name = "fk_address")
	private int fkAddress;
	
	private School() {}
	private School(String identifier, String name, boolean isHeadquarters, int fkAddress) {
		this.identifier = identifier;
		this.name = name;
		this.isHeadquarters = isHeadquarters;
		this.fkAddress = fkAddress;
	}
	
	public int getId() {
		return id;
	}
	public String getIdentifier() {
		return identifier;
	}
	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isHeadquarters() {
		return isHeadquarters;
	}
	public void setHeadquarters(boolean isHeadquarters) {
		this.isHeadquarters = isHeadquarters;
	}
	public int getFkAddress() {
		return fkAddress;
	}
	public void setFkAddress(int fkAddress) {
		this.fkAddress = fkAddress;
	}
			
}
