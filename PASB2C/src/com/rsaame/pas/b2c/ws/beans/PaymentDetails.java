package com.rsaame.pas.b2c.ws.beans;

import java.util.Date;

public class PaymentDetails {
	
	private String paymentMode;
	private Byte payModeCode;
	private Double premium; 
	private Long chequeNo;
	private Date chequeDt;
	private Integer bankCode;
	private Integer creditCardNo;
	private Date expiryDate;
	private Double amount;
	//private boolean isPaymentDone;
	
	
	public String getPaymentMode() {
		return paymentMode;
	}
	public void setPaymentMode(String paymentMode) {
		this.paymentMode = paymentMode;
	}
	public Byte getPayModeCode() {
		return payModeCode;
	}
	public void setPayModeCode(Byte payModeCode) {
		this.payModeCode = payModeCode;
	}
	public Double getPremium() {
		return premium;
	}
	public void setPremium(Double premium) {
		this.premium = premium;
	}
	public Long getChequeNo() {
		return chequeNo;
	}
	public void setChequeNo(Long chequeNo) {
		this.chequeNo = chequeNo;
	}
	public Date getChequeDt() {
		return chequeDt;
	}
	public void setChequeDt(Date chequeDt) {
		this.chequeDt = chequeDt;
	}
	public Integer getBankCode() {
		return bankCode;
	}
	public void setBankCode(Integer bankCode) {
		this.bankCode = bankCode;
	}
	public Integer getCreditCardNo() {
		return creditCardNo;
	}
	public void setCreditCardNo(Integer creditCardNo) {
		this.creditCardNo = creditCardNo;
	}
	public Date getExpiryDate() {
		return expiryDate;
	}
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
	public Double getAmount() {
		return amount;
	}
	public void setAmount(Double amount) {
		this.amount = amount;
	}
	/*public boolean isPaymentDone() {
		return isPaymentDone;
	}
	public void setPaymentDone(boolean isPaymentDone) {
		this.isPaymentDone = isPaymentDone;
	}*/

}
