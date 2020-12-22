package com.rsaame.pas.dao.model;

// Generated May 17, 2012 10:26:02 AM by Hibernate Tools 3.4.0.CR1

import java.math.BigDecimal;
import java.util.Date;

/**
 * TMasPolicyExclusion generated by hbm2java
 */
public class TMasPolicyExclusion implements java.io.Serializable {

	private TMasPolicyExclusionId id;
	private String pexAText;
	private String pexEText;
	private Character pexYn;
	private Integer pexDefaultInd;
	private Integer pexCtCode;
	private Integer pexCstCode;
	private Integer pexUserCode;
	private Integer pexPhrCode;
	private String pexAmount;
	private Integer pexPreparedBy;
	private Date pexPreparedDt;
	private Integer pexModifiedBy;
	private Date pexModifiedDt;
	private String pexDocRef;
	private Boolean pexDocFlag;
	private Date pexEffectiveDate;
	private Date pexExpiryDate;
	private Integer pexOldCode;

	public TMasPolicyExclusion() {
	}

	public TMasPolicyExclusion(TMasPolicyExclusionId id) {
		this.id = id;
	}

	public TMasPolicyExclusion(TMasPolicyExclusionId id, String pexAText,
			String pexEText, Character pexYn, Integer pexDefaultInd,
			Integer pexCtCode, Integer pexCstCode, Integer pexUserCode,
			Integer pexPhrCode, String pexAmount, Integer pexPreparedBy,
			Date pexPreparedDt, Integer pexModifiedBy, Date pexModifiedDt,
			String pexDocRef, Boolean pexDocFlag) {
		this.id = id;
		this.pexAText = pexAText;
		this.pexEText = pexEText;
		this.pexYn = pexYn;
		this.pexDefaultInd = pexDefaultInd;
		this.pexCtCode = pexCtCode;
		this.pexCstCode = pexCstCode;
		this.pexUserCode = pexUserCode;
		this.pexPhrCode = pexPhrCode;
		this.pexAmount = pexAmount;
		this.pexPreparedBy = pexPreparedBy;
		this.pexPreparedDt = pexPreparedDt;
		this.pexModifiedBy = pexModifiedBy;
		this.pexModifiedDt = pexModifiedDt;
		this.pexDocRef = pexDocRef;
		this.pexDocFlag = pexDocFlag;
	}

	public TMasPolicyExclusionId getId() {
		return this.id;
	}

	public void setId(TMasPolicyExclusionId id) {
		this.id = id;
	}

	public String getPexAText() {
		return this.pexAText;
	}

	public void setPexAText(String pexAText) {
		this.pexAText = pexAText;
	}

	public String getPexEText() {
		return this.pexEText;
	}

	public void setPexEText(String pexEText) {
		this.pexEText = pexEText;
	}

	public Character getPexYn() {
		return this.pexYn;
	}

	public void setPexYn(Character pexYn) {
		this.pexYn = pexYn;
	}

	public Integer getPexDefaultInd() {
		return this.pexDefaultInd;
	}

	public void setPexDefaultInd(Integer pexDefaultInd) {
		this.pexDefaultInd = pexDefaultInd;
	}

	public Integer getPexCtCode() {
		return this.pexCtCode;
	}

	public void setPexCtCode(Integer pexCtCode) {
		this.pexCtCode = pexCtCode;
	}

	public Integer getPexCstCode() {
		return this.pexCstCode;
	}

	public void setPexCstCode(Integer pexCstCode) {
		this.pexCstCode = pexCstCode;
	}

	public Integer getPexUserCode() {
		return this.pexUserCode;
	}

	public void setPexUserCode(Integer pexUserCode) {
		this.pexUserCode = pexUserCode;
	}

	public Integer getPexPhrCode() {
		return this.pexPhrCode;
	}

	public void setPexPhrCode(Integer pexPhrCode) {
		this.pexPhrCode = pexPhrCode;
	}

	public String getPexAmount() {
		return this.pexAmount;
	}

	public void setPexAmount(String pexAmount) {
		this.pexAmount = pexAmount;
	}

	public Integer getPexPreparedBy() {
		return this.pexPreparedBy;
	}

	public void setPexPreparedBy(Integer pexPreparedBy) {
		this.pexPreparedBy = pexPreparedBy;
	}

	public Date getPexPreparedDt() {
		return this.pexPreparedDt;
	}

	public void setPexPreparedDt(Date pexPreparedDt) {
		this.pexPreparedDt = pexPreparedDt;
	}

	public Integer getPexModifiedBy() {
		return this.pexModifiedBy;
	}

	public void setPexModifiedBy(Integer pexModifiedBy) {
		this.pexModifiedBy = pexModifiedBy;
	}

	public Date getPexModifiedDt() {
		return this.pexModifiedDt;
	}

	public void setPexModifiedDt(Date pexModifiedDt) {
		this.pexModifiedDt = pexModifiedDt;
	}

	public String getPexDocRef() {
		return this.pexDocRef;
	}

	public void setPexDocRef(String pexDocRef) {
		this.pexDocRef = pexDocRef;
	}

	public Boolean getPexDocFlag() {
		return this.pexDocFlag;
	}

	public void setPexDocFlag(Boolean pexDocFlag) {
		this.pexDocFlag = pexDocFlag;
	}

	public void setPexEffectiveDate( Date pexEffectiveDate ){
		this.pexEffectiveDate = pexEffectiveDate;
	}

	public Date getPexEffectiveDate(){
		return pexEffectiveDate;
	}

	public Date getPexExpiryDate(){
		return pexExpiryDate;
	}

	public void setPexExpiryDate( Date pexExpiryDate ){
		this.pexExpiryDate = pexExpiryDate;
	}

	public Integer getPexOldCode(){
		return pexOldCode;
	}

	public void setPexOldCode( Integer pexOldCode ){
		this.pexOldCode = pexOldCode;
	}
	

}