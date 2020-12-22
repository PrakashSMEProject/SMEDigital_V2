package com.rsaame.pas.b2c.ws.vo;

import java.util.Date;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonDeserialize;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1044786
 *
 */
@JsonSerialize(include = JsonSerialize.Inclusion.NON_NULL)
public class TransactionDetails {
	@JsonProperty("ClassCode")
	private Integer classCode;
	@JsonProperty("PolicyTypeCode")
	private Integer policyTypeCode;
	@JsonProperty("PolicyTerm")
	private Integer policyTerm;
	@JsonProperty("EffectiveDate")
	private Date effectiveDate;
	@JsonProperty("ExpiryDate")
	private Date expiryDate;
	@JsonProperty("SchemeCode")
	private Integer schemeCode;
	@JsonProperty("TariffCode")
	private Integer tariffCode;
	@JsonProperty("DistChannel")
	private Integer distChannel;
	@JsonProperty("DirectSubAgent")
	private Integer directSubAgent;
	@JsonProperty("BusinessSource")
	private Integer businessSource;
	@JsonProperty("Promocode")
	private String promocode;
	@JsonProperty("PartnerTrnReferenceNumber")
	private String partnerTrnReferenceNumber;
	@JsonProperty("FinalUpdate")
	private Boolean finalUpdate;
	public Integer getClassCode() {
		return classCode;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	public Integer getPolicyTypeCode() {
		return policyTypeCode;
	}

	public void setPolicyTypeCode(Integer policyTypeCode) {
		this.policyTypeCode = policyTypeCode;
	}

	public Integer getPolicyTerm() {
		return policyTerm;
	}

	public void setPolicyTerm(Integer policyTerm) {
		this.policyTerm = policyTerm;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getEffectiveDate() {
		return effectiveDate;
	}

	@JsonDeserialize(using = CustomDateDeSerializer.class)
	public void setEffectiveDate(Date effectiveDate) {
		this.effectiveDate = effectiveDate;
	}

	@JsonSerialize(using = CustomDateSerializer.class)
	public Date getExpiryDate() {
		return expiryDate;
	}

	@JsonDeserialize(using = CustomDateDeSerializer.class)
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
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

	public Integer getDistChannel() {
		return distChannel;
	}

	public void setDistChannel(Integer distChannel) {
		this.distChannel = distChannel;
	}

	public Integer getDirectSubAgent() {
		return directSubAgent;
	}

	public void setDirectSubAgent(Integer directSubAgent) {
		this.directSubAgent = directSubAgent;
	}

	public Integer getBusinessSource() {
		return businessSource;
	}

	public void setBusinessSource(Integer businessSource) {
		this.businessSource = businessSource;
	}

	public String getPromocode() {
		return promocode;
	}

	public void setPromocode(String promocode) {
		this.promocode = promocode;
	}

	public String getPartnerTrnReferenceNumber() {
		return partnerTrnReferenceNumber;
	}

	public void setPartnerTrnReferenceNumber(String partnerTrnReferenceNumber) {
		this.partnerTrnReferenceNumber = partnerTrnReferenceNumber;
	}

	public Boolean getFinalUpdate() {
		return finalUpdate;
	}

	public void setFinalUpdate(Boolean finalUpdate) {
		this.finalUpdate = finalUpdate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((businessSource == null) ? 0 : businessSource.hashCode());
		result = prime * result + ((classCode == null) ? 0 : classCode.hashCode());
		result = prime * result + ((directSubAgent == null) ? 0 : directSubAgent.hashCode());
		result = prime * result + ((distChannel == null) ? 0 : distChannel.hashCode());
		result = prime * result + ((effectiveDate == null) ? 0 : effectiveDate.hashCode());
		result = prime * result + ((expiryDate == null) ? 0 : expiryDate.hashCode());
		result = prime * result + ((finalUpdate == null) ? 0 : finalUpdate.hashCode());
		result = prime * result + ((partnerTrnReferenceNumber == null) ? 0 : partnerTrnReferenceNumber.hashCode());
		result = prime * result + ((policyTerm == null) ? 0 : policyTerm.hashCode());
		result = prime * result + ((policyTypeCode == null) ? 0 : policyTypeCode.hashCode());
		result = prime * result + ((promocode == null) ? 0 : promocode.hashCode());
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
		TransactionDetails other = (TransactionDetails) obj;
		if (businessSource == null) {
			if (other.businessSource != null)
				return false;
		} else if (!businessSource.equals(other.businessSource))
			return false;
		if (classCode == null) {
			if (other.classCode != null)
				return false;
		} else if (!classCode.equals(other.classCode))
			return false;
		if (directSubAgent == null) {
			if (other.directSubAgent != null)
				return false;
		} else if (!directSubAgent.equals(other.directSubAgent))
			return false;
		if (distChannel == null) {
			if (other.distChannel != null)
				return false;
		} else if (!distChannel.equals(other.distChannel))
			return false;
		if (effectiveDate == null) {
			if (other.effectiveDate != null)
				return false;
		} else if (!effectiveDate.equals(other.effectiveDate))
			return false;
		if (expiryDate == null) {
			if (other.expiryDate != null)
				return false;
		} else if (!expiryDate.equals(other.expiryDate))
			return false;
		if (finalUpdate == null) {
			if (other.finalUpdate != null)
				return false;
		} else if (!finalUpdate.equals(other.finalUpdate))
			return false;
		if (partnerTrnReferenceNumber == null) {
			if (other.partnerTrnReferenceNumber != null)
				return false;
		} else if (!partnerTrnReferenceNumber.equals(other.partnerTrnReferenceNumber))
			return false;
		if (policyTerm == null) {
			if (other.policyTerm != null)
				return false;
		} else if (!policyTerm.equals(other.policyTerm))
			return false;
		if (policyTypeCode == null) {
			if (other.policyTypeCode != null)
				return false;
		} else if (!policyTypeCode.equals(other.policyTypeCode))
			return false;
		if (promocode == null) {
			if (other.promocode != null)
				return false;
		} else if (!promocode.equals(other.promocode))
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
		return "TransactionDetails [finalUpdate=" + finalUpdate + ", classCode=" + classCode + ", policyTypeCode="
				+ policyTypeCode + ", policyTerm=" + policyTerm + ", effectiveDate=" + effectiveDate + ", expiryDate="
				+ expiryDate + ", schemeCode=" + schemeCode + ", tariffCode=" + tariffCode + ", distChannel="
				+ distChannel + ", directSubAgent=" + directSubAgent + ", businessSource=" + businessSource
				+ ", promocode=" + promocode + ", partnerTrnReferenceNumber=" + partnerTrnReferenceNumber + "]";
	}

}
