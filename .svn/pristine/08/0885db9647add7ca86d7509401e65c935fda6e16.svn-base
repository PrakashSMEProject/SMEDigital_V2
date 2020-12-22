/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * @author M1037404
 *
 */
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class FeesAndTaxes {
	@JsonProperty("LoadingOrDiscountPercent")
	private BigDecimal loadingOrDiscountPercent;
	@JsonProperty("LoadingOrDiscountAmount")
	private BigDecimal loadingOrDiscountAmount;
	@JsonProperty("PromoCodeDiscountPercent")
	private BigDecimal promoCodeDiscountPercent;
	@JsonProperty("PromoCodeDiscountAmount")
	private BigDecimal promoCodeDiscountAmount;
	@JsonProperty("GovtTaxPercent")
	private BigDecimal govtTaxPercent;
	@JsonProperty("VatRatePercent")
	private BigDecimal vatRatePercent;
	@JsonProperty("VatAmount")
	private BigDecimal vatAmount;
	@JsonProperty("PolicyFees")
	private BigDecimal policyFees;
	
	public BigDecimal getLoadingOrDiscountPercent() {
		return loadingOrDiscountPercent;
	}
	public void setLoadingOrDiscountPercent(BigDecimal loadingOrDiscountPercent) {
		this.loadingOrDiscountPercent = loadingOrDiscountPercent;
	}
	public BigDecimal getLoadingOrDiscountAmount() {
		return loadingOrDiscountAmount;
	}
	public void setLoadingOrDiscountAmount(BigDecimal loadingOrDiscountAmount) {
		this.loadingOrDiscountAmount = loadingOrDiscountAmount;
	}
	public BigDecimal getPromoCodeDiscountPercent() {
		return promoCodeDiscountPercent;
	}
	public void setPromoCodeDiscountPercent(BigDecimal promoCodeDiscountPercent) {
		this.promoCodeDiscountPercent = promoCodeDiscountPercent;
	}
	public BigDecimal getPromoCodeDiscountAmount() {
		return promoCodeDiscountAmount;
	}
	public void setPromoCodeDiscountAmount(BigDecimal promoCodeDiscountAmount) {
		this.promoCodeDiscountAmount = promoCodeDiscountAmount;
	}
	public BigDecimal getGovtTaxPercent() {
		return govtTaxPercent;
	}
	public void setGovtTaxPercent(BigDecimal govtTaxPercent) {
		this.govtTaxPercent = govtTaxPercent;
	}
	public BigDecimal getVatRatePercent() {
		return vatRatePercent;
	}
	public void setVatRatePercent(BigDecimal vatRatePercent) {
		this.vatRatePercent = vatRatePercent;
	}
	public BigDecimal getVatAmount() {
		return vatAmount;
	}
	public void setVatAmount(BigDecimal vatAmount) {
		this.vatAmount = vatAmount;
	}
	public BigDecimal getPolicyFees() {
		return policyFees;
	}
	public void setPolicyFees(BigDecimal policyFees) {
		this.policyFees = policyFees;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((govtTaxPercent == null) ? 0 : govtTaxPercent.hashCode());
		result = prime * result + ((loadingOrDiscountAmount == null) ? 0 : loadingOrDiscountAmount.hashCode());
		result = prime * result + ((loadingOrDiscountPercent == null) ? 0 : loadingOrDiscountPercent.hashCode());
		result = prime * result + ((policyFees == null) ? 0 : policyFees.hashCode());
		result = prime * result + ((promoCodeDiscountAmount == null) ? 0 : promoCodeDiscountAmount.hashCode());
		result = prime * result + ((promoCodeDiscountPercent == null) ? 0 : promoCodeDiscountPercent.hashCode());
		result = prime * result + ((vatAmount == null) ? 0 : vatAmount.hashCode());
		result = prime * result + ((vatRatePercent == null) ? 0 : vatRatePercent.hashCode());
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
		FeesAndTaxes other = (FeesAndTaxes) obj;
		if (govtTaxPercent == null) {
			if (other.govtTaxPercent != null)
				return false;
		} else if (!govtTaxPercent.equals(other.govtTaxPercent))
			return false;
		if (loadingOrDiscountAmount == null) {
			if (other.loadingOrDiscountAmount != null)
				return false;
		} else if (!loadingOrDiscountAmount.equals(other.loadingOrDiscountAmount))
			return false;
		if (loadingOrDiscountPercent == null) {
			if (other.loadingOrDiscountPercent != null)
				return false;
		} else if (!loadingOrDiscountPercent.equals(other.loadingOrDiscountPercent))
			return false;
		if (policyFees == null) {
			if (other.policyFees != null)
				return false;
		} else if (!policyFees.equals(other.policyFees))
			return false;
		if (promoCodeDiscountAmount == null) {
			if (other.promoCodeDiscountAmount != null)
				return false;
		} else if (!promoCodeDiscountAmount.equals(other.promoCodeDiscountAmount))
			return false;
		if (promoCodeDiscountPercent == null) {
			if (other.promoCodeDiscountPercent != null)
				return false;
		} else if (!promoCodeDiscountPercent.equals(other.promoCodeDiscountPercent))
			return false;
		if (vatAmount == null) {
			if (other.vatAmount != null)
				return false;
		} else if (!vatAmount.equals(other.vatAmount))
			return false;
		if (vatRatePercent == null) {
			if (other.vatRatePercent != null)
				return false;
		} else if (!vatRatePercent.equals(other.vatRatePercent))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "FeesAndTaxes [loadingOrDiscountPercent=" + loadingOrDiscountPercent + ", loadingOrDiscountAmount="
				+ loadingOrDiscountAmount + ", promoCodeDiscountPercent=" + promoCodeDiscountPercent
				+ ", promoCodeDiscountAmount=" + promoCodeDiscountAmount + ", govtTaxPercent=" + govtTaxPercent
				+ ", vatRatePercent=" + vatRatePercent + ", vatAmount=" + vatAmount + ", policyFees=" + policyFees
				+ "]";
	}
	
}
