package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

public class ReferralListVO extends BaseVO{
	private static final long serialVersionUID = 1L;
	private List<ReferralVO> referrals;
	private boolean isValidationReferral;
	private TaskVO taskVO; //Added during PHASE -3
	private PremiumVO premiumVO; // Added specifically for refferal to be used only for referral in rule mappers.
	private String referalType;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "referrals".equals( fieldName ) ) fieldValue = getReferrals();
		if( "isValidationReferral".equals( fieldName ) ) fieldValue = getIsValidationReferral();
		if ("taskVO".equals(fieldName))fieldValue = getTaskVO();
		if ("referalType".equals(fieldName))fieldValue = getReferalType();

		return fieldValue;
	}

	/**
	 * @return the referrals
	 */
	public List<ReferralVO> getReferrals(){
		return referrals;
	}

	/**
	 * @param referrals the referrals to set
	 */
	public void setReferrals( List<ReferralVO> referrals ){
		this.referrals = referrals;
	}
	
	/**
	 * @return the taskVO
	 */
	public TaskVO getTaskVO() {
		return taskVO;
	}

	/**
	 * @param taskVO the taskVO to set
	 */
	public void setTaskVO(TaskVO taskVO) {
		this.taskVO = taskVO;
	}


	/**
	 * @return whether it's a validation referral.
	 */
	public boolean getIsValidationReferral(){
		return isValidationReferral;
	}

	/**
	 * @param isValidationReferral
	 */
	public void setIsValidationReferral( boolean isValidationReferral ){
		this.isValidationReferral = isValidationReferral;
	}

	/**
	 * @return the premiumVO
	 */
	public PremiumVO getPremiumVO(){
		return premiumVO;
	}

	/**
	 * @param premiumVO the premiumVO to set
	 */
	public void setPremiumVO( PremiumVO premiumVO ){
		this.premiumVO = premiumVO;
	}

	public String getReferalType() {
		return referalType;
	}

	public void setReferalType(String referalType) {
		this.referalType = referalType;
	}

}
