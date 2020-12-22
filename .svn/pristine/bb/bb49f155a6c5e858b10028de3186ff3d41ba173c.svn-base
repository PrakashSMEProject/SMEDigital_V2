/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;

/**
 * @author m1016996
 * 
 */
public class GoodsInTransitVO extends RiskGroupDetails {

    private static final long serialVersionUID = 1L;
    private Long declarationId;
    private Long singleTransitLimit;
    private Long estimatedAnnualCarryValue;
    
  /*private Integer settlmentCurrency; private BigDecimal exchangeRate;
    private Integer settlingAgent; private Integer settlingLocation; */
    
    private String voyageFrom;
    private String voyageTo;
    private Double deductible;
    private java.util.List<CommodityDetailsVO> commodityDtls = new com.mindtree.ruc.cmn.utils.List<CommodityDetailsVO>(
	    CommodityDetailsVO.class);

        
    public Long getDeclarationId() {
        return declarationId;
    }

    public void setDeclarationId(Long declarationId) {
        this.declarationId = declarationId;
    }

    public Long getSingleTransitLimit() {
	return singleTransitLimit;
    }

    public void setSingleTransitLimit(Long singleTransitLimit) {
	this.singleTransitLimit = singleTransitLimit;
    }

    public Long getEstimatedAnnualCarryValue() {
	return estimatedAnnualCarryValue;
    }

    public void setEstimatedAnnualCarryValue(Long estimatedAnnualCarryValue) {
	this.estimatedAnnualCarryValue = estimatedAnnualCarryValue;
    }

    /*
     * public Integer getSettlmentCurrency() { return settlmentCurrency; }
     * 
     * public void setSettlmentCurrency(Integer settlmentCurrency) {
     * this.settlmentCurrency = settlmentCurrency; }
     * 
     * public BigDecimal getExchangeRate() { return exchangeRate; }
     * 
     * public void setExchangeRate(BigDecimal exchangeRate) { this.exchangeRate
     * = exchangeRate; }
     * 
     * public Integer getSettlingAgent() { return settlingAgent; }
     * 
     * public void setSettlingAgent(Integer settlingAgent) { this.settlingAgent
     * = settlingAgent; }
     * 
     * public Integer getSettlingLocation() { return settlingLocation; }
     * 
     * public void setSettlingLocation(Integer settlingLocation) {
     * this.settlingLocation = settlingLocation; }
     */

    public String getVoyageFrom() {
	return voyageFrom;
    }

    public void setVoyageFrom(String voyageFrom) {
	this.voyageFrom = voyageFrom;
    }

    public String getVoyageTo() {
	return voyageTo;
    }

    public void setVoyageTo(String voyageTo) {
	this.voyageTo = voyageTo;
    }

    public Double getDeductible() {
	return deductible;
    }

    public void setDeductible(Double deductible) {
	this.deductible = deductible;
    }

    public java.util.List<CommodityDetailsVO> getCommodityDtls() {
	return commodityDtls;
    }

    public void setCommodityDtls(
	    java.util.List<CommodityDetailsVO> commodityDtls) {
	this.commodityDtls = commodityDtls;
    }

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public Object getFieldValue(String fieldName) {
	Object fieldValue = null;

	if ("singleTransitLimit".equals(fieldName))
	    fieldValue = getSingleTransitLimit();
	if ("estimatedAnnualCarryValue".equals(fieldName))
	    fieldValue = getEstimatedAnnualCarryValue();
	/*
	 * if ("settlmentCurrency".equals(fieldName)) fieldValue =
	 * getSettlmentCurrency(); if ("exchangeRate".equals(fieldName))
	 * fieldValue = getExchangeRate(); if
	 * ("settlingAgent".equals(fieldName)) fieldValue = getSettlingAgent();
	 * if ("settlingLocation".equals(fieldName)) fieldValue =
	 * getSettlingLocation();
	 */
	if ("voyageFrom".equals(fieldName))
	    fieldValue = getVoyageFrom();
	if ("voyageTo".equals(fieldName))
	    fieldValue = getVoyageTo();
	if ("deductible".equals(fieldName))
	    fieldValue = getDeductible();
	if ("commodityDtls".equals(fieldName))
	    fieldValue = getCommodityDtls();
	
	if ("declarationId".equals(fieldName))
	    fieldValue = getDeclarationId();
	
	return fieldValue;
    }

    @Override
    public String toString() {
	return "GoodsInTransitVO [declarationId=" + declarationId
		+ ", singleTransitLimit=" + singleTransitLimit
		+ ", estimatedAnnualCarryValue=" + estimatedAnnualCarryValue
		+ ", voyageFrom=" + voyageFrom + ", voyageTo=" + voyageTo
		+ ", deductible=" + deductible + ", commodityDtls="
		+ commodityDtls + "]";
    }

}
