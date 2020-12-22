package com.rsaame.pas.vo.app;

import java.util.HashMap;

import com.mindtree.ruc.cmn.base.BaseVO;



public class MailVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	
	private String toAddress;
	private String[] ccAddress;
	private String fromAddress;
	private String mailType;
	private String subjectText;
	private String greeting;
	private StringBuilder mailContent;
	private String[] fileNames;
	private String signature;
	private String discalimerText;
	private String mailStatus;
	private String error;
	private String docCreationStatus;
	private HashMap<String, String> docParameter;
	private boolean isDirect;
	private String policyType;
	private String[] toAddresses;
	private String replyToEmailID;
	
	public String getPolicyType() {
		return policyType;
	}

	public void setPolicyType(String policyType) {
		this.policyType = policyType;
	}

	public HashMap<String, String> getDocParameter() {
		return docParameter;
	}

	public void setDocParameter(HashMap<String, String> docParameter) {
		this.docParameter = docParameter;
	}

	public String getDocCreationStatus() {
		return docCreationStatus;
	}

	public void setDocCreationStatus(String docCreationStatus) {
		this.docCreationStatus = docCreationStatus;
	}

	public String getMailStatus() {
		return mailStatus;
	}

	public void setMailStatus(String mailStatus) {
		this.mailStatus = mailStatus;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}



	
	
	public String getToAddress() {
		return toAddress;
	}

	public void setToAddress(String toAddress) {
		this.toAddress = toAddress;
	}

	public String[] getCcAddress() {
		return ccAddress;
	}

	public void setCcAddress(String[] ccAddress) {
		this.ccAddress = ccAddress;
	}

	public String getFromAddress() {
		return fromAddress;
	}

	public void setFromAddress(String fromAddress) {
		this.fromAddress = fromAddress;
	}

	public String getMailType() {
		return mailType;
	}

	public void setMailType(String mailType) {
		this.mailType = mailType;
	}

	public String getSubjectText() {
		return subjectText;
	}

	public void setSubjectText(String subjectText) {
		this.subjectText = subjectText;
	}

	public String getGreeting() {
		return greeting;
	}

	public void setGreeting(String greeting) {
		this.greeting = greeting;
	}

	public StringBuilder getMailContent() {
		return mailContent;
	}

	public void setMailContent(StringBuilder mailContent) {
		this.mailContent = mailContent;
	}

	public String[] getFileNames() {
		return fileNames;
	}

	public void setFileNames(String[] fileNames) {
		this.fileNames = fileNames;
	}

	public String getSignature() {
		return signature;
	}

	public void setSignature(String signature) {
		this.signature = signature;
	}

	public String getDiscalimerText() {
		return discalimerText;
	}

	public void setDiscalimerText(String discalimerText) {
		this.discalimerText = discalimerText;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	/**
	 * @return the toAddresses
	 */
	public String[] getToAddresses(){
		return toAddresses;
	}

	/**
	 * @param toAddresses the toAddresses to set
	 */
	public void setToAddresses( String[] toAddresses ){
		this.toAddresses = toAddresses;
	}

	/**
	 * @return the isDirect
	 */
	public boolean isDirect(){
		return isDirect;
	}

	/**
	 * @param isDirect the isDirect to set
	 */
	public void setDirect( boolean isDirect ){
		this.isDirect = isDirect;
	}
	

	public String getReplyToEmailID() {
		return replyToEmailID;
	}

	public void setReplyToEmailID(String replyToEmailID) {
		this.replyToEmailID = replyToEmailID;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;

		if( "toAddress".equals( fieldName ) ) fieldValue = getToAddress();
		if( "ccAddress;".equals( fieldName ) ) fieldValue = getCcAddress();
		if( "fromAddress".equals( fieldName ) ) fieldValue = getFromAddress();
		if( "mailType".equals( fieldName ) ) fieldValue = getMailType();
		if( "subjectText".equals( fieldName ) ) fieldValue = getSubjectText();
		if( "greeting".equals( fieldName ) ) fieldValue = getGreeting();
		if( "mailContent".equals( fieldName ) ) fieldValue = getMailContent();
		if( "fileNames".equals( fieldName ) ) fieldValue = getFileNames();
		if( "signature".equals( fieldName ) ) fieldValue = getSignature();
		if( "discalimerText".equals( fieldName ) ) fieldValue = getDiscalimerText();
		if( "mailStatus".equals( fieldName ) ) fieldValue = getMailStatus();
		if( "error".equals( fieldName ) ) fieldValue = getError();
		if( "docCreationStatus".equals( fieldName ) ) fieldValue = getDocCreationStatus();
		if( "docParameter".equals( fieldName ) ) fieldValue = getDocParameter();
		
		
		return fieldValue;
	}

}
