package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class TransactionVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private String clazz;
	private String quoteNo;
	private String policyNo;
	private Date transactionFrom;
	private Date transactionTo;
	private String companyName;
	private String customerName;
	private String brokerName;
	private String scheme;
	private Date transactionEffectiveDate;
	private Date transactionExpiryDate;
	private String lastModifiedBy;

	//private String createdBy;
	private String distributionCode;
	private String status;

	private String transactionType;
	private String transactionNo;
	private String transactionEndNo;
	private String transactionPolicyNumber;
	private String transactionEndtId;
	private String tempTransactionEndtNo;
	private String policyType;
	private String transactionDate;
	private BigDecimal transactionSumInsured;
	private String transactionPremium;
	private String referredTo;
	private String branch;
	private String effectiveDate;
	private String expiryDate;
	private String policyTariffCode;
	private Integer locationCode;
	private String comments;
	 // Added as part of Release 3.1 
    private String mobileNo;
    private String phoneNo;
    private String polReferenceNo;


	private String transactionClass; 

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "clazz".equals( fieldName ) ) fieldValue = getClazz();
		if( "quoteNo".equals( fieldName ) ) fieldValue = getQuoteNo();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		if( "transactionFrom".equals( fieldName ) ) fieldValue = getTransactionFrom();
		if( "transactionTo".equals( fieldName ) ) fieldValue = getTransactionTo();
		if( "companyName".equals( fieldName ) ) fieldValue = getCompanyName();
		if( "customerName".equals( fieldName ) ) fieldValue = getCustomerName();
		if( "brokerName".equals( fieldName ) ) fieldValue = getBrokerName();
		if( "scheme".equals( fieldName ) ) fieldValue = getScheme();
		if( "transactionEffectiveDate".equals( fieldName ) ) fieldValue = getTransactionEffectiveDate();
		if( "transactionExpiryDate".equals( fieldName ) ) fieldValue = getTransactionExpiryDate();
		if( "lastModifiedBy".equals( fieldName ) ) fieldValue = getLastModifiedBy();
		if( "createdBy".equals( fieldName ) ) fieldValue = getCreatedBy();
		if( "distributionCode".equals( fieldName ) ) fieldValue = getDistributionCode();
		if( "status".equals( fieldName ) ) fieldValue = getStatus();

		if( "transactionType".equals( fieldName ) ) fieldValue = getTransactionType();
		if( "transactionNo".equals( fieldName ) ) fieldValue = getTransactionNo();
		if( "transactionEndNo".equals( fieldName ) ) fieldValue = getTransactionEndNo();
		if( "transactionPolicyNumber".equals( fieldName ) ) fieldValue = getTransactionPolicyNumber();
		if( "transactionEndtId".equals( fieldName ) ) fieldValue = getTransactionEndtId();
		if( "tempTransactionEndtNo".equals( fieldName ) ) fieldValue = getTempTransactionEndtNo();
		if( "policyType".equals( fieldName ) ) fieldValue = getPolicyType();
		if( "transactionDate".equals( fieldName ) ) fieldValue = getTransactionDate();
		if( "transactionSumInsured".equals( fieldName ) ) fieldValue = getTransactionSumInsured();
		if( "transactionPremium".equals( fieldName ) ) fieldValue = getTransactionPremium();
		if( "referredTo".equals( fieldName ) ) fieldValue = getReferredTo();
		if( "branch".equals( fieldName ) ) fieldValue = getBranch();
		if( "effectiveDate".equals( fieldName ) ) fieldValue = getEffectiveDate();
		if( "expiryDate".equals( fieldName ) ) fieldValue = getExpiryDate();
		if( "policyTariffCode".equals( fieldName ) ) fieldValue = getPolicyTariffCode();
		
		if( "transactionClass".equals( fieldName ) ) fieldValue = getTransactionClass();
		if( "locationCode".equals( fieldName ) ) fieldValue = this.getLocationCode();
		if( "comments".equals( fieldName ) ) fieldValue = this.getComments();
		if( "mobileNo".equals( fieldName ) ) fieldValue = this.getMobileNo();
		if( "phoneNo".equals( fieldName ) ) fieldValue = this.getPhoneNo();
		if( "polReferenceNo".equals( fieldName ) ) fieldValue = this.getPolReferenceNo();
		
		return fieldValue;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( transactionPolicyNumber == null ) ? 0 : transactionPolicyNumber.hashCode() );
		result = prime * result + ( ( transactionType == null ) ? 0 : transactionType.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		TransactionVO other = (TransactionVO) obj;
		if( transactionPolicyNumber == null ){
			if( other.transactionPolicyNumber != null ) return false;
		}
		else if( !transactionPolicyNumber.equals( other.transactionPolicyNumber ) ) return false;
		if( transactionType == null ){
			if( other.transactionType != null ) return false;
		}
		else if( !transactionType.equals( other.transactionType ) ) return false;
		return true;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo() {
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo() {
		return phoneNo;
	}

	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo(String phoneNo) {
		this.phoneNo = phoneNo;
	}

	/**
	 * @return the polReferenceNo
	 */
	public String getPolReferenceNo() {
		return polReferenceNo;
	}

	/**
	 * @param polReferenceNo the polReferenceNo to set
	 */
	public void setPolReferenceNo(String polReferenceNo) {
		this.polReferenceNo = polReferenceNo;
	}

	/**
	 * @return the clazz
	 */
	public String getClazz(){
		return clazz;
	}

	/**
	 * @param clazz the clazz to set
	 */
	public void setClazz( String clazz ){
		this.clazz = clazz;
	}

	/**
	 * @return the quoteNo
	 */
	public String getQuoteNo(){
		return quoteNo;
	}

	/**
	 * @param quoteNo the quoteNo to set
	 */
	public void setQuoteNo( String quoteNo ){
		this.quoteNo = quoteNo;
	}

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo(){
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo( String policyNo ){
		this.policyNo = policyNo;
	}

	/**
	 * @return the transactionFrom
	 */
	public Date getTransactionFrom(){
		return transactionFrom;
	}

	/**
	 * @param transactionFrom the transactionFrom to set
	 */
	public void setTransactionFrom( Date transactionFrom ){
		this.transactionFrom = transactionFrom;
	}

	/**
	 * @return the transactionTo
	 */
	public Date getTransactionTo(){
		return transactionTo;
	}

	/**
	 * @param transactionTo the transactionTo to set
	 */
	public void setTransactionTo( Date transactionTo ){
		this.transactionTo = transactionTo;
	}

	/**
	 * @return the companyName
	 */
	public String getCompanyName(){
		return companyName;
	}

	/**
	 * @param companyName the companyName to set
	 */
	public void setCompanyName( String companyName ){
		this.companyName = companyName;
	}

	/**
	 * @return the customerName
	 */
	public String getCustomerName(){
		return customerName;
	}

	/**
	 * @param customerName the customerName to set
	 */
	public void setCustomerName( String customerName ){
		this.customerName = customerName;
	}

	/**
	 * @return the brokerName
	 */
	public String getBrokerName(){
		return brokerName;
	}

	/**
	 * @param brokerName the brokerName to set
	 */
	public void setBrokerName( String brokerName ){
		this.brokerName = brokerName;
	}

	/**
	 * @return the scheme
	 */
	public String getScheme(){
		return scheme;
	}

	/**
	 * @param scheme the scheme to set
	 */
	public void setScheme( String scheme ){
		this.scheme = scheme;
	}

	/**
	 * @return the transactionEffectiveDate
	 */
	public Date getTransactionEffectiveDate(){
		return transactionEffectiveDate;
	}

	/**
	 * @param transactionEffectiveDate the transactionEffectiveDate to set
	 */
	public void setTransactionEffectiveDate( Date transactionEffectiveDate ){
		this.transactionEffectiveDate = transactionEffectiveDate;
	}

	/**
	 * @return the transactionExpiryDate
	 */
	public Date getTransactionExpiryDate(){
		return transactionExpiryDate;
	}

	/**
	 * @param transactionExpiryDate the transactionExpiryDate to set
	 */
	public void setTransactionExpiryDate( Date transactionExpiryDate ){
		this.transactionExpiryDate = transactionExpiryDate;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy( String lastModifiedBy ){
		this.lastModifiedBy = lastModifiedBy;
	}

	/**
	 * @return the createdBy
	 */
//	public String getCreatedBy(){
//		return createdBy;
//	}
//
//	/**
//	 * @param createdBy the createdBy to set
//	 */
//	public void setCreatedBy( String createdBy ){
//		this.createdBy = createdBy;
//	}

	/**
	 * @return the distributionCode
	 */
	public String getDistributionCode(){
		return distributionCode;
	}

	/**
	 * @param distributionCode the distributionCode to set
	 */
	public void setDistributionCode( String distributionCode ){
		this.distributionCode = distributionCode;
	}

	/**
	 * @return the status
	 */
	public String getStatus(){
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus( String status ){
		this.status = status;
	}

	/**
	 * @return the transactionType
	 */
	public String getTransactionType(){
		return transactionType;
	}

	/**
	 * @param transactionType the transactionType to set
	 */
	public void setTransactionType( String transactionType ){
		this.transactionType = transactionType;
	}

	/**
	 * @return the transactionNo
	 */
	public String getTransactionNo(){
		return transactionNo;
	}

	/**
	 * @param transactionNo the transactionNo to set
	 */
	public void setTransactionNo( String transactionNo ){
		this.transactionNo = transactionNo;
	}

	/**
	 * @return the transactionEndNo
	 */
	public String getTransactionEndNo(){
		return transactionEndNo;
	}

	/**
	 * @param transactionEndNo the transactionEndNo to set
	 */
	public void setTransactionEndNo( String transactionEndNo ){
		this.transactionEndNo = transactionEndNo;
	}

	/**
	 * @return the transactionPolicyNumber
	 */
	public String getTransactionPolicyNumber(){
		return transactionPolicyNumber;
	}

	/**
	 * @param transactionPolicyNumber the transactionPolicyNumber to set
	 */
	public void setTransactionPolicyNumber( String transactionPolicyNumber ){
		this.transactionPolicyNumber = transactionPolicyNumber;
	}

	/**
	 * @return the transactionEndtId
	 */
	public String getTransactionEndtId(){
		return transactionEndtId;
	}

	/**
	 * @param transactionEndtId the transactionEndtId to set
	 */
	public void setTransactionEndtId( String transactionEndtId ){
		this.transactionEndtId = transactionEndtId;
	}

	public void setTempTransactionEndtNo( String tempTransactionEndtNo ){
		this.tempTransactionEndtNo = tempTransactionEndtNo;
	}

	public String getTempTransactionEndtNo(){
		return tempTransactionEndtNo;
	}

	/**
	 * @return the policyType
	 */
	public String getPolicyType(){
		return policyType;
	}

	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType( String policyType ){
		this.policyType = policyType;
	}

	/**
	 * @return the transactionDate
	 */
	public String getTransactionDate(){
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate( String transactionDate ){
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the transactionSumInsured
	 */
	public BigDecimal getTransactionSumInsured(){
		return transactionSumInsured;
	}

	/**
	 * @param transactionSumInsured the transactionSumInsured to set
	 */
	public void setTransactionSumInsured( BigDecimal transactionSumInsured ){
		this.transactionSumInsured = transactionSumInsured;
	}

	/**
	 * @return the transactionPremium
	 */
	public String getTransactionPremium(){
		return transactionPremium;
	}

	/**
	 * @param transactionPremium the transactionPremium to set
	 */
	public void setTransactionPremium( String transactionPremium ){
		this.transactionPremium = transactionPremium;
	}

	/**
	 * @return the referredTo
	 */
	public String getReferredTo(){
		return referredTo;
	}

	/**
	 * @param referredTo the referredTo to set
	 */
	public void setReferredTo( String referredTo ){
		this.referredTo = referredTo;
	}

	/**
	 * @return the branch
	 */
	public String getBranch(){
		return branch;
	}

	/**
	 * @param branch the branch to set
	 */
	public void setBranch( String branch ){
		this.branch = branch;
	}

	/**
	 * @return the effectiveDate
	 */
	public String getEffectiveDate(){
		return effectiveDate;
	}

	/**
	 * @param effectiveDate the effectiveDate to set
	 */
	public void setEffectiveDate( String effectiveDate ){
		this.effectiveDate = effectiveDate;
	}

	/**
	 * @return the expiryDate
	 */
	public String getExpiryDate(){
		return expiryDate;
	}

	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate( String expiryDate ){
		this.expiryDate = expiryDate;
	}

	/**
	 * @return the policyTariffCode
	 */
	public String getPolicyTariffCode(){
		return policyTariffCode;
	}

	/**
	 * @param policyTariffCode the policyTariffCode to set
	 */
	public void setPolicyTariffCode( String policyTariffCode ){
		this.policyTariffCode = policyTariffCode;
	}

	/**
	 * @return the transactionClass
	 */
	public String getTransactionClass(){
		return transactionClass;
	}

	/**
	 * @param transactionClass the transactionClass to set
	 */
	public void setTransactionClass( String transactionClass ){
		this.transactionClass = transactionClass;
	}
	
	public Integer getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(Integer locationCode) {
		this.locationCode = locationCode;
	}

	/**
	 * @return the comments
	 */
	public String getComments(){
		return comments;
	}

	/**
	 * @param comments the comments to set
	 */
	public void setComments( String comments ){
		this.comments = comments;
	}
	
	
}
