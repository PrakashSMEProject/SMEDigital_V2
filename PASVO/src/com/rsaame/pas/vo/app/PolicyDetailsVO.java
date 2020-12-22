package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

public class PolicyDetailsVO extends BaseVO {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String policyId;
	private String policyLinkingId;
	private String endtId;
	private String policyClassCode;
	private Long policyNo;
	private String sectionId;
	private String polDocumentId;
	private String startDate;
	private String polBrCode;
	private String polAgentId;
	private String polExpiryDate;
	private String polConcPolicyNo;
	
	public String getPolConcPolicyNo() {
		return polConcPolicyNo;
	}

	public void setPolConcPolicyNo(String polConcPolicyNo) {
		this.polConcPolicyNo = polConcPolicyNo;
	}

	public String getPolExpiryDate(){
		return polExpiryDate;
	}

	public void setPolExpiryDate( String polExpiryDate ){
		this.polExpiryDate = polExpiryDate;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		return null;
	}

	public String getPolicyId() {
		return policyId;
	}

	public void setPolicyId(String policyId) {
		this.policyId = policyId;
	}

	public String getPolicyLinkingId() {
		return policyLinkingId;
	}

	public void setPolicyLinkingId(String policyLinkingId) {
		this.policyLinkingId = policyLinkingId;
	}

	public String getEndtId() {
		return endtId;
	}

	public void setEndtId(String endtId) {
		this.endtId = endtId;
	}

	public String getPolicyClassCode() {
		return policyClassCode;
	}

	public void setPolicyClassCode(String policyClassCode) {
		this.policyClassCode = policyClassCode;
	}

	public Long getPolicyNo() {
		return policyNo;
	}

	public void setPolicyNo(Long policyNo) {
		this.policyNo = policyNo;
	}

	public String getSectionId() {
		return sectionId;
	}

	public void setSectionId(String sectionId) {
		this.sectionId = sectionId;
	}

	public String getPolDocumentId() {
		return polDocumentId;
	}

	public void setPolDocumentId(String polDocumentId) {
		this.polDocumentId = polDocumentId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the polBrCode
	 */
	public String getPolBrCode(){
		return polBrCode;
	}

	/**
	 * @param polBrCode the polBrCode to set
	 */
	public void setPolBrCode( String polBrCode ){
		this.polBrCode = polBrCode;
	}

	/**
	 * @return the polAgentId
	 */
	public String getPolAgentId(){
		return polAgentId;
	}

	/**
	 * @param polAgentId the polAgentId to set
	 */
	public void setPolAgentId( String polAgentId ){
		this.polAgentId = polAgentId;
	}

	@Override
	public String toString() {
	    return "PolicyDetailsVO [policyId=" + policyId
		    + ", policyLinkingId=" + policyLinkingId + ", endtId="
		    + endtId + ", policyClassCode=" + policyClassCode
		    + ", policyNo=" + policyNo + ", sectionId=" + sectionId
		    + ", polDocumentId=" + polDocumentId + ", startDate="
		    + startDate + ", polBrCode=" + polBrCode + ", polAgentId="
		    + polAgentId + ", polExpiryDate=" + polExpiryDate + "]";
	}

}