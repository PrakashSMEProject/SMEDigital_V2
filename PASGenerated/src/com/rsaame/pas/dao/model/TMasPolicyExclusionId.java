package com.rsaame.pas.dao.model;

// Generated May 17, 2012 10:26:02 AM by Hibernate Tools 3.4.0.CR1

/**
 * TMasPolicyExclusionId generated by hbm2java
 */
public class TMasPolicyExclusionId implements java.io.Serializable {

	private int pexClCode;
	private int pexPtCode;
	private int pexCode;
	private int pexCovCode;
	private int pexSchCode;

	public TMasPolicyExclusionId() {
	}

	public TMasPolicyExclusionId(int pexClCode, int pexPtCode, int pexCode,
			int pexCovCode, int pexSchCode) {
		this.pexClCode = pexClCode;
		this.pexPtCode = pexPtCode;
		this.pexCode = pexCode;
		this.pexCovCode = pexCovCode;
		this.pexSchCode = pexSchCode;
	}

	public int getPexClCode() {
		return this.pexClCode;
	}

	public void setPexClCode(int pexClCode) {
		this.pexClCode = pexClCode;
	}

	public int getPexPtCode() {
		return this.pexPtCode;
	}

	public void setPexPtCode(int pexPtCode) {
		this.pexPtCode = pexPtCode;
	}

	public int getPexCode() {
		return this.pexCode;
	}

	public void setPexCode(int pexCode) {
		this.pexCode = pexCode;
	}

	public int getPexCovCode() {
		return this.pexCovCode;
	}

	public void setPexCovCode(int pexCovCode) {
		this.pexCovCode = pexCovCode;
	}

	public int getPexSchCode() {
		return this.pexSchCode;
	}

	public void setPexSchCode(int pexSchCode) {
		this.pexSchCode = pexSchCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMasPolicyExclusionId))
			return false;
		TMasPolicyExclusionId castOther = (TMasPolicyExclusionId) other;

		return (this.getPexClCode() == castOther.getPexClCode())
				&& (this.getPexPtCode() == castOther.getPexPtCode())
				&& (this.getPexCode() == castOther.getPexCode())
				&& (this.getPexCovCode() == castOther.getPexCovCode())
				&& (this.getPexSchCode() == castOther.getPexSchCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPexClCode();
		result = 37 * result + this.getPexPtCode();
		result = 37 * result + this.getPexCode();
		result = 37 * result + this.getPexCovCode();
		result = 37 * result + this.getPexSchCode();
		return result;
	}

}
