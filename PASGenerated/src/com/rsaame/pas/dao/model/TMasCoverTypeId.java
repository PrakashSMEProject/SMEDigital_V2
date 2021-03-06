package com.rsaame.pas.dao.model;

// Generated Feb 1, 2012 11:52:36 PM by Hibernate Tools 3.4.0.CR1

/**
 * TMasCoverTypeId generated by hbm2java
 */
public class TMasCoverTypeId implements java.io.Serializable {

	private short ctClCode;
	private short ctPtCode;
	private short ctCovCode;
	private short ctCode;

	public TMasCoverTypeId() {
	}

	public TMasCoverTypeId(short ctClCode, short ctPtCode, short ctCovCode,
			short ctCode) {
		this.ctClCode = ctClCode;
		this.ctPtCode = ctPtCode;
		this.ctCovCode = ctCovCode;
		this.ctCode = ctCode;
	}

	public short getCtClCode() {
		return this.ctClCode;
	}

	public void setCtClCode(short ctClCode) {
		this.ctClCode = ctClCode;
	}

	public short getCtPtCode() {
		return this.ctPtCode;
	}

	public void setCtPtCode(short ctPtCode) {
		this.ctPtCode = ctPtCode;
	}

	public short getCtCovCode() {
		return this.ctCovCode;
	}

	public void setCtCovCode(short ctCovCode) {
		this.ctCovCode = ctCovCode;
	}

	public short getCtCode() {
		return this.ctCode;
	}

	public void setCtCode(short ctCode) {
		this.ctCode = ctCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TMasCoverTypeId))
			return false;
		TMasCoverTypeId castOther = (TMasCoverTypeId) other;

		return (this.getCtClCode() == castOther.getCtClCode())
				&& (this.getCtPtCode() == castOther.getCtPtCode())
				&& (this.getCtCovCode() == castOther.getCtCovCode())
				&& (this.getCtCode() == castOther.getCtCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + this.getCtClCode();
		result = 37 * result + this.getCtPtCode();
		result = 37 * result + this.getCtCovCode();
		result = 37 * result + this.getCtCode();
		return result;
	}

}
