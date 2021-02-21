package com.school.portal.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "report_card_detail")
public class ReportCardDetail {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id_detail")
	private int id;
	@Column(name = "fk_report_card")
	private int reportCard;
	private byte unit;
	private byte grade;
	@Column(name = "is_complementary")
	private boolean isComplementary;

	private ReportCardDetail() {
	}

	private ReportCardDetail(int reportCard, byte unit, byte grade, boolean isComplementary) {
		this.reportCard = reportCard;
		this.unit = unit;
		this.grade = grade;
		this.isComplementary = isComplementary;
	}

	public int getId() {
		return id;
	}

	public int getReportCard() {
		return reportCard;
	}

	public void setReportCard(int reportCard) {
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

}
