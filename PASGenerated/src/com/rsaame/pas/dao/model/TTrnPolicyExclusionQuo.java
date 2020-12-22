package com.rsaame.pas.dao.model;

import java.util.Date;

// Generated May 17, 2012 9:13:53 AM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnPolicyExclusionQuo generated by hbm2java
 */
public class TTrnPolicyExclusionQuo implements java.io.Serializable {

	private TTrnPolicyExclusionQuoId id;
	private Integer tpeCovCode;
	private Integer tpeCtCode;
	private Integer tpeCstCode;
	private Date tpeValidityStartDate;
	private Date tpeValidityEndDate;
	
	private Byte tpeStatus;
	private Integer tpePreparedBy;
	private Date tpePreparedDt;
	private Integer tpeModifiedBy;
	private Date tpeModifiedDt;
	private String tpeAmount;
	private Boolean tpePrintFlag;

	public TTrnPolicyExclusionQuo() {
	}

	public TTrnPolicyExclusionQuo(TTrnPolicyExclusionQuoId id,Integer tpeCovCode, Integer tpeCtCode, Integer tpeCstCode,
			Date tpeValidityStartDate, Date tpeValidityEndDate, Byte tpeStatus, Integer tpePreparedBy, Date tpePreparedDt,
			Integer tpeModifiedBy, Date tpeModifiedDt, String tpeAmount,
			Boolean tpePrintFlag) {
		this.id = id;
		this.tpeCovCode = tpeCovCode;
		this.tpeCtCode = tpeCtCode;
		this.tpeCstCode = tpeCstCode;
		this.tpeValidityStartDate = tpeValidityStartDate;
		this.tpeValidityEndDate = tpeValidityEndDate;
		this.tpeStatus = tpeStatus;
		this.tpePreparedBy = tpePreparedBy;
		this.tpePreparedDt = tpePreparedDt;
		this.tpeModifiedBy = tpeModifiedBy;
		this.tpeModifiedDt = tpeModifiedDt;
		this.tpeAmount = tpeAmount;
		this.tpePrintFlag = tpePrintFlag;
	}

	public TTrnPolicyExclusionQuoId getId() {
		return this.id;
	}

	public void setId(TTrnPolicyExclusionQuoId id) {
		this.id = id;
	}

	public Integer getTpeCovCode() {
		return this.tpeCovCode;
	}

	public void setTpeCovCode(Integer tpeCovCode) {
		this.tpeCovCode = tpeCovCode;
	}

	public Integer getTpeCtCode() {
		return this.tpeCtCode;
	}

	public void setTpeCtCode(Integer tpeCtCode) {
		this.tpeCtCode = tpeCtCode;
	}

	public Integer getTpeCstCode() {
		return this.tpeCstCode;
	}

	public void setTpeCstCode(Integer tpeCstCode) {
		this.tpeCstCode = tpeCstCode;
	}

	public Date getTpeValidityStartDate() {
		return this.tpeValidityStartDate;
	}

	public void setTpeValidityStartDate(Date tpeValidityStartDate) {
		this.tpeValidityStartDate = tpeValidityStartDate;
	}

	public Date getTpeValidityEndDate() {
		return this.tpeValidityEndDate;
	}

	public void setTpeValidityEndDate(Date tpeValidityEndDate) {
		this.tpeValidityEndDate = tpeValidityEndDate;
	}
	public Byte getTpeStatus() {
		return this.tpeStatus;
	}

	public void setTpeStatus(Byte tpeStatus) {
		this.tpeStatus = tpeStatus;
	}

	public Integer getTpePreparedBy() {
		return this.tpePreparedBy;
	}

	public void setTpePreparedBy(Integer tpePreparedBy) {
		this.tpePreparedBy = tpePreparedBy;
	}

	public Date getTpePreparedDt() {
		return this.tpePreparedDt;
	}

	public void setTpePreparedDt(Date tpePreparedDt) {
		this.tpePreparedDt = tpePreparedDt;
	}

	public Integer getTpeModifiedBy() {
		return this.tpeModifiedBy;
	}

	public void setTpeModifiedBy(Integer tpeModifiedBy) {
		this.tpeModifiedBy = tpeModifiedBy;
	}

	public Date getTpeModifiedDt() {
		return this.tpeModifiedDt;
	}

	public void setTpeModifiedDt(Date tpeModifiedDt) {
		this.tpeModifiedDt = tpeModifiedDt;
	}

	public String getTpeAmount() {
		return this.tpeAmount;
	}

	public void setTpeAmount(String tpeAmount) {
		this.tpeAmount = tpeAmount;
	}

	public Boolean getTpePrintFlag() {
		return this.tpePrintFlag;
	}

