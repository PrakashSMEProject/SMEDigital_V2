package com.rsaame.pas.dao.model;

// Generated Feb 1, 2012 11:52:36 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TMasClass generated by hbm2java
 */
public class TMasClass implements java.io.Serializable {

	private short clCode;
	private String clADesc;
	private String clEDesc;
	private String clAShortDesc;
	private String clEShortDesc;
	private Short clUserCode;
	private Boolean clRiDisplayCode;
	private Integer clPreparedBy;
	private Date clPreparedDt;
	private Integer clModifiedBy;
	private Date clModifiedDt;

	public TMasClass() {
	}

	public TMasClass(short clCode) {
		this.clCode = clCode;
	}

	public TMasClass(short clCode, String clADesc, String clEDesc,
			String clAShortDesc, String clEShortDesc, Short clUserCode,
			Boolean clRiDisplayCode, Integer clPreparedBy, Date clPreparedDt,
			Integer clModifiedBy, Date clModifiedDt) {
		this.clCode = clCode;
		this.clADesc = clADesc;
		this.clEDesc = clEDesc;
		this.clAShortDesc = clAShortDesc;
		this.clEShortDesc = clEShortDesc;
		this.clUserCode = clUserCode;
		this.clRiDisplayCode = clRiDisplayCode;
		this.clPreparedBy = clPreparedBy;
		this.clPreparedDt = clPreparedDt;
		this.clModifiedBy = clModifiedBy;
		this.clModifiedDt = clModifiedDt;
	}

	public short getClCode() {
		return this.clCode;
	}

	public void setClCode(short clCode) {
		this.clCode = clCode;
	}

	public String getClADesc() {
		return this.clADesc;
	}

	public void setClADesc(String clADesc) {
		this.clADesc = clADesc;
	}

	public String getClEDesc() {
		return this.clEDesc;
	}

	public void setClEDesc(String clEDesc) {
		this.clEDesc = clEDesc;
	}

	public String getClAShortDesc() {
		return this.clAShortDesc;
	}

	public void setClAShortDesc(String clAShortDesc) {
		this.clAShortDesc = clAShortDesc;
	}

	public String getClEShortDesc() {
		return this.clEShortDesc;
	}

	public void setClEShortDesc(String clEShortDesc) {
		this.clEShortDesc = clEShortDesc;
	}

	public Short getClUserCode() {
		return this.clUserCode;
	}

	public void setClUserCode(Short clUserCode) {
		this.clUserCode = clUserCode;
	}

	public Boolean getClRiDisplayCode() {
		return this.clRiDisplayCode;
	}

	public void setClRiDisplayCode(Boolean clRiDisplayCode) {
		this.clRiDisplayCode = clRiDisplayCode;
	}

	public Integer getClPreparedBy() {
		return this.clPreparedBy;
	}

	public void setClPreparedBy(Integer clPreparedBy) {
		this.clPreparedBy = clPreparedBy;
	}

	public Date getClPreparedDt() {
		return this.clPreparedDt;
	}

	public void setClPreparedDt(Date clPreparedDt) {
		this.clPreparedDt = clPreparedDt;
	}

	public Integer getClModifiedBy() {
		return this.clModifiedBy;
	}

	public void setClModifiedBy(Integer clModifiedBy) {
		this.clModifiedBy = clModifiedBy;
	}

	public Date getClModifiedDt() {
		return this.clModifiedDt;
	}

	public void setClModifiedDt(Date clModifiedDt) {
		this.clModifiedDt = clModifiedDt;
	}

}
