/*
 * @(#)CustomerBO.java            
 *
 * Copyright (c) 2007-2012 Royal and Sun Alliance Insurance Group plc.
 * St.Mark’s Court, Chart Way, Horsham, West Sussex RH12 1XL, U.K.
 * All rights reserved.
 *
 * This software is the confidential and proprietary information of Royal
 * and Sun Alliance Insurance Group plc.("Confidential Information").
 * You shall not disclose such Confidential Information and shall use it only in
 * accordance with the terms of the license agreement you entered into
 * with Royal and Sun Alliance Insurance Group plc.
 */
package com.rsaame.kaizen.customer.model;

import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.model.BaseAMEBO;

/**
 * @author cognizant
 * 
 * TODO To change the template for this generated type comment go to Window -
 * Preferences - Java - Code Style - Code Templates
 */
public class CustomerBO extends BaseAMEBO
{
    private String customerId;
    
    private String insuredCode;

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
       
      
// ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)     
    private Integer locationCode;    
    private String locationName;
    
    private Integer hideLocation;
    
    private Integer locationCodeCreate; 
    
    private String customerExists;
    
	private String customerSaveReq;
	
	private String custSaveReq;
	
	private String copyButtonReq;
	
	private String ccgCode;
	
	private Boolean exactSearch;
    
/**
 * @return Returns the locationCode.
 */
public Integer getLocationCode() {
	return locationCode;
}
/**
 * @param locationCode The locationCode to set.
 */
public void setLocationCode(Integer locationCode) {
	this.locationCode = locationCode;
}
	/**
	 * @return Returns the locationName.
	 */
	public String getLocationName() {
		return locationName;
	}
	/**
	 * @param locationName The locationName to set.
	 */
	public void setLocationName(String locationName) {
		this.locationName = locationName;
	}
//  ADM 03.03.2010 : Agent Profile Release 2.5.2 Changes starts
    
    private String agentId;
    
    private String agentName;
    
	/**
	 * @return Returns the agentName.
	 */
	public String getAgentName() {
		return agentName;
	}
	/**
	 * @param agentName The agentName to set.
	 */
	public void setAgentName(String agentName) {
		this.agentName = agentName;
	}
	/**
	 * @return Returns the agentId.
	 */
	public String getAgentId() {
		return agentId;
	}
	/**
	 * @param agentId The agentId to set.
	 */
	public void setAgentId(String agentId) {
		this.agentId = agentId;
	}
	
//  ADM 03.03.2010 : Agent Profile Release 2.5.2 Changes ends
	
    //added for pagination
    private Integer currentPage = new Integer(AMEConstants.CURRENT_PAGE);
    private Integer numberOfRecords = Integer.valueOf(0);
    
    //Added for CR 309
    private String distributionChannel;
    
//  Added for CR 330
    private Integer classCode;
    
    /**
     * @param customerId
     * @param emailId
     * @param phoneNo
     * @param mobileNo
     * @param policyQuoteNo
     * @param firstName
     * @param lastName
     * @param middleName
     * @param completeName
     * @param dateOfBirth
     * @param poBoxNo
     * @param city
     * @param contactNo
     * @param brokerName
     * @param creationDate
     * @param brokerId
     * @param currentPage
     * @param numberOfRecords
     * @param distributionChannel
     */
    public CustomerBO(String customerId, String emailId, String phoneNo, String mobileNo, String policyQuoteNo,
            String firstName, String lastName, String middleName, String completeName, String dateOfBirth,
            String poBoxNo, String city, String contactNo, String brokerName, String creationDate, String brokerId,
            Integer currentPage, Integer numberOfRecords,String distributionChannel) {
       
        this.customerId = customerId;
        this.emailId = emailId;
        this.phoneNo = phoneNo;
        this.mobileNo = mobileNo;
        this.policyQuoteNo = policyQuoteNo;
        this.firstName = firstName;
        this.lastName = lastName;
        this.middleName = middleName;
        this.completeName = completeName;
        this.dateOfBirth = dateOfBirth;
        this.poBoxNo = poBoxNo;
        this.city = city;
        this.contactNo = contactNo;
        this.brokerName = brokerName;
        this.creationDate = creationDate;
        this.brokerId = brokerId;
        this.currentPage = currentPage;
        this.numberOfRecords = numberOfRecords;
        this.distributionChannel = distributionChannel;
    }
    /**
     *  
     */
    public CustomerBO()
    {

        // TODO Auto-generated constructor stub
    }

