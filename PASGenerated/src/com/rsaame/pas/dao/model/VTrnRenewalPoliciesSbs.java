package com.rsaame.pas.dao.model;

import java.math.BigDecimal;
import java.util.Date;

// Generated Jul 11, 2012 2:50:30 PM by Hibernate Tools 3.4.0.CR1

/**
 * VTrnRenewalPoliciesSbs generated by hbm2java
 */
public class VTrnRenewalPoliciesSbs implements java.io.Serializable {

	private VTrnRenewalPoliciesSbsId id;
	private Integer polLocationCode;
	private Short polClassCode;
	private Long polPolicyId;
	private long polEndtId;
	private long polPolicyNo;
	private long polQuotationNo;
	private long polEndtNo;
	private Date polEffectiveDate;
	private String polEffectiveDate1;
	private String polExpiryDate1;
	private Date polExpiryDate;
	private String polConcPolicyNo;
	private Short polDctCode;
	private Short polPolicyYear;
	private Short polBrCode;
	private Integer polDistributionChnl;
	private Integer polCoverNoteHour;
	private Long polInsuredCode;
	private Integer polCcgCode;
	private Long polAgentId;
	private Date polPrintDate;
	private Short polPolicyType;
	private Long polInsuredId;
	/*
	 * Added by Raman for Renewals report change
	 */
	private String polinsuredName;
	private String polbrokerName;
	private String polbranchName;
	private String polschemaName;
	
	/*Added to display the Base Class in generate renewal search result - BugZilla: 4188*/
	private String polBaseClass;
	

	

	public String getPolinsuredName(){
		return polinsuredName;
	}

	public void setPolinsuredName( String polinsuredName ){
		this.polinsuredName = polinsuredName;
	}

	public String getPolbrokerName(){
		return polbrokerName;
	}

	public void setPolbrokerName( String polbrokerName ){
		this.polbrokerName = polbrokerName;
	}

	public String getPolbranchName(){
		return polbranchName;
	}

	public void setPolbranchName( String polbranchName ){
		this.polbranchName = polbranchName;
	}

	
	public String getPolschemaName(){
		return polschemaName;
	}

	public void setPolschemaName( String polschemaName ){
		this.polschemaName = polschemaName;
	}
	
	
	/*
	 * Search Criteria :- Search Criteria based on Quotation No.  
	 */
	public long getPolQuotationNo() {
		return polQuotationNo;
	}

	public void setPolQuotationNo(long polQuotationNo) {
		this.polQuotationNo = polQuotationNo;
	}

	public Integer getPolLocationCode() {
		return this.polLocationCode;
	}

	public void setPolLocationCode(Integer polLocationCode) {
		this.polLocationCode = polLocationCode;
	}
	public Short getPolClassCode(){
		return polClassCode;
	}

	public void setPolClassCode( Short polClassCode ){
		this.polClassCode = polClassCode;
	}
	public long getPolEndtId() {
		return this.polEndtId;
	}

	public void setPolEndtId(long polEndtId) {
		this.polEndtId = polEndtId;
	}

	public long getPolPolicyNo() {
		return this.polPolicyNo;
	}

	public void setPolPolicyNo(long polPolicyNo) {
		this.polPolicyNo = polPolicyNo;
	}

	public long getPolEndtNo() {
		return this.polEndtNo;
	}

	public void setPolEndtNo(long polEndtNo) {
		this.polEndtNo = polEndtNo;
	}

	public Date getPolEffectiveDate() {
		return this.polEffectiveDate;
	}

	public void setPolEffectiveDate(Date polEffectiveDate) {
		this.polEffectiveDate = polEffectiveDate;
	}

	public Date getPolExpiryDate() {
		return this.polExpiryDate;
	}

	public void setPolExpiryDate(Date polExpiryDate) {
		this.polExpiryDate = polExpiryDate;
	}

	public String getPolConcPolicyNo() {
		return this.polConcPolicyNo;
	}

	public void setPolConcPolicyNo(String polConcPolicyNo) {
		this.polConcPolicyNo = polConcPolicyNo;
	}

	public Short getPolDctCode() {
		return this.polDctCode;
	}

	public void setPolDctCode(Short polDctCode) {
		this.polDctCode = polDctCode;
	}

	public Short getPolPolicyYear() {
		return this.polPolicyYear;
	}

	public void setPolPolicyYear(Short polPolicyYear) {
		this.polPolicyYear = polPolicyYear;
	}

	public Short getPolBrCode() {
		return this.polBrCode;
	}

	public void setPolBrCode(Short polBrCode) {
		this.polBrCode = polBrCode;
	}

	public Integer getPolDistributionChnl() {
		return this.polDistributionChnl;
	}

	public void setPolDistributionChnl(Integer polDistributionChnl) {
		this.polDistributionChnl = polDistributionChnl;
	}

	public Integer getPolCoverNoteHour() {
		return this.polCoverNoteHour;
	}

	public void setPolCoverNoteHour(Integer polCoverNoteHour) {
		this.polCoverNoteHour = polCoverNoteHour;
	}

	public Long getPolInsuredCode() {
		return this.polInsuredCode;
	}

	public void setPolInsuredCode(Long polInsuredCode) {
		this.polInsuredCode = polInsuredCode;
	}

	public Long getPolInsuredId() {
		return this.polInsuredId;
	}
	
	public void setPolInsuredId(Long polInsuredId) {
		this.polInsuredId = polInsuredId;
	}
	
	public Long getPolPolicyId() {
		return this.polPolicyId;
	}
	
	public void setPolPolicyId(Long polPolicyId) {
		this.polPolicyId = polPolicyId;
	}

	public Integer getPolCcgCode() {
		return this.polCcgCode;
	}

	public void setPolCcgCode(Integer polCcgCode) {
		this.polCcgCode = polCcgCode;
	}

	public Long getPolAgentId() {
		return this.polAgentId;
	}

	public void setPolAgentId(Long polAgentId) {
		this.polAgentId = polAgentId;
	}

	public Date getPolPrintDate() {
		return this.polPrintDate;
	}

	public void setPolPrintDate(Date polPrintDate) {
		this.polPrintDate = polPrintDate;
	}

	public Short getPolPolicyType() {
		return this.polPolicyType;
	}

	public void setPolPolicyType(Short polPolicyType) {
		this.polPolicyType = polPolicyType;
	}

	public VTrnRenewalPoliciesSbs() {
	}

	public VTrnRenewalPoliciesSbs(VTrnRenewalPoliciesSbsId id) {
		this.id = id;
	}

	public VTrnRenewalPoliciesSbsId getId() {
		return this.id;
	}

	public void setId(VTrnRenewalPoliciesSbsId id) {
		this.id = id;
	}
	public String getPolExpiryDate1(){
		return polExpiryDate1;
	}

	public void setPolExpiryDate1( String polExpiryDate1 ){
		this.polExpiryDate1 = polExpiryDate1;
	}

	public String getPolEffectiveDate1(){
		return polEffectiveDate1;
	}

	public void setPolEffectiveDate1( String polEffectiveDate1 ){
		this.polEffectiveDate1 = polEffectiveDate1;
	}

	/*Added to display the Base Class in generate renewal search result*/
	public String getPolBaseClass() {
		return polBaseClass;
	}

	public void setPolBaseClass(String polBaseClass) {
		this.polBaseClass = polBaseClass;
	}
	
	
}
