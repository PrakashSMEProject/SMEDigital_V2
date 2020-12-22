package com.rsaame.pas.dao.model;

// Generated May 17, 2012 9:14:45 AM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnPolicyConditionId generated by hbm2java
 */
public class TTrnPolicyConditionId implements java.io.Serializable {

	private long tpcPolicyId;
	private long tpcEndtId;
	private int tpcClCode;
	private int tpcPtCode;
	private int tpcCode;

	public TTrnPolicyConditionId() {
	}

	public TTrnPolicyConditionId(long tpcPolicyId, long tpcEndtId,
			int tpcClCode, int tpcPtCode, int tpcCode) {
		this.tpcPolicyId = tpcPolicyId;
		this.tpcEndtId = tpcEndtId;
		this.tpcClCode = tpcClCode;
		this.tpcPtCode = tpcPtCode;
		this.tpcCode = tpcCode;
	}

	public long getTpcPolicyId() {
		return this.tpcPolicyId;
	}

	public void setTpcPolicyId(long tpcPolicyId) {
		this.tpcPolicyId = tpcPolicyId;
	}

	public long getTpcEndtId() {
		return this.tpcEndtId;
	}

	public void setTpcEndtId(long tpcEndtId) {
		this.tpcEndtId = tpcEndtId;
	}

	public int getTpcClCode() {
		return this.tpcClCode;
	}

	public void setTpcClCode(int tpcClCode) {
		this.tpcClCode = tpcClCode;
	}

	public int getTpcPtCode() {
		return this.tpcPtCode;
	}

	public void setTpcPtCode(int tpcPtCode) {
		this.tpcPtCode = tpcPtCode;
	}

	public int getTpcCode() {
		return this.tpcCode;
	}

	public void setTpcCode(int tpcCode) {
		this.tpcCode = tpcCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnPolicyConditionId))
			return false;
		TTrnPolicyConditionId castOther = (TTrnPolicyConditionId) other;

		return (this.getTpcPolicyId() == castOther.getTpcPolicyId())
				&& (this.getTpcEndtId() == castOther.getTpcEndtId())
				&& (this.getTpcClCode() == castOther.getTpcClCode())
				&& (this.getTpcPtCode() == castOther.getTpcPtCode())
				&& (this.getTpcCode() == castOther.getTpcCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getTpcPolicyId();
		result = 37 * result + (int) this.getTpcEndtId();
		result = 37 * result + this.getTpcClCode();
		result = 37 * result + this.getTpcPtCode();
		result = 37 * result + this.getTpcCode();
		return result;
	}

}