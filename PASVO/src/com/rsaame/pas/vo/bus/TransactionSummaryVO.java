/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1014739
 *
 */
public class TransactionSummaryVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords; 
	private Integer recordsPerPage;
	private Integer currentPage;
	private TransactionVO[] transactionArray;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "transactionArray".equals( fieldName ) ) fieldValue = getTransactionArray();
		if( "numberOfRecords".equals( fieldName ) ) fieldValue = getNumberOfRecords();
		if( "recordsPerPage".equals( fieldName ) ) fieldValue = getRecordsPerPage();
		if( "currentPage".equals( fieldName ) ) fieldValue = getCurrentPage();
		return fieldValue;
	}

	/**
	 * @return the transactionArray
	 */
	public TransactionVO[] getTransactionArray(){
		return transactionArray;
	}

	/**
	 * @param transactionArray the transactionArray to set
	 */
	public void setTransactionArray( TransactionVO[] transactionArray ){
		this.transactionArray = transactionArray;
	}

	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}

	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}

	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}

	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}

	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}

	public Integer getCurrentPage() {
		return currentPage;
	}

}
