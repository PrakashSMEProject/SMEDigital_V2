package com.rsaame.pas.b2c.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class UpdateTravelQuoteRequest {
	@JsonProperty("QuotationNo")
	private Integer quotationNo;
	@JsonProperty("EndtId")
	private Long endtId;
	@JsonProperty("PolicyId")
	private Long policyId;
	@JsonProperty("EndtNo")
	private Long endtNo;
	@JsonProperty("QuoteStatus")
	private Integer quoteStatus;
	@JsonProperty("CustomerDetails")
	private CustomerDetails customerDetails;
	@JsonProperty("TransactionDetails")
	private TransactionDetails transactionDetails;
	@JsonProperty("PartnerDetails")
	private PartnerDetails partnerDetails;
	@JsonProperty("UnderWritingQuestions")
	private UnderWritingQuestions underWritingQuestions;
	@JsonProperty("Travellers")
	private List<Travellers> travellers;
	@JsonProperty("Products")
	private List<Products> products;
	@JsonProperty("InclCommunication")
	private Boolean inclCommunication;
	@JsonProperty("ProposalForm")
	private Boolean proposalForm;
	@JsonProperty("QuoteConfirmationEmail")
	private Boolean quoteConfirmationEmail;

	public Integer getQuotationNo() {
		return quotationNo;
	}

	public void setQuotationNo(Integer quotationNo) {
		this.quotationNo = quotationNo;
	}

	public Long getEndtId() {
		return endtId;
	}

	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}

	public Long getPolicyId() {
		return policyId;
	}

	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}

	public Long getEndtNo() {
		return endtNo;
	}

	public void setEndtNo(Long endtNo) {
		this.endtNo = endtNo;
	}

	public Integer getQuoteStatus() {
		return quoteStatus;
	}

	public void setQuoteStatus(Integer quoteStatus) {
		this.quoteStatus = quoteStatus;
	}

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}

	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}

	public PartnerDetails getPartnerDetails() {
		return partnerDetails;
	}

	public void setPartnerDetails(PartnerDetails partnerDetails) {
		this.partnerDetails = partnerDetails;
	}

	public UnderWritingQuestions getUnderWritingQuestions() {
		return underWritingQuestions;
	}

	public void setUnderWritingQuestions(UnderWritingQuestions underWritingQuestions) {
		this.underWritingQuestions = underWritingQuestions;
	}

	public List<Travellers> getTravellers() {
		return travellers;
	}

	public void setTravellers(List<Travellers> travellers) {
		this.travellers = travellers;
	}

	public List<Products> getProducts() {
		return products;
	}

	public void setProducts(List<Products> products) {
		this.products = products;
	}

	public Boolean getInclCommunication() {
		return inclCommunication;
	}

	public void setInclCommunication(Boolean inclCommunication) {
		this.inclCommunication = inclCommunication;
	}

	public Boolean getProposalForm() {
		return proposalForm;
	}

	public void setProposalForm(Boolean proposalForm) {
		this.proposalForm = proposalForm;
	}

	public Boolean getQuoteConfirmationEmail() {
		return quoteConfirmationEmail;
	}

	public void setQuoteConfirmationEmail(Boolean quoteConfirmationEmail) {
		this.quoteConfirmationEmail = quoteConfirmationEmail;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((customerDetails == null) ? 0 : customerDetails.hashCode());
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((endtNo == null) ? 0 : endtNo.hashCode());
		result = prime * result + ((inclCommunication == null) ? 0 : inclCommunication.hashCode());
		result = prime * result + ((partnerDetails == null) ? 0 : partnerDetails.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((products == null) ? 0 : products.hashCode());
		result = prime * result + ((proposalForm == null) ? 0 : proposalForm.hashCode());
		result = prime * result + ((quotationNo == null) ? 0 : quotationNo.hashCode());
		result = prime * result + ((quoteConfirmationEmail == null) ? 0 : quoteConfirmationEmail.hashCode());
		result = prime * result + ((quoteStatus == null) ? 0 : quoteStatus.hashCode());
		result = prime * result + ((transactionDetails == null) ? 0 : transactionDetails.hashCode());
		result = prime * result + ((travellers == null) ? 0 : travellers.hashCode());
		result = prime * result + ((underWritingQuestions == null) ? 0 : underWritingQuestions.hashCode());
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
		UpdateTravelQuoteRequest other = (UpdateTravelQuoteRequest) obj;
		if (customerDetails == null) {
			if (other.customerDetails != null)
				return false;
		} else if (!customerDetails.equals(other.customerDetails))
			return false;
		if (endtId == null) {
			if (other.endtId != null)
				return false;
		} else if (!endtId.equals(other.endtId))
			return false;
		if (endtNo == null) {
			if (other.endtNo != null)
				return false;
		} else if (!endtNo.equals(other.endtNo))
			return false;
		if (inclCommunication == null) {
			if (other.inclCommunication != null)
				return false;
		} else if (!inclCommunication.equals(other.inclCommunication))
			return false;
		if (partnerDetails == null) {
			if (other.partnerDetails != null)
				return false;
		} else if (!partnerDetails.equals(other.partnerDetails))
			return false;
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
			return false;
		if (products == null) {
			if (other.products != null)
				return false;
		} else if (!products.equals(other.products))
			return false;
		if (proposalForm == null) {
			if (other.proposalForm != null)
				return false;
		} else if (!proposalForm.equals(other.proposalForm))
			return false;
		if (quotationNo == null) {
			if (other.quotationNo != null)
				return false;
		} else if (!quotationNo.equals(other.quotationNo))
			return false;
		if (quoteConfirmationEmail == null) {
			if (other.quoteConfirmationEmail != null)
				return false;
		} else if (!quoteConfirmationEmail.equals(other.quoteConfirmationEmail))
			return false;
		if (quoteStatus == null) {
			if (other.quoteStatus != null)
				return false;
		} else if (!quoteStatus.equals(other.quoteStatus))
			return false;
		if (transactionDetails == null) {
			if (other.transactionDetails != null)
				return false;
		} else if (!transactionDetails.equals(other.transactionDetails))
			return false;
		if (travellers == null) {
			if (other.travellers != null)
				return false;
		} else if (!travellers.equals(other.travellers))
			return false;
		if (underWritingQuestions == null) {
			if (other.underWritingQuestions != null)
				return false;
		} else if (!underWritingQuestions.equals(other.underWritingQuestions))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "UpdateTravelQuoteRequest [quotationNo=" + quotationNo + ", endtId=" + endtId + ", policyId=" + policyId
				+ ", endtNo=" + endtNo + ", quoteStatus=" + quoteStatus + ", customerDetails=" + customerDetails
				+ ", transactionDetails=" + transactionDetails + ", partnerDetails=" + partnerDetails
				+ ", underWritingQuestions=" + underWritingQuestions + ", travellers=" + travellers + ", products="
				+ products + ", inclCommunication=" + inclCommunication + ", proposalForm=" + proposalForm
				+ ", quoteConfirmationEmail=" + quoteConfirmationEmail + "]";
	}

}
