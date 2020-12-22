package com.rsaame.pas.dao.model;

// Generated Jun 7, 2012 4:36:09 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJO;

/**
 * TTrnSectionLocation generated by hbm2java
 */
public class TTrnSectionLocation extends POJO implements java.io.Serializable {

	private TTrnSectionLocationId id;
	private String tslActiveFlag;
	private Date tslValidityStartDate;
	private Date tslValidityExpiryDate;
	private Integer tslPreparedBy;
	private Date tslPreparedDt;
	private Integer tslModifiedBy;
	private Date tslModifiedDt;
	private Long tslBasicRiskId;

	public TTrnSectionLocation() {
	}

	public TTrnSectionLocation(TTrnSectionLocationId id,
			Date tslValidityExpiryDate) {
		this.id = id;
		this.tslValidityExpiryDate = tslValidityExpiryDate;
	}

	public TTrnSectionLocation(TTrnSectionLocationId id, String tslActiveFlag,
			Date tslValidityStartDate, Date tslValidityExpiryDate,
			Integer tslPreparedBy, Date tslPreparedDt, Integer tslModifiedBy,
			Date tslModifiedDt, Long tslBasicRiskId) {
		this.id = id;
		this.tslActiveFlag = tslActiveFlag;
		this.tslValidityStartDate = tslValidityStartDate;
		this.tslValidityExpiryDate = tslValidityExpiryDate;
		this.tslPreparedBy = tslPreparedBy;
		this.tslPreparedDt = tslPreparedDt;
		this.tslModifiedBy = tslModifiedBy;
		this.tslModifiedDt = tslModifiedDt;
		this.tslBasicRiskId = tslBasicRiskId;
	}

	public TTrnSectionLocationId getId() {
		return this.id;
	}

	public void setId(TTrnSectionLocationId id) {
		this.id = id;
	}

	public String getTslActiveFlag() {
		return this.tslActiveFlag;
	}

	public void setTslActiveFlag(String tslActiveFlag) {
		this.tslActiveFlag = tslActiveFlag;
	}

	public Date getTslValidityStartDate() {
		return this.tslValidityStartDate;
	}

	public void setTslValidityStartDate(Date tslValidityStartDate) {
		this.tslValidityStartDate = tslValidityStartDate;
	}

	public Date getTslValidityExpiryDate() {
		return this.tslValidityExpiryDate;
	}

	public void setTslValidityExpiryDate(Date tslValidityExpiryDate) {
		this.tslValidityExpiryDate = tslValidityExpiryDate;
	}

	public Integer getTslPreparedBy() {
		return this.tslPreparedBy;
	}

	public void setTslPreparedBy(Integer tslPreparedBy) {
		this.tslPreparedBy = tslPreparedBy;
	}

	public Date getTslPreparedDt() {
		return this.tslPreparedDt;
	}

	public void setTslPreparedDt(Date tslPreparedDt) {
		this.tslPreparedDt = tslPreparedDt;
	}

	public Integer getTslModifiedBy() {
		return this.tslModifiedBy;
	}

	public void setTslModifiedBy(Integer tslModifiedBy) {
		this.tslModifiedBy = tslModifiedBy;
	}

	public Date getTslModifiedDt() {
		return this.tslModifiedDt;
	}

	public void setTslModifiedDt(Date tslModifiedDt) {
		this.tslModifiedDt = tslModifiedDt;
	}

	public Long getTslBasicRiskId() {
		return this.tslBasicRiskId;
	}

	public void setTslBasicRiskId(Long tslBasicRiskId) {
		this.tslBasicRiskId = tslBasicRiskId;
	}

	@Override
	public void setPreparedDate( Date preparedDate ){
		setTslPreparedDt( preparedDate );
	}
	
	@Override
	public Date getPreparedDate(){
		return getTslPreparedDt();
	}
}