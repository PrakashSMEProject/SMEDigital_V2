/**
 * 
 */
package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1014739
 *
 */
public class InsuredHistoryListVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords;
	private Integer recordsPerPage;
	private Integer currentPage;
	private InsuredHistoryVO[] insuredHistoryArray;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "insuredHistoryArray".equals( fieldName ) ) fieldValue = getInsuredHistoryArray();
		if( "numberOfRecords".equals( fieldName ) ) fieldValue = getNumberOfRecords();
		if( "recordsPerPage".equals( fieldName ) ) fieldValue = getRecordsPerPage();
		if( "currentPage".equals( fieldName ) ) fieldValue = getCurrentPage();
		return fieldValue;
	}

	/**
	 * @return the insuredHistoryArray
	 */
	public InsuredHistoryVO[] getInsuredHistoryArray(){
		return insuredHistoryArray;
	}

	/**
	 * @param insuredHistoryArray the insuredHistoryArray to set
	 */
	public void setInsuredHistoryArray( InsuredHistoryVO[] insuredHistoryArray ){
		this.insuredHistoryArray = insuredHistoryArray;
	}

	public void setNumberOfRecords( Integer numberOfRecords ){
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getNumberOfRecords(){
		return numberOfRecords;
	}

	public void setRecordsPerPage( Integer recordsPerPage ){
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getRecordsPerPage(){
		return recordsPerPage;
	}

	public void setCurrentPage( Integer currentPage ){
		this.currentPage = currentPage;
	}

	public Integer getCurrentPage(){
		return currentPage;
	}

}
