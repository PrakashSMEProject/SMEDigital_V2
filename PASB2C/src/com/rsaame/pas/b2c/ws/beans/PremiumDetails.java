package com.rsaame.pas.b2c.ws.beans;

import java.math.BigDecimal;

public class PremiumDetails {
	
	private double premiumAmt;
	private double premiumAmtActual;
	private String currency;
	private double govtTax;
	private double specialDiscount;
	private double policyFees;
	private double lossRatio;
	private Double discOrLoadPerc;
	private BigDecimal discOrLoadAmt;
	private Double promoDiscPerc;
	private BigDecimal minPremium;
	
	/*VAT - Dileep*/
	
	private double vatTax;
	
	
	public double getPremiumAmt() {
		return premiumAmt;
	}
	public void setPremiumAmt(double premiumAmt) {
		this.premiumAmt = premiumAmt;
	}
	public double getPremiumAmtActual() {
		return premiumAmtActual;
	}
	public void setPremiumAmtActual(double premiumAmtActual) {
		this.premiumAmtActual = premiumAmtActual;
	}
	public String getCurrency() {
		return currency;
	}
	public void setCurrency(String currency) {
		this.currency = currency;
	}
	public double getGovtTax() {
		return govtTax;
	}
	public void setGovtTax(double govtTax) {
		this.govtTax = govtTax;
	}
	public double getSpecialDiscount() {
		return specialDiscount;
	}
	public void setSpecialDiscount(double specialDiscount) {
		this.specialDiscount = specialDiscount;
	}
	public double getPolicyFees() {
		return policyFees;
	}
	public void setPolicyFees(double policyFees) {
		this.policyFees = policyFees;
	}
	public double getLossRatio() {
		return lossRatio;
	}
	public void setLossRatio(double lossRatio) {
		this.lossRatio = lossRatio;
	}
	public Double getDiscOrLoadPerc() {
		return discOrLoadPerc;
	}
	public void setDiscOrLoadPerc(Double discOrLoadPerc) {
		this.discOrLoadPerc = discOrLoadPerc;
	}
	public BigDecimal getDiscOrLoadAmt() {
		return discOrLoadAmt;
	}
	public void setDiscOrLoadAmt(BigDecimal discOrLoadAmt) {
		this.discOrLoadAmt = discOrLoadAmt;
	}
	public Double getPromoDiscPerc() {
		return promoDiscPerc;
	}
	public void setPromoDiscPerc(Double promoDiscPerc) {
		this.promoDiscPerc = promoDiscPerc;
	}
	public BigDecimal getMinPremium() {
		return minPremium;
	}
	public void setMinPremium(BigDecimal minPremium) {
		this.minPremium = minPremium;
	}
	
	/*VAT - Dileep*/
	/**
	 * @return the vatTax
	 */
	public double getVatTax() {
		return vatTax;
	}
	/**
	 * @param vatTax the vatTax to set
	 */
	public void setVatTax(double vatTax) {
		this.vatTax = vatTax;
	}

}
