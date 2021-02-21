package com.school.portal.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

enum CycleType {
	SEMESTER, QUARTER;
}

@Entity
public class Career {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_career")
	private int id;
	private String career;
	private String identifier;
	private Date start;
	private Date end;
	private boolean available;
	@Column(name = "cycle_type")
	private CycleType cycleType;
	private byte max_duration;

	private Career() {
	}

	private Career(String career, String identifier, Date start, Date end, boolean available, CycleType cycleType,
			byte max_duration) {
		this.career = career;
		this.identifier = identifier;
		this.start = start;
		this.end = end;
		this.available = available;
		this.cycleType = cycleType;
		this.max_duration = max_duration;
	}

	public int getId() {
		return id;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

	public Date getStart() {
		return start;
	}

	public void setStart(Date start) {
		this.start = start;
	}

	public Date getEnd() {
		return end;
	}

	public void setEnd(Date end) {
		this.end = end;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public CycleType getCycleType() {
		return cycleType;
	}

	public void setCycleType(CycleType cycleType) {
		this.cycleType = cycleType;
	}

	public byte getMax_duration() {
		return max_duration;
	}

	public void setMax_duration(byte max_duration) {
		this.max_duration = max_duration;
	}

}
