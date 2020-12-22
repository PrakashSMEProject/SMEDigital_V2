package com.rsaame.pas.dao.model;

// Generated May 8, 2012 4:38:42 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TTrnReminderId generated by hbm2java
 */
public class TTrnReminderId implements java.io.Serializable {

	private boolean remType;
	private long remTypeId;
	private short remSrlNo;
	private int remPreparedBy;
	private Date remPreparedDt;

	public TTrnReminderId() {
	}

	public TTrnReminderId(boolean remType, long remTypeId, short remSrlNo,
			int remPreparedBy, Date remPreparedDt) {
		this.remType = remType;
		this.remTypeId = remTypeId;
		this.remSrlNo = remSrlNo;
		this.remPreparedBy = remPreparedBy;
		this.remPreparedDt = remPreparedDt;
	}

	public boolean isRemType() {
		return this.remType;
	}

	public void setRemType(boolean remType) {
		this.remType = remType;
	}

	public long getRemTypeId() {
		return this.remTypeId;
	}

	public void setRemTypeId(long remTypeId) {
		this.remTypeId = remTypeId;
	}

	public short getRemSrlNo() {
		return this.remSrlNo;
	}

	public void setRemSrlNo(short remSrlNo) {
		this.remSrlNo = remSrlNo;
	}

	public int getRemPreparedBy() {
		return this.remPreparedBy;
	}

	public void setRemPreparedBy(int remPreparedBy) {
		this.remPreparedBy = remPreparedBy;
	}

	public Date getRemPreparedDt() {
		return this.remPreparedDt;
	}

	public void setRemPreparedDt(Date remPreparedDt) {
		this.remPreparedDt = remPreparedDt;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnReminderId))
			return false;
		TTrnReminderId castOther = (TTrnReminderId) other;

		return (this.isRemType() == castOther.isRemType())
				&& (this.getRemTypeId() == castOther.getRemTypeId())
				&& (this.getRemSrlNo() == castOther.getRemSrlNo())
				&& (this.getRemPreparedBy() == castOther.getRemPreparedBy())
				&& ((this.getRemPreparedDt() == castOther.getRemPreparedDt()) || (this
						.getRemPreparedDt() != null
						&& castOther.getRemPreparedDt() != null && this
						.getRemPreparedDt()
						.equals(castOther.getRemPreparedDt())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (this.isRemType() ? 1 : 0);
		result = 37 * result + (int) this.getRemTypeId();
		result = 37 * result + this.getRemSrlNo();
		result = 37 * result + this.getRemPreparedBy();
		result = 37
				* result
				+ (getRemPreparedDt() == null ? 0 : this.getRemPreparedDt()
						.hashCode());
		return result;
	}

}