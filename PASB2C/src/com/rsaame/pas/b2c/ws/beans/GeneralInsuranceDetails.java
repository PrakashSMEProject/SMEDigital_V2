package com.rsaame.pas.b2c.ws.beans;


public class GeneralInsuranceDetails {
	
	
	private InsuredDetails insured;
	/*private String jurisdiction;*/
	/*private AdditionalInsuredInfoVO additionalInfo;*/
	private SourceOfBusiness sourceOfBus;
	/*private ClaimsSummaryVO claimsHistory;*/
	/*private Boolean isChannelChanged;*/
	/*private String newCustomer;*/
	/*private String customerSaveReq;*/
	private UWQuestionDetails questionsVO;
	// Phasee/Home/travel changes:
	//Following fields have to be stored in t_mas_cash_customer table. 
	//So moving them to GeneralInfoVO to avoid the cash customer mapping issue in BaseSaveOperation
	/*private Integer intAccExecCode;*/
	/*private Integer extAccExecCode;*/
	
	
	public InsuredDetails getInsured() {
		return insured;
	}
	public SourceOfBusiness getSourceOfBus() {
		return sourceOfBus;
	}
	public void setSourceOfBus(SourceOfBusiness sourceOfBus) {
		this.sourceOfBus = sourceOfBus;
	}
	public void setInsured(InsuredDetails insured) {
		this.insured = insured;
	}
	public UWQuestionDetails getQuestionsVO() {
		return questionsVO;
	}
	public void setQuestionsVO(UWQuestionDetails questionsVO) {
		this.questionsVO = questionsVO;
	}

}
