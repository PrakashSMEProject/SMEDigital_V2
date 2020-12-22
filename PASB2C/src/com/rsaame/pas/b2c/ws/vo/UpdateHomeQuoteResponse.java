package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.rsaame.pas.b2c.wsException.ValidationError;
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class UpdateHomeQuoteResponse {
	@JsonProperty("QuotationNo")
	private Long quotationNo;
	@JsonProperty("PolicyEffectiveDate")
	private Date policyEffectiveDate;
	@JsonProperty("PolicyExpiryDate")
	private Date policyExpiryDate;
	@JsonProperty("InsuredId")
	private Long insuredId;
	@JsonProperty("PolicyId")
	private Long policyId;
	@JsonProperty("EndtId")
	private Long endtId;
	@JsonProperty("EndtNo")
	private Integer endtNo;
	@JsonProperty("QuoteValidTill")
	private Date quoteValidTill;
	@JsonProperty("QuoteStatus")
	private Integer quoteStatus;
	@JsonProperty("FinalPremium")
	private BigDecimal finalPremium;
	@JsonProperty("PremiumPayable")
	private BigDecimal premiumPayable;
	@JsonProperty("CurrencyType")
	private String currencyType;
	@JsonProperty("ProductDesc")
	private String productDesc;
	@JsonProperty("FeesAndTaxes")
	private FeesAndTaxes feesAndTaxes;
	@JsonProperty("PartnerDetails")
	private PartnerDetails partnerDetails;
	@JsonProperty("CustomerDetails")
	private CustomerDetails customerDetails;
	@JsonProperty("TransactionDetails")
	private TransactionDetails transactionDetails;
	@JsonProperty("MandatoryCovers")
	private List<MandatoryCovers> mandatoryCovers;
	@JsonProperty("ListedItems")
	private List<ListedItems> listedItems;
	@JsonProperty("OptionalCovers")
	private List<OptionalCovers> optionalCovers;
	@JsonProperty("BuildingDetails")
	private BuildingDetails buildingDetails;
	@JsonProperty("UnderWritingQuestions")
	private UnderWritingQuestions underWritingQuestions;
	@JsonProperty("Extras")
	private Extras extras;
	@JsonProperty("Errors")
	private List<ValidationError> errors;
	@JsonProperty("Document")
	private byte[] document;
	public Long getQuotationNo() {
		return quotationNo;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getPolicyExpiryDate() {
		return policyExpiryDate;
	}
	public Long getInsuredId() {
		return insuredId;
	}
	public Long getPolicyId() {
		return policyId;
	}
	public Long getEndtId() {
		return endtId;
	}
	public Integer getEndtNo() {
		return endtNo;
	}
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getQuoteValidTill() {
		return quoteValidTill;
	}
	public Integer getQuoteStatus() {
		return quoteStatus;
	}
	public BigDecimal getFinalPremium() {
		return finalPremium;
	}
	public BigDecimal getPremiumPayable() {
		return premiumPayable;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public FeesAndTaxes getFeesAndTaxes() {
		return feesAndTaxes;
	}
	public PartnerDetails getPartnerDetails() {
		return partnerDetails;
	}
	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}
	public TransactionDetails getTransactionDetails() {
		return transactionDetails;
	}
	public List<MandatoryCovers> getMandatoryCovers() {
		return mandatoryCovers;
	}
	public List<OptionalCovers> getOptionalCovers() {
		return optionalCovers;
	}
	public BuildingDetails getBuildingDetails() {
		return buildingDetails;
	}

	public UnderWritingQuestions getUnderWritingQuestions() {
		return underWritingQuestions;
	}
	public Extras getExtras() {
		return extras;
	}
	public List<ValidationError> getErrors() {
		return errors;
	}
	public byte[] getDocument() {
		return document;
	}
	public void setQuotationNo(Long quotationNo) {
		this.quotationNo = quotationNo;
	}
	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}
	public void setPolicyExpiryDate(Date policyExpiryDate) {
		this.policyExpiryDate = policyExpiryDate;
	}
	public void setInsuredId(Long insuredId) {
		this.insuredId = insuredId;
	}
	public void setPolicyId(Long policyId) {
		this.policyId = policyId;
	}
	public void setEndtId(Long endtId) {
		this.endtId = endtId;
	}
	public void setEndtNo(Integer endtNo) {
		this.endtNo = endtNo;
	}
	public void setQuoteValidTill(Date quoteValidTill) {
		this.quoteValidTill = quoteValidTill;
	}
	public void setQuoteStatus(Integer quoteStatus) {
		this.quoteStatus = quoteStatus;
	}
	public void setFinalPremium(BigDecimal finalPremium) {
		this.finalPremium = finalPremium;
	}
	public void setPremiumPayable(BigDecimal premiumPayable) {
		this.premiumPayable = premiumPayable;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public void setFeesAndTaxes(FeesAndTaxes feesAndTaxes) {
		this.feesAndTaxes = feesAndTaxes;
	}
	public void setPartnerDetails(PartnerDetails partnerDetails) {
		this.partnerDetails = partnerDetails;
	}
	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}
	public void setTransactionDetails(TransactionDetails transactionDetails) {
		this.transactionDetails = transactionDetails;
	}
	public void setMandatoryCovers(List<MandatoryCovers> mandatoryCovers) {
		this.mandatoryCovers = mandatoryCovers;
	}
	public void setOptionalCovers(List<OptionalCovers> optionalCovers) {
		this.optionalCovers = optionalCovers;
	}
	public void setBuildingDetails(BuildingDetails buildingDetails) {
		this.buildingDetails = buildingDetails;
	}

	public void setUnderWritingQuestions(UnderWritingQuestions underWritingQuestions) {
		this.underWritingQuestions = underWritingQuestions;
	}
	public void setExtras(Extras extras) {
		this.extras = extras;
	}
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	public void setDocument(byte[] document) {
		this.document = document;
	}

	public List<ListedItems> getListedItems() {
		return listedItems;
	}

	public void setListedItems(List<ListedItems> listedItems) {
		this.listedItems = listedItems;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingDetails == null) ? 0 : buildingDetails.hashCode());
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((customerDetails == null) ? 0 : customerDetails.hashCode());
		result = prime * result + Arrays.hashCode(document);
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((endtNo == null) ? 0 : endtNo.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((extras == null) ? 0 : extras.hashCode());
		result = prime * result + ((feesAndTaxes == null) ? 0 : feesAndTaxes.hashCode());
		result = prime * result + ((finalPremium == null) ? 0 : finalPremium.hashCode());
		result = prime * result + ((insuredId == null) ? 0 : insuredId.hashCode());
		result = prime * result + ((listedItems == null) ? 0 : listedItems.hashCode());
		result = prime * result + ((mandatoryCovers == null) ? 0 : mandatoryCovers.hashCode());
		result = prime * result + ((optionalCovers == null) ? 0 : optionalCovers.hashCode());
		result = prime * result + ((partnerDetails == null) ? 0 : partnerDetails.hashCode());
		result = prime * result + ((policyEffectiveDate == null) ? 0 : policyEffectiveDate.hashCode());
		result = prime * result + ((policyExpiryDate == null) ? 0 : policyExpiryDate.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((premiumPayable == null) ? 0 : premiumPayable.hashCode());
		result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
		result = prime * result + ((quotationNo == null) ? 0 : quotationNo.hashCode());
		result = prime * result + ((quoteStatus == null) ? 0 : quoteStatus.hashCode());
		result = prime * result + ((quoteValidTill == null) ? 0 : quoteValidTill.hashCode());
		result = prime * result + ((transactionDetails == null) ? 0 : transactionDetails.hashCode());
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
		UpdateHomeQuoteResponse other = (UpdateHomeQuoteResponse) obj;
		if (buildingDetails == null) {
			if (other.buildingDetails != null)
				return false;
		} else if (!buildingDetails.equals(other.buildingDetails))
			return false;
		if (currencyType == null) {
			if (other.currencyType != null)
				return false;
		} else if (!currencyType.equals(other.currencyType))
			return false;
		if (customerDetails == null) {
			if (other.customerDetails != null)
				return false;
		} else if (!customerDetails.equals(other.customerDetails))
			return false;
		if (!Arrays.equals(document, other.document))
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
		if (errors == null) {
			if (other.errors != null)
				return false;
		} else if (!errors.equals(other.errors))
			return false;
		if (extras == null) {
			if (other.extras != null)
				return false;
		} else if (!extras.equals(other.extras))
			return false;
		if (feesAndTaxes == null) {
			if (other.feesAndTaxes != null)
				return false;
		} else if (!feesAndTaxes.equals(other.feesAndTaxes))
			return false;
		if (finalPremium == null) {
			if (other.finalPremium != null)
				return false;
		} else if (!finalPremium.equals(other.finalPremium))
			return false;
		if (insuredId == null) {
			if (other.insuredId != null)
				return false;
		} else if (!insuredId.equals(other.insuredId))
			return false;
		if (listedItems == null) {
			if (other.listedItems != null)
				return false;
		} else if (!listedItems.equals(other.listedItems))
			return false;
		if (mandatoryCovers == null) {
			if (other.mandatoryCovers != null)
				return false;
		} else if (!mandatoryCovers.equals(other.mandatoryCovers))
			return false;
		if (optionalCovers == null) {
			if (other.optionalCovers != null)
				return false;
		} else if (!optionalCovers.equals(other.optionalCovers))
			return false;
		if (partnerDetails == null) {
			if (other.partnerDetails != null)
				return false;
		} else if (!partnerDetails.equals(other.partnerDetails))
			return false;
		if (policyEffectiveDate == null) {
			if (other.policyEffectiveDate != null)
				return false;
		} else if (!policyEffectiveDate.equals(other.policyEffectiveDate))
			return false;
		if (policyExpiryDate == null) {
			if (other.policyExpiryDate != null)
				return false;
		} else if (!policyExpiryDate.equals(other.policyExpiryDate))
			return false;
		if (policyId == null) {
			if (other.policyId != null)
				return false;
		} else if (!policyId.equals(other.policyId))
			return false;
		if (premiumPayable == null) {
			if (other.premiumPayable != null)
				return false;
		} else if (!premiumPayable.equals(other.premiumPayable))
			return false;
		if (productDesc == null) {
			if (other.productDesc != null)
				return false;
		} else if (!productDesc.equals(other.productDesc))
			return false;
		if (quotationNo == null) {
			if (other.quotationNo != null)
				return false;
		} else if (!quotationNo.equals(other.quotationNo))
			return false;
		if (quoteStatus == null) {
			if (other.quoteStatus != null)
				return false;
		} else if (!quoteStatus.equals(other.quoteStatus))
			return false;
		if (quoteValidTill == null) {
			if (other.quoteValidTill != null)
				return false;
		} else if (!quoteValidTill.equals(other.quoteValidTill))
			return false;
		if (transactionDetails == null) {
			if (other.transactionDetails != null)
				return false;
		} else if (!transactionDetails.equals(other.transactionDetails))
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
		return "UpdateHomeQuoteResponse [quotationNo=" + quotationNo + ", policyEffectiveDate=" + policyEffectiveDate
				+ ", policyExpiryDate=" + policyExpiryDate + ", insuredId=" + insuredId + ", policyId=" + policyId
				+ ", endtId=" + endtId + ", endtNo=" + endtNo + ", quoteValidTill=" + quoteValidTill + ", quoteStatus="
				+ quoteStatus + ", finalPremium=" + finalPremium + ", premiumPayable=" + premiumPayable
				+ ", currencyType=" + currencyType + ", productDesc=" + productDesc + ", feesAndTaxes=" + feesAndTaxes
				+ ", partnerDetails=" + partnerDetails + ", customerDetails=" + customerDetails
				+ ", transactionDetails=" + transactionDetails + ", mandatoryCovers=" + mandatoryCovers
				+ ", listedItems=" + listedItems + ", optionalCovers=" + optionalCovers + ", buildingDetails="
				+ buildingDetails + ", underWritingQuestions=" + underWritingQuestions + ", extras=" + extras
				+ ", errors=" + errors + ", document=" + Arrays.toString(document) + "]";
	}
	
}