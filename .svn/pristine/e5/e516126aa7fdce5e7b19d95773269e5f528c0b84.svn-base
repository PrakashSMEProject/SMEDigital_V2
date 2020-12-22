/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.math.BigDecimal;

import com.rsaame.pas.vo.app.Contents;

/**
 * @author m1014320
 *
 */
public class EmpTypeDetailsVO extends Contents{
	

	private static final long serialVersionUID = 1L;

	private BigDecimal limit;
	private Integer empType;
	private int noOfEmp;
	private Double wageroll;
	private Integer occTradeGroup;
	private Long hazardLevel;
	/*
	 * Added liabilityLimit for JLT Web Service Referral flow
	 * To capture the limit if limit is more than >10M to send to rule engine
	 * For display in the task list
	 */
	private Double liabilityLimit;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empType".equals( fieldName ) ) fieldValue = getEmpType();
		if( "noOfEmp".equals( fieldName ) ) fieldValue = getNoOfEmp();
		if( "wageroll".equals( fieldName ) ) fieldValue = getWageroll();
		if( "deductibles".equals( fieldName ) ) fieldValue = getDeductibles();	
		if( "limit".equals(fieldName) ) fieldValue=getLimit();
		if( "occTradeGroup".equals(fieldName) ) fieldValue=getOccTradeGroup();
		if( "hazardLevel".equals(fieldName) ) fieldValue=getHazardLevel();
		
		return fieldValue;
	}
	
	public Double getLiabilityLimit() {
		return liabilityLimit;
	}

	public void setLiabilityLimit(Double liabilityLimit) {
		this.liabilityLimit = liabilityLimit;
	}

	public Integer getOccTradeGroup() {
		return occTradeGroup;
	}

	public void setOccTradeGroup(Integer occTradeGroup) {
		this.occTradeGroup = occTradeGroup;
	}

	public Long getHazardLevel() {
		return hazardLevel;
	}

	public void setHazardLevel(Long hazardLevel) {
		this.hazardLevel = hazardLevel;
	}

	public BigDecimal getLimit(){
		return limit;
	}
	public void setLimit( BigDecimal limit ){
		this.limit = limit;
	}
	public Integer getEmpType(){
		return empType;
	}
	public void setEmpType( Integer empType ){
		this.empType = empType;
	}
	public int getNoOfEmp(){
		return noOfEmp;
	}
	public void setNoOfEmp( int noOfEmp ){
		this.noOfEmp = noOfEmp;
	}
	public Double getWageroll(){
		return wageroll;
	}
	public void setWageroll( Double wageroll ){
		this.wageroll = wageroll;
	}
		
	@Override
	public String toString(){
		return "EmpTypeDetailsVO [limit=" + limit + ", empType=" + empType + ", noOfEmp=" + noOfEmp + ", wageroll=" + wageroll + ", occTradeGroup=" + occTradeGroup + ", hazardLevel=" + hazardLevel + "]";
	}
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( empType == null ) ? 0 : empType.hashCode() );
		return result;
	}
	
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		EmpTypeDetailsVO other = (EmpTypeDetailsVO) obj;
		if( empType == null ){
			if( other.empType != null ) return false;
		}
		else if( !empType.equals( other.empType ) ) return false;
		return true;
	}
	
	

}
