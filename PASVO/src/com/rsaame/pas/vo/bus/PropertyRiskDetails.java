package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 *  
 * @author m1014644
 *
 */

public class PropertyRiskDetails extends BaseVO implements IFieldValue {

	private static final long serialVersionUID = 1L;
	private Long buildingId;
	private Long coverId;
	
	private Date setValidityStartDate;
	
	private Double cover;
	private String desc;
	private Double deductibles;
	
	private Integer coverCode;
	private Integer coverType;
	private Integer coverSubType;
	
	private Integer basicRiskCode;
	private PremiumVO premium;
	
	public PremiumVO getPremium() {
		return premium;
	}

	public void setPremium(PremiumVO premium) {
		this.premium = premium;
	}

	// add riskCode, riskType, riskCat, riskSubCat
	private Integer riskCode;
	private Integer riskType;
	private Integer riskCat;
	private Integer riskSubCat;
	private Integer coverOpted;/* Significance of this field will only be for coverCode > 1 */
	
	
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "buildingId".equals( fieldName ) ) fieldValue = getBuildingId();
		if( "coverId".equals( fieldName ) ) fieldValue = getCoverId();
		if( "setBldValidityStartDate".equals( fieldName ) ) fieldValue = getSetValidityStartDate();
		if( "cover".equals( fieldName ) ) fieldValue = getCover();
		if( "desc".equals( fieldName ) ) fieldValue = getDesc();
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();
		if( "coverCode".equals( fieldName ) ) fieldValue = getCoverCode();
		if( "coverType".equals( fieldName ) ) fieldValue = getCoverType();
		if( "coverSubType".equals( fieldName ) ) fieldValue = getCoverSubType();
		if( "basicRiskCode".equals( fieldName ) ) fieldValue = getBasicRiskCode();
		if( "riskCode".equals( fieldName ) ) fieldValue = getRiskCode();
		if( "riskType".equals( fieldName ) ) fieldValue = getRiskType();
		if( "riskCat".equals( fieldName ) ) fieldValue = getRiskCat();
		if( "riskSubCat".equals( fieldName ) ) fieldValue = getRiskSubCat();
		if( "coverOpted".equals( fieldName ) ) fieldValue = getCoverOpted();

		return fieldValue;
	}

	public Double getCover() {
		return cover;
	}

	public void setCover(Double cover) {
		this.cover = cover;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public Double getDeductibles() {
		return deductibles;
	}

	public void setDeductibles(Double deductibles) {
		this.deductibles = deductibles;
	}


	/**
	 * @return the buildingId
	 */
	public Long getBuildingId() {
		return buildingId;
	}

	/**
	 * @param buildingId the buildingId to set
	 */
	public void setBuildingId(Long buildingId) {
		this.buildingId = buildingId;
	}

	/**
	 * @return the coverId
	 */
	public Long getCoverId() {
		return coverId;
	}

	/**
	 * @param coverId the coverId to set
	 */
	public void setCoverId(Long coverId) {
		this.coverId = coverId;
	}

	/**
	 * @return the coverCode
	 */
	public Integer getCoverCode() {
		return coverCode;
	}

	/**
	 * @param coverCode the coverCode to set
	 */
	public void setCoverCode(Integer coverCode) {
		this.coverCode = coverCode;
	}

	/**
	 * @return the coverType
	 */
	public Integer getCoverType() {
		return coverType;
	}

	/**
	 * @param coverType the coverType to set
	 */
	public void setCoverType(Integer coverType) {
		this.coverType = coverType;
	}

	/**
	 * @return the coverSubType
	 */
	public Integer getCoverSubType() {
		return coverSubType;
	}

	/**
	 * @param coverSubType the coverSubType to set
	 */
	public void setCoverSubType(Integer coverSubType) {
		this.coverSubType = coverSubType;
	}

	/**
	 * @return the basicRiskCode
	 */
	public Integer getBasicRiskCode() {
		return basicRiskCode;
	}

	/**
	 * @param basicRiskCode the basicRiskCode to set
	 */
	public void setBasicRiskCode(Integer basicRiskCode) {
		this.basicRiskCode = basicRiskCode;
	}

	/**
	 * @return the riskCode
	 */
	public Integer getRiskCode() {
		return riskCode;
	}

	/**
	 * @param riskCode the riskCode to set
	 */
	public void setRiskCode(Integer riskCode) {
		this.riskCode = riskCode;
	}

	/**
	 * @return the riskType
	 */
	public Integer getRiskType() {
		return riskType;
	}

	/**
	 * @param riskType the riskType to set
	 */
	public void setRiskType(Integer riskType) {
		this.riskType = riskType;
	}

	/**
	 * @return the riskCat
	 */
	public Integer getRiskCat() {
		return riskCat;
	}

	/**
	 * @param riskCat the riskCat to set
	 */
	public void setRiskCat(Integer riskCat) {
		this.riskCat = riskCat;
	}

	/**
	 * @return the riskSubCat
	 */
	public Integer getRiskSubCat() {
		return riskSubCat;
	}

	/**
	 * @param riskSubCat the riskSubCat to set
	 */
	public void setRiskSubCat(Integer riskSubCat) {
		this.riskSubCat = riskSubCat;
	}

	/**
	 * @return the setValidityStartDate
	 */
	public Date getSetValidityStartDate() {
		return setValidityStartDate;
	}

	/**
	 * @param setValidityStartDate the setValidityStartDate to set
	 */
	public void setSetValidityStartDate(Date setValidityStartDate) {
		this.setValidityStartDate = setValidityStartDate;
	}

	public Integer getCoverOpted(){
		return coverOpted;
	}

	public void setCoverOpted( Integer coverOpted ){
		this.coverOpted = coverOpted;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "PropertyRiskDetails [buildingId=" + buildingId + ", coverId="
				+ coverId + ", setValidityStartDate=" + setValidityStartDate
				+ ", cover=" + cover + ", desc=" + desc + ", deductibles="
				+ deductibles + ", coverCode=" + coverCode + ", coverType="
				+ coverType + ", coverSubType=" + coverSubType
				+ ", basicRiskCode=" + basicRiskCode + ", riskCode=" + riskCode
				+ ", riskType=" + riskType + ", riskCat=" + riskCat
				+ ", riskSubCat=" + riskSubCat + "]";
	}



	
	
}
