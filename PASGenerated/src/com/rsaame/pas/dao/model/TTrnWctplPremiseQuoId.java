package com.rsaame.pas.dao.model;

// Generated Mar 3, 2012 11:52:17 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnWctplPremiseQuoId generated by hbm2java
 */
public class TTrnWctplPremiseQuoId implements java.io.Serializable,POJOId {

	private long wbdId;
	private Date wbdValidityStartDate;

	public TTrnWctplPremiseQuoId() {
	}

	public TTrnWctplPremiseQuoId(long wbdId, Date wbdValidityStartDate) {
		this.wbdId = wbdId;
		this.wbdValidityStartDate = wbdValidityStartDate;
	}

	public long getWbdId() {
		return this.wbdId;
	}

	public void setWbdId(long wbdId) {
		this.wbdId = wbdId;
	}

	public Date getWbdValidityStartDate() {
		return this.wbdValidityStartDate;
	}

	public void setWbdValidityStartDate(Date wbdValidityStartDate) {
		this.wbdValidityStartDate = wbdValidityStartDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnWctplPremiseQuoId))
			return false;
		TTrnWctplPremiseQuoId castOther = (TTrnWctplPremiseQuoId) other;

		return (this.getWbdId() == castOther.getWbdId())
				&& ((this.getWbdValidityStartDate() == castOther
						.getWbdValidityStartDate()) || (this
						.getWbdValidityStartDate() != null
						&& castOther.getWbdValidityStartDate() != null && this
						.getWbdValidityStartDate().equals(
								castOther.getWbdValidityStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getWbdId();
		result = 37
				* result
				+ (getWbdValidityStartDate() == null ? 0 : this
						.getWbdValidityStartDate().hashCode());
		return result;
	}

}