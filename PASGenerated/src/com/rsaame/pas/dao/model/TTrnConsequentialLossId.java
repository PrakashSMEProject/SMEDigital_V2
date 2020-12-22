package com.rsaame.pas.dao.model;

// Generated Jun 29, 2012 4:20:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnConsequentialLossId generated by hbm2java
 */
public class TTrnConsequentialLossId implements java.io.Serializable, POJOId {

	private long colPolicyId;
	private long colEndtId;
	private Date colValidityStartDate;

	public TTrnConsequentialLossId() {
	}

	public TTrnConsequentialLossId(long colPolicyId, long colEndtId,
			Date colValidityStartDate) {
		this.colPolicyId = colPolicyId;
		this.colEndtId = colEndtId;
		this.colValidityStartDate = colValidityStartDate;
	}

	public long getColPolicyId() {
		return this.colPolicyId;
	}

	public void setColPolicyId(long colPolicyId) {
		this.colPolicyId = colPolicyId;
	}

	public long getColEndtId() {
		return this.colEndtId;
	}

	public void setColEndtId(long colEndtId) {
		this.colEndtId = colEndtId;
	}

	public Date getColValidityStartDate() {
		return this.colValidityStartDate;
	}

	public void setColValidityStartDate(Date colValidityStartDate) {
		this.colValidityStartDate = colValidityStartDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnConsequentialLossId))
			return false;
		TTrnConsequentialLossId castOther = (TTrnConsequentialLossId) other;

		return (this.getColPolicyId() == castOther.getColPolicyId())
				&& (this.getColEndtId() == castOther.getColEndtId())
				&& ((this.getColValidityStartDate() == castOther
						.getColValidityStartDate()) || (this
						.getColValidityStartDate() != null
						&& castOther.getColValidityStartDate() != null && this
						.getColValidityStartDate().equals(
								castOther.getColValidityStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getColPolicyId();
		result = 37 * result + (int) this.getColEndtId();
		result = 37
				* result
				+ (getColValidityStartDate() == null ? 0 : this
						.getColValidityStartDate().hashCode());
		return result;
	}

}