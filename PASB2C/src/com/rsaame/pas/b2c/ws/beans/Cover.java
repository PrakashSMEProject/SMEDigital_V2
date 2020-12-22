package com.rsaame.pas.b2c.ws.beans;

public class Cover {
	
	private short covCode;
	private short covTypeCode;
	private short covSubTypeCode;
	private Integer covCriteriaCode;
	
	
	public short getCovCode() {
		return covCode;
	}
	public void setCovCode(short covCode) {
		this.covCode = covCode;
	}
	public short getCovTypeCode() {
		return covTypeCode;
	}
	public void setCovTypeCode(short covTypeCode) {
		this.covTypeCode = covTypeCode;
	}
	public short getCovSubTypeCode() {
		return covSubTypeCode;
	}
	public void setCovSubTypeCode(short covSubTypeCode) {
		this.covSubTypeCode = covSubTypeCode;
	}
	public Integer getCovCriteriaCode() {
		return covCriteriaCode;
	}
	public void setCovCriteriaCode(Integer covCriteriaCode) {
		this.covCriteriaCode = covCriteriaCode;
	}

}
