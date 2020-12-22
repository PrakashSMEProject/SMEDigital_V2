package com.rsaame.pas.dao.model;

// Generated May 17, 2012 9:15:39 AM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnPolicyExclusionId generated by hbm2java
 */
public class TTrnPolicyExclusionId implements java.io.Serializable {

	private long tpePolicyId;
	private long tpeEndtId;
	private int tpeClCode;
	private int tpePtCode;
	private int tpeCode;
	private long tpeRskId;

	public TTrnPolicyExclusionId() {
	}

	public TTrnPolicyExclusionId(long tpePolicyId, long tpeEndtId,
			int tpeClCode, int tpePtCode, int tpeCode, long tpeRskId) {
		this.tpePolicyId = tpePolicyId;
		this.tpeEndtId = tpeEndtId;
		this.tpeClCode = tpeClCode;
		this.tpePtCode = tpePtCode;
		this.tpeCode = tpeCode;
		this.tpeRskId = tpeRskId;
	}

	public long getTpePolicyId() {
		return this.tpePolicyId;
	}

	public void setTpePolicyId(long tpePolicyId) {
		this.tpePolicyId = tpePolicyId;
	}

	public long getTpeEndtId() {
		return this.tpeEndtId;
	}

	public void setTpeEndtId(long tpeEndtId) {
		this.tpeEndtId = tpeEndtId;
	}

	public int getTpeClCode() {
		return this.tpeClCode;
	}

	public void setTpeClCode(int tpeClCode) {
		this.tpeClCode = tpeClCode;
	}

	public int getTpePtCode() {
		return this.tpePtCode;
	}

	public void setTpePtCode(int tpePtCode) {
		this.tpePtCode = tpePtCode;
	}

	public int getTpeCode() {
		return this.tpeCode;
	}

	public void setTpeCode(int tpeCode) {
		this.tpeCode = tpeCode;
	}

	public long getTpeRskId() {
		return this.tpeRskId;
	}

	public void setTpeRskId(long tpeRskId) {
		this.tpeRskId = tpeRskId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnPolicyExclusionId))
			return false;
		TTrnPolicyExclusionId castOther = (TTrnPolicyExclusionId) other;

		return (this.getTpePolicyId() == castOther.getTpePolicyId())
				&& (this.getTpeEndtId() == castOther.getTpeEndtId())
				&& (this.getTpeClCode() == castOther.getTpeClCode())
				&& (this.getTpePtCode() == castOther.getTpePtCode())
				&& (this.getTpeCode() == castOther.getTpeCode())
				&& (this.getTpeRskId() == castOther.getTpeRskId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getTpePolicyId();
		result = 37 * result + (int) this.getTpeEndtId();
		result = 37 * result + this.getTpeClCode();
		result = 37 * result + this.getTpePtCode();
		result = 37 * result + this.getTpeCode();
		result = 37 * result + (int) this.getTpeRskId();
		return result;
	}

}