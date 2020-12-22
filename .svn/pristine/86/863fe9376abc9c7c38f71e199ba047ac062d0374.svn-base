



package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.List;

public class WCVO extends com.rsaame.pas.vo.bus.RiskGroupDetails {
	
	private static final long serialVersionUID = 1L;
	private Integer empType;
	private int noOfEmp;
	private Double wageroll;
	private Double deductibles;
	private BigDecimal limit;
	private WCCoversVO wcCovers;
	private Long wcId;
	private Date validityStartDate;
	private Long riskId;/* WUP_ID */
	private List<EmpTypeDetailsVO> empTypeDetails = new List<EmpTypeDetailsVO>( EmpTypeDetailsVO.class );
	private java.util.List<WCNammedEmployeeVO> wcEmployeeDetails = new com.mindtree.ruc.cmn.utils.List<WCNammedEmployeeVO>(
			WCNammedEmployeeVO.class );
	
	

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empType".equals( fieldName ) ) fieldValue = getEmpType();
		if( "noOfEmp".equals( fieldName ) ) fieldValue = getNoOfEmp();
		if( "wageroll".equals( fieldName ) ) fieldValue = getWageroll();
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();
		if( "wcCovers".equals( fieldName ) ) fieldValue = getWcCovers();
		if( "wcId".equals( fieldName ) ) fieldValue = getWcId();
		if( "validityStartDate".equals( fieldName ) ) fieldValue = getValidityStartDate();
		if( "empTypeDetails".equals(fieldName) ) fieldValue=getEmpTypeDetails();
		if( "limit".equals(fieldName) ) fieldValue=getLimit();
		if( "riskId".equals(fieldName) ) fieldValue=getRiskId();
		if( "wcEmployeeDetails".equals(fieldName) ) fieldValue=getWcEmployeeDetails();
		
		return fieldValue;
	}

	public Integer getEmpType(){
		return empType;
	}

	public void setEmpType( Integer empType ){
		this.empType = empType;
	}

	
	public int getNoOfEmp() {
		return noOfEmp;
	}

	public void setNoOfEmp(int noOfEmp) {
		this.noOfEmp = noOfEmp;
	}

	public Double getWageroll(){
		return wageroll;
	}

	public void setWageroll( Double wageroll ){
		this.wageroll = wageroll;
	}

	public Double getDeductibles(){
		return deductibles;
	}

	public void setDeductibles( Double deductibles ){
		this.deductibles = deductibles;
	}

	public WCCoversVO getWcCovers(){
		return wcCovers;
	}

	public void setWcCovers( WCCoversVO wcCovers ){
		this.wcCovers = wcCovers;
	}
	
	public BigDecimal getLimit() {
		return limit;
	}

	public void setLimit(BigDecimal limit) {
		this.limit = limit;
	}

	/**
	 * @return the wcId
	 */
	public Long getWcId() {
		return wcId;
	}

	/**
	 * @param wcId the wcId to set
	 */
	public void setWcId(Long wcId) {
		this.wcId = wcId;
	}

	/**
	 * @return the validityStartDate
	 */
	public Date getValidityStartDate() {
		return validityStartDate;
	}

	/**
	 * @param validityStartDate the validityStartDate to set
	 */
	public void setValidityStartDate(Date validityStartDate) {
		this.validityStartDate = validityStartDate;
	}
		/**
	 * @return the empTypeDetails
	 */
	public List<EmpTypeDetailsVO> getEmpTypeDetails(){
		return empTypeDetails;
	}

	/**
	 * @param empTypeDetails the empTypeDetails to set
	 */
	public void setEmpTypeDetails( List<EmpTypeDetailsVO> empTypeDetails ){
		this.empTypeDetails = empTypeDetails;
	}

	public Long getRiskId(){
		return riskId;
	}

	public void setRiskId( Long riskId ){
		this.riskId = riskId;
	}

	/**
	 * @return the wcEmployeeDetails
	 */
	public java.util.List<WCNammedEmployeeVO> getWcEmployeeDetails() {
		return wcEmployeeDetails;
	}

	/**
	 * @param wcEmployeeDetails the wcEmployeeDetails to set
	 */
	public void setWcEmployeeDetails(
			java.util.List<WCNammedEmployeeVO> wcEmployeeDetails) {
		this.wcEmployeeDetails = wcEmployeeDetails;
	}
	
}