    /**
     * @return Returns the brokerName.
     */
    public String getBrokerName()
    {
        return brokerName;
    }

    /**
     * @param brokerName
     *            The brokerName to set.
     */
    public void setBrokerName(String brokerName)
    {
        this.brokerName = brokerName;
    }

    /**
     * @return Returns the city.
     */
    public String getCity()
    {
        return city;
    }

    /**
     * @param city
     *            The city to set.
     */
    public void setCity(String city)
    {
        this.city = city;
    }

    /**
     * @return Returns the completeName.
     */
    public String getCompleteName()
    {
        return completeName;
    }

    /**
     * @param completeName
     *            The completeName to set.
     */
    public void setCompleteName(String completeName)
    {
        this.completeName = completeName;
    }

    /**
     * @return Returns the contactNo.
     */
    public String getContactNo()
    {
        return contactNo;
    }

    /**
     * @param contactNo
     *            The contactNo to set.
     */
    public void setContactNo(String contactNo)
    {
        this.contactNo = contactNo;
    }

    /**
     * @return Returns the creationDate.
     */
    public String getCreationDate()
    {
        return creationDate;
    }

    /**
     * @param creationDate
     *            The creationDate to set.
     */
    public void setCreationDate(String creationDate)
    {
        this.creationDate = creationDate;
    }

//    public Date ()
//    {
//        return dateOfBirth;
//    }
//
//    public void setDateOfBirth(Date dateOfBirth)
//    {
//        this.dateOfBirth = dateOfBirth;
//    }

    /**
     * @return Returns the emailId.
     */
    public String getEmailId()
    {
        return emailId;
    }

    /**
     * @param emailId
     *            The emailId to set.
     */
    public void setEmailId(String emailId)
    {
        this.emailId = emailId;
    }

    /**
     * @return Returns the firstName.
     */
    public String getFirstName()
    {
        return firstName;
    }

    /**
     * @param firstName
     *            The firstName to set.
     */
    public void setFirstName(String firstName)
    {
        this.firstName = firstName;
    }

    /**
     * @return Returns the lastName.
     */
    public String getLastName()
    {
        return lastName;
    }

    /**
     * @param lastName
     *            The lastName to set.
     */
    public void setLastName(String lastName)
    {
        this.lastName = lastName;
    }

    /**
     * @return Returns the middleName.
     */
    public String getMiddleName()
    {
        return middleName;
    }

    /**
     * @param middleName
     *            The middleName to set.
     */
    public void setMiddleName(String middleName)
    {
        this.middleName = middleName;
    }

    /**
     * @return Returns the mobileNo.
     */
    public String getMobileNo()
    {
        return mobileNo;
    }

    /**
     * @param mobileNo
     *            The mobileNo to set.
     */
    public void setMobileNo(String mobileNo)
    {
        this.mobileNo = mobileNo;
    }

    /**
     * @return Returns the phoneNo.
     */
    public String getPhoneNo()
    {
        return phoneNo;
    }

    /**
     * @param phoneNo
     *            The phoneNo to set.
     */
    public void setPhoneNo(String phoneNo)
    {
        this.phoneNo = phoneNo;
    }

    /**
     * @return Returns the poBoxNo.
     */
    public String getPoBoxNo()
    {
        return poBoxNo;
    }

    /**
     * @param poBoxNo
     *            The poBoxNo to set.
     */
    public void setPoBoxNo(String poBoxNo)
    {
        this.poBoxNo = poBoxNo;
    }

    /**
     * @return Returns the policyQuoteNo.
     */
    public String getPolicyQuoteNo()
    {
        return policyQuoteNo;
    }

    /**
     * @param policyQuoteNo
     *            The policyQuoteNo to set.
     */
    public void setPolicyQuoteNo(String policyQuoteNo)
    {
        this.policyQuoteNo = policyQuoteNo;
    }

    /**
     * @return Returns the customerId.
     */
    public String getCustomerId()
    {
        return customerId;
    }

