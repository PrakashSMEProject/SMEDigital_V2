/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author M1044786
 *
 */
public class CreateHomeQuoteRequest {

	@JsonProperty("CustomerDetails")
	private CustomerDetails customerDetails;
	@JsonProperty("TransactionDetails")
	private TransactionDetails transactionDetails;
	@JsonProperty("PartnerDetails")
	private PartnerDetails partnerDetails;
	@JsonProperty("MandatoryCovers")
	private List<MandatoryCovers> mandatoryCovers;
	@JsonProperty("ListedItems")
	private List<ListedItems> listedItems;
	@JsonProperty("OptionalCovers")
	private List<OptionalCovers> optionalCovers;
	@JsonProperty("BuildingDetails")
	private BuildingDetails buildingDetails;
	@JsonProperty("StaffDetails")
	private StaffDetails staffDetails;
	private String pmmId;

	public CustomerDetails getCustomerDetails() {
		return customerDetails;
	}

	public void setCustomerDetails(CustomerDetails customerDetails) {
		this.customerDetails = customerDetails;
	}

	public String getPmmId() {
		return pmmId;
	}

	public void setPmmId(String pmmId) {
		this.pmmId = pmmId;
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
		result = prime * result + ((customerDetails == null) ? 0 : customerDetails.hashCode());
		result = prime * result + ((listedItems == null) ? 0 : listedItems.hashCode());
		result = prime * result + ((mandatoryCovers == null) ? 0 : mandatoryCovers.hashCode());
		result = prime * result + ((optionalCovers == null) ? 0 : optionalCovers.hashCode());
		result = prime * result + ((partnerDetails == null) ? 0 : partnerDetails.hashCode());
		result = prime * result + ((pmmId == null) ? 0 : pmmId.hashCode());
		result = prime * result + ((staffDetails == null) ? 0 : staffDetails.hashCode());
		result = prime * result + ((transactionDetails == null) ? 0 : transactionDetails.hashCode());
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
		CreateHomeQuoteRequest other = (CreateHomeQuoteRequest) obj;
		if (buildingDetails == null) {
			if (other.buildingDetails != null)
				return false;
		} else if (!buildingDetails.equals(other.buildingDetails))
			return false;
		if (customerDetails == null) {
			if (other.customerDetails != null)
				return false;
		} else if (!customerDetails.equals(other.customerDetails))
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
		if (pmmId == null) {
			if (other.pmmId != null)
				return false;
		} else if (!pmmId.equals(other.pmmId))
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
		return true;
	}

	@Override
	public String toString() {
		return "CreateHomeQuoteRequest [customerDetails=" + customerDetails + ", transactionDetails="
				+ transactionDetails + ", partnerDetails=" + partnerDetails + ", mandatoryCovers=" + mandatoryCovers
				+ ", listedItems=" + listedItems + ", optionalCovers=" + optionalCovers + ", buildingDetails="
				+ buildingDetails + ", staffDetails=" + staffDetails + ", pmmId=" + pmmId + "]";
	}

}
