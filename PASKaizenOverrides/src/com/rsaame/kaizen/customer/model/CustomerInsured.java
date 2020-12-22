/*
 * @(#)CustomerInsured.java             2007/03/18
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

import java.util.Date;

import com.rsaame.kaizen.admin.model.Agent;
import com.rsaame.kaizen.admin.model.Country;
import com.rsaame.kaizen.admin.model.Employee;
import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.model.BaseAMEBO;

/**
 * T_MAS_CUSTOMER_INSURED is mapped to CustomerInsured Class.
 * 
 * @version 1.0 1 Sep 2007
 * @author Cognizant Technology Solutions
 */

public class CustomerInsured extends BaseAMEBO {

	/* currentPage and numberOfRecords Added by ADM 28.04.2009 CR04 Customer Versioning (Release 2.5) */
	
	private Integer currentPage = new Integer(AMEConstants.CURRENT_PAGE);
	
    private Integer numberOfRecords = Integer.valueOf(0);
	
	/** identifier field */
    private Long insuredId;

    /** nullable persistent field */
    private Integer nameTitle;

    /** nullable persistent field */
    private String engFirstName;

    /** nullable persistent field */
    private String arabicFirstName;

    /** nullable persistent field */
    private String engMiddleName;

    /** nullable persistent field */
    private String arabicMiddleName;

    /** nullable persistent field */
    private String engLastName;

    /** nullable persistent field */
    private String arabicLastName;

    /** nullable persistent field */
    private String companyName;

    /** nullable persistent field */
    private String companyAddr1;

    /** nullable persistent field */
    private String companyAddr2;

    /** nullable persistent field */
    private String companyAddr3;

    /** nullable persistent field */
    private String engZipCode;

    /** nullable persistent field */
    private String engMobileNo;

    /** nullable persistent field */
    private String engEmailId;

    /** nullable persistent field */
    private Long faxNo;

    /** nullable persistent field */
    private Date dob;

    /** nullable persistent field */
    private String placeOfBirth;

    /** nullable persistent field */
    private String business;

    /** nullable persistent field */
    private String engPassportNo;

    /** nullable persistent field */
    private String engCoRegnNo;

    /** nullable persistent field */
    private Integer extAccExecCode;

    /** nullable persistent field */
    private String engPhoneNo;

    /** nullable persistent field */
    private String wayNo;

    /** nullable persistent field */
    private String bldgNo;

    /** nullable persistent field */
    private String affinityRefNo;

    /** nullable persistent field */
    private String customerType;

    /** nullable persistent field */
    private String excComModes;

    /** nullable persistent field */
    private String legalForm;

    /** nullable persistent field */
    private Date dtEstablishment;

    /** nullable persistent field */
    private String placeEstablishment;

    /** nullable persistent field */
    private String regulatoryBody;

    /** nullable persistent field */
    private String externalAuditor;

    /** nullable persistent field */
    private Date dtCollectionKyc;

    /** nullable persistent field */
    private Date expiryDate;

    /** nullable persistent field */
    private String suspendCustomer;

    /** nullable persistent field */
    private Integer brCode;

    // private Broker broker;

    private Long customerId;

    /** persistent field */
    private Agent agent;

    /** persistent field */
    private DistributionChannel distributionChannel;

    //    /** persistent field */
    //    private Employee employee;

    private Integer employee;

    /** persistent field */
    private Country nationality;

    /** persistent field */
    private Country country;

    /** persistent field */
    private CustomerCategory customerCategory;

    /** persistent field */
    private Occupation occupation;

    /** persistent field */
    private CustomerSource customerSource;

    /* Dummy field to set response Status */
    private String statusResponse;

    private Integer status;

    private String idNo;

    //	private Location location;

    private Integer idType;

    private String fullName;

    private String companyAddress;

    private String hintQuestion;

    private String hintAnswer;

    private String password;

    private String newPassword;

    private Integer locationCode;

    private String arabicAddress;

    private String engAddress;

    private String remarks;

    private Integer preparedBy;

    private Integer modifiedBy;

    private Date preparedDt;

    private Date modifiedDt;

    private String engFullName;

    private Integer branchCode;

    private Integer regionCode;
    
    private String sex;
    
    private String postalCode;
    
    private Long policyCount;
    
//  CR70 Matching CustInsured change:
    private String citydesc;
    
    private String brkname;
    
    private String statusdesc;
    
    private String nametitleDesc;
    
    private Long marketingFee;
    
    private String internalexecdesc;
    
    private String externalexecdesc;
    
    // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)
    private Integer locationCodeCustomer;

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
	 * @return Returns the sex.
	 */
	public String getSex() {
		return sex;
	}
	/**
	 * @param sex The sex to set.
	 */
	public void setSex(String sex) {
		this.sex = sex;
	}
    /** full constructor */
    public CustomerInsured(Long insuredId, Integer nameTitle, String engFirstName, String arabicFirstName,
            String engMiddleName, String arabicMiddleName, String engLastName, String arabicLastName,
            String companyName, String companyAddr1, String companyAddr2, String companyAddr3, String engZipCode,
            String engMobileNo, String arabicMobileNo, String engEmailId, String arabicEmailId,
            Long faxNo, Date dob, String placeOfBirth, String business, String engPassportNo, String arabicPassportNo,
            String engCoRegnNo, String arabicCoRegnNo, Integer extAccExecCode, String engPhoneNo, String arabicPhoneNo,
            String wayNo, String bldgNo, String affinityRefNo, String customerType, String excComModes,
            String legalForm, Date dtEstablishment, String placeEstablishment, String regulatoryBody,
            String externalAuditor, Date dtCollectionKyc, Date expiryDate, String suspendCustomer, Integer brCode,
            Long customerId, Agent agent, DistributionChannel distributionChannel, Employee employee,
            Country nationality, Country country, CustomerCategory customerCategory, Occupation occupation,
            CustomerSource customerSource, String statusResponse, Integer status, String idNo, Integer idType,
            String fullName, String companyAddress, String hintQuestion, String hintAnswer, String password,
            Integer locationCode, String arabicAddress, String engAddress,Integer currentPage,Integer numberOfRecords) {
        this.insuredId = insuredId;
        this.nameTitle = nameTitle;
        this.engFirstName = engFirstName;
        this.arabicFirstName = arabicFirstName;
        this.engMiddleName = engMiddleName;
        this.arabicMiddleName = arabicMiddleName;
        this.engLastName = engLastName;
        this.arabicLastName = arabicLastName;
        this.companyName = companyName;
        this.companyAddr1 = companyAddr1;
        this.companyAddr2 = companyAddr2;
        this.companyAddr3 = companyAddr3;
        this.engZipCode = engZipCode;
        this.engMobileNo = engMobileNo;
        this.engEmailId = engEmailId;
        this.faxNo = faxNo;
        this.dob = dob;
        this.placeOfBirth = placeOfBirth;
        this.business = business;
        this.engPassportNo = engPassportNo;
        this.engCoRegnNo = engCoRegnNo;
        this.extAccExecCode = extAccExecCode;
        this.engPhoneNo = engPhoneNo;
        this.wayNo = wayNo;
        this.bldgNo = bldgNo;
        this.affinityRefNo = affinityRefNo;
        this.customerType = customerType;
        this.excComModes = excComModes;
        this.legalForm = legalForm;
        this.dtEstablishment = dtEstablishment;
        this.placeEstablishment = placeEstablishment;
        this.regulatoryBody = regulatoryBody;
        this.externalAuditor = externalAuditor;
        this.dtCollectionKyc = dtCollectionKyc;
        this.expiryDate = expiryDate;
        this.suspendCustomer = suspendCustomer;
        this.brCode = brCode;
        this.customerId = customerId;
        this.agent = agent;
        this.distributionChannel = distributionChannel;
        //  this.employee = employee;
        this.nationality = nationality;
        this.country = country;
        this.customerCategory = customerCategory;
        this.occupation = occupation;
        this.customerSource = customerSource;
        this.statusResponse = statusResponse;
        this.status = status;
        this.idNo = idNo;
        this.idType = idType;
        this.fullName = fullName;
        this.companyAddress = companyAddress;
        this.hintQuestion = hintQuestion;
        this.hintAnswer = hintAnswer;
        this.password = password;
        this.locationCode = locationCode;
        this.arabicAddress = arabicAddress;
        this.engAddress = engAddress;
        this.currentPage = currentPage;
        this.numberOfRecords = numberOfRecords;
    }

