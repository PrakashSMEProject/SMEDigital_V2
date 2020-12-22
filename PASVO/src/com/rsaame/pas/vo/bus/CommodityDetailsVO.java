/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1016996
 * 
 */
public class CommodityDetailsVO extends BaseVO {

    private static final long serialVersionUID = 1L;
    private Long serialNo;
    private Short modeOfTransit;
    private Long commodityId;
    /*private String voyageFrom;
    private String voyageTo;*/
    private Integer commodityType;
    private String goodsDescription;
    private BigDecimal siBasis;
    /*private Double deductible;*/
    private PremiumVO premium;
    private Boolean isToBeDeleted;
    private Integer index;

    

    public Long getCommodityId() {
        return commodityId;
    }

    public void setCommodityId(Long commodityId) {
        this.commodityId = commodityId;
    }

    public Long getSerialNo() {
        return serialNo;
    }

    public void setSerialNo(Long serialNo) {
        this.serialNo = serialNo;
    }

    public Short getModeOfTransit() {
	return modeOfTransit;
    }

    public void setModeOfTransit(Short modeOfTransit) {
	this.modeOfTransit = modeOfTransit;
    }

   /* public String getVoyageFrom() {
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
    }*/

    public Integer getCommodityType() {
	return commodityType;
    }

    public void setCommodityType(Integer commodityType) {
	this.commodityType = commodityType;
    }

    public String getGoodsDescription() {
	return goodsDescription;
    }

    public void setGoodsDescription(String goodsDescription) {
	this.goodsDescription = goodsDescription;
    }

    public BigDecimal getSiBasis() {
	return siBasis;
    }

    public void setSiBasis(BigDecimal siBasis) {
	this.siBasis = siBasis;
    }

    /*public Double getDeductible() {
	return deductible;
    }

    public void setDeductible(Double deductible) {
	this.deductible = deductible;
    }*/

    public static long getSerialversionuid() {
	return serialVersionUID;
    }

    public PremiumVO getPremium() {
	return premium;
    }

    public void setPremium(PremiumVO premium) {
	this.premium = premium;
    }

    public Boolean getIsToBeDeleted() {
	return isToBeDeleted;
    }

    public void setIsToBeDeleted(Boolean isToBeDeleted) {
	this.isToBeDeleted = isToBeDeleted;
    }

    public Integer getIndex() {
	return index;
    }

    public void setIndex(Integer index) {
	this.index = index;
    }
    
   
    @Override
    public String toString() {
	return "CommodityDetailsVO [serialNo=" + serialNo + ", modeOfTransit="
		+ modeOfTransit + ", commodityId=" + commodityId
		+ ", commodityType=" + commodityType + ", goodsDescription="
		+ goodsDescription + ", siBasis=" + siBasis + ", premium="
		+ premium + ", isToBeDeleted=" + isToBeDeleted + ", index="
		+ index + "]";
    }

    @Override
    public int hashCode() {
	final int prime = 31;
	int result = 1;
	result = prime * result
		+ ((commodityId == null) ? 0 : commodityId.hashCode());
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
	CommodityDetailsVO other = (CommodityDetailsVO) obj;
	if (commodityId == null) {
	    if (other.commodityId != null)
		return false;
	} else if (!commodityId.equals(other.commodityId))
	    return false;
	return true;
    }

     public Object getFieldValue(String fieldName) {
	Object fieldValue = null;

	if ("modeOfTransit".equals(fieldName))
	    fieldValue = getModeOfTransit();
	/*if ("voyageFrom".equals(fieldName))
	    fieldValue = getVoyageFrom();
	if ("voyageTo".equals(fieldName))
	    fieldValue = getVoyageTo();*/
	if ("commodityType".equals(fieldName))
	    fieldValue = getCommodityType();
	if ("goodsDescription".equals(fieldName))
	    fieldValue = getGoodsDescription();
	if ("siBasis".equals(fieldName))
	    fieldValue = getSiBasis();
	/*if ("deductible".equals(fieldName))
	    fieldValue = getDeductible();*/
	if ("declarationId".equals(fieldName))
	    fieldValue = getSerialNo();
	if ("isToBeDeleted".equals(fieldName))
	    fieldValue = getIsToBeDeleted();
	if ("index".equals(fieldName))
	    fieldValue = getIndex();
	if ("commodityId".equals(fieldName))
	    fieldValue = getCommodityId();

	return fieldValue;

    }

}
