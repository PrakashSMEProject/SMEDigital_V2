package com.rsaame.pas.dao.model;

// Generated Feb 9, 2012 5:55:56 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;

/**
 * VParCntDeductiblesId generated by hbm2java
 */
public class VParCntDeductiblesId implements java.io.Serializable {

	private Short schCode;
	private Integer tariff;
	private Integer riskType;
	private String riskTypeDesc;
	private BigDecimal compulsoryExcess;

	public VParCntDeductiblesId() {
	}

	public VParCntDeductiblesId(Short schCode, Integer tariff,
			Integer riskType, String riskTypeDesc, String riskTypeADesc,
			BigDecimal compulsoryExcess) {
		this.schCode = schCode;
		this.tariff = tariff;
		this.riskType = riskType;
		this.riskTypeDesc = riskTypeDesc;
		this.compulsoryExcess = compulsoryExcess;
	}

	public Short getSchCode() {
		return this.schCode;
	}

	public void setSchCode(Short schCode) {
		this.schCode = schCode;
	}

	public Integer getTariff() {
		return this.tariff;
	}

	public void setTariff(Integer tariff) {
		this.tariff = tariff;
	}

	public Integer getRiskType() {
		return this.riskType;
	}

	public void setRiskType(Integer riskType) {
		this.riskType = riskType;
	}

	public String getRiskTypeDesc() {
		return this.riskTypeDesc;
	}

	public void setRiskTypeDesc(String riskTypeDesc) {
		this.riskTypeDesc = riskTypeDesc;
	}

	public BigDecimal getCompulsoryExcess() {
		return this.compulsoryExcess;
	}

	public void setCompulsoryExcess(BigDecimal compulsoryExcess) {
		this.compulsoryExcess = compulsoryExcess;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VParCntDeductiblesId))
			return false;
		VParCntDeductiblesId castOther = (VParCntDeductiblesId) other;

		return ((this.getSchCode() == castOther.getSchCode()) || (this
				.getSchCode() != null && castOther.getSchCode() != null && this
				.getSchCode().equals(castOther.getSchCode())))
				&& ((this.getTariff() == castOther.getTariff()) || (this
						.getTariff() != null && castOther.getTariff() != null && this
						.getTariff().equals(castOther.getTariff())))
				&& ((this.getRiskType() == castOther.getRiskType()) || (this
						.getRiskType() != null
						&& castOther.getRiskType() != null && this
						.getRiskType().equals(castOther.getRiskType())))
				&& ((this.getRiskTypeDesc() == castOther.getRiskTypeDesc()) || (this
						.getRiskTypeDesc() != null
						&& castOther.getRiskTypeDesc() != null && this
						.getRiskTypeDesc().equals(castOther.getRiskTypeDesc())))
				&& ((this.getCompulsoryExcess() == castOther
						.getCompulsoryExcess()) || (this.getCompulsoryExcess() != null
						&& castOther.getCompulsoryExcess() != null && this
						.getCompulsoryExcess().equals(
								castOther.getCompulsoryExcess())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getSchCode() == null ? 0 : this.getSchCode().hashCode());
		result = 37 * result
				+ (getTariff() == null ? 0 : this.getTariff().hashCode());
		result = 37 * result
				+ (getRiskType() == null ? 0 : this.getRiskType().hashCode());
		result = 37
				* result
				+ (getRiskTypeDesc() == null ? 0 : this.getRiskTypeDesc()
						.hashCode());
		result = 37
				* result
				+ (getCompulsoryExcess() == null ? 0 : this
						.getCompulsoryExcess().hashCode());
		return result;
	}

}