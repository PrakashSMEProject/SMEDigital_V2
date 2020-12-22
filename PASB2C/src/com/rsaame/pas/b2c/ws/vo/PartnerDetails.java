/**
 * 
 */
package com.rsaame.pas.b2c.ws.vo;

import java.math.BigDecimal;

import org.codehaus.jackson.annotate.JsonProperty;

/**
 * @author M1037404
 * Modified By : M1044786
 *
 */
public class PartnerDetails {
	
	@JsonProperty("CommissionPercentage")
	private BigDecimal commissionPercentage;
	@JsonProperty("CommissionAmount")
	private BigDecimal commissionAmount;
	
	public BigDecimal getCommissionPercentage() {
		return commissionPercentage;
	}
	public void setCommissionPercentage(BigDecimal commissionPercentage) {
		this.commissionPercentage = commissionPercentage;
	}
	public BigDecimal getCommissionAmount() {
		return commissionAmount;
	}
	public void setCommissionAmount(BigDecimal commissionAmount) {
		this.commissionAmount = commissionAmount;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((commissionAmount == null) ? 0 : commissionAmount.hashCode());
		result = prime * result + ((commissionPercentage == null) ? 0 : commissionPercentage.hashCode());
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
		PartnerDetails other = (PartnerDetails) obj;
		if (commissionAmount == null) {
			if (other.commissionAmount != null)
				return false;
		} else if (!commissionAmount.equals(other.commissionAmount))
			return false;
		if (commissionPercentage == null) {
			if (other.commissionPercentage != null)
				return false;
		} else if (!commissionPercentage.equals(other.commissionPercentage))
			return false;
		return true;
	}
	@Override
	public String toString() {
		return "PartnerDetails [commissionPercentage=" + commissionPercentage + ", commissionAmount=" + commissionAmount
				+ "]";
	}
	
	
}
