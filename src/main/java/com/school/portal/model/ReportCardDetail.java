package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "report_card_detail")
public class ReportCardDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "detail_id")
	private Integer id;
	
	@JsonIgnore
	@ManyToOne
	@JoinColumn(name = "report_card_fk", referencedColumnName = "report_card_id")
	private ReportCard reportCard;
	
	private byte unit;
	private byte grade;
	
	@Column(name = "is_complementary")
	private boolean isComplementary;

	public ReportCardDetail() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public ReportCard getReportCard() {
		return reportCard;
	}

	public void setReportCard(ReportCard reportCard) {
		this.reportCard = reportCard;
	}

	public byte getUnit() {
		return unit;
	}

	public void setUnit(byte unit) {
		this.unit = unit;
	}

	public byte getGrade() {
		return grade;
	}

	public void setGrade(byte grade) {
		this.grade = grade;
	}

	public boolean isComplementary() {
		return isComplementary;
	}

	public void setComplementary(boolean isComplementary) {
		this.isComplementary = isComplementary;
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
		ReportCardDetail other = (ReportCardDetail) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ReportCardDetail [id=" + id + ", unit=" + unit + ", grade=" + grade + ", isComplementary="
				+ isComplementary + "]";
	}

}
