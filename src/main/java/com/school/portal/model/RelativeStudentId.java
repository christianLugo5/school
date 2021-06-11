package com.school.portal.model;

import java.io.Serializable;

import javax.persistence.Embeddable;

@Embeddable
public class RelativeStudentId implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer relativeId;
	private Integer studentId;

	public RelativeStudentId() {
	}

	public RelativeStudentId(Integer relativeId, Integer studentId) {
		super();
		this.relativeId = relativeId;
		this.studentId = studentId;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((relativeId == null) ? 0 : relativeId.hashCode());
		result = prime * result + ((studentId == null) ? 0 : studentId.hashCode());
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
		RelativeStudentId other = (RelativeStudentId) obj;
		if (relativeId == null) {
			if (other.relativeId != null)
				return false;
		} else if (!relativeId.equals(other.relativeId))
			return false;
		if (studentId == null) {
			if (other.studentId != null)
				return false;
		} else if (!studentId.equals(other.studentId))
			return false;
		return true;
	}

}