	public void setTpePrintFlag(Boolean tpePrintFlag) {
		this.tpePrintFlag = tpePrintFlag;
	}
	


	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnPolicyExclusionQuo))
			return false;
		TTrnPolicyExclusionQuo castOther = (TTrnPolicyExclusionQuo) other;

		return ((this.getTpeCovCode() == castOther.getTpeCovCode()) || (this
						.getTpeCovCode() != null
						&& castOther.getTpeCovCode() != null && this
						.getTpeCovCode().equals(castOther.getTpeCovCode())))
				&& ((this.getTpeCtCode() == castOther.getTpeCtCode()) || (this
						.getTpeCtCode() != null
						&& castOther.getTpeCtCode() != null && this
						.getTpeCtCode().equals(castOther.getTpeCtCode())))
				&& ((this.getTpeCstCode() == castOther.getTpeCstCode()) || (this
						.getTpeCstCode() != null
						&& castOther.getTpeCstCode() != null && this
						.getTpeCstCode().equals(castOther.getTpeCstCode())))
				&& ((this.getTpeValidityStartDate() == castOther
						.getTpeValidityStartDate()) || (this
						.getTpeValidityStartDate() != null
						&& castOther.getTpeValidityStartDate() != null && this
						.getTpeValidityStartDate().equals(
								castOther.getTpeValidityStartDate())))
				&& ((this.getTpeValidityEndDate() == castOther
						.getTpeValidityEndDate()) || (this
						.getTpeValidityEndDate() != null
						&& castOther.getTpeValidityEndDate() != null && this
						.getTpeValidityEndDate().equals(
								castOther.getTpeValidityEndDate())))
				&& ((this.getTpeStatus() == castOther.getTpeStatus()) || (this
						.getTpeStatus() != null
						&& castOther.getTpeStatus() != null && this
						.getTpeStatus().equals(castOther.getTpeStatus())))
				&& ((this.getTpePreparedBy() == castOther.getTpePreparedBy()) || (this
						.getTpePreparedBy() != null
						&& castOther.getTpePreparedBy() != null && this
						.getTpePreparedBy()
						.equals(castOther.getTpePreparedBy())))
				&& ((this.getTpePreparedDt() == castOther.getTpePreparedDt()) || (this
						.getTpePreparedDt() != null
						&& castOther.getTpePreparedDt() != null && this
						.getTpePreparedDt()
						.equals(castOther.getTpePreparedDt())))
				&& ((this.getTpeModifiedBy() == castOther.getTpeModifiedBy()) || (this
						.getTpeModifiedBy() != null
						&& castOther.getTpeModifiedBy() != null && this
						.getTpeModifiedBy()
						.equals(castOther.getTpeModifiedBy())))
				&& ((this.getTpeModifiedDt() == castOther.getTpeModifiedDt()) || (this
						.getTpeModifiedDt() != null
						&& castOther.getTpeModifiedDt() != null && this
						.getTpeModifiedDt()
						.equals(castOther.getTpeModifiedDt())))
				&& ((this.getTpeAmount() == castOther.getTpeAmount()) || (this
						.getTpeAmount() != null
						&& castOther.getTpeAmount() != null && this
						.getTpeAmount().equals(castOther.getTpeAmount())))
				&& ((this.getTpePrintFlag() == castOther.getTpePrintFlag()) || (this
						.getTpePrintFlag() != null
						&& castOther.getTpePrintFlag() != null && this
						.getTpePrintFlag().equals(castOther.getTpePrintFlag())));
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getTpeCovCode() == null ? 0 : this.getTpeCovCode()
						.hashCode());
		result = 37 * result
				+ (getTpeCtCode() == null ? 0 : this.getTpeCtCode().hashCode());
		result = 37
				* result
				+ (getTpeCstCode() == null ? 0 : this.getTpeCstCode()
						.hashCode());
		result = 37
				* result
				+ (getTpeValidityStartDate() == null ? 0 : this
						.getTpeValidityStartDate().hashCode());
		result = 37
				* result
				+ (getTpeValidityEndDate() == null ? 0 : this
						.getTpeValidityEndDate().hashCode());
		result = 37 * result
				+ (getTpeStatus() == null ? 0 : this.getTpeStatus().hashCode());
		result = 37
				* result
				+ (getTpePreparedBy() == null ? 0 : this.getTpePreparedBy()
						.hashCode());
		result = 37
				* result
				+ (getTpePreparedDt() == null ? 0 : this.getTpePreparedDt()
						.hashCode());
		result = 37
				* result
				+ (getTpeModifiedBy() == null ? 0 : this.getTpeModifiedBy()
						.hashCode());
		result = 37
				* result
				+ (getTpeModifiedDt() == null ? 0 : this.getTpeModifiedDt()
						.hashCode());
		result = 37 * result
				+ (getTpeAmount() == null ? 0 : this.getTpeAmount().hashCode());
		result = 37
				* result
				+ (getTpePrintFlag() == null ? 0 : this.getTpePrintFlag()
						.hashCode());
		return result;
	}
	
}