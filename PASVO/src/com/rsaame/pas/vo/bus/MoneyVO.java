package com.rsaame.pas.vo.bus;

import java.util.Date;
import java.util.List;

import com.rsaame.pas.vo.app.Contents;


public class MoneyVO extends RiskGroupDetails {
	
	private static final long serialVersionUID = 1L;
	private java.util.List< SumInsuredVO> sumInsuredDets =  new com.mindtree.ruc.cmn.utils.List<SumInsuredVO>(SumInsuredVO.class);
	private java.util.List< SafeVO> safeDetails =  new com.mindtree.ruc.cmn.utils.List<SafeVO>(SafeVO.class);
	private Boolean cashInResidence;
	/* Does locked Safe outside business hours exceeds AED 200,000  */
	private Boolean excessCashInSafe;
	private java.util.List<CashResidenceVO> cashResDetails = new com.mindtree.ruc.cmn.utils.List<CashResidenceVO>(CashResidenceVO.class);
	private  Date  validityStartDate;
	private  List<Contents> contentsList = new com.mindtree.ruc.cmn.utils.List<Contents>(Contents.class);
	private PremiumVO cashResidencePremium;  
	private Boolean isCashResNotSelected;
	
	public PremiumVO getCashResidencePremium() {
		return cashResidencePremium;
	}



	public void setCashResidencePremium(PremiumVO cashResidencePremium) {
		this.cashResidencePremium = cashResidencePremium;
	}



	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "sumInsuredDets".equals( fieldName ) ) fieldValue = getSumInsuredDets();
		if( "safeDetails".equals( fieldName ) ) fieldValue = getSafeDetails();
		if( "cashInResidence".equals( fieldName ) ) fieldValue = getCashInResidence();
		if( "cashResDetails".equals( fieldName ) ) fieldValue = getCashResDetails();
		if( "validityStartDate".equals( fieldName ) ) fieldValue = getValidityStartDate();
		if( "cashResidencePremium".equals( fieldName ) ) fieldValue = getCashResidencePremium();
		

		return fieldValue;
	}



	/**
	 * @return the sumInsuredDets
	 */
	public java.util.List<SumInsuredVO> getSumInsuredDets() {
		return sumInsuredDets;
	}



	/**
	 * @param sumInsuredDets the sumInsuredDets to set
	 */
	public void setSumInsuredDets(java.util.List<SumInsuredVO> sumInsuredDets) {
		this.sumInsuredDets = sumInsuredDets;
	}



	/**
	 * @return the safeDetails
	 */
	public java.util.List<SafeVO> getSafeDetails() {
		return safeDetails;
	}



	/**
	 * @param safeDetails the safeDetails to set
	 */
	public void setSafeDetails(java.util.List<SafeVO> safeDetails) {
		this.safeDetails = safeDetails;
	}



	/**
	 * @return the cashInResidence
	 */
	public Boolean getCashInResidence() {
		return cashInResidence;
	}



	/**
	 * @param cashInResidence the cashInResidence to set
	 */
	public void setCashInResidence(Boolean cashInResidence) {
		this.cashInResidence = cashInResidence;
	}



	/**
	 * @return the cashResDetails
	 */
	public java.util.List<CashResidenceVO> getCashResDetails() {
		return cashResDetails;
	}



	/**
	 * @param cashResDetails the cashResDetails to set
	 */
	public void setCashResDetails(java.util.List<CashResidenceVO> cashResDetails) {
		this.cashResDetails = cashResDetails;
	}



	/**
	 * @return the excessCashInSafe
	 */
	public Boolean getExcessCashInSafe() {
		return excessCashInSafe;
	}



	/**
	 * @param excessCashInSafe the excessCashInSafe to set
	 */
	public void setExcessCashInSafe(Boolean excessCashInSafe) {
		this.excessCashInSafe = excessCashInSafe;
	}



	/**
	 * @return the validityStartDate
	 */
	public Date getValidityStartDate(){
		return validityStartDate;
	}



	/**
	 * @param validityStartDate the validityStartDate to set
	 */
	public void setValidityStartDate( Date validityStartDate ){
		this.validityStartDate = validityStartDate;
	}



	public List<Contents> getContentsList() {
		return contentsList;
	}



	public void setContentsList(List<Contents> contentsList) {
		this.contentsList = contentsList;
	}
	
	public Boolean getIsCashResNotSelected() {
		return isCashResNotSelected;
	}



	public void setIsCashResNotSelected(Boolean isCashResNotSelected) {
		this.isCashResNotSelected = isCashResNotSelected;
	}



	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "MoneyVO [sumInsuredDets=" + sumInsuredDets + ", safeDetails="
				+ safeDetails + ", cashInResidence=" + cashInResidence
				+ ", excessCashInSafe=" + excessCashInSafe
				+ ", cashResDetails=" + cashResDetails 
				+ ", validityStartDate=" + validityStartDate+"]";
	}





	
}
