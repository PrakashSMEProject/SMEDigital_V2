/**
 * 
 */
package com.rsaame.pas.dao.model;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Sarath Varier
 * @since Phase 3
 *
 */
public class TTrnPaymentDtl implements java.io.Serializable {
	
	private static final long serialVersionUID = 165456156L;
	
	private Long pdlQutoteNo;
	private String pdlTransId;
	private Long pdlPolicyId;
	private Date pdlTransaDate;
	private BigDecimal pdlTransaAmount;
	private String pdlTransStatus;
	private String pdlMerchantRefNo;
	private String pdlCurName;
	private Integer pdlErrCode;
	private String pdlErrDesc;
	private String pdlCreditCrdNo;
	private String pdlCreditCrdTyp;
	private String pdlBillingAddrs;
	private String pdlCustName;

	/**
	 * @return the pdlQutoteId
	 */
	public Long getPdlQutoteNo(){
		return pdlQutoteNo;
	}
	/**
	 * @param pdlQutoteId the pdlQutoteId to set
	 */
	public void setPdlQutoteNo( Long pdlQutoteNo ){
		this.pdlQutoteNo = pdlQutoteNo;
	}
	/**
	 * @return the pdlTransId
	 */
	public String getPdlTransId(){
		return pdlTransId;
	}
	/**
	 * @param pdlTransId the pdlTransId to set
	 */
	public void setPdlTransId( String pdlTransId ){
		this.pdlTransId = pdlTransId;
	}
	/**
	 * @return the pdlPolicyId
	 */
	public Long getPdlPolicyId(){
		return pdlPolicyId;
	}
	/**
	 * @param pdlPolicyId the pdlPolicyId to set
	 */
	public void setPdlPolicyId( Long pdlPolicyId ){
		this.pdlPolicyId = pdlPolicyId;
	}
	/**
	 * @return the pdlTransaDate
	 */
	public Date getPdlTransaDate(){
		return pdlTransaDate;
	}
	/**
	 * @param pdlTransaDate the pdlTransaDate to set
	 */
	public void setPdlTransaDate( Date pdlTransaDate ){
		this.pdlTransaDate = pdlTransaDate;
	}
	/**
	 * @return the pdlTransaAmount
	 */
	public BigDecimal getPdlTransaAmount(){
		return pdlTransaAmount;
	}
	/**
	 * @param pdlTransaAmount the pdlTransaAmount to set
	 */
	public void setPdlTransaAmount( BigDecimal pdlTransaAmount ){
		this.pdlTransaAmount = pdlTransaAmount;
	}
	/**
	 * @return the pdlTransStatus
	 */
	public String getPdlTransStatus(){
		return pdlTransStatus;
	}
	/**
	 * @param pdlTransStatus the pdlTransStatus to set
	 */
	public void setPdlTransStatus( String pdlTransStatus ){
		this.pdlTransStatus = pdlTransStatus;
	}
	/**
	 * @return the pdlMerchantRefNo
	 */
	public String getPdlMerchantRefNo(){
		return pdlMerchantRefNo;
	}
	/**
	 * @param pdlMerchantRefNo the pdlMerchantRefNo to set
	 */
	public void setPdlMerchantRefNo( String pdlMerchantRefNo ){
		this.pdlMerchantRefNo = pdlMerchantRefNo;
	}
	/**
	 * @return the pdlCurName
	 */
	public String getPdlCurName(){
		return pdlCurName;
	}
	/**
	 * @param pdlCurName the pdlCurName to set
	 */
	public void setPdlCurName( String pdlCurName ){
		this.pdlCurName = pdlCurName;
	}
	/**
	 * @return the pdlErrCode
	 */
	public Integer getPdlErrCode(){
		return pdlErrCode;
	}
	/**
	 * @param pdlErrCode the pdlErrCode to set
	 */
	public void setPdlErrCode( Integer pdlErrCode ){
		this.pdlErrCode = pdlErrCode;
	}
	/**
	 * @return the pdlErrDesc
	 */
	public String getPdlErrDesc(){
		return pdlErrDesc;
	}
	/**
	 * @param pdlErrDesc the pdlErrDesc to set
	 */
	public void setPdlErrDesc( String pdlErrDesc ){
		this.pdlErrDesc = pdlErrDesc;
	}
	/**
	 * @return the pdlCreditCrdNo
	 */
	public String getPdlCreditCrdNo(){
		return pdlCreditCrdNo;
	}
	/**
	 * @param pdlCreditCrdNo the pdlCreditCrdNo to set
	 */
	public void setPdlCreditCrdNo( String pdlCreditCrdNo ){
		this.pdlCreditCrdNo = pdlCreditCrdNo;
	}
	/**
	 * @return the pdlCreditCrdTyp
	 */
	public String getPdlCreditCrdTyp(){
		return pdlCreditCrdTyp;
	}
	/**
	 * @param pdlCreditCrdTyp the pdlCreditCrdTyp to set
	 */
	public void setPdlCreditCrdTyp( String pdlCreditCrdTyp ){
		this.pdlCreditCrdTyp = pdlCreditCrdTyp;
	}
	/**
	 * @return the pdlBillingAddrs
	 */
	public String getPdlBillingAddrs(){
		return pdlBillingAddrs;
	}
	/**
	 * @param pdlBillingAddrs the pdlBillingAddrs to set
	 */
	public void setPdlBillingAddrs( String pdlBillingAddrs ){
		this.pdlBillingAddrs = pdlBillingAddrs;
	}
	/**
	 * @return the pdlCustName
	 */
	public String getPdlCustName(){
		return pdlCustName;
	}
	/**
	 * @param pdlCustName the pdlCustName to set
	 */
	public void setPdlCustName( String pdlCustName ){
		this.pdlCustName = pdlCustName;
	}

}