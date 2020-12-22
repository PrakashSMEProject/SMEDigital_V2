package com.rsaame.pas.cmn.pojo;

import java.util.Date;

import com.mindtree.ruc.cmn.constants.CommonConstants;

/**
 * A base class representing a POJO. All POJO classes must extend from this class. The purpose of this class is to
 * enable framework component to work around POJOs.
 */
public class POJO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * A blank implementation of <code>setId()</code>. This blank implementation is necessary in cases where there is no
	 * Id class in a POJO.
	 * 
	 * @param id The Id instance corresponding to this POJO
	 */
	public void setPOJOId( POJOId id ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	/**
	 * Returns the POJOId associated with the 
	 * @return
	 */
	public POJOId getPOJOId(){
		return null;
	}
	
	/**
	 * Since status columns are different in different POJOs, the actual POJO class can extend this method to allow access to the
	 * record's status value in a generic manner.
	 * @return
	 */
	public int getStatus(){
		return CommonConstants.DEFAULT_LOW_INTEGER;
	}
	
	public void setPreparedDate( Date preparedDate ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	public Date getPreparedDate(){
		return null;
	} 
	
	public void setStatus( Integer status ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	public void setEndtId( Long endtId ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	public void setEndtNo( Long endtNo ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	public void setValidityStartDate( Date vsd ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
	
	public void setValidityExpiryDate( Date ved ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
}
