/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author M1037404
 *
 */
public class RetrieveQuoteByPolicyRequest {
	
	@JsonProperty("RetrieveType")
	private Integer retrieveType;
	@JsonProperty("PolicyNumber")
	private Integer transactionNumber;
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	@JsonProperty("EmailId")
	private String emailId;
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	@JsonProperty("Dob")
	private Date dob;
	@JsonProperty("PartnerTrnReferenceNumber")
	private String partnerTrnReferenceNumber;
	@JsonProperty("ProposalForm")
	private Boolean proposalForm;
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Starts
	private String pmmId;
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Ends

	
	public Integer getRetrieveType() {
		return retrieveType;
	}
	public Integer getTransactionNumber() {
		return transactionNumber;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	public String getEmailId() {
		return emailId;
	}
	public void setEmailId(String emailId) {
		this.emailId = emailId;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	public Date getDob() {
		return dob;
	}
	public String getPartnerTrnReferenceNumber() {
		return partnerTrnReferenceNumber;
	}
	public Boolean getProposalForm() {
		return proposalForm;
	}
	public void setRetrieveType(Integer retrieveType) {
		this.retrieveType = retrieveType;
	}
	public void setTransactionNumber(Integer transactionNumber) {
		this.transactionNumber = transactionNumber;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public void setPartnerTrnReferenceNumber(String partnerTrnReferenceNumber) {
		this.partnerTrnReferenceNumber = partnerTrnReferenceNumber;
	}
	public void setProposalForm(Boolean proposalForm) {
		this.proposalForm = proposalForm;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	public String getPmmId() {
		return pmmId;
	}
	public void setPmmId(String pmmId) {
		this.pmmId = pmmId;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Ends
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((dob == null) ? 0 : dob.hashCode());
		result = prime * result + ((partnerTrnReferenceNumber == null) ? 0 : partnerTrnReferenceNumber.hashCode());
		result = prime * result + ((proposalForm == null) ? 0 : proposalForm.hashCode());
		result = prime * result + ((retrieveType == null) ? 0 : retrieveType.hashCode());
		//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
		result = prime * result + ((emailId == null) ? 0 : emailId.hashCode());
		//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
		result = prime * result + ((transactionNumber == null) ? 0 : transactionNumber.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RetrieveQuoteByPolicyRequest other = (RetrieveQuoteByPolicyRequest) obj;
		if (dob == null) {
			if (other.dob != null)
				return false;
		} else if (!dob.equals(other.dob))
			return false;
		if (partnerTrnReferenceNumber == null) {
			if (other.partnerTrnReferenceNumber != null)
				return false;
		} else if (!partnerTrnReferenceNumber.equals(other.partnerTrnReferenceNumber))
			return false;
		if (proposalForm == null) {
			if (other.proposalForm != null)
				return false;
		} else if (!proposalForm.equals(other.proposalForm))
			return false;
		//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
		if (emailId == null) {
			if (other.emailId != null)
				return false;
		} else if (!emailId.equals(other.emailId))
			return false;
		//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
		if (retrieveType == null) {
			if (other.retrieveType != null)
				return false;
		} else if (!retrieveType.equals(other.retrieveType))
			return false;
		if (transactionNumber == null) {
			if (other.transactionNumber != null)
				return false;
		} else if (!transactionNumber.equals(other.transactionNumber))
			return false;
		return true;
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
	@Override
	public String toString() {
		return "RetrieveQuoteByPolicyRequest [retrieveType=" + retrieveType + ", transactionNumber=" + transactionNumber
				+ ", emailId=" + emailId + ", dob=" + dob + ", partnerTrnReferenceNumber=" + partnerTrnReferenceNumber + ", proposalForm="
				+ proposalForm + "]";
	}
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
	
	
	
	
	
}
