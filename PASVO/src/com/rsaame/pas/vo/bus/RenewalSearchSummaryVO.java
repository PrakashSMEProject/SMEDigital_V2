package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import java.util.List;

public class RenewalSearchSummaryVO extends BaseVO{

	/**
	 * @author M1006438
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords;
	private Integer recordsPerPage;
	private Integer currentPage;
	private List <RenewalResultsVO> renPolList =   new com.mindtree.ruc.cmn.utils.List<RenewalResultsVO>(RenewalResultsVO.class);
	

	public List<RenewalResultsVO> getRenPolList(){
		return renPolList;
	}

	public void setRenPolList( List<RenewalResultsVO> renPolList ){
		this.renPolList = renPolList;
	}

	//private List<BrokerAcctResultVO> brokerAcct = new com.mindtree.ruc.cmn.utils.List<BrokerAcctResultVO>(BrokerAcctResultVO.class);
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

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "renPolList".equals( fieldName ) ) fieldValue = this.getRenPolList();
		if( "numberOfRecords".equals( fieldName ) ) fieldValue = getNumberOfRecords();
		if( "recordsPerPage".equals( fieldName ) ) fieldValue = getRecordsPerPage();
		if( "currentPage".equals( fieldName ) ) fieldValue = getCurrentPage();
		return fieldValue;
	}

}
