package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author m1019834
 *  
 *
 */

public class GPAUnnammedEmpVO extends RiskGroupDetails{

	private static final long serialVersionUID = 5448564596446904970L;
	
	private Integer unnammedEmployeeType;
	private Integer unnammedNumberOfEmloyee;
	private Double  unnammedAnnualSalary;
	private SumInsuredVO sumInsuredDetails;
	private String gupId;
	private Integer gpaIndex;
	

	/**
	 * @return the gupId
	 */
	public String getGupId(){
		return gupId;
	}

	/**
	 * @param gupId the gupId to set
	 */
	public void setGupId( String gupId ){
		this.gupId = gupId;
	}

	
	/**
	 * @return the deductible
	 */
	public SumInsuredVO getSumInsuredDetails() {
		return sumInsuredDetails;
	}

	/**
	 * @param deductible the deductible to set
	 */
	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}



	/**
	 * @return the unnammedEmployeeType
	 */
	public Integer getUnnammedEmployeeType(){
		return unnammedEmployeeType;
	}



	/**
	 * @param unnammedEmployeeType the unnammedEmployeeType to set
	 */
	public void setUnnammedEmployeeType( Integer unnammedEmployeeType ){
		this.unnammedEmployeeType = unnammedEmployeeType;
	}



	/**
	 * @return the unnammedNumberOfEmloyee
	 */
	public Integer getUnnammedNumberOfEmloyee(){
		return unnammedNumberOfEmloyee;
	}



	/**
	 * @param unnammedNumberOfEmloyee the unnammedNumberOfEmloyee to set
	 */
	public void setUnnammedNumberOfEmloyee( Integer unnammedNumberOfEmloyee ){
		this.unnammedNumberOfEmloyee = unnammedNumberOfEmloyee;
	}



	/**
	 * @return the unnammedAnnualSalary
	 */
	public Double getUnnammedAnnualSalary(){
		return unnammedAnnualSalary;
	}



	/**
	 * @param unnammedAnnualSalary the unnammedAnnualSalary to set
	 */
	public void setUnnammedAnnualSalary( Double unnammedAnnualSalary ){
		this.unnammedAnnualSalary = unnammedAnnualSalary;
	}

	public Integer getgpaIndex(){
		return gpaIndex;
	}

	public void setgpaIndex( Integer gpaIndex ){
		this.gpaIndex = gpaIndex;
	}
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "unnammedEmployeeType".equals( fieldName ) ) fieldValue = getUnnammedEmployeeType();
		if( "unnammedNumberOfEmloyee".equals( fieldName ) ) fieldValue = getUnnammedNumberOfEmloyee();
		if( "unnamedAnnualSalary".equals( fieldName ) ) fieldValue = getUnnammedAnnualSalary();
		if( "sumInsuredDetails".equals( fieldName ) ) fieldValue = getSumInsuredDetails();
		if( "gpaIndex".equals( fieldName ) ) fieldValue = getgpaIndex();
		
		return fieldValue;
	}

	@Override
	public String toString() {
		return "GPAUnnammedEmpVO [unnammedEmployeeType=" + unnammedEmployeeType + "unnammedNumberOfEmloyee="+unnammedNumberOfEmloyee+"unnammedAnnualSalary="+unnammedAnnualSalary
		+"sumInsuredDetails="+sumInsuredDetails+"gupId="+gupId+"]";
	}
	
	
	@Override
	public boolean equals( Object obj ){
		boolean equal = false;

		if(Utils.isEmpty( obj ) || !(obj instanceof GPAUnnammedEmpVO))
		return false;
		
		if(Utils.isEmpty( this.getGupId() ) && Utils.isEmpty(((GPAUnnammedEmpVO)obj).getGupId()))
				return true;
		
		if(!Utils.isEmpty( ((GPAUnnammedEmpVO)obj).getGupId() )){
			
			if(this.getGupId().equalsIgnoreCase( ((GPAUnnammedEmpVO)obj).getGupId() )){
				return true;
			}
		}
		
		return equal;
	}
	
	@Override
	public int hashCode(){
		
		return 1;
	}
	

}
