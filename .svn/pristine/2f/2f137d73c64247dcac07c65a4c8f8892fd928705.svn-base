package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.bus.IPolQuoType;


public class RecentActivityVO extends BaseVO implements IPolQuoType{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Long quotePolicyNumber;
	private Date expiryDate;
	private String effectiveDate;
	private String insuredName;
	private Long insuredCode;
	private Integer schemeCode;
	private String schemeName;
	private Integer userId;
	private Boolean isQuote;
	private byte statusCode;
	private String statusDesc;
	private String recentActivityTransName;
	private Long endtID;
	// Oman multibranching
	private Short docCode;
	private Short polLocCode;
	private String lob;		//Phase 3 changes
	private String polPremium; //Phase 3 changes to include Premium
	
	/**
	 * 
	 * @return string
	 */
	public String getLob(){
		return lob;
	}

	/**
	 * 
	 * @param lob
	 */
	public void setLob( String lob ){
		this.lob = lob;
	}



	public Short getPolLocCode() {
		return polLocCode;
	}



	public void setPolLocCode(Short polLocCode) {
		this.polLocCode = polLocCode;
	}



	public Short getDocCode() {
		return docCode;
	}



	public void setDocCode(Short docCode) {
		this.docCode = docCode;
	}



	public Long getEndtID() {
		return endtID;
	}



	public void setEndtID(Long endtID) {
		this.endtID = endtID;
	}



	/**
	 * @return the statusCode
	 */
	public byte getStatusCode(){
		return statusCode;
	}



	/**
	 * @return the statusDesc
	 */
	public String getStatusDesc(){
		return statusDesc;
	}



	/**
	 * @param statusCode the statusCode to set
	 */
	public void setStatusCode( byte statusCode ){
		this.statusCode = statusCode;
	}



	/**
	 * @param statusDesc the statusDesc to set
	 */
	public void setStatusDesc( String statusDesc ){
		this.statusDesc = statusDesc;
	}

	/**
	 * @return polPremium
	 */
	public String getPolPremium(){
		return polPremium;
	}

	/**
	 * @param polPremium
	 */
	public void setPolPremium( String polPremium ){
		this.polPremium = polPremium;
	}



	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "quotePolicyNumber".equals( fieldName ) ) fieldValue = getQuotePolicyNumber();
		if( "policyNumber".equals( fieldName ) ) fieldValue = getQuotePolicyNumber();
		if( "expiryDate".equals( fieldName ) ) fieldValue = getExpiryDate();
		if( "effectiveDate".equals( fieldName ) ) fieldValue = getEffectiveDate();
		if( "insuredName".equals( fieldName ) ) fieldValue = getInsuredName();
		if( "insuredCode".equals( fieldName ) ) fieldValue = getInsuredCode();
		if( "schemeCode".equals( fieldName ) ) fieldValue = getSchemeCode();
		if( "schemeName".equals( fieldName ) ) fieldValue = getSchemeName();
		if( "userId".equals( fieldName ) ) fieldValue = getUserId();
		return fieldValue;
	}






	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate(){
		return expiryDate;
	}



	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate(){
		return effectiveDate;
	}



	/**
	 * @return the insuredName
	 */
	public String getInsuredName(){
		return insuredName;
	}



	/**
	 * @return the insuredCode
	 */
	public Long getInsuredCode(){
		return insuredCode;
	}



	/**
	 * @return the schemeCode
	 */
	public Integer getSchemeCode(){
		return schemeCode;
	}



	/**
	 * @return the schemeName
	 */
	public String getSchemeName(){
		return schemeName;
	}



	/**
	 * @return the userId
	 */
	public Integer getUserId(){
		return userId;
	}



	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate( Date expiryDate ){
		this.expiryDate = expiryDate;
	}



	/**
	 * @return the quotePolicyNumber
	 */
	public Long getQuotePolicyNumber(){
		return quotePolicyNumber;
	}



	/**
	 * @param quotePolicyNumber the quotePolicyNumber to set
	 */
	public void setQuotePolicyNumber( Long quotePolicyNumber ){
		this.quotePolicyNumber = quotePolicyNumber;
	}



	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate( String effectiveDate ){
		this.effectiveDate = effectiveDate;
	}



	/**
	 * @param insuredName the insuredName to set
	 */
	public void setInsuredName( String insuredName ){
		this.insuredName = insuredName;
	}



	/**
	 * @param insuredCode the insuredCode to set
	 */
	public void setInsuredCode( Long insuredCode ){
		this.insuredCode = insuredCode;
	}



	/**
	 * @param schemeCode the schemeCode to set
	 */
	public void setSchemeCode( Integer schemeCode ){
		this.schemeCode = schemeCode;
	}



	/**
	 * @param schemeName the schemeName to set
	 */
	public void setSchemeName( String schemeName ){
		this.schemeName = schemeName;
	}



	/**
	 * @param userId the userId to set
	 */
	public void setUserId( Integer userId ){
		this.userId = userId;
	}



	@Override
	public Boolean isQuote(){
		return isQuote;
	}



	@Override
	public void setQuote( Boolean isQuote ){
		this.isQuote=isQuote;
		
	}



	public String getRecentActivityTransName() {
		return recentActivityTransName;
	}



	public void setRecentActivityTransName(String recentActivityTransName) {
		this.recentActivityTransName = recentActivityTransName;
	}
	

}
