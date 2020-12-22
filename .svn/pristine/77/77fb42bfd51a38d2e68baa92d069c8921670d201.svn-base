package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1006438
 * 
 */
public class GenerateRenewalsSearchCriteriaVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private String clazz;
	private String policyNo;
	private Date transactionFrom;
	private Date transactionTo;
	private String insuredName;
	private String brokerName;
	private String scheme;
	private boolean allDirect;
	private String branch;
	private String noOfDays;
	private String lob;
	private String quoteNo;
	private String statusId;
	
	public String getStatusId(){
		return statusId;
	}

	public void setStatusId( String statusId ){
		this.statusId = statusId;
	}

	/*
	 *  Search Criteria :- Search Criteria based on Quotation No.  
	 */
	public String getQuoteNo() {
		return quoteNo;
	}

	public void setQuoteNo(String quoteNo) {
		this.quoteNo = quoteNo;
	}

	public String getNoOfDays(){
		return noOfDays;
	}

	public void setNoOfDays( String noOfDays ){
		this.noOfDays = noOfDays;
	}

	public String getClazz(){
		return clazz;
	}

	public void setClazz( String clazz ){
		this.clazz = clazz;	
	}

	public String getPolicyNo(){
		return policyNo;
	}

	public void setPolicyNo( String policyNo ){
		this.policyNo = policyNo;
	}

	public Date getTransactionFrom(){
		return transactionFrom;
	}

	public void setTransactionFrom( Date transactionFrom ){
		this.transactionFrom = transactionFrom;
	}

	public Date getTransactionTo(){
		return transactionTo;
	}

	public void setTransactionTo( Date transactionTo ){
		this.transactionTo = transactionTo;
	}

	public String getInsuredName(){
		return insuredName;
	}

	public void setInsuredName( String insuredName ){
		this.insuredName = insuredName;
	}

	public String getBrokerName(){
		return brokerName;
	}

	public void setBrokerName( String brokerName ){
		this.brokerName = brokerName;
	}

	public String getScheme(){
		return scheme;
	}

	public void setScheme( String scheme ){
		this.scheme = scheme;
	}

	public boolean getAllDirect(){
		return allDirect;
	}

	public void setAllDirect( boolean allDirect ){
		this.allDirect = allDirect;
	}

	public String getBranch(){
		return branch;
	}

	public void setBranch( String branch ){
		this.branch = branch;
	}
	
	/**
	 * @return the lob
	 */
	public String getLob() {
		return lob;
	}

	/**
	 * @param lob the lob to set
	 */
	public void setLob(String lob) {
		this.lob = lob;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;
		if( "clazz".equals( fieldName ) ) fieldValue = getClazz();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		if( "transactionFrom".equals( fieldName ) ) fieldValue = getTransactionFrom();
		if( "transactionTo".equals( fieldName ) ) fieldValue = getTransactionTo();
		if( "insuredName".equals( fieldName ) ) fieldValue = getInsuredName();
		if( "brokerName".equals( fieldName ) ) fieldValue = getBrokerName();
		if( "scheme".equals( fieldName ) ) fieldValue = getScheme();
		if( "allDirect".equals( fieldName ) ) fieldValue = getAllDirect();
		if( "branch".equals( fieldName ) ) fieldValue = getBranch();
		if( "noOfDays".equals( fieldName ) ) fieldValue = getNoOfDays();
		if( "statusId".equals( fieldName ) ) fieldValue = getStatusId();
		if( "lob".equals( fieldName ) ) fieldValue = getLob();
		return fieldValue;
	}

}
