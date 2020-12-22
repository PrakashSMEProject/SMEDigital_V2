package com.rsaame.pas.dao.model;

// Generated Jun 29, 2012 4:20:03 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnConsequentialLoss generated by hbm2java
 */
public class TTrnConsequentialLoss extends POJO implements java.io.Serializable {

	private TTrnConsequentialLossId id;
	private long colGpSumInsured;
	private short colHazardCategory;
	private short colRiskClass;
	private byte colIndemnityPeriod;
	private Date colValidityExpiryDate;
	private Byte colStatus;
	private Integer colPreparedBy;
	private Date colPreparedDt;
	private Integer colModifiedBy;
	private Date colModifiedDt;
	private String colDescription;
	private String colPremiseDesc;
	private String colPremiseADesc;
	private Long colBldId;
	private BigDecimal colEmlPerc;
	private Long colEmlSi;

	public TTrnConsequentialLoss() {
	}

	public TTrnConsequentialLoss(TTrnConsequentialLossId id,
			long colGpSumInsured, short colHazardCategory, short colRiskClass,
			byte colIndemnityPeriod, Date colValidityExpiryDate) {
		this.id = id;
		this.colGpSumInsured = colGpSumInsured;
		this.colHazardCategory = colHazardCategory;
		this.colRiskClass = colRiskClass;
		this.colIndemnityPeriod = colIndemnityPeriod;
		this.colValidityExpiryDate = colValidityExpiryDate;
	}

	public TTrnConsequentialLoss(TTrnConsequentialLossId id,
			long colGpSumInsured, short colHazardCategory, short colRiskClass,
			byte colIndemnityPeriod, Date colValidityExpiryDate,
			Byte colStatus, Integer colPreparedBy, Date colPreparedDt,
			Integer colModifiedBy, Date colModifiedDt, String colDescription,
			String colPremiseDesc, String colPremiseADesc, Long colBldId,
			BigDecimal colEmlPerc, Long colEmlSi) {
		this.id = id;
		this.colGpSumInsured = colGpSumInsured;
		this.colHazardCategory = colHazardCategory;
		this.colRiskClass = colRiskClass;
		this.colIndemnityPeriod = colIndemnityPeriod;
		this.colValidityExpiryDate = colValidityExpiryDate;
		this.colStatus = colStatus;
		this.colPreparedBy = colPreparedBy;
		this.colPreparedDt = colPreparedDt;
		this.colModifiedBy = colModifiedBy;
		this.colModifiedDt = colModifiedDt;
		this.colDescription = colDescription;
		this.colPremiseDesc = colPremiseDesc;
		this.colPremiseADesc = colPremiseADesc;
		this.colBldId = colBldId;
		this.colEmlPerc = colEmlPerc;
		this.colEmlSi = colEmlSi;
	}

	
	public TTrnConsequentialLossId getId() {
		return this.id;
	}

	public void setId(TTrnConsequentialLossId id) {
		this.id = id;
	}

	public long getColGpSumInsured() {
		return this.colGpSumInsured;
	}

	public void setColGpSumInsured(long colGpSumInsured) {
		this.colGpSumInsured = colGpSumInsured;
	}

	public short getColHazardCategory() {
		return this.colHazardCategory;
	}

	public void setColHazardCategory(short colHazardCategory) {
		this.colHazardCategory = colHazardCategory;
	}

	public short getColRiskClass() {
		return this.colRiskClass;
	}

	public void setColRiskClass(short colRiskClass) {
		this.colRiskClass = colRiskClass;
	}

	public byte getColIndemnityPeriod() {
		return this.colIndemnityPeriod;
	}

	public void setColIndemnityPeriod(byte colIndemnityPeriod) {
		this.colIndemnityPeriod = colIndemnityPeriod;
	}

	public Date getColValidityExpiryDate() {
		return this.colValidityExpiryDate;
	}

	public void setColValidityExpiryDate(Date colValidityExpiryDate) {
		this.colValidityExpiryDate = colValidityExpiryDate;
	}

	public Byte getColStatus() {
		return this.colStatus;
	}

	public void setColStatus(Byte colStatus) {
		this.colStatus = colStatus;
	}

	public Integer getColPreparedBy() {
		return this.colPreparedBy;
	}

	public void setColPreparedBy(Integer colPreparedBy) {
		this.colPreparedBy = colPreparedBy;
	}

	public Date getColPreparedDt() {
		return this.colPreparedDt;
	}

	public void setColPreparedDt(Date colPreparedDt) {
		this.colPreparedDt = colPreparedDt;
	}

	public Integer getColModifiedBy() {
		return this.colModifiedBy;
	}

	public void setColModifiedBy(Integer colModifiedBy) {
		this.colModifiedBy = colModifiedBy;
	}

	public Date getColModifiedDt() {
		return this.colModifiedDt;
	}

	public void setColModifiedDt(Date colModifiedDt) {
		this.colModifiedDt = colModifiedDt;
	}

	public String getColDescription() {
		return this.colDescription;
	}

	public void setColDescription(String colDescription) {
		this.colDescription = colDescription;
	}

	public String getColPremiseDesc() {
		return this.colPremiseDesc;
	}

	public void setColPremiseDesc(String colPremiseDesc) {
		this.colPremiseDesc = colPremiseDesc;
	}

	public String getColPremiseADesc() {
		return this.colPremiseADesc;
	}

	public void setColPremiseADesc(String colPremiseADesc) {
		this.colPremiseADesc = colPremiseADesc;
	}

	public Long getColBldId() {
		return this.colBldId;
	}

	public void setColBldId(Long colBldId) {
		this.colBldId = colBldId;
	}

	public BigDecimal getColEmlPerc() {
		return this.colEmlPerc;
	}

	public void setColEmlPerc(BigDecimal colEmlPerc) {
		this.colEmlPerc = colEmlPerc;
	}

	public Long getColEmlSi() {
		return this.colEmlSi;
	}

	public void setColEmlSi(Long colEmlSi) {
		this.colEmlSi = colEmlSi;
	}
	@Override
	public int getStatus(){
		return getColStatus();
	}
	
	@Override
	public void setStatus( Integer status ){
		setColStatus( status.byteValue() );
	}
	
	@Override
	public void setEndtId( Long endtId ){
		if( !Utils.isEmpty( getId() ) ) getId().setColEndtId(endtId);
	}
	
	@Override
	public POJOId getPOJOId(){
		return this.getId();
	}
	
	@Override
	public void setPOJOId( POJOId id ){
		setId((TTrnConsequentialLossId)id);
	}
	
	@Override
	public void setValidityExpiryDate( Date ved ){
		setColValidityExpiryDate(ved);
	}
	
	@Override
	public void setValidityStartDate( Date vsd ){
		if( !Utils.isEmpty( getId() ) ) getId().setColValidityStartDate(vsd);
	}

	@Override
	public void setPreparedDate( Date preparedDate ){
		setColPreparedDt( preparedDate );
	}
	
	@Override
	public Date getPreparedDate(){
		return getColPreparedDt();
	}
}
