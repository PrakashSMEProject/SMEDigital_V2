package com.rsaame.pas.vo.app;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.rsaame.pas.vo.bus.PremiumVO;

public class Contents extends BaseVO implements Serializable, IFieldValue{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Short secId;
	private Short classCode;
	private BigDecimal cover;
	private String desc;
	private BigDecimal deductibles;
	private String contentDesc;
	private String contentADesc;
	
	private Integer coverCode;
	private Long coverId; 
	private Integer coverType;
	private Integer coverSubType;
	
	private Integer basicRiskCode;	
	
	private Integer riskCode;
	private Integer riskType;
	private Integer riskCat;
	private Long riskDtl;
	

	public Long getRiskDtl() {
		return riskDtl;
	}

	public void setRiskDtl(Long riskDtl) {
		this.riskDtl = riskDtl;
	}

	private Integer riskSubCat;
	private Long 	riskId;/* This id represents contents id updated after DB ops */
	
	private Date vsd;
	private PremiumVO premium;
	private String covDesc;
	private Integer coverOpted;/* Significance of this field will only be for coverCode > 1 */
	private Date setValidityStartDate;
	


	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "contentDesc".equals( fieldName ) ) fieldValue = getContentDesc();
		if( "contentADesc".equals( fieldName ) ) fieldValue = getContentADesc();
		if( "coverCode".equals( fieldName ) ) fieldValue = getCoverCode();
		if( "coverType".equals( fieldName ) ) fieldValue = getCoverType();
		if( "coverSubType".equals( fieldName ) ) fieldValue = getCoverSubType();
		if( "basicRiskCode".equals( fieldName ) ) fieldValue = getBasicRiskCode();
		if( "riskCode".equals( fieldName ) ) fieldValue = getRiskCode();
		if( "riskType".equals( fieldName ) ) fieldValue = getRiskType();
		if( "riskCat".equals( fieldName ) ) fieldValue = getRiskCat();
		if( "riskSubCat".equals( fieldName ) ) fieldValue = getRiskSubCat();
		if( "cover".equals( fieldName ) ) fieldValue = getCover();
		if( "desc".equals( fieldName ) ) fieldValue = getDesc();
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();
		if( "secId".equals( fieldName ) ) fieldValue = getSecId();
		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();
		if( "riskId".equals( fieldName ) ) fieldValue = getRiskId();
		if( "premium".equals( fieldName ) ) fieldValue = getPremium();
		if( "covDesc".equals( fieldName ) ) fieldValue = getCovDesc();
		if( "coverOpted".equals( fieldName ) ) fieldValue = getCoverOpted();
		if( "coverId".equals( fieldName ) ) fieldValue = getCoverId();
		return fieldValue;
	}

	public Date getSetValidityStartDate() {
		return setValidityStartDate;
	}


	public void setSetValidityStartDate(Date setValidityStartDate) {
		this.setValidityStartDate = setValidityStartDate;
	}
	public Long getCoverId() {
		return coverId;
	}

	public void setCoverId(Long coverId) {
		this.coverId = coverId;
	}
	public PremiumVO getPremium() {
		return premium;
	}

	public void setPremium(PremiumVO premium) {
		this.premium = premium;
	}

	public BigDecimal getCover() {
		return cover;
	}

	public void setCover(BigDecimal cover) {
		this.cover = cover;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public BigDecimal getDeductibles() {
		return deductibles;
	}

	public void setDeductibles(BigDecimal deductibles) {
		this.deductibles = deductibles;
	}
	
	/**
	 * @return the contentDesc
	 */
	public String getContentDesc() {
		return contentDesc;
	}

	/**
	 * @param contentDesc the contentDesc to set
	 */
	public void setContentDesc(String contentDesc) {
		this.contentDesc = contentDesc;
	}
	
	/**
	 * @return the contentADesc
	 */
	public String getContentADesc() {
		return contentADesc;
	}

	/**
	 * @param contentADesc the contentADesc to set
	 */
	public void setContentADesc(String contentADesc) {
		this.contentADesc = contentADesc;
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

	public Short getSecId() {
		return secId;
	}

	public void setSecId(Short secId) {
		this.secId = secId;
	}

	public Short getClassCode() {
		return classCode;
	}

	public void setClassCode(Short classCode) {
		this.classCode = classCode;
	}

	public Long getRiskId(){
		return riskId;
	}

	public void setRiskId( Long riskId ){
		this.riskId = riskId;
	}

	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}
	

	public String getCovDesc(){
		return covDesc;
	}

	public void setCovDesc( String covDesc ){
		this.covDesc = covDesc;
	}
	
	public Integer getCoverOpted(){
		return coverOpted;
	}

	public void setCoverOpted( Integer coverOpted ){
		this.coverOpted = coverOpted;
	}

	@Override
	public String toString(){
		return "Contents [secId=" + secId + ", classCode=" + classCode + ", cover=" + cover + ", desc=" + desc + ", deductibles=" + deductibles + ", contentDesc=" + contentDesc
				+ ", contentADesc=" + contentADesc + ", coverCode=" + coverCode + ", coverType=" + coverType + ", coverSubType=" + coverSubType + ", basicRiskCode="
				+ basicRiskCode + ", riskCode=" + riskCode + ", riskType=" + riskType + ", riskCat=" + riskCat + ", riskSubCat=" + riskSubCat + ", riskId=" + riskId + ", vsd="
				+ vsd + ", premium=" + premium + ", covDesc=" + covDesc + ", coverOpted=" + coverOpted + "]";
	}
	 
	
}
