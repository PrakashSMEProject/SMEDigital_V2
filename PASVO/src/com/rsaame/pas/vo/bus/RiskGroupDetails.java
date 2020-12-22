//
//
//  Generated by StarUML(tm) Java Add-In
//
//  @ Project : Untitled
//  @ File Name : RiskGroupDetails.java
//  @ Date : 12/20/2011
//  @ Author : 
//
//

package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

public abstract class RiskGroupDetails extends BaseVO{
	private static final long serialVersionUID = 1L;
	
	private UWQuestionsVO uwQuestions;
	private UWDetails uwDetails;
	
	private Long basicRiskId;
	private Boolean isToBeDeleted;
	
	private PremiumVO premium;
	private Double commission;

	private Integer basicRiskcode;
	private Integer riskCode;
	private Integer riskType;
	private Integer riskCategory;
	private Integer riskSubCategory;

	private Integer classCode;
	private Long policyId;

	private double sumInsured;

	private ReferralVO referralVO;

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		block: {
			if( "uwQuestions".equals( fieldName ) ){ fieldValue = getUwQuestions(); break block; }
			if( "uwDetails".equals( fieldName ) ){  fieldValue = getUwDetails(); break block; }
			if( "premium".equals( fieldName ) ){  fieldValue = getPremium(); break block; }
			if( "commission".equals( fieldName ) ){  fieldValue = getCommission(); break block; }
			if( "basicRiskcode".equals( fieldName ) ){  fieldValue = getBasicRiskcode(); break block; }
			if( "riskCode".equals( fieldName ) ){  fieldValue = getBasicRiskcode(); break block; }
			if( "riskType".equals( fieldName ) ){  fieldValue = getRiskType(); break block; }
			if( "riskCategory".equals( fieldName ) ){  fieldValue = getRiskCategory(); break block; }
			if( "riskSubCategory".equals( fieldName ) ){  fieldValue = getRiskSubCategory(); break block; }
			if( "classCode".equals( fieldName ) ){  fieldValue = getClassCode(); break block; }
			if( "policyId".equals( fieldName ) ){  fieldValue = getPolicyId(); break block; }
			if( "sumInsured".equals( fieldName ) ){  fieldValue = getSumInsured(); break block; }
			if( "referralVO".equals( fieldName ) ){  fieldValue = getReferralVO(); break block; }
			if( "isToBeDeleted".equals( fieldName ) ){  fieldValue = getIsToBeDeleted(); break block; }
		}
		return fieldValue;
	}

	/**
	 * @return the sumInsured
	 */
	public double getSumInsured(){
		return sumInsured;
	}

	/**
	 * @param sumInsured the sumInsured to set
	 */
	public void setSumInsured( double sumInsured ){
		this.sumInsured = sumInsured;
	}

	public UWQuestionsVO getUwQuestions(){
		return uwQuestions;
	}

	public void setUwQuestions( UWQuestionsVO uwQuestions ){
		this.uwQuestions = uwQuestions;
	}

	public UWDetails getUwDetails(){
		return uwDetails;
	}

	public void setUwDetails( UWDetails uwDetails ){
		this.uwDetails = uwDetails;
	}

	public PremiumVO getPremium(){
		return premium;
	}

	public void setPremium( PremiumVO premium ){
		this.premium = premium;
	}

	public Double getCommission(){
		return commission;
	}

	public void setCommission( Double commission ){
		this.commission = commission;
	}

	/**
	 * @return the basicRiskcode
	 */
	public Integer getBasicRiskcode(){
		return basicRiskcode;
	}

	/**
	 * @param basicRiskcode the basicRiskcode to set
	 */
	public void setBasicRiskcode( Integer basicRiskcode ){
		this.basicRiskcode = basicRiskcode;
	}

	/**
	 * @return the riskCode
	 */
	public Integer getRiskCode(){
		return riskCode;
	}

	/**
	 * @param riskCode the riskCode to set
	 */
	public void setRiskCode( Integer riskCode ){
		this.riskCode = riskCode;
	}

	/**
	 * @return the riskType
	 */
	public Integer getRiskType(){
		return riskType;
	}

	/**
	 * @param riskType the riskType to set
	 */
	public void setRiskType( Integer riskType ){
		this.riskType = riskType;
	}

	/**
	 * @return the riskCategory
	 */
	public Integer getRiskCategory(){
		return riskCategory;
	}

	/**
	 * @param riskCategory the riskCategory to set
	 */
	public void setRiskCategory( Integer riskCategory ){
		this.riskCategory = riskCategory;
	}

	/**
	 * @return the riskSubCategory
	 */
	public Integer getRiskSubCategory(){
		return riskSubCategory;
	}

	/**
	 * @param riskSubCategory the riskSubCategory to set
	 */
	public void setRiskSubCategory( Integer riskSubCategory ){
		this.riskSubCategory = riskSubCategory;
	}

	/**
	 * @return the classCode
	 */
	public Integer getClassCode(){
		return classCode;
	}

	/**
	 * @param classCode the classCode to set
	 */
	public void setClassCode( Integer classCode ){
		this.classCode = classCode;
	}

	/**
	 * @return the policyId
	 */
	public Long getPolicyId(){
		return policyId;
	}

	/**
	 * @param policyId the policyId to set
	 */
	public void setPolicyId( Long policyId ){
		this.policyId = policyId;
	}

	/**
	 * @return the referralVO
	 */
	public ReferralVO getReferralVO(){
		return referralVO;
	}

	/**
	 * @param referralVO the referralVO to set
	 */
	public void setReferralVO( ReferralVO referralVO ){
		this.referralVO = referralVO;
	}

	public Boolean getIsToBeDeleted(){
		return isToBeDeleted;
	}

	public void setIsToBeDeleted( Boolean isToBeDeleted ){
		this.isToBeDeleted = isToBeDeleted;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "RiskGroupDetails [uwQuestions=" + uwQuestions + ", uwDetails=" + uwDetails + ", premium=" + premium + ", commission=" + commission + ", basicRiskcode="
				+ basicRiskcode + ", riskCode=" + riskCode + ", riskType=" + riskType + ", riskCategory=" + riskCategory + ", riskSubCategory=" + riskSubCategory + ", classCode="
				+ classCode + ", policyId=" + policyId + ", sumInsured=" + sumInsured + ", referralVO=" + referralVO + "]";
	}

	public Long getBasicRiskId(){
		return basicRiskId;
	}

	public void setBasicRiskId( Long basicRiskId ){
		this.basicRiskId = basicRiskId;
	}

}