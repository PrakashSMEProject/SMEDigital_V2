/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author m1014644
 *
 */
public class PaymentVO extends BaseVO implements IFieldValue{

	private String paymentMode;
	private Byte payModeCode;
	private Double premium; 
	private Long chequeNo;
	private Date chequeDt;
	private Integer bankCode;
	private Integer creditCardNo;
	private Date expiryDate;
	private Double amount;
	private boolean isPaymentDone;
	private Long terminalId;
	
	/**
	 * @return the amount
	 */
	public Double getAmount() {
		return amount;
	}


	/**
	 * @param amount the amount to set
	 */
	public void setAmount(Double amount) {
		this.amount = amount;
	}


	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "paymentMode".equals( fieldName ) ) fieldValue = getPaymentMode();
		if( "payModeCode".equals( fieldName ) ) fieldValue = getPayModeCode();
		if( "premium".equals( fieldName ) ) fieldValue = getPremium();
		if( "chequeNo".equals( fieldName ) ) fieldValue = getChequeNo();
		if( "chequeDt".equals( fieldName ) ) fieldValue = getChequeDt();
		if( "bankCode".equals( fieldName ) ) fieldValue = getBankCode();
		if( "creditCardNo".equals( fieldName ) ) fieldValue = getCreditCardNo();
		if( "expiryDate".equals( fieldName ) ) fieldValue = getExpiryDate();
		if( "amount".equals( fieldName ) ) fieldValue = getAmount();
		if( "isPaymentDone".equals( fieldName ) ) fieldValue = isPaymentDone();

		return fieldValue;
	}


	/**
	 * @return the isPaymentDone
	 */
	public boolean isPaymentDone(){
		return isPaymentDone;
	}


	/**
	 * @param isPaymentDone the isPaymentDone to set
	 */
	public void setPaymentDone( boolean isPaymentDone ){
		this.isPaymentDone = isPaymentDone;
	}


	/**
	 * @return the paymentMode
	 */
	public String getPaymentMode(){
		return paymentMode;
	}


	/**
	 * @param paymentMode the paymentMode to set
	 */
	public void setPaymentMode( String paymentMode ){
		this.paymentMode = paymentMode;
	}


	/**
	 * @return the premium
	 */
	public Double getPremium(){
		return premium;
	}


	/**
	 * @param premium the premium to set
	 */
	public void setPremium( Double premium ){
		this.premium = premium;
	}


	/**
	 * @return the chequeNo
	 */
	public Long getChequeNo(){
		return chequeNo;
	}


	/**
	 * @param chequeNo the chequeNo to set
	 */
	public void setChequeNo( Long chequeNo ){
		this.chequeNo = chequeNo;
	}


	/**
	 * @return the chequeDt
	 */
	public Date getChequeDt(){
		return chequeDt;
	}


	/**
	 * @param chequeDt the chequeDt to set
	 */
	public void setChequeDt( Date chequeDt ){
		this.chequeDt = chequeDt;
	}


	/**
	 * @return the bankCode
	 */
	public Integer getBankCode(){
		return bankCode;
	}


	/**
	 * @param bankCode the bankCode to set
	 */
	public void setBankCode( Integer bankCode ){
		this.bankCode = bankCode;
	}


	/**
	 * @return the creditCardNo
	 */
	public Integer getCreditCardNo(){
		return creditCardNo;
	}


	/**
	 * @param creditCardNo the creditCardNo to set
	 */
	public void setCreditCardNo( Integer creditCardNo ){
		this.creditCardNo = creditCardNo;
	}


	/**
	 * @return the expiryDate
	 */
	public Date getExpiryDate(){
		return expiryDate;
	}


	/**
	 * @param expiryDate the expiryDate to set
	 */
	public void setExpiryDate( Date expiryDate ){
		this.expiryDate = expiryDate;
	}

	

	public Byte getPayModeCode() {
		return payModeCode;
	}


	public void setPayModeCode(Byte payModeCode) {
		this.payModeCode = payModeCode;
	}

	/**
	 * @return the terminalId
	 */
	public Long getTerminalId(){
		return terminalId;
	}


	/**
	 * @param terminalId the terminalId to set
	 */
	public void setTerminalId( Long terminalId ){
		this.terminalId = terminalId;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "PaymentVO [paymentMode=" + paymentMode + ", premium=" + premium + ", chequeNo=" + chequeNo + ", chequeDt=" + chequeDt + ", bankCode=" + bankCode
				+ ", creditCardNo=" + creditCardNo + ", expiryDate=" + expiryDate + "]";
	}

	
}
