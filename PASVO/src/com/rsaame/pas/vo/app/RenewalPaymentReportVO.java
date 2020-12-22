package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class RenewalPaymentReportVO extends BaseVO{

	private String insuranceType;
	private Long policyNumber;
	private String name;
	private Date renewalDate;
	private Date payDate;
	private Double amount;
	private Long recieptNumber;
	private String transactionNumber;
	private String status;
	private String quotationStatus;
	private String airmiles;
	
	
	public String getInsuranceType() {
		return insuranceType;
	}


	public void setInsuranceType(String insuranceType) {
		this.insuranceType = insuranceType;
	}


	public Long getPolicyNumber() {
		return policyNumber;
	}


	public void setPolicyNumber(Long policyNumber) {
		this.policyNumber = policyNumber;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public Date getRenewalDate() {
		return renewalDate;
	}


	public void setRenewalDate(Date renewalDate) {
		this.renewalDate = renewalDate;
	}


	public Date getPayDate() {
		return payDate;
	}


	public void setPayDate(Date payDate) {
		this.payDate = payDate;
	}


	public Double getAmount() {
		return amount;
	}


	public void setAmount(Double amount) {
		this.amount = amount;
	}


	public Long getRecieptNumber() {
		return recieptNumber;
	}


	public void setRecieptNumber(Long recieptNumber) {
		this.recieptNumber = recieptNumber;
	}


	public String getTransactionNumber() {
		return transactionNumber;
	}


	public void setTransactionNumber(String transactionNumber) {
		this.transactionNumber = transactionNumber;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


	public String getQuotationStatus() {
		return quotationStatus;
	}


	public void setQuotationStatus(String quotationStatus) {
		this.quotationStatus = quotationStatus;
	}


	public String getAirmiles() {
		return airmiles;
	}


	public void setAirmiles(String airmiles) {
		this.airmiles = airmiles;
	}


	@Override
	public Object getFieldValue(String fieldName) {
		// TODO Auto-generated method stub
		return null;
	}
}
