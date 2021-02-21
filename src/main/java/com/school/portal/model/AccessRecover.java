package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "access_recover")
public class AccessRecover {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_recover")
	private int id;
	@Column(name = "fk_password")
	private int fkPassword;
	private String token;
	private Date creation;
	private Date expiration;

	private AccessRecover() {
	}

	private AccessRecover(int fkPassword, String token, Date creation, Date expiration) {
		this.fkPassword = fkPassword;
		this.token = token;
		this.creation = creation;
		this.expiration = expiration;
	}

	public int getId() {
		return id;
	}

	public int getFkPassword() {
		return fkPassword;
	}

	public void setFkPassword(int fkPassword) {
		this.fkPassword = fkPassword;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Date getCreation() {
		return creation;
	}

	public void setCreation(Date creation) {
		this.creation = creation;
	}

	public Date getExpiration() {
		return expiration;
	}

	public void setExpiration(Date expiration) {
		this.expiration = expiration;
	}

}
