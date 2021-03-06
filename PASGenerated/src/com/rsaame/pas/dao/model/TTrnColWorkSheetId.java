package com.rsaame.pas.dao.model;

// Generated Jun 29, 2012 4:20:03 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TTrnColWorkSheetId generated by hbm2java
 */
public class TTrnColWorkSheetId implements java.io.Serializable {

	private long cwsPolicyId;
	private long cwsEndtId;
	private short cwsItemCode;
	private Date cwsValidityStartDate;

	public TTrnColWorkSheetId() {
	}

	public TTrnColWorkSheetId(long cwsPolicyId, long cwsEndtId,
			short cwsItemCode, Date cwsValidityStartDate) {
		this.cwsPolicyId = cwsPolicyId;
		this.cwsEndtId = cwsEndtId;
		this.cwsItemCode = cwsItemCode;
		this.cwsValidityStartDate = cwsValidityStartDate;
	}

	public long getCwsPolicyId() {
		return this.cwsPolicyId;
	}

	public void setCwsPolicyId(long cwsPolicyId) {
		this.cwsPolicyId = cwsPolicyId;
	}

	public long getCwsEndtId() {
		return this.cwsEndtId;
	}

	public void setCwsEndtId(long cwsEndtId) {
		this.cwsEndtId = cwsEndtId;
	}

	public short getCwsItemCode() {
		return this.cwsItemCode;
	}

	public void setCwsItemCode(short cwsItemCode) {
		this.cwsItemCode = cwsItemCode;
	}

	public Date getCwsValidityStartDate() {
		return this.cwsValidityStartDate;
	}

	public void setCwsValidityStartDate(Date cwsValidityStartDate) {
		this.cwsValidityStartDate = cwsValidityStartDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnColWorkSheetId))
			return false;
		TTrnColWorkSheetId castOther = (TTrnColWorkSheetId) other;

		return (this.getCwsPolicyId() == castOther.getCwsPolicyId())
				&& (this.getCwsEndtId() == castOther.getCwsEndtId())
				&& (this.getCwsItemCode() == castOther.getCwsItemCode())
				&& ((this.getCwsValidityStartDate() == castOther
						.getCwsValidityStartDate()) || (this
						.getCwsValidityStartDate() != null
						&& castOther.getCwsValidityStartDate() != null && this
						.getCwsValidityStartDate().equals(
								castOther.getCwsValidityStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getCwsPolicyId();
		result = 37 * result + (int) this.getCwsEndtId();
		result = 37 * result + this.getCwsItemCode();
		result = 37
				* result
				+ (getCwsValidityStartDate() == null ? 0 : this
						.getCwsValidityStartDate().hashCode());
		return result;
	}

}
