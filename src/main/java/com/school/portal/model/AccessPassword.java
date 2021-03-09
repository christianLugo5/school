package com.school.portal.model;

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

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "access_password")
public class AccessPassword {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "password_id")
	private int id;
	
	@NotEmpty
	@Size(min = 5, max = 128)
	private String password;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "access_fk", referencedColumnName = "access_id")
	private Access access;
	
	@JsonIgnore
	@OneToOne(mappedBy = "password")
	private AccessRecover recover;

	public AccessPassword() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Access getAccess() {
		return access;
	}

	public void setAccess(Access access) {
		this.access = access;
	}

	public AccessRecover getRecover() {
		return recover;
	}

	public void setRecover(AccessRecover recover) {
		this.recover = recover;
	}

}
