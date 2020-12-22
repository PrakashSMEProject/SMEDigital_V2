/**
 * 
 */
package com.rsaame.pas.vo.app;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;


/**
 * @author M1014957
 *
 */
public class BrReportSearchVO extends BaseVO {
	
	private Integer branchCode;
	private String allOrUnmatched;
	private String byNameOrCode;
	private Integer brokerName_Code;
	private Date startDate;
	private Date endDate;
	private Integer brokerCode;
	private String lob;
	private String policyNo;
	
	
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "startString".equals( fieldName ) ) fieldValue = getStartDate();
		if( "endString".equals( fieldName ) ) fieldValue = getEndDate();
		if( "brokerName".equals( fieldName ) ) fieldValue = getBrokerName_Code();
		if( "brokerCode".equals( fieldName ) ) fieldValue = getBrokerCode();
		if( "branchCode".equals( fieldName ) ) fieldValue = getBranchCode();
		if( "policyNo".equals( fieldName ) ) fieldValue = getPolicyNo();
		
		return fieldValue;
	}

	/**
	 * @return the policyNo
	 */
	public String getPolicyNo() {
		return policyNo;
	}

	/**
	 * @param policyNo the policyNo to set
	 */
	public void setPolicyNo(String policyNo) {
		this.policyNo = policyNo;
	}

	/**
	 * @return the branchCode
	 */
	public Integer getBranchCode() {
		return branchCode;
	}

	/**
	 * @param branchCode the branchCode to set
	 */
	public void setBranchCode(Integer branchCode) {
		this.branchCode = branchCode;
	}

	/**
	 * @return the allOrUnmatched
	 */
	public String getAllOrUnmatched() {
		return allOrUnmatched;
	}

	/**
	 * @param allOrUnmatched the allOrUnmatched to set
	 */
	public void setAllOrUnmatched(String allOrUnmatched) {
		this.allOrUnmatched = allOrUnmatched;
	}

	/**
	 * @return the byNameOrCode
	 */
	public String getByNameOrCode() {
		return byNameOrCode;
	}

	/**
	 * @param byNameOrCode the byNameOrCode to set
	 */
	public void setByNameOrCode(String byNameOrCode) {
		this.byNameOrCode = byNameOrCode;
	}

	/**
	 * @return the brokerName_Code
	 */
	public Integer getBrokerName_Code() {
		return brokerName_Code;
	}

	/**
	 * @param brokerName_Code the brokerName_Code to set
	 */
	public void setBrokerName_Code(Integer brokerName_Code) {
		this.brokerName_Code = brokerName_Code;
	}

	

	/**
	 * @return the startDate
	 */
	public Date getStartDate() {
		return startDate;
	}

	/**
	 * @param startDate the startDate to set
	 */
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	/**
	 * @return the endDate
	 */
	public Date getEndDate() {
		return endDate;
	}

	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	/**
	 * @return the brokerCode
	 */
	public Integer getBrokerCode() {
		return brokerCode;
	}

	/**
	 * @param brokerCode the brokerCode to set
	 */
	public void setBrokerCode(Integer brokerCode) {
		this.brokerCode = brokerCode;
	}

	/**
	 * @return the lob
	 */
	public String getLob(){
		return lob;
	}

	/**
	 * @param lob the lob to set
	 */
	public void setLob( String lob ){
		this.lob = lob;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ReportsSearchVO [branchCode=" + branchCode
				+ ", allOrUnmatched=" + allOrUnmatched + ", byNameOrCode="
				+ byNameOrCode + ", brokerName_Code=" + brokerName_Code
				+ ", startDate=" + startDate + ", endDate=" + endDate
				+ ", brokerCode=" + brokerCode + "]";
	}

	

}
