/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1037404
 * Modified By : M1044786
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class OptionalCovers {

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
	@JsonProperty("RiskDetails")
	private RiskDetails riskDetails;
	@JsonProperty("RiskMappingCode")
	private String riskMappingCode;
	@JsonProperty("StaffDetails")
	private StaffDetails staffDetails;
	@JsonProperty("TLLimit")
	private List<TLLimit> tllLimit;

	public String getRiskMappingCode() {
		return riskMappingCode;
	}

	public void setRiskMappingCode(String riskMappingCode) {
		this.riskMappingCode = riskMappingCode;
	}
	
	public StaffDetails getStaffDetails() {
		return staffDetails;
	}

	public void setStaffDetails(StaffDetails staffDetails) {
		this.staffDetails = staffDetails;
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

	public RiskDetails getRiskDetails() {
		return riskDetails;
	}

	public void setRiskDetails(RiskDetails riskDetails) {
		this.riskDetails = riskDetails;
	}

	public List<TLLimit> getTllLimit() {
		return tllLimit;
	}

	public void setTllLimit(List<TLLimit> tllLimit) {
		this.tllLimit = tllLimit;
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
		result = prime * result + ((staffDetails == null) ? 0 : staffDetails.hashCode());
		result = prime * result + ((tllLimit == null) ? 0 : tllLimit.hashCode());
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
		OptionalCovers other = (OptionalCovers) obj;
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
		if (staffDetails == null) {
			if (other.staffDetails != null)
				return false;
		} else if (!staffDetails.equals(other.staffDetails))
			return false;
		if (tllLimit == null) {
			if (other.tllLimit != null)
				return false;
		} else if (!tllLimit.equals(other.tllLimit))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "OptionalCovers [coverDesc=" + coverDesc + ", premium=" + premium + ", coverIncluded=" + coverIncluded
				+ ", coverageLimit=" + coverageLimit + ", coverMappingCode=" + coverMappingCode + ", riskDetails="
				+ riskDetails + ", riskMappingCode=" + riskMappingCode + ", staffDetails=" + staffDetails
				+ ", tllLimit=" + tllLimit + "]";
	}

	public OptionalCovers(String coverDesc, BigDecimal premium, Boolean coverIncluded, BigDecimal coverageLimit,
			String coverMappingCode, RiskDetails riskDetails) {
		super();
		this.coverDesc = coverDesc;
		this.premium = premium;
		this.coverIncluded = coverIncluded;
		this.coverageLimit = coverageLimit;
		this.coverMappingCode = coverMappingCode;
		this.riskDetails = riskDetails;
	}

	public OptionalCovers() {

	}
}
