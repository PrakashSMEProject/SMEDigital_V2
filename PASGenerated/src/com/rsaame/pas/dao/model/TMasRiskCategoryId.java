package com.rsaame.pas.dao.model;

// Generated Feb 1, 2012 11:52:36 PM by Hibernate Tools 3.4.0.CR1

/**
 * TMasRiskCategoryId generated by hbm2java
 */
public class TMasRiskCategoryId implements java.io.Serializable {

	private int rcRskCode;
	private int rcRtCode;
	private int rcCode;

	public TMasRiskCategoryId() {
	}

	public TMasRiskCategoryId(int rcRskCode, int rcRtCode, int rcCode) {
		this.rcRskCode = rcRskCode;
		this.rcRtCode = rcRtCode;
		this.rcCode = rcCode;
	}

	public int getRcRskCode() {
		return this.rcRskCode;
	}

	public void setRcRskCode(int rcRskCode) {
		this.rcRskCode = rcRskCode;
	}

	public int getRcRtCode() {
		return this.rcRtCode;
	}

	public void setRcRtCode(int rcRtCode) {
		this.rcRtCode = rcRtCode;
	}

	public int getRcCode() {
		return this.rcCode;
	}

	public void setRcCode(int rcCode) {
		this.rcCode = rcCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMasRiskCategoryId))
			return false;
		TMasRiskCategoryId castOther = (TMasRiskCategoryId) other;

		return (this.getRcRskCode() == castOther.getRcRskCode())
				&& (this.getRcRtCode() == castOther.getRcRtCode())
				&& (this.getRcCode() == castOther.getRcCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getRcRskCode();
		result = 37 * result + this.getRcRtCode();
		result = 37 * result + this.getRcCode();
		return result;
	}

}