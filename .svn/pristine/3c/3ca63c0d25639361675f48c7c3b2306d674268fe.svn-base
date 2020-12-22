/**
 * 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1016303
 *
 */
public class FidelityUnnammedEmployeeVO extends RiskGroupDetails{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	private Integer empType;
	private Integer totalNumberOfEmployee;
	private Double limitPerPerson;
	private SumInsuredVO sumInsuredDetails = new SumInsuredVO();
	private Long gupFidelityId;
	private Integer index;

	/**
	 * @return the limitPerPerson
	 */
	public Double getLimitPerPerson(){
		return limitPerPerson;
	}

	/**
	 * @param limitPerPerson the limitPerPerson to set
	 */
	public void setLimitPerPerson( Double limitPerPerson ){
		this.limitPerPerson = limitPerPerson;
	}

	public Integer getEmpType(){
		return empType;
	}

	public void setEmpType( Integer empType ){
		this.empType = empType;
	}

	public Integer getTotalNumberOfEmployee(){
		return totalNumberOfEmployee;
	}

	public void setTotalNumberOfEmployee( Integer totalNumberOfEmployee ){
		this.totalNumberOfEmployee = totalNumberOfEmployee;
	}

	public SumInsuredVO getSumInsuredDetails(){
		return sumInsuredDetails;
	}

	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}

	/**
	 * @return the gupFidelityId
	 */
	public Long getGupFidelityId(){
		return gupFidelityId;
	}

	/**
	 * @param gupFidelityId the gupFidelityId to set
	 */
	public void setGupFidelityId( Long gupFidelityId ){
		this.gupFidelityId = gupFidelityId;
	}
	

	/**
	 * @return the index
	 */
	public Integer getIndex(){
		return index;
	}

	/**
	 * @param index the index to set
	 */
	public void setIndex( Integer index ){
		this.index = index;
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empType".equals( fieldName ) ) fieldValue = getEmpType();
		if( "totalNumberOfEmployee".equals( fieldName ) ) fieldValue = getTotalNumberOfEmployee();
		if( "limitPerPerson".equals( fieldName ) ) fieldValue = getLimitPerPerson();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();
		return fieldValue;
	}

	@Override
	public String toString(){
		return "FidelityUnnammedEmployeeVO [empType=" + empType + ", totalNumberOfEmployee=" + totalNumberOfEmployee + ", limitPerPerson=" + limitPerPerson + ", gupFidelityId="
				+ gupFidelityId + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		return 1;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){

		if( Utils.isEmpty( obj ) || !( obj instanceof FidelityUnnammedEmployeeVO ) ) return false;
		if( Utils.isEmpty( this.getGupFidelityId() ) && Utils.isEmpty( ( (FidelityUnnammedEmployeeVO) obj ).getGupFidelityId() ) ) return true;

		if( !Utils.isEmpty( ( (FidelityUnnammedEmployeeVO) obj ).getGupFidelityId() ) ){
			if( this.getGupFidelityId().equals( ( (FidelityUnnammedEmployeeVO) obj ).getGupFidelityId() ) ) return true;
		}
		return false;

	}

}
