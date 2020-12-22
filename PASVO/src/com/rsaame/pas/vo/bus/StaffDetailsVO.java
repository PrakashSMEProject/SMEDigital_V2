/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author M1020278
 *
 */
public class StaffDetailsVO extends PremiumVO implements Comparable<StaffDetailsVO>{

	private static final long serialVersionUID = 1L;
	private double empId;
	private String empName;
	private Date empDob;
	private Date empVsd;
	
	/**
	 * @return the empId
	 */
	public double getEmpId() {
		return empId;
	}

	/**
	 * @param empId the empId to set
	 */
	public void setEmpId(double empId) {
		this.empId = empId;
	}


	/**
	 * @return the empName
	 */
	public String getEmpName() {
		return empName;
	}


	/**
	 * @param empName the empName to set
	 */
	public void setEmpName(String empName) {
		this.empName = empName;
	}


	/**
	 * @return the empDob
	 */
	public Date getEmpDob() {
		return empDob;
	}


	/**
	 * @param empDob the empDob to set
	 */
	public void setEmpDob(Date empDob) {
		this.empDob = empDob;
	}


	/**
	 * @return the empVsd
	 */
	public Date getEmpVsd() {
		return empVsd;
	}


	/**
	 * @param empVsd the empVsd to set
	 */
	public void setEmpVsd(Date empVsd) {
		this.empVsd = empVsd;
	}


	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "empId".equals( fieldName ) ) fieldValue = getEmpId();
		if( "empName".equals( fieldName ) ) fieldValue = getEmpName();
		if( "empDob".equals( fieldName ) ) fieldValue = getEmpDob();
		if( "empVsd".equals( fieldName ) ) fieldValue = getEmpVsd();
		return fieldValue;
	}



	@Override
	public int compareTo(StaffDetailsVO other) {
		if( !Utils.isEmpty( this ) && !Utils.isEmpty( other ) && !Utils.isEmpty( this.getEmpId() ) && !Utils.isEmpty( other.getEmpId() ) ){
			return (int)this.getEmpId() - (int)other.getEmpId();
		}
		return 0;
	}


	
	
}
