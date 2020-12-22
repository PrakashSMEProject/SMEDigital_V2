package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;

public class RenewalResultsVO extends BaseVO{

	private static final long serialVersionUID = -3023385586566271551L;

	private Long polLinkingId;
	private Long endtId;
	private Long endtNo;
	private String concatPolicyNo;
	private Long policyNo;
	private Long renQuoteNo;
	private String polExpiryDate;
	private String polEffectiveDate;
	private String classCode;
	private Short policyYear;
	private String polValidityStartDate;
	private String emailId;
	private Integer polLocCode;
	//change- phase 3
	private Long policyId;
	private short polDocumentCode;
	/*
	 * added to include additional fields in renewal search	 */
	private String insuredName;
	private String brokerName;
	private String branchName;
	private String schemaName;
	private String statusDescripton;
	private String productDescription;
	private BigDecimal renewedQuotePremium;
	private String renewalTerm;
	private String renewalTermCompleteTxt;
	private String brEmailId;
	private String brAccountExeEmail;
	private String cuEInterests;
	private String policyType;
	private String brRemarks;
	private String brAccountKeyManagerName;
	private String brAccountKeyManagerNum;
	
	public String getCuEInterests() {
		return cuEInterests;
	}

	public void setCuEInterests(String cuEInterests) {
		this.cuEInterests = cuEInterests;
	}

	public String getBrEmailId() {
		return brEmailId;
	}

	public void setBrEmailId(String brEmailId) {
		this.brEmailId = brEmailId;
	}

	public String getBrAccountExeEmail() {
		return brAccountExeEmail;
	}

	public void setBrAccountExeEmail(String brAccountExeEmail) {
		this.brAccountExeEmail = brAccountExeEmail;
	}
	
	

	public String getRenewalTermCompleteTxt() {
		return renewalTermCompleteTxt;
	}

	public void setRenewalTermCompleteTxt(String renewalTermCompleteTxt) {
		this.renewalTermCompleteTxt = renewalTermCompleteTxt;
	}

	public String getRenewalTerm() {
		return renewalTerm;
	}

	public void setRenewalTerm(String renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public BigDecimal getRenewedQuotePremium() {
		return renewedQuotePremium;
	}

	public void setRenewedQuotePremium(BigDecimal renewedQuotePremium) {
		this.renewedQuotePremium = renewedQuotePremium;
	}

	public String getProductDescription() {
		return productDescription;
	}

	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}

	public String getInsuredName(){
		return insuredName;
	}

	public void setInsuredName( String insuredName ){
		this.insuredName = insuredName;
	}

	public String getBrokerName(){
		return brokerName;
	}

	public void setBrokerName( String brokerName ){
		this.brokerName = brokerName;
	}

	public String getBranchName(){
		return branchName;
	}

	public void setBranchName( String branchName ){
		this.branchName = branchName;
	}

	

	public String getSchemaName(){
		return schemaName;
	}

	public void setSchemaName( String schemaName ){
		this.schemaName = schemaName;
	}
	
	

	public String getStatusDescripton() {
		return statusDescripton;
	}

