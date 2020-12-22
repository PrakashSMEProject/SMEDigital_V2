package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

public class ClaimsHistoryListVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords; 
	private Integer recordsPerPage;
	private Integer currentPage;
	private ClaimsHistoryVO[] claimsHistoryArray;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "claimsHistoryArray".equals( fieldName ) ) fieldValue = getClaimsHistoryArray();
		if( "numberOfRecords".equals( fieldName ) ) fieldValue = getNumberOfRecords();
		if( "recordsPerPage".equals( fieldName ) ) fieldValue = getRecordsPerPage();
		if( "currentPage".equals( fieldName ) ) fieldValue = getCurrentPage();
		return fieldValue;
	}

	public Integer getNumberOfRecords(){
		return numberOfRecords;
	}

	public void setNumberOfRecords( Integer numberOfRecords ){
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getRecordsPerPage(){
		return recordsPerPage;
	}

	public void setRecordsPerPage( Integer recordsPerPage ){
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getCurrentPage(){
		return currentPage;
	}

	public void setCurrentPage( Integer currentPage ){
		this.currentPage = currentPage;
	}

	public ClaimsHistoryVO[] getClaimsHistoryArray(){
		return claimsHistoryArray;
	}

	public void setClaimsHistoryArray( ClaimsHistoryVO[] claimsHistoryArray ){
		this.claimsHistoryArray = claimsHistoryArray;
	}

}
