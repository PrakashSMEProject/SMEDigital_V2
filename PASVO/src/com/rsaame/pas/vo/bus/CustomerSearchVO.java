package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

public class CustomerSearchVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	@Override
	public Object getFieldValue( String fieldName ){
		// TODO Auto-generated method stub
		Object fieldValue = null;

		if( "customerId".equals( fieldName ) ) fieldValue = getCustomerId();
		if( "emailId".equals( fieldName ) ) fieldValue = getEmailId();
		if( "phoneNo".equals( fieldName ) ) fieldValue = getPhoneNo();
		if( "mobileNo".equals( fieldName ) ) fieldValue = getMobileNo();
		if( "policyQuoteNo".equals( fieldName ) ) fieldValue = getPolicyQuoteNo();
		if( "firstName".equals( fieldName ) ) fieldValue = getFirstName();
		if( "lastName".equals( fieldName ) ) fieldValue = getLastName();
		if( "middleName".equals( fieldName ) ) fieldValue = getMiddleName();
		if( "completeName".equals( fieldName ) ) fieldValue = getCompleteName();
		if( "dateOfBirth".equals( fieldName ) ) fieldValue = getDateOfBirth();
		if("poBoxNo".equals(fieldName)) fieldValue = getPoBoxNo();
		//if( "jurisdiction".equals( fieldName ) ) fieldValue = getJurisdiction();
		if("contactNo".equals(fieldName)) fieldValue = getContactNo();
		if("brokerName".equals(fieldName)) fieldValue = getBrokerName();
		if("creationDate".equals(fieldName)) fieldValue = getCreationDate();
		if("brokerId".equals(fieldName)) fieldValue = getBrokerId();
		if("userId".equals(fieldName)) fieldValue = getUserId();
		
		return fieldValue;
	}

	private String customerId;

    private String emailId;

    private String phoneNo;

    private String mobileNo;

    private String policyQuoteNo;

    private String firstName;

    private String lastName;

    private String middleName;

    private String completeName;

    //private Date dateOfBirth;
    private String dateOfBirth;

    private String poBoxNo;

    private String city;

    private String contactNo;

    private String brokerName;

    private String creationDate;
    
    private String brokerId;
    
    private Integer userId;  
	/**
	 * @return the customerId
	 */
	public String getCustomerId(){
		return customerId;
	}
	/**
	 * @return the emailId
	 */
	public String getEmailId(){
		return emailId;
	}
	/**
	 * @return the phoneNo
	 */
	public String getPhoneNo(){
		return phoneNo;
	}
	/**
	 * @return the mobileNo
	 */
	public String getMobileNo(){
		return mobileNo;
	}
	/**
	 * @return the policyQuoteNo
	 */
	public String getPolicyQuoteNo(){
		return policyQuoteNo;
	}
	/**
	 * @return the firstName
	 */
	public String getFirstName(){
		return firstName;
	}
	/**
	 * @return the lastName
	 */
	public String getLastName(){
		return lastName;
	}
	/**
	 * @return the middleName
	 */
	public String getMiddleName(){
		return middleName;
	}
	/**
	 * @return the completeName
	 */
	public String getCompleteName(){
		return completeName;
	}
	/**
	 * @return the dateOfBirth
	 */
	public String getDateOfBirth(){
		return dateOfBirth;
	}
	/**
	 * @return the poBoxNo
	 */
	public String getPoBoxNo(){
		return poBoxNo;
	}
	/**
	 * @return the city
	 */
	public String getCity(){
		return city;
	}
	/**
	 * @return the contactNo
	 */
	public String getContactNo(){
		return contactNo;
	}
	/**
	 * @return the brokerName
	 */
	public String getBrokerName(){
		return brokerName;
	}
	/**
	 * @return the creationDate
	 */
	public String getCreationDate(){
		return creationDate;
	}
	/**
	 * @return the brokerId
	 */
	public String getBrokerId(){
		return brokerId;
	}
	/**
	 * @return the userId
	 */
	public Integer getUserId(){
		return userId;
	}
	/**
	 * @param customerId the customerId to set
	 */
	public void setCustomerId( String customerId ){
		this.customerId = customerId;
	}
	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId( String emailId ){
		this.emailId = emailId;
	}
	/**
	 * @param phoneNo the phoneNo to set
	 */
	public void setPhoneNo( String phoneNo ){
		this.phoneNo = phoneNo;
	}
	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo( String mobileNo ){
		this.mobileNo = mobileNo;
	}
	/**
	 * @param policyQuoteNo the policyQuoteNo to set
	 */
	public void setPolicyQuoteNo( String policyQuoteNo ){
		this.policyQuoteNo = policyQuoteNo;
	}
	/**
	 * @param firstName the firstName to set
	 */
	public void setFirstName( String firstName ){
		this.firstName = firstName;
	}
	/**
	 * @param lastName the lastName to set
	 */
	public void setLastName( String lastName ){
		this.lastName = lastName;
	}
	/**
	 * @param middleName the middleName to set
	 */
	public void setMiddleName( String middleName ){
		this.middleName = middleName;
	}
	/**
	 * @param completeName the completeName to set
	 */
	public void setCompleteName( String completeName ){
		this.completeName = completeName;
	}
	/**
	 * @param dateOfBirth the dateOfBirth to set
	 */
	public void setDateOfBirth( String dateOfBirth ){
		this.dateOfBirth = dateOfBirth;
	}
	/**
	 * @param poBoxNo the poBoxNo to set
	 */
	public void setPoBoxNo( String poBoxNo ){
		this.poBoxNo = poBoxNo;
	}
	/**
	 * @param city the city to set
	 */
	public void setCity( String city ){
		this.city = city;
	}
	/**
	 * @param contactNo the contactNo to set
	 */
	public void setContactNo( String contactNo ){
		this.contactNo = contactNo;
	}
	/**
	 * @param brokerName the brokerName to set
	 */
	public void setBrokerName( String brokerName ){
		this.brokerName = brokerName;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate( String creationDate ){
		this.creationDate = creationDate;
	}
	/**
	 * @param brokerId the brokerId to set
	 */
	public void setBrokerId( String brokerId ){
		this.brokerId = brokerId;
	}
	/**
	 * @param userId the userId to set
	 */
	public void setUserId( Integer userId ){
		this.userId = userId;
	}
	
}