    /**
     * @param customerId
     *            The customerId to set.
     */
    public void setCustomerId(String customerId)
    {
        this.customerId = customerId;
    }
	/**
	 * @return Returns the currentPage.
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @return Returns the numberOfRecords.
	 */
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	/**
	 * @param numberOfRecords The numberOfRecords to set.
	 */
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	
	/**
	 * @return Returns the dateOfBirth.
	 */
	public String getDateOfBirth() {
		return dateOfBirth;
	}
	/**
	 * @param dateOfBirth The dateOfBirth to set.
	 */
	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}
	
	 /**
     * @return Returns the brokerId.
     */
    public String getBrokerId() {
        return brokerId;
    }
    /**
     * @param brokerId The brokerId to set.
     */
    public void setBrokerId(String brokerId) {
    	super.setBrokerCode(new Integer(brokerId));
        this.brokerId = brokerId;
    }
    
	public String getDistributionChannel() {
		return distributionChannel;
	}
	public void setDistributionChannel(String distributionChannel) {
		this.distributionChannel = distributionChannel;
	}
/**
 * @return Returns the classCode.
 */
public Integer getClassCode() {
	return classCode;
}
/**
 * @param classCode The classCode to set.
 */
public void setClassCode(Integer classCode) {
	this.classCode = classCode;
}
	/**
	 * @return Returns the userId.
	 */
	public Integer getUserId() {
		return userId;
	}
	/**
	 * @param userId The userId to set.
	 */
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	/**
	 * @return Returns the customerExists.
	 */
	public String getCustomerExists() {
		return customerExists;
	}
	/**
	 * @param customerExists The customerExists to set.
	 */
	public void setCustomerExists(String customerExists) {
		this.customerExists = customerExists;
	}
	/**
	 * @return Returns the locationCodeCreate.
	 */
	public Integer getLocationCodeCreate() {
		return locationCodeCreate;
	}
	/**
	 * @param locationCodeCreate The locationCodeCreate to set.
	 */
	public void setLocationCodeCreate(Integer locationCodeCreate) {
		this.locationCodeCreate = locationCodeCreate;
	}
	/**
	 * @return Returns the customerSaveReq.
	 */
	public String getCustomerSaveReq() {
		return customerSaveReq;
	}
	/**
	 * @param customerSaveReq The customerSaveReq to set.
	 */
	public void setCustomerSaveReq(String customerSaveReq) {
		this.customerSaveReq = customerSaveReq;
	}
	/**
	 * @return Returns the hideLocation.
	 */
	public Integer getHideLocation() {
		return hideLocation;
	}
	/**
	 * @param hideLocation The hideLocation to set.
	 */
	public void setHideLocation(Integer hideLocation) {
		this.hideLocation = hideLocation;
	}
	/**
	 * @return Returns the custSaveReq.
	 */
	public String getCustSaveReq() {
		return custSaveReq;
	}
	/**
	 * @param custSaveReq The custSaveReq to set.
	 */
	public void setCustSaveReq(String custSaveReq) {
		this.custSaveReq = custSaveReq;
	}
	/**
	 * @return Returns the copyButtonReq.
	 */
	public String getCopyButtonReq() {
		return copyButtonReq;
	}
	/**
	 * @param copyButtonReq The copyButtonReq to set.
	 */
	public void setCopyButtonReq(String copyButtonReq) {
		this.copyButtonReq = copyButtonReq;
	}
	
	 /**
	 * @return the insuredCode
	 */
	public String getInsuredCode() {
		return insuredCode;
	}
	/**
	 * @param insuredCode the insuredCode to set
	 */
	public void setInsuredCode(String insuredCode) {
		this.insuredCode = insuredCode;
	}
	/**
	 * @return the ccgCode
	 */
	public String getCcgCode(){
		return ccgCode;
	}
	/**
	 * @param ccgCode the ccgCode to set
	 */
	public void setCcgCode( String ccgCode ){
		this.ccgCode = ccgCode;
	}
	/**
	 * @return the exactSearch
	 */
	public Boolean getExactSearch(){
		return exactSearch;
	}
	/**
	 * @param exactSearch the exactSearch to set
	 */
	public void setExactSearch( Boolean exactSearch ){
		this.exactSearch = exactSearch;
	}
	
	
}