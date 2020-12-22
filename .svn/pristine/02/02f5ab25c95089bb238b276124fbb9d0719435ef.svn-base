/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

import com.rsaame.pas.b2c.wsException.ValidationError;

/**
 * @author M1044786
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class CreateHomeQuoteResponse {
	@JsonProperty("QuotationNo")
	private Integer quotationNo;
	@JsonProperty("EndtId")
	private Long endtId;
	@JsonProperty("PolicyId")
	private Long policyId;
	@JsonProperty("EndtNo")
	private Long endtNo;
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
	@JsonProperty("CustomerDetails")
	private CustomerDetails customerDetails;
	@JsonProperty("TransactionDetails")
	private TransactionDetails transactionDetails;
	@JsonProperty("FeesAndTaxes")
	private FeesAndTaxes feesAndTaxes;
	@JsonProperty("PartnerDetails")
	private PartnerDetails partnerDetails;
	@JsonProperty("MandatoryCovers")
	private List<MandatoryCovers> mandatoryCovers;
	@JsonProperty("ListedItems")
	private List<ListedItems> listedItems;
	public List<ListedItems> getListedItems() {
		return listedItems;
	}
	public void setListedItems(List<ListedItems> listedItems) {
		this.listedItems = listedItems;
	}
	@JsonProperty("OptionalCovers")
	private List<OptionalCovers> optionalCovers;
	@JsonProperty("BuildingDetails")
	private BuildingDetails buildingDetails;
	@JsonProperty("StaffDetails")
	private StaffDetails staffDetails;
	@JsonProperty("UnderWritingQuestions")
	private UnderWritingQuestions underWritingQuestions;
	@JsonProperty("Extras")
	private Extras extras;
	@JsonProperty("ValidationError")
	private List<ValidationError> errors;
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
	@JsonSerialize(using=CustomDateSerializer.class)
	public Date getQuoteValidTill() {
		return quoteValidTill;
	}
	@JsonDeserialize(using=CustomDateDeSerializer.class)
	public void setQuoteValidTill(Date quoteValidTill) {
		this.quoteValidTill = quoteValidTill;
	}
	public Integer getQuoteStatus() {
		return quoteStatus;
	}
	public void setQuoteStatus(Integer quoteStatus) {
		this.quoteStatus = quoteStatus;
	}
	public BigDecimal getFinalPremium() {
		return finalPremium;
	}
	public void setFinalPremium(BigDecimal finalPremium) {
		this.finalPremium = finalPremium;
	}
	public BigDecimal getPremiumPayable() {
		return premiumPayable;
	}
	public void setPremiumPayable(BigDecimal premiumPayable) {
		this.premiumPayable = premiumPayable;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
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
	public FeesAndTaxes getFeesAndTaxes() {
		return feesAndTaxes;
	}
	public void setFeesAndTaxes(FeesAndTaxes feesAndTaxes) {
		this.feesAndTaxes = feesAndTaxes;
	}
	public PartnerDetails getPartnerDetails() {
		return partnerDetails;
	}
	public void setPartnerDetails(PartnerDetails partnerDetails) {
		this.partnerDetails = partnerDetails;
	}
	public List<MandatoryCovers> getMandatoryCovers() {
		return mandatoryCovers;
	}
	public void setMandatoryCovers(List<MandatoryCovers> mandatoryCovers) {
		this.mandatoryCovers = mandatoryCovers;
	}
	public List<OptionalCovers> getOptionalCovers() {
		return optionalCovers;
	}
	public void setOptionalCovers(List<OptionalCovers> optionalCovers) {
		this.optionalCovers = optionalCovers;
	}
	public BuildingDetails getBuildingDetails() {
		return buildingDetails;
	}
	public void setBuildingDetails(BuildingDetails buildingDetails) {
		this.buildingDetails = buildingDetails;
	}
	public StaffDetails getStaffDetails() {
		return staffDetails;
	}
	public void setStaffDetails(StaffDetails staffDetails) {
		this.staffDetails = staffDetails;
	}
	public UnderWritingQuestions getUnderWritingQuestions() {
		return underWritingQuestions;
	}
	public void setUnderWritingQuestions(UnderWritingQuestions underWritingQuestions) {
		this.underWritingQuestions = underWritingQuestions;
	}
	public Extras getExtras() {
		return extras;
	}
	public void setExtras(Extras extras) {
		this.extras = extras;
	}
	public List<ValidationError> getErrors() {
		return errors;
	}
	public void setErrors(List<ValidationError> errors) {
		this.errors = errors;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((buildingDetails == null) ? 0 : buildingDetails.hashCode());
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((customerDetails == null) ? 0 : customerDetails.hashCode());
		result = prime * result + ((endtId == null) ? 0 : endtId.hashCode());
		result = prime * result + ((endtNo == null) ? 0 : endtNo.hashCode());
		result = prime * result + ((errors == null) ? 0 : errors.hashCode());
		result = prime * result + ((extras == null) ? 0 : extras.hashCode());
		result = prime * result + ((feesAndTaxes == null) ? 0 : feesAndTaxes.hashCode());
		result = prime * result + ((finalPremium == null) ? 0 : finalPremium.hashCode());
		result = prime * result + ((listedItems == null) ? 0 : listedItems.hashCode());
		result = prime * result + ((mandatoryCovers == null) ? 0 : mandatoryCovers.hashCode());
		result = prime * result + ((optionalCovers == null) ? 0 : optionalCovers.hashCode());
		result = prime * result + ((partnerDetails == null) ? 0 : partnerDetails.hashCode());
		result = prime * result + ((policyId == null) ? 0 : policyId.hashCode());
		result = prime * result + ((premiumPayable == null) ? 0 : premiumPayable.hashCode());
		result = prime * result + ((quotationNo == null) ? 0 : quotationNo.hashCode());
		result = prime * result + ((quoteStatus == null) ? 0 : quoteStatus.hashCode());
		result = prime * result + ((quoteValidTill == null) ? 0 : quoteValidTill.hashCode());
		result = prime * result + ((staffDetails == null) ? 0 : staffDetails.hashCode());
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
		CreateHomeQuoteResponse other = (CreateHomeQuoteResponse) obj;
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
		if (staffDetails == null) {
			if (other.staffDetails != null)
				return false;
		} else if (!staffDetails.equals(other.staffDetails))
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
		return "CreateHomeQuoteResponse [quotationNo=" + quotationNo + ", endtId=" + endtId + ", policyId=" + policyId
				+ ", endtNo=" + endtNo + ", quoteValidTill=" + quoteValidTill + ", quoteStatus=" + quoteStatus
				+ ", finalPremium=" + finalPremium + ", premiumPayable=" + premiumPayable + ", currencyType="
				+ currencyType + ", customerDetails=" + customerDetails + ", transactionDetails=" + transactionDetails
				+ ", feesAndTaxes=" + feesAndTaxes + ", partnerDetails=" + partnerDetails + ", mandatoryCovers="
				+ mandatoryCovers + ", listedItems=" + listedItems + ", optionalCovers=" + optionalCovers
				+ ", buildingDetails=" + buildingDetails + ", staffDetails=" + staffDetails + ", underWritingQuestions="
				+ underWritingQuestions + ", extras=" + extras + ", errors=" + errors + "]";
	}
}