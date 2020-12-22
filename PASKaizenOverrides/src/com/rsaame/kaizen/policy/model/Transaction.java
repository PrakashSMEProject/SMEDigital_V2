/*
* Created on Sep 5, 2007
*
* TODO To change the template for this generated file go to
* Window - Preferences - Java - Code Style - Code Templates
*/
package com.rsaame.kaizen.policy.model;

import java.math.BigDecimal;
import java.util.Date;

import com.rsaame.kaizen.framework.constants.AMEConstants;
import com.rsaame.kaizen.framework.model.BaseAMEBO;

/**
* @author 123711
* 
 * TODO To change the template for this generated type comment go to Window -
* Preferences - Java - Code Style - Code Templates
*/
public class Transaction extends BaseAMEBO
{
    private String transactionNumber;

    private String transactionEndtId;

    private Date transactionFrom;

    private Date transactionTo;

    private String transactionCustomerName;

    private String transactionCompanyName; //Added by ADM 07.08.2009 for Ticket # 8185 To Add Company name in Transaction Search criteria
        
    private String transactionBrokerName;

    private String transactionScheme;

    private Date transactionEffectiveDate;

    private Date transactionExpiryDate;

    private String transactionLastModifiedBy;

    private String transactionCreatedBy;

    private String transactionDistributionCode;

    private String transactionStatus;

    private String transactionCertificateNumberFrom;

    private String transactionCertificateNumberTo;

    private boolean lastTransaction;

    private String transactionEndorsementNumber;

    private String transactionPolicyNumber;

    private String transactionType;

    private String transactionPolicyType;

    private String transactionDateTime;

    private String transactionRisk;

    private BigDecimal transactionSumInsured;//Modified for CurrencyHandler

    private BigDecimal transactionFinalPremium;//Modified for CurrencyHandler

    private String transactionPremium;

    private String transactionClass;
    
  //Sonar fix
    private Integer currentPage = Integer.valueOf(AMEConstants.CURRENT_PAGE);
    
    private Integer numberOfRecords = Integer.valueOf( 0 );
    
    private Boolean isChecked = Boolean.FALSE;
    
    private String transactionEngineNo;
    
    private String transactionChassisNo;
    
    //private String transactionCertificateNo;
    
    private String transactionRegNo;
    
    //added for transaction history
    private String effectiveDate;

    private String expiryDate;
    
    private String comments;
    
    private String policyId;
    
    private String quotationNo;
    
    private String issueDate;
    
    private String isEditMode;

        
    //Added for Transaction Drop Down
    
    private String quotePolicy;

    //Added for role specific search - Broker
    private String brokerId;
    
    private String brokerName;
    
    private String schemeName;
    
    
    
    //Added for Transaction View/Edit
    private String transactionTypeCode;
    private String suspendTransactionInd;
    
    //For Full Customer Name
    private String completeName;
    // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)
    private String companyName;
    
    //CR 236
    private boolean exactSearch;
    private String quoteEntered;
    private String policyEntered;
    private String transactionNumberPolicy;
    
    //Restrict view Manual Tariff Policy
    private String policyTariffCode;
    private String policyIssueHour;
    
    //policy renewal counter - CR 33
    private String polRenewalCounter;
    
    //CR 47
    private String policyApprover;
    //ADM 08.10.2009 : CR04 : For concurrent user alert message 
    private String tempTransactionEndtNo;
    // ADM 03.03.2010 Agent Profile (Release 2.5.2)
    private String transactionAgentName;    
    private String agentId;
    
       // ADM 18.02.2010 CR65 Access To Other Location data (Release 3.0)     
    private Integer locationCode;    
    private String locationName;    
    private Integer locationCodeCreate;  
    private Integer userId;  
    private Integer borderInd;
    
    // Added as part of Release 3.1 
    private String mobileNo;
    private String phoneNo;
    private String polReferenceNo;
    
