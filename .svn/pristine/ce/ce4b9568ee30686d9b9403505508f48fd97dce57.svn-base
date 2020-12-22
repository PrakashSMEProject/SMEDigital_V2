package com.rsaame.pas.b2c.ws.vo;

public class CoverDetails {
	
	private String coverName;
	private Integer covCode;
	private Integer coverType;
	private Integer coverSubType;
	private RiskDetails riskDetails;
	private double sumInsured;
	public String getCoverName() {
		return coverName;
	}
	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}
	public Integer getCovCode() {
		return covCode;
	}
	public void setCovCode(Integer covCode) {
		this.covCode = covCode;
	}
	public Integer getCoverType() {
		return coverType;
	}
	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}
	public Integer getCoverSubType() {
		return coverSubType;
	}
	public void setCoverSubType(Integer coverSubType) {
		this.coverSubType = coverSubType;
	}
	public RiskDetails getRiskDetails() {
		return riskDetails;
	}
	public void setRiskDetails(RiskDetails riskDetails) {
		this.riskDetails = riskDetails;
	}
	public double getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(double sumInsured) {
		this.sumInsured = sumInsured;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((covCode == null) ? 0 : covCode.hashCode());
		result = prime * result + ((coverName == null) ? 0 : coverName.hashCode());
		result = prime * result + ((coverSubType == null) ? 0 : coverSubType.hashCode());
		result = prime * result + ((coverType == null) ? 0 : coverType.hashCode());
		result = prime * result + ((riskDetails == null) ? 0 : riskDetails.hashCode());
		long temp;
		temp = Double.doubleToLongBits(sumInsured);
		result = prime * result + (int) (temp ^ (temp >>> 32));
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
		CoverDetails other = (CoverDetails) obj;
		if (covCode == null) {
			if (other.covCode != null)
				return false;
		} else if (!covCode.equals(other.covCode))
			return false;
		if (coverName == null) {
			if (other.coverName != null)
				return false;
		} else if (!coverName.equals(other.coverName))
			return false;
		if (coverSubType == null) {
			if (other.coverSubType != null)
				return false;
		} else if (!coverSubType.equals(other.coverSubType))
			return false;
		if (coverType == null) {
			if (other.coverType != null)
				return false;
		} else if (!coverType.equals(other.coverType))
			return false;
		if (riskDetails == null) {
			if (other.riskDetails != null)
				return false;
		} else if (!riskDetails.equals(other.riskDetails))
			return false;
		if (Double.doubleToLongBits(sumInsured) != Double.doubleToLongBits(other.sumInsured))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "CoverDetails [coverName=" + coverName + ", covCode=" + covCode + ", coverType=" + coverType
				+ ", coverSubType=" + coverSubType + ", riskDetails=" + riskDetails + ", sumInsured=" + sumInsured
				+ "]";
	}
	
	
}
