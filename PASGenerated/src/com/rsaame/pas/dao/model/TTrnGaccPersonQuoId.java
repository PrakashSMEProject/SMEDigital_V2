package com.rsaame.pas.dao.model;

// Generated Jun 26, 2012 3:09:02 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

/**
 * TTrnGaccPersonQuoId generated by hbm2java
 */
public class TTrnGaccPersonQuoId implements java.io.Serializable,POJOWrapperId {

	private Long gprId;
	private Date gprValidityStartDate;

	public TTrnGaccPersonQuoId() {
	}

	public TTrnGaccPersonQuoId(long gprId, Date gprValidityStartDate) {
		this.gprId = gprId;
		this.gprValidityStartDate = gprValidityStartDate;
	}

	public Long getGprId() {
		return this.gprId;
	}

	public void setGprId(Long gprId) {
		this.gprId = gprId;
	}

	public Date getGprValidityStartDate() {
		return this.gprValidityStartDate;
	}

	public void setGprValidityStartDate(Date gprValidityStartDate) {
		this.gprValidityStartDate = gprValidityStartDate;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode(){
		final int prime = 31;
		int result = 1;
		result = prime * result + ( ( gprId == null ) ? 0 : gprId.hashCode() );
		result = prime * result + ( ( gprValidityStartDate == null ) ? 0 : gprValidityStartDate.hashCode() );
		return result;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals( Object obj ){
		if( this == obj ){
			return true;
		}
		if( obj == null ){
			return false;
		}
		if( getClass() != obj.getClass() ){
			return false;
		}
		TTrnGaccPersonQuoId other = (TTrnGaccPersonQuoId) obj;
		if( gprId == null ){
			if( other.gprId != null ) return false;
		}
		else if( !gprId.equals( other.gprId ) ){
			return false;
		}
		if( gprValidityStartDate == null ){
			if( other.gprValidityStartDate != null ) return false;
		}
		else if( !gprValidityStartDate.equals( other.gprValidityStartDate ) ){
			return false;
		}
		return true;
	}

	@Override
	public void setId( Long id ){
		setGprId( id );
		
	}

	@Override
	public void setVSD( Date vsd ){
		setGprValidityStartDate( vsd );
		
	}

	@Override
	public void setEndtId( Long endtId ){
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setPolicyId( Long policyId ){
		// TODO Auto-generated method stub
		
	}

	@Override
	public Long getId(){
		return getGprId();
	}

	@Override
	public Date getVSD(){
		return getGprValidityStartDate();
	}

	@Override
	public Long getEndtId(){
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getPolicyId(){
		// TODO Auto-generated method stub
		return null;
	}

	
	@Override
	public String toStringPojoId(){
		return toString();
	}

	@Override
	public String toString(){
		return "TTrnGaccPersonQuoId [gprId=" + gprId + ", gprValidityStartDate=" + gprValidityStartDate + "]";
	}
	
	
	

}