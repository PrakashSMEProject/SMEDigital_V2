/**
 * 
 */
package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1014739
 *
 */
public class SearchInsuredSummaryVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords;
	private Integer recordsPerPage;
	private Integer currentPage;
	private SearchInsuredVO[] insuredArray;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "insuredArray".equals( fieldName ) ) fieldValue = getInsuredArray();
		if( "numberOfRecords".equals( fieldName ) ) fieldValue = getNumberOfRecords();
		if( "recordsPerPage".equals( fieldName ) ) fieldValue = getRecordsPerPage();
		if( "currentPage".equals( fieldName ) ) fieldValue = getCurrentPage();
		return fieldValue;
	}

	/**
	 * @return the numberOfRecords
	 */
	public Integer getNumberOfRecords(){
		return numberOfRecords;
	}

	/**
	 * @param numberOfRecords the numberOfRecords to set
	 */
	public void setNumberOfRecords( Integer numberOfRecords ){
		this.numberOfRecords = numberOfRecords;
	}

	/**
	 * @return the recordsPerPage
	 */
	public Integer getRecordsPerPage(){
		return recordsPerPage;
	}

	/**
	 * @param recordsPerPage the recordsPerPage to set
	 */
	public void setRecordsPerPage( Integer recordsPerPage ){
		this.recordsPerPage = recordsPerPage;
	}

	/**
	 * @return the currentPage
	 */
	public Integer getCurrentPage(){
		return currentPage;
	}

	/**
	 * @param currentPage the currentPage to set
	 */
	public void setCurrentPage( Integer currentPage ){
		this.currentPage = currentPage;
	}

	/**
	 * @return the insuredArray
	 */
	public SearchInsuredVO[] getInsuredArray(){
		return insuredArray;
	}

	/**
	 * @param insuredArray the insuredArray to set
	 */
	public void setInsuredArray( SearchInsuredVO[] insuredArray ){
		this.insuredArray = insuredArray;
	}

}
