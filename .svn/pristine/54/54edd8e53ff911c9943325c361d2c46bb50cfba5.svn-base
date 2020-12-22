package com.rsaame.pas.cmn.pojo.wrapper;

import java.util.Date;

import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.rsaame.pas.cmn.pojo.POJO;

/**
 * A base class representing a POJO. All POJO classes must extend from this class. The purpose of this class is to
 * enable framework component to work around POJOs.
 */
public abstract class POJOWrapper extends POJO implements java.io.Serializable{
	private static final long serialVersionUID = 1L;

	/**
	 * A blank implementation of <code>setId()</code>. This blank implementation is necessary in cases where there is no
	 * Id class in a POJO.
	 * 
	 * @param id The Id instance corresponding to this POJO
	 */
	public abstract void setPOJOWrapperId( POJOWrapperId id );
	
	/**
	 * Returns the POJOId associated with the 
	 * @return
	 */
	public abstract POJOWrapperId getPOJOWrapperId();
	
	/**
	 * Since status columns are different in different POJOs, the actual POJO class can extend this method to allow access to the
	 * record's status value in a generic manner.
	 * @return
	 */
	
	public abstract void setStatus( int status );
	
	public abstract void setEndtId( Long endtId );
	
	public abstract void setEndtNo( Long endtNo );
	
	public abstract void setValidityStartDate( Date vsd );
	
	public abstract void setValidityExpiryDate( Date ved );
	
	public abstract void setPreparedBy( Integer preparedBy );
	
	public abstract void setPreparedDt( Date preparedDt );
	
	public abstract void setModifiedBy( Integer modifiedBy );
	
	public abstract void setModifiedDt( Date modifiedDt );
	
	public abstract Integer getPreparedBy();
	
	public abstract Date getPreparedDt();
	
	public abstract Integer getModifiedBy();
	
	public abstract Date getModifiedDt();
	
}
