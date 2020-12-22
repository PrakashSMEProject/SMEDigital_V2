package com.rsaame.pas.dao.model;

// Generated Jul 10, 2012 6:11:54 PM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * TTrnMarineHeaderQuo generated by hbm2java
 */
public class TTrnMarineHeaderQuo extends POJO implements java.io.Serializable {

    private static final long serialVersionUID = 1L;
    private TTrnMarineHeaderQuoId id;
    private long mhPolicyId;
    private long mhEndtId;
    private long mhOpenId;
    private Long mhDeclarationNo;
    private Date mhDeclarationDate;
    private Long mhCertificateNo;
    private Date mhCertificateDate;
    private Integer mhStampDuty;
    private Integer mhSettlementCurrency;
    private BigDecimal mhExchangeRate;
    private Integer mhSelectedTransit;
    private Integer mhSettlingAgent;
    private String mhASubjectMatterDesc;
    private String mhESubjectMatterDesc;
    private Byte mhStatus;
    private Date mhValidityExpiryDate;
    private String mhATranshipment;
    private String mhETranshipment;
    private Integer mhESettlementLoc;
    private Long mhLcId;
    private Integer mhPreparedBy;
    private Date mhPreparedDt;
    private Integer mhModifiedBy;
    private Date mhModifiedDt;
    private Date mhStartDate;
    private Date mhEndDate;
    private Long mhSingleTransitLimit;

    public TTrnMarineHeaderQuo() {
    	//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
    }

    public TTrnMarineHeaderQuo(TTrnMarineHeaderQuoId id, long mhPolicyId,
	    long mhEndtId, long mhOpenId, Date mhValidityExpiryDate) {
	this.id = id;
	this.mhPolicyId = mhPolicyId;
	this.mhEndtId = mhEndtId;
	this.mhOpenId = mhOpenId;
	this.mhValidityExpiryDate = mhValidityExpiryDate;
    }

    public TTrnMarineHeaderQuo(TTrnMarineHeaderQuoId id, long mhPolicyId,
	    long mhEndtId, long mhOpenId, Long mhDeclarationNo,
	    Date mhDeclarationDate, Long mhCertificateNo,
	    Date mhCertificateDate, Integer mhStampDuty,
	    Integer mhSettlementCurrency, BigDecimal mhExchangeRate,
	    Integer mhSelectedTransit, Integer mhSettlingAgent,
	    String mhASubjectMatterDesc, String mhESubjectMatterDesc,
	    Byte mhStatus, Date mhValidityExpiryDate, String mhATranshipment,
	    String mhETranshipment, Integer mhESettlementLoc, Long mhLcId,
	    Integer mhPreparedBy, Date mhPreparedDt, Integer mhModifiedBy,
	    Date mhModifiedDt, Date mhStartDate, Date mhEndDate,
	    Long mhSingleTransitLimit) {
	this.id = id;
	this.mhPolicyId = mhPolicyId;
	this.mhEndtId = mhEndtId;
	this.mhOpenId = mhOpenId;
	this.mhDeclarationNo = mhDeclarationNo;
	this.mhDeclarationDate = mhDeclarationDate;
	this.mhCertificateNo = mhCertificateNo;
	this.mhCertificateDate = mhCertificateDate;
	this.mhStampDuty = mhStampDuty;
	this.mhSettlementCurrency = mhSettlementCurrency;
	this.mhExchangeRate = mhExchangeRate;
	this.mhSelectedTransit = mhSelectedTransit;
	this.mhSettlingAgent = mhSettlingAgent;
	this.mhASubjectMatterDesc = mhASubjectMatterDesc;
	this.mhESubjectMatterDesc = mhESubjectMatterDesc;
	this.mhStatus = mhStatus;
	this.mhValidityExpiryDate = mhValidityExpiryDate;
	this.mhATranshipment = mhATranshipment;
	this.mhETranshipment = mhETranshipment;
	this.mhESettlementLoc = mhESettlementLoc;
	this.mhLcId = mhLcId;
	this.mhPreparedBy = mhPreparedBy;
	this.mhPreparedDt = mhPreparedDt;
	this.mhModifiedBy = mhModifiedBy;
	this.mhModifiedDt = mhModifiedDt;
	this.mhStartDate = mhStartDate;
	this.mhEndDate = mhEndDate;
	this.mhSingleTransitLimit = mhSingleTransitLimit;
    }

    public TTrnMarineHeaderQuoId getId() {
	return this.id;
    }

    public void setId(TTrnMarineHeaderQuoId id) {
	this.id = id;
    }

    public long getMhPolicyId() {
	return this.mhPolicyId;
    }

    public void setMhPolicyId(long mhPolicyId) {
	this.mhPolicyId = mhPolicyId;
    }

    public long getMhEndtId() {
	return this.mhEndtId;
    }

    public void setMhEndtId(long mhEndtId) {
	this.mhEndtId = mhEndtId;
    }

    public long getMhOpenId() {
	return this.mhOpenId;
    }

    public void setMhOpenId(long mhOpenId) {
	this.mhOpenId = mhOpenId;
    }

    public Long getMhDeclarationNo() {
	return this.mhDeclarationNo;
    }

    public void setMhDeclarationNo(Long mhDeclarationNo) {
	this.mhDeclarationNo = mhDeclarationNo;
    }

    public Date getMhDeclarationDate() {
	return this.mhDeclarationDate;
    }

    public void setMhDeclarationDate(Date mhDeclarationDate) {
	this.mhDeclarationDate = mhDeclarationDate;
    }

    public Long getMhCertificateNo() {
	return this.mhCertificateNo;
    }

    public void setMhCertificateNo(Long mhCertificateNo) {
	this.mhCertificateNo = mhCertificateNo;
    }

