package com.rsaame.pas.dao.model;

// Generated Mar 7, 2012 5:39:16 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnGaccCashDetailsQuoId generated by hbm2java
 */
public class TTrnGaccCashDetailsQuoId implements java.io.Serializable, POJOId {

	private long gcdId;
	private Date gcdValidityStartDate;

	public TTrnGaccCashDetailsQuoId() {
	}

	public TTrnGaccCashDetailsQuoId(long gcdId, Date gcdValidityStartDate) {
		this.gcdId = gcdId;
		this.gcdValidityStartDate = gcdValidityStartDate;
	}

	public long getGcdId() {
		return this.gcdId;
	}

	public void setGcdId(long gcdId) {
		this.gcdId = gcdId;
	}

	public Date getGcdValidityStartDate() {
		return this.gcdValidityStartDate;
	}

	public void setGcdValidityStartDate(Date gcdValidityStartDate) {
		this.gcdValidityStartDate = gcdValidityStartDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnGaccCashDetailsQuoId))
			return false;
		TTrnGaccCashDetailsQuoId castOther = (TTrnGaccCashDetailsQuoId) other;

		return (this.getGcdId() == castOther.getGcdId())
				&& ((this.getGcdValidityStartDate() == castOther
						.getGcdValidityStartDate()) || (this
						.getGcdValidityStartDate() != null
						&& castOther.getGcdValidityStartDate() != null && this
						.getGcdValidityStartDate().equals(
								castOther.getGcdValidityStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getGcdId();
		result = 37
				* result
				+ (getGcdValidityStartDate() == null ? 0 : this
						.getGcdValidityStartDate().hashCode());
		return result;
	}

}