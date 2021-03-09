package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

@Entity
@Table(name = "access_recover")
public class AccessRecover {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "recover_id")
	private int id;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "password_fk", referencedColumnName = "password_id")
	private AccessPassword password;
	
	@NotEmpty
	@Size(min = 10, max = 128)
	private String token;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date creation;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private Date expiration;

	public AccessRecover() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public AccessPassword getPassword() {
		return password;
	}

	public void setPassword(AccessPassword password) {
		this.password = password;
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
