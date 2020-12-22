package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class ListedItems {
	@JsonProperty("CoverDesc")
	private String coverDesc;
	@JsonProperty("Premium")
	private BigDecimal premium;
	@JsonProperty("CoverIncluded")
	private Boolean coverIncluded;
	@JsonProperty("CoverageLimit")
	private BigDecimal coverageLimit;
	@JsonProperty("CoverMappingCode")
	private String coverMappingCode;
	@JsonProperty("RiskMappingCode")
	private String riskMappingCode;
	@JsonProperty("RiskDetails")
	private RiskDetails riskDetails;

	public RiskDetails getRiskDetails() {
		return riskDetails;
	}

	public void setRiskDetails(RiskDetails riskDetails) {
		this.riskDetails = riskDetails;
	}

	public String getCoverDesc() {
		return coverDesc;
	}

	public void setCoverDesc(String coverDesc) {
		this.coverDesc = coverDesc;
	}

	public BigDecimal getPremium() {
		return premium;
	}

	public void setPremium(BigDecimal premium) {
		this.premium = premium;
	}

	public Boolean getCoverIncluded() {
		return coverIncluded;
	}

	public void setCoverIncluded(Boolean coverIncluded) {
		this.coverIncluded = coverIncluded;
	}

	public BigDecimal getCoverageLimit() {
		return coverageLimit;
	}

	public void setCoverageLimit(BigDecimal coverageLimit) {
		this.coverageLimit = coverageLimit;
	}

	public String getCoverMappingCode() {
		return coverMappingCode;
	}

	public void setCoverMappingCode(String coverMappingCode) {
		this.coverMappingCode = coverMappingCode;
	}

	public String getRiskMappingCode() {
		return riskMappingCode;
	}

	public void setRiskMappingCode(String riskMappingCode) {
		this.riskMappingCode = riskMappingCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((coverDesc == null) ? 0 : coverDesc.hashCode());
		result = prime * result + ((coverIncluded == null) ? 0 : coverIncluded.hashCode());
		result = prime * result + ((coverMappingCode == null) ? 0 : coverMappingCode.hashCode());
		result = prime * result + ((coverageLimit == null) ? 0 : coverageLimit.hashCode());
		result = prime * result + ((premium == null) ? 0 : premium.hashCode());
		result = prime * result + ((riskDetails == null) ? 0 : riskDetails.hashCode());
		result = prime * result + ((riskMappingCode == null) ? 0 : riskMappingCode.hashCode());
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
		ListedItems other = (ListedItems) obj;
		if (coverDesc == null) {
			if (other.coverDesc != null)
				return false;
		} else if (!coverDesc.equals(other.coverDesc))
			return false;
		if (coverIncluded == null) {
			if (other.coverIncluded != null)
				return false;
		} else if (!coverIncluded.equals(other.coverIncluded))
			return false;
		if (coverMappingCode == null) {
			if (other.coverMappingCode != null)
				return false;
		} else if (!coverMappingCode.equals(other.coverMappingCode))
			return false;
		if (coverageLimit == null) {
			if (other.coverageLimit != null)
				return false;
		} else if (!coverageLimit.equals(other.coverageLimit))
			return false;
		if (premium == null) {
			if (other.premium != null)
				return false;
		} else if (!premium.equals(other.premium))
			return false;
		if (riskDetails == null) {
			if (other.riskDetails != null)
				return false;
		} else if (!riskDetails.equals(other.riskDetails))
			return false;
		if (riskMappingCode == null) {
			if (other.riskMappingCode != null)
				return false;
		} else if (!riskMappingCode.equals(other.riskMappingCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "ListedItems [coverDesc=" + coverDesc + ", premium=" + premium + ", coverIncluded=" + coverIncluded
				+ ", coverageLimit=" + coverageLimit + ", coverMappingCode=" + coverMappingCode + ", riskMappingCode="
				+ riskMappingCode + ", riskDetails=" + riskDetails + "]";
	}

	public ListedItems() {
		// TODO Auto-generated constructor stub
	}

	public ListedItems(String coverDesc, BigDecimal premium, Boolean coverIncluded, BigDecimal coverageLimit,
			String coverMappingCode, String riskMappingCode, RiskDetails riskDetails) {
		super();
		this.coverDesc = coverDesc;
		this.premium = premium;
		this.coverIncluded = coverIncluded;
		this.coverageLimit = coverageLimit;
		this.coverMappingCode = coverMappingCode;
		this.riskMappingCode = riskMappingCode;
		this.riskDetails = riskDetails;
	}

}
