package com.rsaame.pas.vo.app;

import java.util.HashMap;

import com.mindtree.ruc.cmn.base.BaseVO;



public class PrintDocVO extends BaseVO {
	private static final long serialVersionUID = 1L;
	
	private String fileNames;
	private String printStatus;
	private String error;
	private String docCreationStatus;
	private String location;

	private HashMap<String, String> docParameter;
	
	
	public String getPrintStatus() {
		return printStatus;
	}

	public void setPrintStatus(String printStatus) {
		this.printStatus = printStatus;
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

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getFileNames() {
		return fileNames;
	}

	public void setFileNames(String fileNames) {
		this.fileNames = fileNames;
	}


	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getLocation(){
		return location;
	}

	public void setLocation( String location ){
		this.location = location;
	}
	
	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;

		if( "fileNames".equals( fieldName ) ) fieldValue = getFileNames();
		if( "printStatus".equals( fieldName ) ) fieldValue = getPrintStatus();
		if( "error".equals( fieldName ) ) fieldValue = getError();
		if( "docCreationStatus".equals( fieldName ) ) fieldValue = getDocCreationStatus();
		if( "docParameter".equals( fieldName ) ) fieldValue = getDocParameter();
		
		
		return fieldValue;
	}

}

