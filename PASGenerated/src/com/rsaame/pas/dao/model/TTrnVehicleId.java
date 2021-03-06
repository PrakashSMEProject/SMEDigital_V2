package com.rsaame.pas.dao.model;

// Generated Sep 28, 2012 10:51:08 AM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TTrnVehicleId generated by hbm2java
 */
public class TTrnVehicleId implements java.io.Serializable {

	private long vehPolicyId;
	private long vehId;
	private Date vehValidityStartDate;

	public TTrnVehicleId() {
	}

	public TTrnVehicleId(long vehPolicyId, long vehId, Date vehValidityStartDate) {
		this.vehPolicyId = vehPolicyId;
		this.vehId = vehId;
		this.vehValidityStartDate = vehValidityStartDate;
	}

	public long getVehPolicyId() {
		return this.vehPolicyId;
	}

	public void setVehPolicyId(long vehPolicyId) {
		this.vehPolicyId = vehPolicyId;
	}

	public long getVehId() {
		return this.vehId;
	}

	public void setVehId(long vehId) {
		this.vehId = vehId;
	}

	public Date getVehValidityStartDate() {
		return this.vehValidityStartDate;
	}

	public void setVehValidityStartDate(Date vehValidityStartDate) {
		this.vehValidityStartDate = vehValidityStartDate;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnVehicleId))
			return false;
		TTrnVehicleId castOther = (TTrnVehicleId) other;

		return (this.getVehPolicyId() == castOther.getVehPolicyId())
				&& (this.getVehId() == castOther.getVehId())
				&& ((this.getVehValidityStartDate() == castOther
						.getVehValidityStartDate()) || (this
						.getVehValidityStartDate() != null
						&& castOther.getVehValidityStartDate() != null && this
						.getVehValidityStartDate().equals(
								castOther.getVehValidityStartDate())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getVehPolicyId();
		result = 37 * result + (int) this.getVehId();
		result = 37
				* result
				+ (getVehValidityStartDate() == null ? 0 : this
						.getVehValidityStartDate().hashCode());
		return result;
	}

}