    /** default constructor */
    public CustomerInsured() {
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getStatusResponse() {
        return statusResponse;
    }

    public void setStatusResponse(String statusResponse) {
        this.statusResponse = statusResponse;
    }

    public String getAffinityRefNo() {
        return affinityRefNo;
    }

    public void setAffinityRefNo(String affinityRefNo) {
        this.affinityRefNo = affinityRefNo;
    }

    public Agent getAgent() {
        return agent;
    }

    public void setAgent(Agent agent) {
        this.agent = agent;
    }

    public String getArabicFirstName() {
        return arabicFirstName;
    }

    public void setArabicFirstName(String arabicFirstName) {
        this.arabicFirstName = arabicFirstName;
    }

    public String getArabicLastName() {
        return arabicLastName;
    }

    public void setArabicLastName(String arabicLastName) {
        this.arabicLastName = arabicLastName;
    }

    public String getArabicMiddleName() {
        return arabicMiddleName;
    }

    public void setArabicMiddleName(String arabicMiddleName) {
        this.arabicMiddleName = arabicMiddleName;
    }

    public String getBldgNo() {
        return bldgNo;
    }

    public void setBldgNo(String bldgNo) {
        this.bldgNo = bldgNo;
    }

    public Integer getBrCode() {
        return brCode;
    }

    public void setBrCode(Integer brCode) {
    	super.setBrokerCode(brCode);
        this.brCode = brCode;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCompanyAddr1() {
        return companyAddr1;
    }

    public void setCompanyAddr1(String companyAddr1) {
        this.companyAddr1 = companyAddr1;
    }

    public String getCompanyAddr2() {
        return companyAddr2;
    }

    public void setCompanyAddr2(String companyAddr2) {
        this.companyAddr2 = companyAddr2;
    }

    public String getCompanyAddr3() {
        return companyAddr3;
    }

    public void setCompanyAddr3(String companyAddr3) {
        this.companyAddr3 = companyAddr3;
    }

    public String getCompanyAddress() {
        return this.getCompanyAddr1() + "\n" + this.getCompanyAddr2() + "\n" + this.getCompanyAddr3();
    }

    public void setCompanyAddress(String companyAddress) {
        this.companyAddress = companyAddress;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public CustomerCategory getCustomerCategory() {
        return customerCategory;
    }

    public void setCustomerCategory(CustomerCategory customerCategory) {
        this.customerCategory = customerCategory;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public CustomerSource getCustomerSource() {
        return customerSource;
    }

    public void setCustomerSource(CustomerSource customerSource) {
        this.customerSource = customerSource;
    }

    public String getCustomerType() {
        return customerType;
    }

    public void setCustomerType(String customerType) {
        this.customerType = customerType;
    }

    public DistributionChannel getDistributionChannel() {
        return distributionChannel;
    }

    public void setDistributionChannel(DistributionChannel distributionChannel) {
        this.distributionChannel = distributionChannel;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    public Date getDtCollectionKyc() {
        return dtCollectionKyc;
    }

    public void setDtCollectionKyc(Date dtCollectionKyc) {
        this.dtCollectionKyc = dtCollectionKyc;
    }

    public Date getDtEstablishment() {
        return dtEstablishment;
    }

    public void setDtEstablishment(Date dtEstablishment) {
        this.dtEstablishment = dtEstablishment;
    }

    /*
     * public Employee getEmployee() { return employee; }
     * 
     * public void setEmployee(Employee employee) { this.employee = employee; }
     */
    public String getEngCoRegnNo() {
        return engCoRegnNo;
    }

    public void setEngCoRegnNo(String engCoRegnNo) {
        this.engCoRegnNo = engCoRegnNo;
    }

    public String getEngEmailId() {
        return engEmailId;
    }

    public void setEngEmailId(String engEmailId) {
        this.engEmailId = engEmailId;
    }

    public String getEngFirstName() {
        return engFirstName;
    }

    public void setEngFirstName(String engFirstName) {
        if (engFirstName != null)
        /* Overriding Kaizen logic to save the insured name in caps as in PAS that is not required*/	
        //  engFirstName = engFirstName.toUpperCase();
        this.engFirstName = engFirstName;
    }

    public String getEngLastName() {
        return engLastName;
    }

    public void setEngLastName(String engLastName) {
        if (engLastName != null)
        	 /* Overriding Kaizen logic to save the insured name in caps as in PAS that is not required*/
        	// engLastName = engLastName.toUpperCase();
        this.engLastName = engLastName;
    }

    public String getEngMiddleName() {

        return engMiddleName;
    }

    public void setEngMiddleName(String engMiddleName) {
        if (engMiddleName != null)
        	 /* Overriding Kaizen logic to save the insured name in caps as in PAS that is not required*/
            // engMiddleName = engMiddleName.toUpperCase();
        this.engMiddleName = engMiddleName;
    }

    public String getEngMobileNo() {
        return engMobileNo;
    }

    public void setEngMobileNo(String engMobileNo) {
        this.engMobileNo = engMobileNo;
    }

    public String getEngPassportNo() {
        return engPassportNo;
    }

    public void setEngPassportNo(String engPassportNo) {
        this.engPassportNo = engPassportNo;
    }

    public String getEngPhoneNo() {
        return engPhoneNo;
    }

    public void setEngPhoneNo(String engPhoneNo) {
        this.engPhoneNo = engPhoneNo;
    }

    public String getEngZipCode() {
        return engZipCode;
    }

    public void setEngZipCode(String engZipCode) {
        this.engZipCode = engZipCode;
    }

    public String getExcComModes() {
        return excComModes;
    }

    public void setExcComModes(String excComModes) {
        this.excComModes = excComModes;
    }

    public Date getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Date expiryDate) {
        this.expiryDate = expiryDate;
    }

    public Integer getExtAccExecCode() {
        return extAccExecCode;
    }

    public void setExtAccExecCode(Integer extAccExecCode) {
        this.extAccExecCode = extAccExecCode;
    }

    public String getExternalAuditor() {
        return externalAuditor;
    }

    public void setExternalAuditor(String externalAuditor) {
        this.externalAuditor = externalAuditor;
    }

    public Long getFaxNo() {
        return faxNo;
    }

    public void setFaxNo(Long faxNo) {
        this.faxNo = faxNo;
    }

    public String getFullName() {
        return this.getEngFirstName() + " " + this.getEngMiddleName() + " " + this.getEngLastName();
    }

    public void setFullName(String fullName) {
        this.fullName = this.getEngFirstName() + " " + this.getEngMiddleName() + " " + this.getEngLastName();
    }

    public String getHintAnswer() {
        return hintAnswer;
    }

    public void setHintAnswer(String hintAnswer) {
        this.hintAnswer = hintAnswer;
    }

    public String getHintQuestion() {
        return hintQuestion;
    }

    public void setHintQuestion(String hintQuestion) {
        this.hintQuestion = hintQuestion;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getIdType() {
        return idType;
    }

    public void setIdType(Integer idType) {
        this.idType = idType;
    }

    public Long getInsuredId() {
        return insuredId;
    }

    public void setInsuredId(Long insuredId) {
        this.insuredId = insuredId;
    }

    public String getLegalForm() {
        return legalForm;
    }

    public void setLegalForm(String legalForm) {
        this.legalForm = legalForm;
    }

    public Integer getLocationCode() {
        return locationCode;
    }

    public void setLocationCode(Integer locationCode) {
        this.locationCode = locationCode;
    }

    public Integer getNameTitle() {
        return nameTitle;
    }

    public void setNameTitle(Integer nameTitle) {
        this.nameTitle = nameTitle;
    }

    public Country getNationality() {
        return nationality;
    }

    public void setNationality(Country nationality) {
        this.nationality = nationality;
    }

    public Occupation getOccupation() {
        return occupation;
    }

    public void setOccupation(Occupation occupation) {
        this.occupation = occupation;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPlaceEstablishment() {
        return placeEstablishment;
    }

    public void setPlaceEstablishment(String placeEstablishment) {
        this.placeEstablishment = placeEstablishment;
    }

    public String getPlaceOfBirth() {
        return placeOfBirth;
    }

    public void setPlaceOfBirth(String placeOfBirth) {
        this.placeOfBirth = placeOfBirth;
    }

    public String getRegulatoryBody() {
        return regulatoryBody;
    }

    public void setRegulatoryBody(String regulatoryBody) {
        this.regulatoryBody = regulatoryBody;
    }

    public String getSuspendCustomer() {
        return suspendCustomer;
    }

    public void setSuspendCustomer(String suspendCustomer) {
        this.suspendCustomer = suspendCustomer;
    }

    public String getWayNo() {
        return wayNo;
    }

    public void setWayNo(String wayNo) {
        this.wayNo = wayNo;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getArabicAddress() {
        return arabicAddress;
    }

    public void setArabicAddress(String arabicAddress) {
        this.arabicAddress = arabicAddress;
    }

    public String getEngAddress() {
        return engAddress;
    }

    public void setEngAddress(String engAddress) {
        this.engAddress = engAddress;
    }

    public Integer getModifiedBy() {
        if (this.modifiedBy == null) {
            return getLoggedInUserId();
        }
        return modifiedBy;

    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Date getModifiedDt() {
        return modifiedDt;
    }

    public void setModifiedDt(Date modifiedDt) {
        this.modifiedDt = modifiedDt;
    }

    public Integer getPreparedBy() {
        if (this.preparedBy == null) {
            return getLoggedInUserId();
        }
        return preparedBy;
    }

    public void setPreparedBy(Integer preparedBy) {
        this.preparedBy = preparedBy;
    }

    public Date getPreparedDt() {
        return preparedDt;
    }

    public void setPreparedDt(Date preparedDt) {
        this.preparedDt = preparedDt;
    }/*
      * public Broker getBroker() { return broker; } public void
      * setBroker(Broker broker) { this.broker = broker; }
      */

    public String getEngFullName() {

        engFullName = (getEngFirstName() != null ? getEngFirstName() : "")
                + (getEngMiddleName() != null ? " " + getEngMiddleName() : "")
                + (getEngLastName() != null ? " " + getEngLastName() : "");
        return engFullName;
    }

    public void setEngFullName(String engFullName) {
        this.engFullName = engFullName;
    }

    /**
     * @return Returns the newPassword.
     */
    public String getNewPassword() {
        return newPassword;
    }

    /**
     * @param newPassword
     *            The newPassword to set.
     */
    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    /**
     * @return Returns the branchCode.
     */
    public Integer getBranchCode() {
        return branchCode;
    }

    /**
     * @param branchCode
     *            The branchCode to set.
     */
    public void setBranchCode(Integer branchCode) {
        this.branchCode = branchCode;
    }

    /**
     * @return Returns the regionCode.
     */
    public Integer getRegionCode() {
        return regionCode;
    }

    /**
     * @param regionCode
     *            The regionCode to set.
     */
    public void setRegionCode(Integer regionCode) {
        this.regionCode = regionCode;
    }

    public Integer getEmployee() {
        return employee;
    }

    public void setEmployee(Integer employee) {
        this.employee = employee;
    }
    
	/**
	 * @return Returns the policyCount.
	 */
	public Long getPolicyCount() {
		return policyCount;
	}
	/**
	 * @param policyCount The policyCount to set.
	 */
	public void setPolicyCount(Long policyCount) {
		this.policyCount = policyCount;
	}
	/**
	 * @return Returns the citydesc.
	 */
	public String getCitydesc() {
		return citydesc;
	}
	/**
	 * @param citydesc The citydesc to set.
	 */
	public void setCityDesc(String citydesc) {
		this.citydesc = citydesc;
	}
	/**
	 * @return Returns the brkname.
	 */
	public String getBrkname() {
		return brkname;
	}
	/**
	 * @param brkname The brkname to set.
	 */
	public void setBrkname(String brkname) {
		this.brkname = brkname;
	}
	/**
	 * @return Returns the statusdesc.
	 */
	public String getStatusdesc() {
		return statusdesc;
	}
	/**
	 * @param statusdesc The statusdesc to set.
	 */
	public void setStatusdesc(String statusdesc) {
		this.statusdesc = statusdesc;
	}

	/**
	 * @return Returns the externalexecdesc.
	 */
	public String getExternalexecdesc() {
		return externalexecdesc;
	}
	/**
	 * @param externalexecdesc The externalexecdesc to set.
	 */
	public void setExternalexecdesc(String externalexecdesc) {
		this.externalexecdesc = externalexecdesc;
	}
	/**
	 * @return Returns the internalexecdesc.
	 */
	public String getInternalexecdesc() {
		return internalexecdesc;
	}
	/**
	 * @param internalexecdesc The internalexecdesc to set.
	 */
	public void setInternalexecdesc(String internalexecdesc) {
		this.internalexecdesc = internalexecdesc;
	}
	/**
	 * @return Returns the marketingFee.
	 */
	public Long getMarketingFee() {
		return marketingFee;
	}
	/**
	 * @param marketingFee The marketingFee to set.
	 */
	public void setMarketingFee(Long marketingFee) {
		this.marketingFee = marketingFee;
	}
	/**
	 * @return Returns the nametitleDesc.
	 */
	public String getNametitleDesc() {
		return nametitleDesc;
	}
	/**
	 * @param nametitleDesc The nametitleDesc to set.
	 */
	public void setNametitleDesc(String nametitleDesc) {
		this.nametitleDesc = nametitleDesc;
	}
	/**
	 * @return Returns the locationCodeCustomer.
	 */
	public Integer getLocationCodeCustomer() {
		return locationCodeCustomer;
	}
	/**
	 * @param locationCodeCustomer The locationCodeCustomer to set.
	 */
	public void setLocationCodeCustomer(Integer locationCodeCustomer) {
		this.locationCodeCustomer = locationCodeCustomer;
	}
	/**
	 * @return Returns the postalCode.
	 */
	public String getPostalCode() {
		return postalCode;
	}
	/**
	 * @param postalCode The postalCode to set.
	 */
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}
}