    public Date getMhCertificateDate() {
	return this.mhCertificateDate;
    }

    public void setMhCertificateDate(Date mhCertificateDate) {
	this.mhCertificateDate = mhCertificateDate;
    }

    public Integer getMhStampDuty() {
	return this.mhStampDuty;
    }

    public void setMhStampDuty(Integer mhStampDuty) {
	this.mhStampDuty = mhStampDuty;
    }

    public Integer getMhSettlementCurrency() {
	return this.mhSettlementCurrency;
    }

    public void setMhSettlementCurrency(Integer mhSettlementCurrency) {
	this.mhSettlementCurrency = mhSettlementCurrency;
    }

    public BigDecimal getMhExchangeRate() {
	return this.mhExchangeRate;
    }

    public void setMhExchangeRate(BigDecimal mhExchangeRate) {
	this.mhExchangeRate = mhExchangeRate;
    }

    public Integer getMhSelectedTransit() {
	return this.mhSelectedTransit;
    }

    public void setMhSelectedTransit(Integer mhSelectedTransit) {
	this.mhSelectedTransit = mhSelectedTransit;
    }

    public Integer getMhSettlingAgent() {
	return this.mhSettlingAgent;
    }

    public void setMhSettlingAgent(Integer mhSettlingAgent) {
	this.mhSettlingAgent = mhSettlingAgent;
    }

    public String getMhASubjectMatterDesc() {
	return this.mhASubjectMatterDesc;
    }

    public void setMhASubjectMatterDesc(String mhASubjectMatterDesc) {
	this.mhASubjectMatterDesc = mhASubjectMatterDesc;
    }

    public String getMhESubjectMatterDesc() {
	return this.mhESubjectMatterDesc;
    }

    public void setMhESubjectMatterDesc(String mhESubjectMatterDesc) {
	this.mhESubjectMatterDesc = mhESubjectMatterDesc;
    }

    public Byte getMhStatus() {
	return this.mhStatus;
    }

    public void setMhStatus(Byte mhStatus) {
	this.mhStatus = mhStatus;
    }

    public Date getMhValidityExpiryDate() {
	return this.mhValidityExpiryDate;
    }

    public void setMhValidityExpiryDate(Date mhValidityExpiryDate) {
	this.mhValidityExpiryDate = mhValidityExpiryDate;
    }

    public String getMhATranshipment() {
	return this.mhATranshipment;
    }

    public void setMhATranshipment(String mhATranshipment) {
	this.mhATranshipment = mhATranshipment;
    }

    public String getMhETranshipment() {
	return this.mhETranshipment;
    }

    public void setMhETranshipment(String mhETranshipment) {
	this.mhETranshipment = mhETranshipment;
    }

    public Integer getMhESettlementLoc() {
	return this.mhESettlementLoc;
    }

    public void setMhESettlementLoc(Integer mhESettlementLoc) {
	this.mhESettlementLoc = mhESettlementLoc;
    }

    public Long getMhLcId() {
	return this.mhLcId;
    }

    public void setMhLcId(Long mhLcId) {
	this.mhLcId = mhLcId;
    }

    public Integer getMhPreparedBy() {
	return this.mhPreparedBy;
    }

    public void setMhPreparedBy(Integer mhPreparedBy) {
	this.mhPreparedBy = mhPreparedBy;
    }

    public Date getMhPreparedDt() {
	return this.mhPreparedDt;
    }

    public void setMhPreparedDt(Date mhPreparedDt) {
	this.mhPreparedDt = mhPreparedDt;
    }

    public Integer getMhModifiedBy() {
	return this.mhModifiedBy;
    }

    public void setMhModifiedBy(Integer mhModifiedBy) {
	this.mhModifiedBy = mhModifiedBy;
    }

    public Date getMhModifiedDt() {
	return this.mhModifiedDt;
    }

    public void setMhModifiedDt(Date mhModifiedDt) {
	this.mhModifiedDt = mhModifiedDt;
    }

    public Date getMhStartDate() {
	return this.mhStartDate;
    }

    public void setMhStartDate(Date mhStartDate) {
	this.mhStartDate = mhStartDate;
    }

    public Date getMhEndDate() {
	return this.mhEndDate;
    }

    public void setMhEndDate(Date mhEndDate) {
	this.mhEndDate = mhEndDate;
    }

    public Long getMhSingleTransitLimit() {
	return this.mhSingleTransitLimit;
    }

    public void setMhSingleTransitLimit(Long mhSingleTransitLimit) {
	this.mhSingleTransitLimit = mhSingleTransitLimit;
    }
    
    @Override
	public POJOId getPOJOId(){
		return this.getId();
	}

    @Override
    public void setPOJOId(POJOId id) {
	setId((TTrnMarineHeaderQuoId) id);
    }

    @Override
    public int getStatus() {
	// TODO Auto-generated method stub
	return getMhStatus();
    }

    @Override
    public void setStatus(Integer status) {
	setMhStatus(status.byteValue());
    }

    @Override
    public void setEndtId(Long endtId) {
	setMhEndtId(endtId);
    }

    @Override
    public void setEndtNo(Long endtNo) {
		
		//SONARFIX -- 26/04/2018 -- DO NOTHING IN METHOD.

    }

    @Override
    public void setValidityStartDate(Date vsd) {
	getId().setMhValidityStartDate(vsd);
    }

    @Override
    public void setValidityExpiryDate(Date ved) {
	setMhValidityExpiryDate(ved);
    }

    @Override
	public void setPreparedDate( Date preparedDate ){
		setMhPreparedDt( preparedDate );
	}
    
    @Override
	public Date getPreparedDate(){
		return getMhPreparedDt();
	}
}
