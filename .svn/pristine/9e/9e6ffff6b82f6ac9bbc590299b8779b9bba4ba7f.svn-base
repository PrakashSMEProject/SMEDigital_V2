/**
 * 
 */
package com.rsaame.pas.vo.cmn;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author M1014644
 *
 */
public class CoverVO extends BaseVO{

	
	private short covCode;
	private short covTypeCode;
	private short covSubTypeCode;
	private Integer covCriteriaCode; // added as part of Phase 3. Used for rendering Home Risk details.
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * @return the covCode
	 */
	public short getCovCode(){
		return covCode;
	}

	/**
	 * @param covCode the covCode to set
	 */
	public void setCovCode( short covCode ){
		this.covCode = covCode;
	}

	/**
	 * @return the covTypeCode
	 */
	public short getCovTypeCode(){
		return covTypeCode;
	}

	/**
	 * @param covTypeCode the covTypeCode to set
	 */
	public void setCovTypeCode( short covTypeCode ){
		this.covTypeCode = covTypeCode;
	}

	/**
	 * @return the covSubTypeCode
	 */
	public short getCovSubTypeCode(){
		return covSubTypeCode;
	}

	/**
	 * @param covSubTypeCode the covSubTypeCode to set
	 */
	public void setCovSubTypeCode( short covSubTypeCode ){
		this.covSubTypeCode = covSubTypeCode;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + covCode;
		result = prime * result + covSubTypeCode;
		result = prime * result + covTypeCode;
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ) return true;
		if( obj == null ) return false;
		if( getClass() != obj.getClass() ) return false;
		CoverVO other = (CoverVO) obj;
		if( covCode != other.covCode ) return false;
		if( covSubTypeCode != other.covSubTypeCode ) return false;
		if( covTypeCode != other.covTypeCode ) return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString(){
		return "CoverVO [covCode=" + covCode + ", covTypeCode=" + covTypeCode + ", covSubTypeCode=" + covSubTypeCode + "]";
	}

	/**
	 * @param covCriteriaCode
	 */
	public void setCovCriteriaCode( Integer covCriteriaCode ){
		this.covCriteriaCode = covCriteriaCode;
	}

	/**
	 * @return Integer
	 */
	public Integer getCovCriteriaCode(){
		return covCriteriaCode;
	}

}
