package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

public class InsuredHistoryVO extends BaseVO{

	private static final long serialVersionUID = 1L;

	private Long insuredId;
	private Date transactionDate;
	private Date lastModifiedDate;
	private String name;
	private String poBox;
	private String insuredStatusDesc;
	private String emailId;
	private String mobileNo;
	private String insuredTypeDesc;
	private String address;
	private String lastModifiedBy;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "insuredId".equals( fieldName ) ) fieldValue = getInsuredId();
		if( "transactionDate".equals( fieldName ) ) fieldValue = getTransactionDate();
		if( "lastModifiedDate".equals( fieldName ) ) fieldValue = getLastModifiedDate();
		if( "name".equals( fieldName ) ) fieldValue = getName();
		if( "poBox".equals( fieldName ) ) fieldValue = getPoBox();
		if( "insuredStatusDesc".equals( fieldName ) ) fieldValue = getInsuredStatusDesc();
		if( "emailId".equals( fieldName ) ) fieldValue = getEmailId();
		if( "mobileNo".equals( fieldName ) ) fieldValue = getMobileNo();
		if( "insuredTypeDesc".equals( fieldName ) ) fieldValue = getInsuredTypeDesc();
		if( "address".equals( fieldName ) ) fieldValue = getAddress();
		if( "lastModifiedBy".equals( fieldName ) ) fieldValue = getLastModifiedBy();

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
	 * @return the transactionDate
	 */
	public Date getTransactionDate(){
		return transactionDate;
	}

	/**
	 * @param transactionDate the transactionDate to set
	 */
	public void setTransactionDate( Date transactionDate ){
		this.transactionDate = transactionDate;
	}

	/**
	 * @return the lastModifiedDate
	 */
	public Date getLastModifiedDate(){
		return lastModifiedDate;
	}

	/**
	 * @param lastModifiedDate the lastModifiedDate to set
	 */
	public void setLastModifiedDate( Date lastModifiedDate ){
		this.lastModifiedDate = lastModifiedDate;
	}

	/**
	 * @return the name
	 */
	public String getName(){
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName( String name ){
		this.name = name;
	}

	/**
	 * @return the poBox
	 */
	public String getPoBox(){
		return poBox;
	}

	/**
	 * @param poBox the poBox to set
	 */
	public void setPoBox( String poBox ){
		this.poBox = poBox;
	}

	/**
	 * @return the insuredStatusDesc
	 */
	public String getInsuredStatusDesc(){
		return insuredStatusDesc;
	}

	/**
	 * @param insuredStatusDesc the insuredStatusDesc to set
	 */
	public void setInsuredStatusDesc( String insuredStatusDesc ){
		this.insuredStatusDesc = insuredStatusDesc;
	}

	/**
	 * @return the emailId
	 */
	public String getEmailId(){
		return emailId;
	}

	/**
	 * @param emailId the emailId to set
	 */
	public void setEmailId( String emailId ){
		this.emailId = emailId;
	}

	/**
	 * @return the mobileNo
	 */
	public String getMobileNo(){
		return mobileNo;
	}

	/**
	 * @param mobileNo the mobileNo to set
	 */
	public void setMobileNo( String mobileNo ){
		this.mobileNo = mobileNo;
	}

	/**
	 * @return the insuredTypeDesc
	 */
	public String getInsuredTypeDesc(){
		return insuredTypeDesc;
	}

	/**
	 * @param insuredTypeDesc the insuredTypeDesc to set
	 */
	public void setInsuredTypeDesc( String insuredTypeDesc ){
		this.insuredTypeDesc = insuredTypeDesc;
	}

	/**
	 * @return the address
	 */
	public String getAddress(){
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress( String address ){
		this.address = address;
	}

	/**
	 * @return the lastModifiedBy
	 */
	public String getLastModifiedBy(){
		return lastModifiedBy;
	}

	/**
	 * @param lastModifiedBy the lastModifiedBy to set
	 */
	public void setLastModifiedBy( String lastModifiedBy ){
		this.lastModifiedBy = lastModifiedBy;
	}

}