    //Added 3.8 release for comments.
    private String forHistoryView;
    
   
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
                /**
                * @return Returns the transactionAgentName.
                */
                public String getTransactionAgentName() {
                                return transactionAgentName;
                }
                /**
                * @param transactionAgentName The transactionAgentName to set.
                */
                public void setTransactionAgentName(String transactionAgentName) {
                                this.transactionAgentName = transactionAgentName;
                }
    
                    
                /**
                * 
                 * @param transactionNumber
                * @param transactionEndtId
                * @param transactionFrom
                * @param transactionTo
                * @param transactionCustomerName
                * @param transactionCompanyName
                * @param transactionBrokerName
                * @param transactionScheme
                * @param transactionEffectiveDate
                * @param transactionExpiryDate
                * @param transactionLastModifiedBy
                * @param transactionCreatedBy
                * @param transactionDistributionCode
                * @param transactionStatus
                * @param transactionCertificateNumberFrom
                * @param transactionCertificateNumberTo
                * @param lastTransaction
                * @param transactionEndorsementNumber
                * @param transactionPolicyNumber
                * @param transactionType
                * @param transactionPolicyType
                * @param transactionDateTime
                * @param transactionRisk
                * @param transactionSumInsured
                * @param transactionFinalPremium
                * @param transactionPremium
                * @param transactionClass
                * @param currentPage
                * @param numberOfRecords
                * @param isChecked
                * @param effectiveDate
                * @param expiryDate
                * @param comments
                * @param policyId
                * @param quotationNo
                * @param issueDate
                * @param quotePolicy
                * @param brokerId
                * @param transactionTypeCode
                * @param suspendTransactionInd
                * @param completeName
                * @param exactSearch
                * @param quoteEntered
                * @param policyEntered
                * @param transactionNumberPolicy
                * @param policyTariffCode
                * @param policyIssueHour
                * @param polRenewalCounter
                * @param policyApprover
                */
                public Transaction(String transactionNumber, String transactionEndtId,
                                                Date transactionFrom, Date transactionTo,
                                                String transactionCustomerName, String transactionCompanyName, 
                                                String transactionBrokerName,
                                                String transactionScheme, Date transactionEffectiveDate,
                                                Date transactionExpiryDate, String transactionLastModifiedBy,
                                                String transactionCreatedBy, String transactionDistributionCode,
                                                String transactionStatus, String transactionCertificateNumberFrom,
                                                String transactionCertificateNumberTo, boolean lastTransaction,
                                                String transactionEndorsementNumber,
                                                String transactionPolicyNumber, String transactionType,
                                                String transactionPolicyType, String transactionDateTime,
                                                String transactionRisk, BigDecimal transactionSumInsured,
                                                BigDecimal transactionFinalPremium, String transactionPremium,
                                                String transactionClass, Integer currentPage,
                                                Integer numberOfRecords, Boolean isChecked, String effectiveDate, String expiryDate,
                                                String comments,String policyId, String quotationNo,String issueDate,String quotePolicy,
                                                String brokerId,String transactionTypeCode,String suspendTransactionInd,String completeName,
                                                boolean exactSearch,String quoteEntered,String policyEntered,String transactionNumberPolicy,
                                                String policyTariffCode,String policyIssueHour,String polRenewalCounter, String policyApprover,
                                                String mobileNo,String phoneNo,String polReferenceNo,String forHistoryView) {
                                this.transactionNumber = transactionNumber;
                                this.transactionEndtId = transactionEndtId;
                                this.transactionFrom = transactionFrom;
                                this.transactionTo = transactionTo;
                                this.transactionCustomerName = transactionCustomerName;
                                this.transactionCompanyName = transactionCompanyName;
                                this.transactionBrokerName = transactionBrokerName;
                                this.transactionScheme = transactionScheme;
                                this.transactionEffectiveDate = transactionEffectiveDate;
                                this.transactionExpiryDate = transactionExpiryDate;
                                this.transactionLastModifiedBy = transactionLastModifiedBy;
                                this.transactionCreatedBy = transactionCreatedBy;
                                this.transactionDistributionCode = transactionDistributionCode;
                                this.transactionStatus = transactionStatus;
                                this.transactionCertificateNumberFrom = transactionCertificateNumberFrom;
                                this.transactionCertificateNumberTo = transactionCertificateNumberTo;
                                this.lastTransaction = lastTransaction;
                                this.transactionEndorsementNumber = transactionEndorsementNumber;
                                this.transactionPolicyNumber = transactionPolicyNumber;
                                this.transactionType = transactionType;
                                this.transactionPolicyType = transactionPolicyType;
                                this.transactionDateTime = transactionDateTime;
                                this.transactionRisk = transactionRisk;
                                this.transactionSumInsured = transactionSumInsured;
                                this.transactionFinalPremium = transactionFinalPremium;
                                this.transactionPremium = transactionPremium;
                                this.transactionClass = transactionClass;
                                this.currentPage = currentPage;
                                this.numberOfRecords = numberOfRecords;
                                this.isChecked = isChecked;
                                this.effectiveDate = effectiveDate;
                                this.expiryDate = expiryDate;
                                this.comments = comments;
                                this.policyId = policyId;
                                this.quotationNo = quotationNo;
                                this.issueDate = issueDate;
                                this.quotePolicy = quotePolicy;
                                this.brokerId = brokerId;
                                this.transactionTypeCode = transactionTypeCode;
                                this.suspendTransactionInd = suspendTransactionInd;
                                this.completeName = completeName;
                                this.exactSearch = exactSearch;
                                this.quoteEntered = quoteEntered;
                                this.policyEntered = policyEntered;
                                this.transactionNumberPolicy = transactionNumberPolicy;
                                this.policyTariffCode = policyTariffCode;
                                this.policyIssueHour = policyIssueHour;
                                this.polRenewalCounter = polRenewalCounter;
                                this.policyApprover = policyApprover;
                                this.mobileNo = mobileNo;
                                this.phoneNo = phoneNo;
                                this.polReferenceNo = polReferenceNo;
                                this.forHistoryView=forHistoryView;
                }
    /**
     * @return Returns the isChecked.
     */
    public Boolean getIsChecked()
    {
        return isChecked;
    }