	public void setStatusDescripton(String statusDescripton) {
		this.statusDescripton = statusDescripton;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "polLinkingId".equals( fieldName ) ) fieldValue = getPolLinkingId();
		if( "endtId".equals( fieldName ) ) fieldValue = getEndtId();
		if( "endtNo".equals( fieldName ) ) fieldValue = getEndtNo();
		if( "concatPolicyNo".equals( fieldName ) ) fieldValue = getConcatPolicyNo();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		if( "polExpiryDate".equals( fieldName ) ) fieldValue = getPolExpiryDate();
		if( "polEffectiveDate".equals( fieldName ) ) fieldValue =getPolEffectiveDate();
		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();
		if( "policyYear".equals( fieldName ) ) fieldValue = getPolicyYear();
		if( "polValidityStartDate".equals( fieldName ) ) fieldValue = getPolValidityStartDate();
		if( "emailId".equals( fieldName ) ) fieldValue = getEmailId();
		if( "renQuoteNo".equals( fieldName ) ) fieldValue = getRenQuoteNo();
		if( "polLocCode".equals( fieldName ) ) fieldValue = getPolLocCode();
		if( "policyId".equals( fieldName ) ) fieldValue = getPolicyId();
		if( "polDocumentCode".equals( fieldName ) ) fieldValue = getPolDocumentCode();
		if( "statusDescripton".equals( fieldName ) ) fieldValue = getStatusDescripton();
		if("brEmailId".equals(fieldName))fieldValue = getBrEmailId();
		if("brAccountExeEmail".equals(fieldName))fieldName = getBrAccountExeEmail();
		return fieldValue;
	}
	
	public Long getPolLinkingId(){
		return polLinkingId;
	}
	public void setPolLinkingId( Long polLinkingId ){
		this.polLinkingId = polLinkingId;
	}
	public Long getEndtId(){
		return endtId;
	}
	public void setEndtId( Long endtId ){
		this.endtId = endtId;
	}
	public Long getEndtNo(){
		return endtNo;
	}
	public void setEndtNo( Long endtNo ){
		this.endtNo = endtNo;
	}
	public String getConcatPolicyNo(){
		return concatPolicyNo;
	}
	public void setConcatPolicyNo( String concatPolicyNo ){
		this.concatPolicyNo = concatPolicyNo;
	}
	public Long getPolicyNo(){
		return policyNo;
	}
	public void setPolicyNo( Long policyNo ){
		this.policyNo = policyNo;
	}
	public String getPolExpiryDate(){
		return polExpiryDate;
	}
	public void setPolExpiryDate( String polExpiryDate ){
		this.polExpiryDate = polExpiryDate;
	}
	public String getPolEffectiveDate(){
		return polEffectiveDate;
	}
	public void setPolEffectiveDate( String polEffectiveDate ){
		this.polEffectiveDate = polEffectiveDate;
	}
	public String getClassCode(){
		return classCode;
	}
	public void setClassCode( String classCode ){
		this.classCode = classCode;
	}
	public Short getPolicyYear(){
		return policyYear;
	}

	public void setPolicyYear( Short policyYear ){
		this.policyYear = policyYear;
	}

	public String getPolValidityStartDate(){
		return polValidityStartDate;
	}

	public void setPolValidityStartDate( String polValidityStartDate ){
		this.polValidityStartDate = polValidityStartDate;
	}

	public String getEmailId(){
		return emailId;
	}

	public void setEmailId( String emailId ){
		this.emailId = emailId;
	}
	public Long getRenQuoteNo(){
		return renQuoteNo;
	}

	public void setRenQuoteNo( Long renQuoteNo ){
		this.renQuoteNo = renQuoteNo;
	}
	
	public Integer getPolLocCode() {
		return polLocCode;
	}

	public void setPolLocCode(Integer polLocCode) {
		this.polLocCode = polLocCode;
	}

	/**
	 * @return the policyId
	 */
	public Long getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	
	/**
	 * @return the polDocumentCode
	 */
	public short getPolDocumentCode() {
		return polDocumentCode;
	}

	/**
	 * @param polDocumentCode the polDocumentCode to set
	 */
	public void setPolDocumentCode(short polDocumentCode) {
		this.polDocumentCode = polDocumentCode;
	}

	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public String getBrRemarks() {
		return brRemarks;
	}

	public void setBrRemarks(String brRemarks) {
		this.brRemarks = brRemarks;
	}

	public String getBrAccountKeyManagerName() {
		return brAccountKeyManagerName;
	}

	public void setBrAccountKeyManagerName(String brAccountKeyManagerName) {
		this.brAccountKeyManagerName = brAccountKeyManagerName;
	}

	public String getBrAccountKeyManagerNum() {
		return brAccountKeyManagerNum;
	}

	public void setBrAccountKeyManagerNum(String brAccountKeyManagerNum) {
		this.brAccountKeyManagerNum = brAccountKeyManagerNum;
	}
	
	

}


