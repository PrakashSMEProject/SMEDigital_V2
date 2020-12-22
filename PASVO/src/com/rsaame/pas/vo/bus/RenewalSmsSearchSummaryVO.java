package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.rsaame.pas.vo.app.RenewalResultsSmsVO;

import java.util.List;

/**
 * @author M1020278
 * 
 */
public class RenewalSmsSearchSummaryVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer numberOfRecords;
	private Integer recordsPerPage;
	private Integer currentPage;
	private List <RenewalResultsSmsVO> renPolList =   new com.mindtree.ruc.cmn.utils.List<RenewalResultsSmsVO>(RenewalResultsSmsVO.class);
	

	public List<RenewalResultsSmsVO> getRenPolList(){
		return renPolList;
	}

	public void setRenPolList( List<RenewalResultsSmsVO> renPolList ){
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
