package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1044786
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class RiskDetails {
	@JsonProperty("RskId")
	private Integer rskId;
	@JsonProperty("BasicRskId")
	private Integer basicRskId;
	@JsonProperty("BasicRskCode")
	private Integer basicRskCode;
	@JsonProperty("RiskCode")
	private Integer riskCode;
	@JsonProperty("RiskType")
	private Integer riskType;
	@JsonProperty("RiskCat")
	private Integer riskCat;
	
	public Integer getRskId() {
		return rskId;
	}
	public void setRskId(Integer rskId) {
		this.rskId = rskId;
	}
	public Integer getBasicRskId() {
		return basicRskId;
	}
	public void setBasicRskId(Integer basicRskId) {
		this.basicRskId = basicRskId;
	}
	public Integer getBasicRskCode() {
		return basicRskCode;
	}
	public void setBasicRskCode(Integer basicRskCode) {
		this.basicRskCode = basicRskCode;
	}
	public Integer getRiskCode() {
		return riskCode;
	}
	public void setRiskCode(Integer riskCode) {
		this.riskCode = riskCode;
	}
	public Integer getRiskType() {
		return riskType;
	}
	public void setRiskType(Integer riskType) {
		this.riskType = riskType;
	}
	public Integer getRiskCat() {
		return riskCat;
	}
	public void setRiskCat(Integer riskCat) {
		this.riskCat = riskCat;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((basicRskCode == null) ? 0 : basicRskCode.hashCode());
		result = prime * result + ((basicRskId == null) ? 0 : basicRskId.hashCode());
		result = prime * result + ((riskCat == null) ? 0 : riskCat.hashCode());
		result = prime * result + ((riskCode == null) ? 0 : riskCode.hashCode());
		result = prime * result + ((riskType == null) ? 0 : riskType.hashCode());
		result = prime * result + ((rskId == null) ? 0 : rskId.hashCode());
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
		RiskDetails other = (RiskDetails) obj;
		if (basicRskCode == null) {
			if (other.basicRskCode != null)
				return false;
		} else if (!basicRskCode.equals(other.basicRskCode))
			return false;
		if (basicRskId == null) {
			if (other.basicRskId != null)
				return false;
		} else if (!basicRskId.equals(other.basicRskId))
			return false;
		if (riskCat == null) {
			if (other.riskCat != null)
				return false;
		} else if (!riskCat.equals(other.riskCat))
			return false;
		if (riskCode == null) {
			if (other.riskCode != null)
				return false;
		} else if (!riskCode.equals(other.riskCode))
			return false;
		if (riskType == null) {
			if (other.riskType != null)
				return false;
		} else if (!riskType.equals(other.riskType))
			return false;
		if (rskId == null) {
			if (other.rskId != null)
				return false;
		} else if (!rskId.equals(other.rskId))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "RiskDetails [rskId=" + rskId + ", basicRskId=" + basicRskId + ", basicRskCode=" + basicRskCode
				+ ", riskCode=" + riskCode + ", riskType=" + riskType + ", riskCat=" + riskCat + "]";
	}
	public RiskDetails(Integer rskId, Integer basicRskId, Integer basicRskCode, Integer riskCode, Integer riskType,
			Integer riskCat) {
		super();
		this.rskId = rskId;
		this.basicRskId = basicRskId;
		this.basicRskCode = basicRskCode;
		this.riskCode = riskCode;
		this.riskType = riskType;
		this.riskCat = riskCat;
	}
	public RiskDetails() {
		// TODO Auto-generated constructor stub
	}
	
	
}
