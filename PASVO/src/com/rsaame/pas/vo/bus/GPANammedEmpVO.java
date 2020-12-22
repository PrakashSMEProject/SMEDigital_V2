package com.rsaame.pas.vo.bus;

import java.util.Comparator;
import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;

public class GPANammedEmpVO extends RiskGroupDetails implements Comparable<GPANammedEmpVO>{

	private static final long serialVersionUID = 5550056977098784877L;
	
	private String nameOfEmployee;
	private Integer employeeType;
	private char nammedEmpGender;
	private String nammedEmpDob;
	private String nammedEmpDesignation;
	private Double nammedEmpAnnualSalary;
	private String gprId;
	//private double nammedEmpSumInsured;
	private SumInsuredVO sumInsuredDetails;
	private Integer gpaIndex;
	

	/**
	 * @return the gprId
	 */
	public String getGprId(){
		return gprId;
	}

	/**
	 * @param gprId the gprId to set
	 */
	public void setGprId( String gprId ){
		this.gprId = gprId;
	}

	/**
	 * @return the namedNameOfEmployee
	 */
	public String getNameOfEmployee(){
		return nameOfEmployee;
	}

	/**
	 * @param namedNameOfEmployee the namedNameOfEmployee to set
	 */
	public void setNameOfEmployee( String nameOfEmployee ){
		this.nameOfEmployee = nameOfEmployee;
	}

	/**
	 * @return the namedTypeOfEmployee
	 */
	public Integer getEmployeeType(){
		return employeeType;
	}

	/**
	 * @param namedTypeOfEmployee the namedTypeOfEmployee to set
	 */
	public void setEmployeeType( Integer employeeType ){
		this.employeeType = employeeType;
	}

	/**
	 * @return the namedEmpGender
	 */
	public char getNammedEmpGender(){
		return nammedEmpGender;
	}

	/**
	 * @param namedEmpGender the namedEmpGender to set
	 */
	public void setNamedEmpGender( char nammedEmpGender ){
		this.nammedEmpGender = nammedEmpGender;
	}

	/**
	 * @return the nammedEmpDob
	 */
	public String getNammedEmpDob(){
		return nammedEmpDob;
	}

	/**
	 * @param nammedEmpDob the nammedEmpDob to set
	 */
	public void setNammedEmpDob( String nammedEmpDob ){
		this.nammedEmpDob = nammedEmpDob;
	}

	/**
	 * @return the nammedEmpDesignation
	 */
	public String getNammedEmpDesignation(){
		return nammedEmpDesignation;
	}

	/**
	 * @param nammedEmpDesignation the nammedEmpDesignation to set
	 */
	public void setNammedEmpDesignation( String nammedEmpDesignation ){
		this.nammedEmpDesignation = nammedEmpDesignation;
	}

	/**
	 * @return the nammedEmpAnnualSalary
	 */
	public Double getNammedEmpAnnualSalary(){
		return nammedEmpAnnualSalary;
	}

	/**
	 * @param nammedEmpAnnualSalary the nammedEmpAnnualSalary to set
	 */
	public void setNammedEmpAnnualSalary( Double nammedEmpAnnualSalary ){
		this.nammedEmpAnnualSalary = nammedEmpAnnualSalary;
	}

	/**
	 * @return the sumInsuredDetails
	 */
	public SumInsuredVO getSumInsuredDetails() {
		return sumInsuredDetails;
	}

	/**
	 * @param sumInsuredDetails the sumInsuredDetails to set
	 */
	public void setSumInsuredDetails( SumInsuredVO sumInsuredDetails ){
		this.sumInsuredDetails = sumInsuredDetails;
	}
	
	/**
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid(){
		return serialVersionUID;
	}
	
	public Integer getgpaIndex(){
		return gpaIndex;
	}

	public void setgpaIndex( Integer gpaIndex ){
		this.gpaIndex = gpaIndex;
	}

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "nameOfEmployee".equals( fieldName ) ) fieldValue = getNameOfEmployee();
		if( "employeeType".equals( fieldName ) ) fieldValue = getEmployeeType();
		if( "nammedEmpGender".equals( fieldName ) ) fieldValue = getNammedEmpGender();
		if( "nammedEmpDob".equals( fieldName ) ) fieldValue = getNammedEmpDob();
		if( "nammedEmpDesignation".equals( fieldName ) ) fieldValue = getNammedEmpDesignation();
		if( "nammedEmpAnnualSalary".equals( fieldName ) ) fieldValue = getNammedEmpAnnualSalary();
		if( "sumInsuredDetails".equals( fieldName ) ) fieldValue = getSumInsuredDetails();
		if( "gpaIndex".equals( fieldName ) ) fieldValue = getgpaIndex();
		
		return fieldValue;
	}

	
	@Override
	public String toString() {
		return "GPANammedEmpVO [nameOfEmployee=" + nameOfEmployee + "employeeType="+employeeType+"nammedEmpGender="+nammedEmpGender
		+"nammedEmpDob="+nammedEmpDob+"nammedEmpDesignation="+nammedEmpDesignation+"nammedEmpAnnualSalary="+nammedEmpAnnualSalary+ "sumInsuredDetails="+sumInsuredDetails+"gprId="+gprId+"]";
	}	
	
	@Override
	public boolean equals( Object obj ){
		/*boolean equal = false;

		if(Utils.isEmpty( obj ) || !(obj instanceof GPANammedEmpVO))
		return false;
		
		if(Utils.isEmpty( this.getGprId() ) && Utils.isEmpty(((GPANammedEmpVO)obj).getGprId()))
				return true;
		
		if(!Utils.isEmpty( ((GPANammedEmpVO)obj).getGprId() )){
			
			if(this.getGprId().equalsIgnoreCase( ((GPANammedEmpVO)obj).getGprId() )){
				return true;
			}
		}
		
		return equal;*/
		
		if (this == obj)
		    return true;
		if (obj == null)
		    return false;
		if (getClass() != obj.getClass())
		    return false;
		GPANammedEmpVO other = (GPANammedEmpVO) obj;
		if (gprId == null) {
		    if (other.gprId != null)
			return false;
		} else if (!gprId.equals(other.gprId))
		    return false;
		return true;
		
	}
	
	@Override
	public int hashCode(){
		
		return 1;
	}

	@Override
	public int compareTo(GPANammedEmpVO Obj) {
		int result;
		result = gpaIndex.compareTo(Obj.getgpaIndex());
		return result;
	}
	
}
