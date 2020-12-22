package com.rsaame.pas.b2c.ws.beans;

import java.math.BigDecimal;
import java.util.Date;

public class BuildingDetails {

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
	private boolean isLoading = Boolean.FALSE;
	private BigDecimal minPremium;
	
	private String emirates;
	private String area;
	private String buildingname;
	private String otherDetails;
	private String flatVillaNo;
	// Added two fields Home Revamp 4.1 
	private Short totalNoRooms;
	private Short totalNoFloors;
	private String mortgageeName;
	private Short ownershipStatus;
	private Short typeOfProperty;
	private Short geoAreaCode;
	private Date vsd;
	
	private Cover coverCodes;
	private RiskDetails riskCodes;
	private SumInsuredDetails sumInsured;
	
	
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
	public boolean isLoading() {
		return isLoading;
	}
	public void setLoading(boolean isLoading) {
		this.isLoading = isLoading;
	}
	public BigDecimal getMinPremium() {
		return minPremium;
	}
	public void setMinPremium(BigDecimal minPremium) {
		this.minPremium = minPremium;
	}
	public String getEmirates() {
		return emirates;
	}
	public void setEmirates(String emirates) {
		this.emirates = emirates;
	}
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getBuildingname() {
		return buildingname;
	}
	public void setBuildingname(String buildingname) {
		this.buildingname = buildingname;
	}
	public String getOtherDetails() {
		return otherDetails;
	}
	public void setOtherDetails(String otherDetails) {
		this.otherDetails = otherDetails;
	}
	public String getFlatVillaNo() {
		return flatVillaNo;
	}
	public void setFlatVillaNo(String flatVillaNo) {
		this.flatVillaNo = flatVillaNo;
	}
	// Changes Home Revamp requirement 4.1 */
	public Short getTotalNoRooms() {
		return totalNoRooms;
	}
	public void setTotalNoRooms(Short totalNoRooms) {
		this.totalNoRooms = totalNoRooms;
	}
	public Short getTotalNoFloors() {
		return totalNoFloors;
	}
	public void setTotalNoFloors(Short totalNoFloors) {
		this.totalNoFloors = totalNoFloors;
	}
	public String getMortgageeName() {
		return mortgageeName;
	}
	public void setMortgageeName(String mortgageeName) {
		this.mortgageeName = mortgageeName;
	}
	public Short getOwnershipStatus() {
		return ownershipStatus;
	}
	public void setOwnershipStatus(Short ownershipStatus) {
		this.ownershipStatus = ownershipStatus;
	}
	public Short getTypeOfProperty() {
		return typeOfProperty;
	}
	public void setTypeOfProperty(Short typeOfProperty) {
		this.typeOfProperty = typeOfProperty;
	}
	public Short getGeoAreaCode() {
		return geoAreaCode;
	}
	public void setGeoAreaCode(Short geoAreaCode) {
		this.geoAreaCode = geoAreaCode;
	}
	public Date getVsd() {
		return vsd;
	}
	public void setVsd(Date vsd) {
		this.vsd = vsd;
	}
	public Cover getCoverCodes() {
		return coverCodes;
	}
	public void setCoverCodes(Cover coverCodes) {
		this.coverCodes = coverCodes;
	}
	public RiskDetails getRiskCodes() {
		return riskCodes;
	}
	public void setRiskCodes(RiskDetails riskCodes) {
		this.riskCodes = riskCodes;
	}
	public SumInsuredDetails getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(SumInsuredDetails sumInsured) {
		this.sumInsured = sumInsured;
	}
}
