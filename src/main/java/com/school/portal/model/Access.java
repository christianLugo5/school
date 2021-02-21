package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Access {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_access")
	private int id;
	private String user;
	@Column(name = "fk_person")
	private int person;
	@Column(name = "last_login")
	private Date lastLogin;
	private Date created;
	@Column(name = "wrong_login_attempt")
	private int wrongLoginAttempt;

	private Access() {
	}

	private Access(String user, int person, Date lastLogin, Date created, int wrongLoginAttempt) {
		this.user = user;
		this.person = person;
		this.lastLogin = lastLogin;
		this.created = created;
		this.wrongLoginAttempt = wrongLoginAttempt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public int getPerson() {
		return person;
	}

	public void setPerson(int person) {
		this.person = person;
	}

	public Date getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(Date lastLogin) {
		this.lastLogin = lastLogin;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public int getWrongLoginAttempt() {
		return wrongLoginAttempt;
	}

	public void setWrongLoginAttempt(int wrongLoginAttempt) {
		this.wrongLoginAttempt = wrongLoginAttempt;
	}

}
