package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

public class Products {
	@JsonProperty("ProductCode")
	private Integer productCode;
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
	@JsonProperty("MandatoryCovers")
	private List<MandatoryCovers> mandatoryCovers;
	@JsonProperty("OptionalCovers")
	private List<OptionalCovers> optionalCovers;
	public Integer getProductCode() {
		return productCode;
	}
	public void setProductCode(Integer productCode) {
		this.productCode = productCode;
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
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public FeesAndTaxes getFeesAndTaxes() {
		return feesAndTaxes;
	}
	public void setFeesAndTaxes(FeesAndTaxes feesAndTaxes) {
		this.feesAndTaxes = feesAndTaxes;
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
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((currencyType == null) ? 0 : currencyType.hashCode());
		result = prime * result + ((feesAndTaxes == null) ? 0 : feesAndTaxes.hashCode());
		result = prime * result + ((finalPremium == null) ? 0 : finalPremium.hashCode());
		result = prime * result + ((mandatoryCovers == null) ? 0 : mandatoryCovers.hashCode());
		result = prime * result + ((optionalCovers == null) ? 0 : optionalCovers.hashCode());
		result = prime * result + ((premiumPayable == null) ? 0 : premiumPayable.hashCode());
		result = prime * result + ((productCode == null) ? 0 : productCode.hashCode());
		result = prime * result + ((productDesc == null) ? 0 : productDesc.hashCode());
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
		Products other = (Products) obj;
		if (currencyType == null) {
			if (other.currencyType != null)
				return false;
		} else if (!currencyType.equals(other.currencyType))
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
		if (premiumPayable == null) {
			if (other.premiumPayable != null)
				return false;
		} else if (!premiumPayable.equals(other.premiumPayable))
			return false;
		if (productCode == null) {
			if (other.productCode != null)
				return false;
		} else if (!productCode.equals(other.productCode))
			return false;
		if (productDesc == null) {
			if (other.productDesc != null)
				return false;
		} else if (!productDesc.equals(other.productDesc))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "Products [productCode=" + productCode + ", finalPremium=" + finalPremium + ", premiumPayable="
				+ premiumPayable + ", currencyType=" + currencyType + ", productDesc=" + productDesc + ", feesAndTaxes="
				+ feesAndTaxes + ", mandatoryCovers=" + mandatoryCovers + ", optionalCovers=" + optionalCovers + "]";
	}
	
	
}
