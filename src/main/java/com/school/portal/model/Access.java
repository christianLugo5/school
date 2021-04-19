package com.school.portal.model;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Access {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "access_id")
	private Integer id;
	
	@NotEmpty
	@Size(min = 3, max = 128)
	private String user;
	
	@NotNull
	@OneToOne
	@JoinColumn(name = "person_fk", referencedColumnName = "person_id")
	private Person person;
		
	@Column(name = "last_login")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime lastLogin;
	
	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime created;
	
	@Column(name = "wrong_login_attempt")
	@PositiveOrZero
	private int wrongLoginAttempt;
	
	@JsonIgnore
	@OneToOne(mappedBy = "access")
	private AccessPassword password;

	public Access() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public Person getPerson() {
		return person;
	}

	public void setPerson(Person person) {
		this.person = person;
	}

	public LocalDateTime getLastLogin() {
		return lastLogin;
	}

	public void setLastLogin(LocalDateTime lastLogin) {
		this.lastLogin = lastLogin;
	}

	public LocalDateTime getCreated() {
		return created;
	}

	public void setCreated(LocalDateTime created) {
		this.created = created;
	}

	public int getWrongLoginAttempt() {
		return wrongLoginAttempt;
	}

	public void setWrongLoginAttempt(int wrongLoginAttempt) {
		this.wrongLoginAttempt = wrongLoginAttempt;
	}

	public AccessPassword getPassword() {
		return password;
	}

	public void setPassword(AccessPassword password) {
		this.password = password;
	}

}
