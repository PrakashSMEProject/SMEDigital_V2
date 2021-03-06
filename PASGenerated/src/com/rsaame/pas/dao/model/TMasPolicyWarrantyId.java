package com.rsaame.pas.dao.model;

// Generated May 17, 2012 10:25:33 AM by Hibernate Tools 3.4.0.CR1

/**
 * TMasPolicyWarrantyId generated by hbm2java
 */
public class TMasPolicyWarrantyId implements java.io.Serializable {

	private short pwClCode;
	private short pwPtCode;
	private short pwCode;
	private int pwCovCode;
	private int pwSchCode;

	public TMasPolicyWarrantyId() {
	}

	public TMasPolicyWarrantyId(short pwClCode, short pwPtCode, short pwCode,
			int pwCovCode, int pwSchCode) {
		this.pwClCode = pwClCode;
		this.pwPtCode = pwPtCode;
		this.pwCode = pwCode;
		this.pwCovCode = pwCovCode;
		this.pwSchCode = pwSchCode;
	}

	public short getPwClCode() {
		return this.pwClCode;
	}

	public void setPwClCode(short pwClCode) {
		this.pwClCode = pwClCode;
	}

	public short getPwPtCode() {
		return this.pwPtCode;
	}

	public void setPwPtCode(short pwPtCode) {
		this.pwPtCode = pwPtCode;
	}

	public short getPwCode() {
		return this.pwCode;
	}

	public void setPwCode(short pwCode) {
		this.pwCode = pwCode;
	}

	public int getPwCovCode() {
		return this.pwCovCode;
	}

	public void setPwCovCode(int pwCovCode) {
		this.pwCovCode = pwCovCode;
	}

	public int getPwSchCode() {
		return this.pwSchCode;
	}

	public void setPwSchCode(int pwSchCode) {
		this.pwSchCode = pwSchCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMasPolicyWarrantyId))
			return false;
		TMasPolicyWarrantyId castOther = (TMasPolicyWarrantyId) other;

		return (this.getPwClCode() == castOther.getPwClCode())
				&& (this.getPwPtCode() == castOther.getPwPtCode())
				&& (this.getPwCode() == castOther.getPwCode())
				&& (this.getPwCovCode() == castOther.getPwCovCode())
				&& (this.getPwSchCode() == castOther.getPwSchCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getPwClCode();
		result = 37 * result + this.getPwPtCode();
		result = 37 * result + this.getPwCode();
		result = 37 * result + this.getPwCovCode();
		result = 37 * result + this.getPwSchCode();
		return result;
	}

}
