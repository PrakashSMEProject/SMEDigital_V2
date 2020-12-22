/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1018585
 *
 */
public class QuoteRportVO extends BaseVO {
	
	
	private String brokerName;
	private String insuredName;
	private String quoteType;
	private String quoteIssueMonth;
	private String quoteIssueYear;
	private Long quotationNumber;
	
	private Date quoteCreationDate;
	private String quotationStatus;
	private Long policyNumber;
	private Date policyEffectiveDate;
	private Date policyExpiryDate;
	
	private Double quotationPremium;
	private Double commission;
	
	private String userName;
	
	

	public String getBrokerName() {
		return brokerName;
	}



	public void setBrokerName(String brokerName) {
		this.brokerName = brokerName;
	}



	public String getInsuredName() {
		return insuredName;
	}



	public void setInsuredName(String insuredName) {
		this.insuredName = insuredName;
	}



	public String getQuoteType() {
		return quoteType;
	}



	public void setQuoteType(String quoteType) {
		this.quoteType = quoteType;
	}



	public String getQuoteIssueMonth() {
		return quoteIssueMonth;
	}



	public void setQuoteIssueMonth(String quoteIssueMonth) {
		this.quoteIssueMonth = quoteIssueMonth;
	}



	public String getQuoteIssueYear() {
		return quoteIssueYear;
	}



	public void setQuoteIssueYear(String quoteIssueYear) {
		this.quoteIssueYear = quoteIssueYear;
	}



	public Long getQuotationNumber() {
		return quotationNumber;
	}



	public void setQuotationNumber(Long quotationNumber) {
		this.quotationNumber = quotationNumber;
	}



	public Date getQuoteCreationDate() {
		return quoteCreationDate;
	}



	public void setQuoteCreationDate(Date quoteCreationDate) {
		this.quoteCreationDate = quoteCreationDate;
	}



	public String getQuotationStatus() {
		return quotationStatus;
	}



	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}



	public Long getPolicyNumber() {
		return policyNumber;
	}



	public void setPolicyNumber(Long policyNumber) {
		this.policyNumber = policyNumber;
	}



	public Date getPolicyEffectiveDate() {
		return policyEffectiveDate;
	}



	public void setPolicyEffectiveDate(Date policyEffectiveDate) {
		this.policyEffectiveDate = policyEffectiveDate;
	}



	public Date getPolicyExpiryDate() {
		return policyExpiryDate;
	}



	public void setPolicyExpiryDate(Date policyExpiryDate) {
		this.policyExpiryDate = policyExpiryDate;
	}



	public Double getQuotationPremium() {
		return quotationPremium;
	}



	public void setQuotationPremium(Double quotationPremium) {
		this.quotationPremium = quotationPremium;
	}



	public Double getCommission() {
		return commission;
	}



	public void setCommission(Double commission) {
		this.commission = commission;
	}



	public String getUserName() {
		return userName;
	}



	public void setUserName(String userName) {
		this.userName = userName;
	}



	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}

}