    /**
     * @param isChecked
     *            The isChecked to set.
     */
    public void setIsChecked(Boolean isChecked)
    {
        this.isChecked = isChecked;
    }

    public String getTransactionEndtId()
    {
        return transactionEndtId;
    }

    public void setTransactionEndtId(String transactionEndtId)
    {
        this.transactionEndtId = transactionEndtId;
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
    public Transaction()
    {

    }

    public boolean isLastTransaction()
    {
        return lastTransaction;
    }

    public void setLastTransaction(boolean lastTransaction)
    {
        this.lastTransaction = lastTransaction;
    }

    public String getTransactionBrokerName()
    {
        return transactionBrokerName;
    }

    public void setTransactionBrokerName(String transactionBrokerName)
    {
        this.transactionBrokerName = transactionBrokerName;
    }

    public String getTransactionCertificateNumberFrom()
    {
        return transactionCertificateNumberFrom;
    }

    public void setTransactionCertificateNumberFrom(
            String transactionCertificateNumberFrom)
    {
        this.transactionCertificateNumberFrom = transactionCertificateNumberFrom;
    }

    public String getTransactionCertificateNumberTo()
    {
        return transactionCertificateNumberTo;
    }

    public void setTransactionCertificateNumberTo(
            String transactionCertificateNumberTo)
    {
        this.transactionCertificateNumberTo = transactionCertificateNumberTo;
    }

    public String getTransactionCreatedBy()
    {
        return transactionCreatedBy;
    }

    public void setTransactionCreatedBy(String transactionCreatedBy)
    {
        this.transactionCreatedBy = transactionCreatedBy;
    }

    public String getTransactionCustomerName()
    {
        return transactionCustomerName;
    }

    public void setTransactionCustomerName(String transactionCustomerName)
    {
        this.transactionCustomerName = transactionCustomerName;
    }
    
    //Added by ADM 07.08.2009 for Ticket # 8185 To Add Company name in Transaction Search criteria
   public String getTransactionCompanyName()
    {
        return transactionCompanyName;
    }
    
    public void setTransactionCompanyName(String transactionCompanyName)
    {
        this.transactionCompanyName = transactionCompanyName;
    }
    //Added by ADM 07.08.2009 Ends
    
    public String getTransactionDateTime()
    {
        return transactionDateTime;
    }

    public void setTransactionDateTime(String transactionDateTime)
    {
        this.transactionDateTime = transactionDateTime;
    }

    public String getTransactionDistributionCode()
    {
        return transactionDistributionCode;
    }

    public void setTransactionDistributionCode(
            String transactionDistributionCode)
    {
        this.transactionDistributionCode = transactionDistributionCode;
    }

    public Date getTransactionEffectiveDate()
    {
        return transactionEffectiveDate;
    }

    public void setTransactionEffectiveDate(Date transactionEffectiveDate)
    {
        this.transactionEffectiveDate = transactionEffectiveDate;
    }

    public String getTransactionEndorsementNumber()
    {
        return transactionEndorsementNumber;
    }

    public void setTransactionEndorsementNumber(
            String transactionEndorsementNumber)
    {
        this.transactionEndorsementNumber = transactionEndorsementNumber;
    }

    public Date getTransactionExpiryDate()
    {
        return transactionExpiryDate;
    }

    public void setTransactionExpiryDate(Date transactionExpiryDate)
    {
        this.transactionExpiryDate = transactionExpiryDate;
    }

    public BigDecimal getTransactionFinalPremium()
    {
        return transactionFinalPremium;
    }

    public void setTransactionFinalPremium(BigDecimal transactionFinalPremium)
    {
        this.transactionFinalPremium = transactionFinalPremium;
    }

    public Date getTransactionFrom()
    {
        return transactionFrom;
    }

    public void setTransactionFrom(Date transactionFrom)
    {
        this.transactionFrom = transactionFrom;
    }

    public String getTransactionLastModifiedBy()
    {
        return transactionLastModifiedBy;
    }

    public void setTransactionLastModifiedBy(String transactionLastModifiedBy)
    {
        this.transactionLastModifiedBy = transactionLastModifiedBy;
    }

    public String getTransactionNumber()
    {
        return transactionNumber;
    }

    public void setTransactionNumber(String transactionNumber)
    {
        this.transactionNumber = transactionNumber;
    }

    public String getTransactionPolicyType()
    {
        return transactionPolicyType;
    }

    public void setTransactionPolicyType(String transactionPolicyType)
    {
        this.transactionPolicyType = transactionPolicyType;
    }

    public String getTransactionPremium()
    {
        return transactionPremium;
    }

    public void setTransactionPremium(String transactionPremium)
    {
        this.transactionPremium = transactionPremium;
    }

    public String getTransactionRisk()
    {
        return transactionRisk;
    }

    public void setTransactionRisk(String transactionRisk)
    {
        this.transactionRisk = transactionRisk;
    }

    public String getTransactionScheme()
    {
        return transactionScheme;
    }

    public void setTransactionScheme(String transactionScheme)
    {
        this.transactionScheme = transactionScheme;
    }

    public String getTransactionStatus()
    {
        return transactionStatus;
    }

    public void setTransactionStatus(String transactionStatus)
    {
        this.transactionStatus = transactionStatus;
    }

    public BigDecimal getTransactionSumInsured()
    {
        return transactionSumInsured;
    }

    public void setTransactionSumInsured(BigDecimal transactionSumInsured)
    {
        this.transactionSumInsured = transactionSumInsured;
    }

    public Date getTransactionTo()
    {
        return transactionTo;
    }

    public void setTransactionTo(Date transactionTo)
    {
        this.transactionTo = transactionTo;
    }

    public String getTransactionType()
    {
        return transactionType;
    }

    public void setTransactionType(String transactionType)
    {
        this.transactionType = transactionType;
    }

    public String getTransactionPolicyNumber()
    {
        return transactionPolicyNumber;
    }

    public void setTransactionPolicyNumber(String transactionPolicyNumber)
    {
        this.transactionPolicyNumber = transactionPolicyNumber;
    }

    public String getTransactionClass()
    {
        return transactionClass;
    }

    public void setTransactionClass(String transactionClass)
    {
        this.transactionClass = transactionClass;
    }
    
    public String getTransactionEngineNo()
    {
        return transactionEngineNo;
    }

    public void setTransactionEngineNo(String transactionEngineNo)
    {
        this.transactionEngineNo = transactionEngineNo;
    }
    
    public String getTransactionChassisNo()
    {
        return transactionChassisNo;
    }

    public void setTransactionChassisNo(String transactionChassisNo)
    {
        this.transactionChassisNo = transactionChassisNo;
    }
    
    /*public String getTransactionCertificateNo()
    {
        return transactionCertificateNo;
    }

    public void setTransactionCertificateNo(String transactionCertificateNo)
    {
        this.transactionCertificateNo = transactionCertificateNo;
    }*/
    
  
  
    
    public Integer getCurrentPage() {
        return currentPage;
    }
    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }
    /**
     * @return Returns the issueDate.
     */
    public String getIssueDate() {
        return issueDate;
    }
    /**
     * @param issueDate The issueDate to set.
     */
    public void setIssueDate(String issueDate) {
        this.issueDate = issueDate;
    }
    /**
     * @return Returns the comments.
     */
    public String getComments() {
        return comments;
    }
    /**
     * @param comments The comments to set.
     */
    public void setComments(String comments) {
        this.comments = comments;
    }
    /**
     * @return Returns the effectiveDate.
     */
    public String getEffectiveDate() {
        return effectiveDate;
    }
    /**
     * @param effectiveDate The effectiveDate to set.
     */
    public void setEffectiveDate(String effectiveDate) {
        this.effectiveDate = effectiveDate;
    }
    /**
     * @return Returns the expiryDate.
     */
    public String getExpiryDate() {
        return expiryDate;
    }
    /**
     * @param expiryDate The expiryDate to set.
     */
    public void setExpiryDate(String expiryDate) {
        this.expiryDate = expiryDate;
    }
    /**
     * @return Returns the policyId.
     */
    public String getPolicyId() {
        return policyId;
    }
    /**
     * @param policyId The policyId to set.
     */
    public void setPolicyId(String policyId) {
        this.policyId = policyId;
    }
    /**
     * @return Returns the quotationNo.
     */
    public String getQuotationNo() {
        return quotationNo;
    }
    /**
     * @param quotationNo The quotationNo to set.
     */
    public void setQuotationNo(String quotationNo) {
        this.quotationNo = quotationNo;
    }
    
    
                /**
                * @return Returns the quotePolicy.
                */
                public String getQuotePolicy() {
                                return quotePolicy;
                }
                /**
                * @param quotePolicy The quotePolicy to set.
                */
                public void setQuotePolicy(String quotePolicy) {
                                this.quotePolicy = quotePolicy;
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
                
                
                /**
                * @return Returns the suspendTransactionInd.
                */
                public String getSuspendTransactionInd() {
                                return suspendTransactionInd;
                }
                /**
                * @param suspendTransactionInd The suspendTransactionInd to set.
                */
                public void setSuspendTransactionInd(String suspendTransactionInd) {
                                this.suspendTransactionInd = suspendTransactionInd;
                }
                /**
                * @return Returns the transactionTypeCode.
                */
                public String getTransactionTypeCode() {
                                return transactionTypeCode;
                }
                /**
                * @param transactionTypeCode The transactionTypeCode to set.
                */
                public void setTransactionTypeCode(String transactionTypeCode) {
                                this.transactionTypeCode = transactionTypeCode;
                }
                
                /**
                * @return Returns the completeName.
                */
                public String getCompleteName() {
                                return completeName;
                }
                /**
                * @param completeName The completeName to set.
                */
                public void setCompleteName(String completeName) {
                                this.completeName = completeName;
                }
                
                /**
                * @return Returns the exactSearch.
                */
                public boolean isExactSearch() {
                                return exactSearch;
                }
                /**
                * @param exactSearch The exactSearch to set.
                */
                public void setExactSearch(boolean exactSearch) {
                                this.exactSearch = exactSearch;
                }
                /**
                * @return Returns the policyEntered.
                */
                public String getPolicyEntered() {
                                return policyEntered;
                }
                /**
                * @param policyEntered The policyEntered to set.
                */
                public void setPolicyEntered(String policyEntered) {
                                this.policyEntered = policyEntered;
                }
                /**
                * @return Returns the quoteEntered.
                */
                public String getQuoteEntered() {
                                return quoteEntered;
                }
                /**
                * @param quoteEntered The quoteEntered to set.
                */
                public void setQuoteEntered(String quoteEntered) {
                                this.quoteEntered = quoteEntered;
                }
                
                /**
                * @return Returns the transactionNumberPolicy.
                */
                public String getTransactionNumberPolicy() {
                                return transactionNumberPolicy;
                }
                /**
                * @param transactionNumberPolicy The transactionNumberPolicy to set.
                */
                public void setTransactionNumberPolicy(String transactionNumberPolicy) {
                                this.transactionNumberPolicy = transactionNumberPolicy;
                }
                
                
                /**
                * @return Returns the policyIssueHour.
                */
                public String getPolicyIssueHour() {
                                return policyIssueHour;
                }
                /**
                * @param policyIssueHour The policyIssueHour to set.
                */
                public void setPolicyIssueHour(String policyIssueHour) {
                                this.policyIssueHour = policyIssueHour;
                }
                /**
                * @return Returns the policyTariffCode.
                */
                public String getPolicyTariffCode() {
                                return policyTariffCode;
                }
                /**
                * @param policyTariffCode The policyTariffCode to set.
                */
                public void setPolicyTariffCode(String policyTariffCode) {
                                this.policyTariffCode = policyTariffCode;
                }
                
                /**
                * @return Returns the polRenewalCounter.
                */
                public String getPolRenewalCounter() {
                                return polRenewalCounter;
                }
                /**
                * @param polRenewalCounter The polRenewalCounter to set.
                */
                public void setPolRenewalCounter(String polRenewalCounter) {
                                this.polRenewalCounter = polRenewalCounter;
                }
                
                /**
                * @return Returns the isEditMode.
                */
                public String getIsEditMode() {
                                return isEditMode;
                }
                /**
                * @param isEditMode The isEditMode to set.
                */
                public void setIsEditMode(String isEditMode) {
                                this.isEditMode = isEditMode;
                }
                
                
                /**
                * @return Returns the policyApprover.
                */
                public String getPolicyApprover() {
                                return policyApprover;
                }
                /**
                * @param policyApprover The policyApprover to set.
                */
                public void setPolicyApprover(String policyApprover) {
                                this.policyApprover = policyApprover;
                }
                /**
                * @return Returns the tempTransactionEndtNo.
                */
                public String getTempTransactionEndtNo() {
                                return tempTransactionEndtNo;
                }
                /**
                * @param tempTransactionEndtNo The tempTransactionEndtNo to set.
                */
                public void setTempTransactionEndtNo(String tempTransactionEndtNo) {
                                this.tempTransactionEndtNo = tempTransactionEndtNo;
                }
                /**
                * @return Returns the brokerName.
                */
                public String getBrokerName() {
                                return brokerName;
                }
                /**
                * @param brokerName The brokerName to set.
                */
                public void setBrokerName(String brokerName) {
                                this.brokerName = brokerName;
                }
                /**
                * @return Returns the companyName.
                */
                public String getCompanyName() {
                                return companyName;
                }
                /**
                * @param companyName The companyName to set.
                */
                public void setCompanyName(String companyName) {
                                this.companyName = companyName;
                }
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
                /**
                * @return Returns the schemeName.
                */
                public String getSchemeName() {
                                return schemeName;
                }
                /**
                * @param schemeName The schemeName to set.
                */
                public void setSchemeName(String schemeName) {
                                this.schemeName = schemeName;
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
                * @return Returns the transactionRegNo.
                */
                public String getTransactionRegNo() {
                                return transactionRegNo;
                }
                /**
                * @param transactionRegNo The transactionRegNo to set.
                */
                public void setTransactionRegNo(String transactionRegNo) {
                                this.transactionRegNo = transactionRegNo;
                }
                
                /**
                * @return Returns the borderInd.
                */
                public Integer getBorderInd() {
                                return borderInd;
                }
                /**
                * @param borderInd The borderInd to set.
                */
                public void setBorderInd(Integer borderInd) {
                                this.borderInd = borderInd;
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
            	 * @param forHistoryView the forHistoryView to set
            	 */
				public String getForHistoryView() {
					return forHistoryView;
				}
				public void setForHistoryView(String forHistoryView) {
					this.forHistoryView = forHistoryView;
				}  
                
            	
}
