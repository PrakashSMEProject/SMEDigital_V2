package com.rsaame.pas.dao.model;

// Generated Mar 5, 2012 10:41:01 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

/**
 * TTrnWctplUnnamedPersonQuoId generated by hbm2java
 */
public class TTrnWctplUnnamedPersonQuoId implements java.io.Serializable,POJOWrapperId {
	
	private static final long serialVersionUID = 1L;
	
	private Date wupValidityStartDate;
	private long wupId;

	public TTrnWctplUnnamedPersonQuoId() {
	}

	public TTrnWctplUnnamedPersonQuoId(Date wupValidityStartDate, long wupId) {
		this.wupValidityStartDate = wupValidityStartDate;
		this.wupId = wupId;
	}

	public Date getWupValidityStartDate() {
		return this.wupValidityStartDate;
	}

	public void setWupValidityStartDate(Date wupValidityStartDate) {
		this.wupValidityStartDate = wupValidityStartDate;
	}

	public long getWupId() {
		return this.wupId;
	}

	public void setWupId(long wupId) {
		this.wupId = wupId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnWctplUnnamedPersonQuoId))
			return false;
		TTrnWctplUnnamedPersonQuoId castOther = (TTrnWctplUnnamedPersonQuoId) other;

		return ((this.getWupValidityStartDate() == castOther
				.getWupValidityStartDate()) || (this.getWupValidityStartDate() != null
				&& castOther.getWupValidityStartDate() != null && this
				.getWupValidityStartDate().equals(
						castOther.getWupValidityStartDate())))
				&& (this.getWupId() == castOther.getWupId());
	}

	public int hashCode() {
		int result = 17;

		result = 37
				* result
				+ (getWupValidityStartDate() == null ? 0 : this
						.getWupValidityStartDate().hashCode());
		result = 37 * result + (int) this.getWupId();
		return result;
	}

	@Override
	public void setId(Long id) {
		setWupId(id);
	}

	@Override
	public void setVSD(Date vsd) {
		setWupValidityStartDate(vsd);
	}

	@Override
	public void setEndtId(Long endtId) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
		
	}

	@Override
	public void setPolicyId(Long policyId) {
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD 
		
	}

	@Override
	public Long getId() {
		return getWupId();
	}

	@Override
	public Date getVSD() {
		return getWupValidityStartDate();
	}

	@Override
	public Long getEndtId() {
		return null;
	}

	@Override
	public Long getPolicyId() {
		return null;
	}

	@Override
	public String toStringPojoId() {
		return "TTrnWctplUnnamedPersonQuoId [wupValidityStartDate=" + wupValidityStartDate + ", wupId=" + wupId + " ]";
	}

}
