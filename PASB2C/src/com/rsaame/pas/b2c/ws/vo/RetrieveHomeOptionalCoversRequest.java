package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;

public class RetrieveHomeOptionalCoversRequest {
	@JsonProperty("ClassCode")
	private Integer classCode;
	@JsonProperty("PolicyTypeCode")
	private Integer policyType;
	@JsonProperty("SchemeCode")
	private Integer schemeCode;
	@JsonProperty("TariffCode")
	private Integer tariffCode;

	public Integer getClassCode() {
		return classCode;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	public Integer getPolicyType() {
		return policyType;
	}

	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}

	public Integer getSchemeCode() {
		return schemeCode;
	}

	public void setSchemeCode(Integer schemeCode) {
		this.schemeCode = schemeCode;
	}

	public Integer getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(Integer tariffCode) {
		this.tariffCode = tariffCode;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result + ((policyType == null) ? 0 : policyType.hashCode());
		result = prime * result + ((schemeCode == null) ? 0 : schemeCode.hashCode());
		result = prime * result + ((tariffCode == null) ? 0 : tariffCode.hashCode());
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
		RetrieveHomeOptionalCoversRequest other = (RetrieveHomeOptionalCoversRequest) obj;
		if (classCode == null) {
			if (other.classCode != null)
				return false;
		} else if (!classCode.equals(other.classCode))
			return false;
		if (policyType == null) {
			if (other.policyType != null)
				return false;
		} else if (!policyType.equals(other.policyType))
			return false;
		if (schemeCode == null) {
			if (other.schemeCode != null)
				return false;
		} else if (!schemeCode.equals(other.schemeCode))
			return false;
		if (tariffCode == null) {
			if (other.tariffCode != null)
				return false;
		} else if (!tariffCode.equals(other.tariffCode))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "RetrieveHomeOptionalCoversRequest [classCode=" + classCode + ", policyType=" + policyType
				+ ", schemeCode=" + schemeCode + ", tariffCode=" + tariffCode + "]";
	}

}
