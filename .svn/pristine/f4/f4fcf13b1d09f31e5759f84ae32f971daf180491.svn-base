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
public class FidelityNammedEmployeeDetailsVO extends RiskGroupDetails{

	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */

	private String empName;
	private String empDesignation;
	private Double limitPerPerson;
	private Integer empType;
	private SumInsuredVO sumInsuredDetails = new SumInsuredVO();
	private Long gprFidelityId;
	private Integer index;

	/**
		 * @return the empName
		 */
	public String getEmpName(){
		return empName;
	}

	/**
	 * @param empName the empName to set
	 */
	public void setEmpName( String empName ){
		this.empName = empName;
	}

	/**
	 * @return the empDesignation
	 */
	public String getEmpDesignation(){
		return empDesignation;
	}

	/**
	 * @param empDesignation the empDesignation to set
	 */
	public void setEmpDesignation( String empDesignation ){
		this.empDesignation = empDesignation;
	}

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

	/**
	 * @return the sumInsuredDetails
	 */
	public SumInsuredVO getSumInsuredDetails(){
		return sumInsuredDetails;
	}

	/**
	 * @param sumInsuredDetails the sumInsuredDetails to set
	 */
	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}

	/**
	 * @return the empType
	 */
	public Integer getEmpType(){
		return empType;
	}

	/**
	 * @param empType the empType to set
	 */
	public void setEmpType( Integer empType ){
		this.empType = empType;
	}

	/**
	 * @return the gprFidelityId
	 */
	public Long getGprFidelityId(){
		return gprFidelityId;
	}

	/**
	 * @param gprFidelityId the gprFidelityId to set
	 */
	public void setGprFidelityId( Long gprFidelityId ){
		this.gprFidelityId = gprFidelityId;
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

		if( "empName".equals( fieldName ) ) fieldValue = getEmpName();
		if( "empDesignation".equals( fieldName ) ) fieldValue = getEmpDesignation();
		if( "limitPerPerson".equals( fieldName ) ) fieldValue = getLimitPerPerson();
		if( "empType".equals( fieldName ) ) fieldValue = getEmpType();
		if( "index".equals( fieldName ) ) fieldValue = getIndex();

		return fieldValue;
	}

	@Override
	public String toString(){
		return "FidelityNammedEmployeeDetailsVO [empName=" + empName + ", empDesignation=" + empDesignation + ", limitPerPerson=" + limitPerPerson + ", empType=" + empType
				+ ", gprFidelity=" + gprFidelityId + "]";
	}

	@Override
	public boolean equals( Object obj ){

		if( Utils.isEmpty( obj ) || !( obj instanceof FidelityNammedEmployeeDetailsVO ) ) return false;
		if( Utils.isEmpty( this.getGprFidelityId() ) && Utils.isEmpty( ( (FidelityNammedEmployeeDetailsVO) obj ).getGprFidelityId() ) ) return true;

		if( !Utils.isEmpty( ( (FidelityNammedEmployeeDetailsVO) obj ).getGprFidelityId() ) ){
			if( this.getGprFidelityId().equals( ( (FidelityNammedEmployeeDetailsVO) obj ).getGprFidelityId() ) ) return true;
		}
		return false;

	}

	@Override
	public int hashCode(){
		return 1;
	}

}
