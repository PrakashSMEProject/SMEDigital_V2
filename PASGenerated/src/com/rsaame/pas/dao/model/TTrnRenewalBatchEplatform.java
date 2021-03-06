package com.rsaame.pas.dao.model;

// Generated Aug 6, 2012 2:02:45 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TTrnRenewalBatchEplatform generated by hbm2java
 */
public class TTrnRenewalBatchEplatform implements java.io.Serializable {

	private long polLinkingId;
	private Long polPolicyNo;
	private Date effectiveDate;
	private Date expiryDate;
	private Short policyYear;
	private Integer requesterId;
	private Date requestDate;
	private Short lastExecutedStep;
	private Date lastProcessedDate;
	private Long renQuotationNo;
	private String renQuotationStatus;
	private String remarks;
	private long policyId;
	private String application;


	public TTrnRenewalBatchEplatform() {
	}

	public TTrnRenewalBatchEplatform(long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}
	

	/**
	 * @param polLinkingId
	 * @param polPolicyNo
	 * @param effectiveDate
	 * @param expiryDate
	 * @param policyYear
	 * @param requesterId
	 * @param requestDate
	 * @param lastExecutedStep
	 * @param lastProcessedDate
	 * @param renQuotationNo
	 * @param renQuotationStatus
	 * @param remarks
	 * @param policyId
	 */
	public TTrnRenewalBatchEplatform(long polLinkingId, Long polPolicyNo,
			Date effectiveDate, Date expiryDate, Short policyYear,
			Integer requesterId, Date requestDate, Short lastExecutedStep,
			Date lastProcessedDate, Long renQuotationNo,
			String renQuotationStatus, String remarks, long policyId) {
		this.polLinkingId = polLinkingId;
		this.polPolicyNo = polPolicyNo;
		this.effectiveDate = effectiveDate;
		this.expiryDate = expiryDate;
		this.policyYear = policyYear;
		this.requesterId = requesterId;
		this.requestDate = requestDate;
		this.lastExecutedStep = lastExecutedStep;
		this.lastProcessedDate = lastProcessedDate;
		this.renQuotationNo = renQuotationNo;
		this.renQuotationStatus = renQuotationStatus;
		this.remarks = remarks;
		this.policyId = policyId;
	}

	public long getPolLinkingId() {
		return this.polLinkingId;
	}

	public void setPolLinkingId(long polLinkingId) {
		this.polLinkingId = polLinkingId;
	}

	public Long getPolPolicyNo() {
		return this.polPolicyNo;
	}

	public void setPolPolicyNo(Long polPolicyNo) {
		this.polPolicyNo = polPolicyNo;
	}

	public Date getEffectiveDate() {
		return this.effectiveDate;
	}

	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	public Date getExpiryDate() {
		return this.expiryDate;
	}

	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}

	public Short getPolicyYear() {
		return this.policyYear;
	}

	public void setPolicyYear(Short policyYear) {
		this.policyYear = policyYear;
	}

	public Integer getRequesterId() {
		return this.requesterId;
	}

	public void setRequesterId(Integer requesterId) {
		this.requesterId = requesterId;
	}

	public Date getRequestDate() {
		return this.requestDate;
	}

	public void setRequestDate(Date requestDate) {
		this.requestDate = requestDate;
	}

	public Short getLastExecutedStep() {
		return this.lastExecutedStep;
	}

	public void setLastExecutedStep(Short lastExecutedStep) {
		this.lastExecutedStep = lastExecutedStep;
	}

	public Date getLastProcessedDate() {
		return this.lastProcessedDate;
	}

	public void setLastProcessedDate(Date lastProcessedDate) {
		this.lastProcessedDate = lastProcessedDate;
	}

	public Long getRenQuotationNo() {
		return this.renQuotationNo;
	}

	public void setRenQuotationNo(Long renQuotationNo) {
		this.renQuotationNo = renQuotationNo;
	}

	public String getRenQuotationStatus() {
		return this.renQuotationStatus;
	}

	public void setRenQuotationStatus(String renQuotationStatus) {
		this.renQuotationStatus = renQuotationStatus;
	}

	public String getRemarks() {
		return this.remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	/**
	 * @return the policyId
	 */
	public long getPolicyId() {
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId(long policyId) {
		this.policyId = policyId;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "TTrnRenewalBatchEplatform [polLinkingId=" + polLinkingId
				+ ", polPolicyNo=" + polPolicyNo + ", effectiveDate="
				+ effectiveDate + ", expiryDate=" + expiryDate
				+ ", policyYear=" + policyYear + ", requesterId=" + requesterId
				+ ", requestDate=" + requestDate + ", lastExecutedStep="
				+ lastExecutedStep + ", lastProcessedDate=" + lastProcessedDate
				+ ", renQuotationNo=" + renQuotationNo
				+ ", renQuotationStatus=" + renQuotationStatus + ", remarks="
				+ remarks + ", policyId=" + policyId + "]";
	}	
	
	/**
     * @return the application
     */

    public String getApplication(){
            return application;
    }

    /**
     * @param application the application to set
     */
    public void setApplication( String application ){
    	this.application = application;
    }

}
