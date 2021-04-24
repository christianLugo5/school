package com.school.portal.model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

enum CycleType {
	SEMESTER, QUARTER;
}

@Entity
public class Career {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "career_id")
	private Integer id;

	@NotEmpty
	@Size(min = 3, max = 45)
	private String career;

	@NotEmpty
	@Size(min = 3, max = 25)
	private String identifier;

	@NotNull
	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime start;

	@DateTimeFormat(iso = ISO.DATE_TIME)
	private LocalDateTime end;

	@NotNull
	private Boolean available;

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(name = "cycle_type")
	private CycleType cycleType;

	@Positive
	private byte max_duration;
	
	@ManyToMany
	@JoinTable(joinColumns = {@JoinColumn(name = "career_fk")}, inverseJoinColumns = {@JoinColumn(name = "course_fk")})
	private Set<Course> course = new HashSet<Course>();

	public Career() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getCareer() {
		return career;
	}

	public void setCareer(String career) {
		this.career = career.strip().replaceAll("\\s+", " ");
	}

	public String getIdentifier() {
		return identifier;
	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier.strip().replaceAll("\\s+", " ");
	}

	public LocalDateTime getStart() {
		return start;
	}

	public void setStart(LocalDateTime start) {
		this.start = start;
	}

	public LocalDateTime getEnd() {
		return end;
	}

	public void setEnd(LocalDateTime end) {
		this.end = end;
	}

	public Boolean getAvailable() {
		return available;
	}

	public void setAvailable(Boolean available) {
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

	public Set<Course> getCourses() {
		return course;
	}

	public void setCourses(Set<Course> courses) {
		this.course = courses;
	}
	
	public void addCourse(Course course) {
		this.course.add(course);
	}
	
	public void removeCourse(int id) {
		this.course.removeIf(course -> course.getId() == id);
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
		Career other = (Career) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Career [id=" + id + ", career=" + career + ", identifier=" + identifier + ", start=" + start + ", end="
				+ end + ", available=" + available + ", cycleType=" + cycleType + ", max_duration=" + max_duration
				+ ", course=" + course + "]";
	}
	
}
