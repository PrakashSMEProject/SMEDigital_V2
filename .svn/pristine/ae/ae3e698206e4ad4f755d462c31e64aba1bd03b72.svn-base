package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1019860
 * 
 */
public class PrintRenewalsSearchCriteriaVO extends BaseVO{

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
	private boolean withEmailID;
	private boolean notYetPrinted;
	private String lob;
	private String[] statusIdList;
	private String renewalTerm;

	

	public String getRenewalTerm() {
		return renewalTerm;
	}

	public void setRenewalTerm(String renewalTerm) {
		this.renewalTerm = renewalTerm;
	}

	public boolean getNotYetPrinted(){
		return notYetPrinted;
	}

	public void setNotYetPrinted( boolean notYetPrinted ){
		this.notYetPrinted = notYetPrinted;
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
	public boolean getWithEmailID(){
		return withEmailID;
	}

	public void setWithEmailID( boolean withEmailID ){
		this.withEmailID = withEmailID;
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
	
	

	public String[] getStatusIdList() {
		return statusIdList;
	}

	public void setStatusIdList(String[] statusIdList) {
		this.statusIdList = statusIdList;
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
		if( "withEmailID".equals( fieldName ) ) fieldValue = getWithEmailID();
		if( "notYetPrinted".equals( fieldName ) ) fieldValue = getNotYetPrinted();
		if( "lob".equals( fieldName ) ) fieldValue = getLob();
		if( "statusIdList".equals( fieldName ) ) fieldValue = getStatusIdList();
		if( "renewalTerm".equals( fieldName ) ) fieldValue = getRenewalTerm();
		return fieldValue;
	}

}
