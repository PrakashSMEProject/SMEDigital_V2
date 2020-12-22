package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

public class CopyQuoteVO extends BaseVO implements IFieldValue{
	private static final long serialVersionUID = 1L;

	private Long insuredId;
	private Long polLinkingId;
	private Integer userId;
	private Integer policyCode;
	private Integer locationCode;
	private Long deletePolLinkingId;
	private Long newQuoteNo;
	private Long newPolicyNo;
	private Long newEndtId;
	private Long newPolLinkingId;
	private Boolean isNewCustomer;
	// added for Home/Travel
	private Long deletePolicyId;
	private Long polPolicyId;
	private String polType;


	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "insuredId".equals( fieldName ) ) fieldValue = getInsuredId();
		if( "polLinkingId".equals( fieldName ) ) fieldValue = getPolLinkingId();
		if( "userId".equals( fieldName ) ) fieldValue = getUserId();
		if( "policyCode".equals( fieldName ) ) fieldValue = getPolicyCode();
		if( "locationCode".equals( fieldName ) ) fieldValue = getLocationCode();
		if( "deletePolLinkingId".equals( fieldName ) ) fieldValue = getDeletePolLinkingId();
		if( "newQuoteNo".equals( fieldName ) ) fieldValue = getNewQuoteNo();
		if( "newPolicyNo".equals( fieldName ) ) fieldValue = getNewPolicyNo();
		if( "newEndtId".equals( fieldName ) ) fieldValue = getNewEndtId();
		if( "deletePolicyId".equals( fieldName ) ) fieldValue = getDeletePolicyId();
		if( "polPolicyId".equals( fieldName ) ) fieldValue = getPolPolicyId();
		if( "polType".equals( fieldName ) ) fieldValue = getPolType();

		return fieldValue;
	}

	/**
	 * @return the insuredId
	 */
	public Long getInsuredId(){
		return insuredId;
	}

	/**
	 * @param insuredId the insuredId to set
	 */
	public void setInsuredId( Long insuredId ){
		this.insuredId = insuredId;
	}

	/**
	 * @return the polLinkingId
	 */
	public Long getPolLinkingId(){
		return polLinkingId;
	}

	/**
	 * @param polLinkingId the polLinkingId to set
	 */
	public void setPolLinkingId( Long polLinkingId ){
		this.polLinkingId = polLinkingId;
	}

	/**
	 * @return the userId
	 */
	public Integer getUserId(){
		return userId;
	}

	/**
	 * @param userId the userId to set
	 */
	public void setUserId( Integer userId ){
		this.userId = userId;
	}

	/**
	 * @return the policyCode
	 */
	public Integer getPolicyCode(){
		return policyCode;
	}

	/**
	 * @param policyCode the policyCode to set
	 */
	public void setPolicyCode( Integer policyCode ){
		this.policyCode = policyCode;
	}

	/**
	 * @return the locationCode
	 */
	public Integer getLocationCode(){
		return locationCode;
	}

	/**
	 * @param locationCode the locationCode to set
	 */
	public void setLocationCode( Integer locationCode ){
		this.locationCode = locationCode;
	}

	/**
	 * @return the deletePolLinkingId
	 */
	public Long getDeletePolLinkingId(){
		return deletePolLinkingId;
	}

	/**
	 * @param deletePolLinkingId the deletePolLinkingId to set
	 */
	public void setDeletePolLinkingId( Long deletePolLinkingId ){
		this.deletePolLinkingId = deletePolLinkingId;
	}

	/**
	 * @return the newQuoteNo
	 */
	public Long getNewQuoteNo(){
		return newQuoteNo;
	}

	/**
	 * @param newQuoteNo the newQuoteNo to set
	 */
	public void setNewQuoteNo( Long newQuoteNo ){
		this.newQuoteNo = newQuoteNo;
	}

	/**
	 * @return the newPolicyNo
	 */
	public Long getNewPolicyNo(){
		return newPolicyNo;
	}

	/**
	 * @param newPolicyNo the newPolicyNo to set
	 */
	public void setNewPolicyNo( Long newPolicyNo ){
		this.newPolicyNo = newPolicyNo;
	}

	/**
	 * @return the newEndtId
	 */
	public Long getNewEndtId(){
		return newEndtId;
	}

	/**
	 * @param newEndtId the newEndtId to set
	 */
	public void setNewEndtId( Long newEndtId ){
		this.newEndtId = newEndtId;
	}

	/**
	 * @return the newPolLinkingId
	 */
	public Long getNewPolLinkingId(){
		return newPolLinkingId;
	}

	/**
	 * @param newPolLinkingId the newPolLinkingId to set
	 */
	public void setNewPolLinkingId( Long newPolLinkingId ){
		this.newPolLinkingId = newPolLinkingId;
	}

	public Boolean getIsNewCustomer() {
		return isNewCustomer;
	}

	public void setIsNewCustomer(Boolean isNewCustomer) {
		this.isNewCustomer = isNewCustomer;
	}
	public Long getDeletePolicyId(){
		return deletePolicyId;
	}

	public void setDeletePolicyId( Long deletePolicyId ){
		this.deletePolicyId = deletePolicyId;
	}

	public Long getPolPolicyId(){
		return polPolicyId;
	}

	public void setPolPolicyId( Long polPolicyId ){
		this.polPolicyId = polPolicyId;
	}
	public String getPolType(){
		return polType;
	}

	public void setPolType( String polType ){
		this.polType = polType;
	}

}
