/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.mindtree.ruc.cmn.utils.List;

/**
 * @author m1014644
 *
 */
public class ParContent extends BaseVO implements IFieldValue{

	
	private static final long serialVersionUID = 1L;
	private String contentDesc;
	private String contentADesc;
	
	private Integer coverCode;
	private Integer coverType;
	private Integer coverSubType;
	
	private Integer basicRiskCode;
	
	private Integer riskCode;
	private Integer riskType;
	private Integer riskCat;
	private Integer riskSubCat;
	
	private List<BigDecimal> deductibles =  new List<BigDecimal>(BigDecimal.class);
	
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
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();

		return fieldValue;
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
	 * @return the deductibles
	 */
	public List<BigDecimal> getDeductibles() {
		return deductibles;
	}

	/**
	 * @param deductibles the deductibles to set
	 */
	public void setDeductibles(List<BigDecimal> deductibles) {
		this.deductibles = deductibles;
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



	
}
