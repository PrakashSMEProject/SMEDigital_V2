package com.rsaame.pas.dao.model;

// Generated Feb 15, 2012 3:19:16 PM by Hibernate Tools 3.4.0.CR1

import java.util.Date;

/**
 * TMasUwMapping generated by hbm2java
 */
public class TMasUwMapping implements java.io.Serializable {

	private TMasUwMappingId id;
	private Integer uwmPreparedBy;
	private Date uwmPreparedDt;
	private Integer uwmModifiedBy;
	private Date uwmModifiedDt;

	public TMasUwMapping() {
	}

	public TMasUwMapping(TMasUwMappingId id) {
		this.id = id;
	}

	public TMasUwMapping(TMasUwMappingId id, Integer uwmPreparedBy,
			Date uwmPreparedDt, Integer uwmModifiedBy, Date uwmModifiedDt) {
		this.id = id;
		this.uwmPreparedBy = uwmPreparedBy;
		this.uwmPreparedDt = uwmPreparedDt;
		this.uwmModifiedBy = uwmModifiedBy;
		this.uwmModifiedDt = uwmModifiedDt;
	}

	public TMasUwMappingId getId() {
		return this.id;
	}

	public void setId(TMasUwMappingId id) {
		this.id = id;
	}

	public Integer getUwmPreparedBy() {
		return this.uwmPreparedBy;
	}

	public void setUwmPreparedBy(Integer uwmPreparedBy) {
		this.uwmPreparedBy = uwmPreparedBy;
	}

	public Date getUwmPreparedDt() {
		return this.uwmPreparedDt;
	}

	public void setUwmPreparedDt(Date uwmPreparedDt) {
		this.uwmPreparedDt = uwmPreparedDt;
	}

	public Integer getUwmModifiedBy() {
		return this.uwmModifiedBy;
	}

	public void setUwmModifiedBy(Integer uwmModifiedBy) {
		this.uwmModifiedBy = uwmModifiedBy;
	}

	public Date getUwmModifiedDt() {
		return this.uwmModifiedDt;
	}

	public void setUwmModifiedDt(Date uwmModifiedDt) {
		this.uwmModifiedDt = uwmModifiedDt;
	}

}
