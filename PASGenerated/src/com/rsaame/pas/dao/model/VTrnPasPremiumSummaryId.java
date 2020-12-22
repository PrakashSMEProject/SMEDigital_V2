package com.rsaame.pas.dao.model;

// Generated May 1, 2012 12:48:39 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * VTrnPasPremiumSummaryId generated by hbm2java
 */
public class VTrnPasPremiumSummaryId implements java.io.Serializable {

	private Long linkingId;
	private Long policyId;
	private Long endtId;
	private Short class_;
	private Short secId;
	private String secName;
	private Long locationId;
	private String locationName;
	private BigDecimal coverId;
	

	public VTrnPasPremiumSummaryId() {
	}

	public VTrnPasPremiumSummaryId(Long linkingId, Long policyId, Long endtId,
			Short class_, Short secId, String secName, Long locationId,
			String locationName, BigDecimal coverId, BigDecimal commission,
			BigDecimal coverSiAmt, BigDecimal coverPrmAmt, Date valStartDate,
			Date valExpDate, Byte status, Character polQuoFlag) {
		this.linkingId = linkingId;
		this.policyId = policyId;
		this.endtId = endtId;
		this.class_ = class_;
		this.secId = secId;
		this.secName = secName;
		this.locationId = locationId;
		this.locationName = locationName;
		this.coverId = coverId;
		
	}

	public Long getLinkingId() {
		return this.linkingId;
	}

	public void setLinkingId(Long linkingId) {
		this.linkingId = linkingId;
	}

	public Long getPolicyId() {
		return this.policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Long getEndtId() {
		return this.endtId;
	}

	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}

	public Short getClass_() {
		return this.class_;
	}

	public void setClass_(Short class_) {
		this.class_ = class_;
	}

	public Short getSecId() {
		return this.secId;
	}

	public void setSecId(Short secId) {
		this.secId = secId;
	}

	public String getSecName() {
		return this.secName;
	}

	public void setSecName(String secName) {
		this.secName = secName;
	}

	public Long getLocationId() {
		return this.locationId;
	}

	public void setLocationId(Long locationId) {
		this.locationId = locationId;
	}

	public String getLocationName() {
		return this.locationName;
	}

	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}

	public BigDecimal getCoverId() {
		return this.coverId;
	}

	public void setCoverId(BigDecimal coverId) {
		this.coverId = coverId;
	}

	

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof VTrnPasPremiumSummaryId))
			return false;
		VTrnPasPremiumSummaryId castOther = (VTrnPasPremiumSummaryId) other;

		return ((this.getLinkingId() == castOther.getLinkingId()) || (this
				.getLinkingId() != null && castOther.getLinkingId() != null && this
				.getLinkingId().equals(castOther.getLinkingId())))
				&& ((this.getPolicyId() == castOther.getPolicyId()) || (this
						.getPolicyId() != null
						&& castOther.getPolicyId() != null && this
						.getPolicyId().equals(castOther.getPolicyId())))
				&& ((this.getEndtId() == castOther.getEndtId()) || (this
						.getEndtId() != null && castOther.getEndtId() != null && this
						.getEndtId().equals(castOther.getEndtId())))
				&& ((this.getClass_() == castOther.getClass_()) || (this
						.getClass_() != null && castOther.getClass_() != null && this
						.getClass_().equals(castOther.getClass_())))
				&& ((this.getSecId() == castOther.getSecId()) || (this
						.getSecId() != null && castOther.getSecId() != null && this
						.getSecId().equals(castOther.getSecId())))
				&& ((this.getSecName() == castOther.getSecName()) || (this
						.getSecName() != null && castOther.getSecName() != null && this
						.getSecName().equals(castOther.getSecName())))
				&& ((this.getLocationId() == castOther.getLocationId()) || (this
						.getLocationId() != null
						&& castOther.getLocationId() != null && this
						.getLocationId().equals(castOther.getLocationId())))
				&& ((this.getLocationName() == castOther.getLocationName()) || (this
						.getLocationName() != null
						&& castOther.getLocationName() != null && this
						.getLocationName().equals(castOther.getLocationName())))
				&& ((this.getCoverId() == castOther.getCoverId()) || (this
						.getCoverId() != null && castOther.getCoverId() != null && this
						.getCoverId().equals(castOther.getCoverId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLinkingId() == null ? 0 : this.getLinkingId().hashCode());
		result = 37 * result
				+ (getPolicyId() == null ? 0 : this.getPolicyId().hashCode());
		result = 37 * result
				+ (getEndtId() == null ? 0 : this.getEndtId().hashCode());
		result = 37 * result
				+ (getClass_() == null ? 0 : this.getClass_().hashCode());
		result = 37 * result
				+ (getSecId() == null ? 0 : this.getSecId().hashCode());
		result = 37 * result
				+ (getSecName() == null ? 0 : this.getSecName().hashCode());
		result = 37
				* result
				+ (getLocationId() == null ? 0 : this.getLocationId()
						.hashCode());
		result = 37
				* result
				+ (getLocationName() == null ? 0 : this.getLocationName()
						.hashCode());
		result = 37 * result
				+ (getCoverId() == null ? 0 : this.getCoverId().hashCode());
		
		return result;
	}

}