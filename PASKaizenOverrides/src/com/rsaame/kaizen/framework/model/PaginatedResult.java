/*
 * Created on Nov 17, 2007
 *
 * TODO To change the template for this generated file go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
package com.rsaame.kaizen.framework.model;

import com.rsaame.kaizen.framework.constants.AMEConstants;


/**
 * @author 131238
 *
 * TODO To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Style - Code Templates
 */
public class PaginatedResult extends BaseAMEBO{
	
	private Integer numberOfRecords;
	private Integer recordsPerPage = new Integer(com.rsaame.kaizen.framework.util.PASKaizenConstants.RECORDS_PER_PAGE);
	private Integer currentPage = new Integer(AMEConstants.CURRENT_PAGE);
	private Object[] objectArray;
	
	/**
	 * default constructor
	 */
	public PaginatedResult() {
	}
	/**
	 * @return Returns the currentPage.
	 */
	public Integer getCurrentPage() {
		return currentPage;
	}
	/**
	 * @param currentPage The currentPage to set.
	 */
	public void setCurrentPage(Integer currentPage) {
		this.currentPage = currentPage;
	}
	/**
	 * @param numberOfRecords
	 * @param recordsPerPage
	 * @param currentPage
	 * @param objectArray
	 */
	public PaginatedResult(Integer numberOfRecords, Integer recordsPerPage,
			Integer currentPage, Object[] objectArray) {
		this.numberOfRecords = numberOfRecords;
		this.recordsPerPage = recordsPerPage;
		this.currentPage = currentPage;
		this.objectArray = objectArray;
	}
	/**
	 * @return Returns the numberOfRecords.
	 */
	public Integer getNumberOfRecords() {
		return numberOfRecords;
	}
	/**
	 * @param numberOfRecords The numberOfRecords to set.
	 */
	public void setNumberOfRecords(Integer numberOfRecords) {
		this.numberOfRecords = numberOfRecords;
	}
	/**
	 * @return Returns the objectArray.
	 */
	public Object[] getObjectArray() {
		return objectArray;
	}
	/**
	 * @param objectArray The objectArray to set.
	 */
	public void setObjectArray(Object[] objectArray) {
		this.objectArray = objectArray;
	}
	/**
	 * @return Returns the recordsPerPage.
	 */
	public Integer getRecordsPerPage() {
		return recordsPerPage;
	}
	/**
	 * @param recordsPerPage The recordsPerPage to set.
	 */
	public void setRecordsPerPage(Integer recordsPerPage) {
		this.recordsPerPage = recordsPerPage;
	}
}
