package com.rsaame.pas.b2c.ws.vo;

import org.codehaus.jackson.annotate.JsonProperty;

public class Covers {
	@JsonProperty("CoverDesc")
	String coverName;
	/*@JsonProperty("ProductId")
	Integer productId;*/
	@JsonProperty("CoverMappingCode")
	String coverId;
	@JsonProperty("CoverageLimit")
	String sumInsured;
	/*@JsonProperty("MandatoryIndicator")
	Boolean mandatoryIndicator;*/
	@JsonProperty("CoverIncluded")
	String isCovered;
	/*@JsonProperty("FieldType")
	String fieldType;*/
	@JsonProperty("RiskDetails")
	RiskDetails riskDetails;
	
	public String getCoverName() {
		return coverName;
	}
	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}
	public String getSumInsured() {
		return sumInsured;
	}
	public void setSumInsured(String sumInsured) {
		this.sumInsured = sumInsured;
	}
	/*public Boolean getMandatoryIndicator() {
		return mandatoryIndicator;
	}
	public void setMandatoryIndicator(Boolean mandatoryIndicator) {
		this.mandatoryIndicator = mandatoryIndicator;
	}*/
	
	public String getIsCovered() {
		return isCovered;
	}
	public void setIsCovered(String isCovered) {
		this.isCovered = isCovered;
	}
	/*public String getFieldType() {
		return fieldType;
	}
	public void setFieldType(String fieldType) {
		this.fieldType = fieldType;
	}*/
	/*public Integer getProductId() {
		return productId;
	}
	public void setProductId(Integer productId) {
		this.productId = productId;
	}*/
	public String getCoverId() {
		return coverId;
	}
	public void setCoverId(String coverId) {
		this.coverId = coverId;
	}
	public RiskDetails getRiskDetails() {
		return riskDetails;
	}
	public void setRiskDetails(RiskDetails riskDetails) {
		this.riskDetails = riskDetails;
	}
	
}
