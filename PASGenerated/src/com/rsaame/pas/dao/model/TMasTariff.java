package com.rsaame.pas.dao.model;

// Generated Feb 18, 2012 3:49:05 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TMasTariff generated by hbm2java
 */
public class TMasTariff implements java.io.Serializable {

	private short tarCode;
	private String tarADesc;
	private String tarEDesc;
	private Integer tarPreparedBy;
	private Date tarPreparedDt;
	private Integer tarModifiedBy;
	private Date tarModifiedDt;
	private Date tarEffectiveDate;
	private Date tarExpiryDate;
	private Byte tarStatus;
	private Boolean tarManualRating;
	private BigDecimal tarLocationCode;
	private Integer tarDefaultPolType;
	private Boolean tarConfigInd;
	private Date tarConfigDate;
	private String tarPrePkgFlag;

	public TMasTariff() {
	}

	public TMasTariff(short tarCode) {
		this.tarCode = tarCode;
	}

	public TMasTariff(short tarCode, String tarADesc, String tarEDesc,
			Integer tarPreparedBy, Date tarPreparedDt, Integer tarModifiedBy,
			Date tarModifiedDt, Date tarEffectiveDate, Date tarExpiryDate,
			Byte tarStatus, Boolean tarManualRating,
			BigDecimal tarLocationCode, Integer tarDefaultPolType,
			Boolean tarConfigInd, Date tarConfigDate, String tarPrePkgFlag) {
		this.tarCode = tarCode;
		this.tarADesc = tarADesc;
		this.tarEDesc = tarEDesc;
		this.tarPreparedBy = tarPreparedBy;
		this.tarPreparedDt = tarPreparedDt;
		this.tarModifiedBy = tarModifiedBy;
		this.tarModifiedDt = tarModifiedDt;
		this.tarEffectiveDate = tarEffectiveDate;
		this.tarExpiryDate = tarExpiryDate;
		this.tarStatus = tarStatus;
		this.tarManualRating = tarManualRating;
		this.tarLocationCode = tarLocationCode;
		this.tarDefaultPolType = tarDefaultPolType;
		this.tarConfigInd = tarConfigInd;
		this.tarConfigDate = tarConfigDate;
		this.tarPrePkgFlag = tarPrePkgFlag;
	}

	public short getTarCode() {
		return this.tarCode;
	}

	public void setTarCode(short tarCode) {
		this.tarCode = tarCode;
	}

	public String getTarADesc() {
		return this.tarADesc;
	}

	public void setTarADesc(String tarADesc) {
		this.tarADesc = tarADesc;
	}

	public String getTarEDesc() {
		return this.tarEDesc;
	}

	public void setTarEDesc(String tarEDesc) {
		this.tarEDesc = tarEDesc;
	}

	public Integer getTarPreparedBy() {
		return this.tarPreparedBy;
	}

	public void setTarPreparedBy(Integer tarPreparedBy) {
		this.tarPreparedBy = tarPreparedBy;
	}

	public Date getTarPreparedDt() {
		return this.tarPreparedDt;
	}

	public void setTarPreparedDt(Date tarPreparedDt) {
		this.tarPreparedDt = tarPreparedDt;
	}

	public Integer getTarModifiedBy() {
		return this.tarModifiedBy;
	}

	public void setTarModifiedBy(Integer tarModifiedBy) {
		this.tarModifiedBy = tarModifiedBy;
	}

	public Date getTarModifiedDt() {
		return this.tarModifiedDt;
	}

	public void setTarModifiedDt(Date tarModifiedDt) {
		this.tarModifiedDt = tarModifiedDt;
	}

	public Date getTarEffectiveDate() {
		return this.tarEffectiveDate;
	}

	public void setTarEffectiveDate(Date tarEffectiveDate) {
		this.tarEffectiveDate = tarEffectiveDate;
	}

	public Date getTarExpiryDate() {
		return this.tarExpiryDate;
	}

	public void setTarExpiryDate(Date tarExpiryDate) {
		this.tarExpiryDate = tarExpiryDate;
	}

	public Byte getTarStatus() {
		return this.tarStatus;
	}

	public void setTarStatus(Byte tarStatus) {
		this.tarStatus = tarStatus;
	}

	public Boolean getTarManualRating() {
		return this.tarManualRating;
	}

	public void setTarManualRating(Boolean tarManualRating) {
		this.tarManualRating = tarManualRating;
	}

	public BigDecimal getTarLocationCode() {
		return this.tarLocationCode;
	}

	public void setTarLocationCode(BigDecimal tarLocationCode) {
		this.tarLocationCode = tarLocationCode;
	}

	public Integer getTarDefaultPolType() {
		return this.tarDefaultPolType;
	}

	public void setTarDefaultPolType(Integer tarDefaultPolType) {
		this.tarDefaultPolType = tarDefaultPolType;
	}

	public Boolean getTarConfigInd() {
		return this.tarConfigInd;
	}

	public void setTarConfigInd(Boolean tarConfigInd) {
		this.tarConfigInd = tarConfigInd;
	}

	public Date getTarConfigDate() {
		return this.tarConfigDate;
	}

	public void setTarConfigDate(Date tarConfigDate) {
		this.tarConfigDate = tarConfigDate;
	}

	public String getTarPrePkgFlag() {
		return this.tarPrePkgFlag;
	}

	public void setTarPrePkgFlag(String tarPrePkgFlag) {
		this.tarPrePkgFlag = tarPrePkgFlag;
	}

}
