package com.rsaame.pas.dao.model;

import com.rsaame.pas.cmn.pojo.POJO;
import com.rsaame.pas.cmn.pojo.POJOId;

// Generated Jun 7, 2012 4:33:30 PM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnSectionLocationQuoId generated by hbm2java
 */
public class TTrnSectionLocationQuoId implements java.io.Serializable, POJOId {

	private long tslPolLinkingId;
	private long tslPolEndtNo;
	private short tslSecId;
	private long tslLocId;

	public TTrnSectionLocationQuoId() {
	}

	public TTrnSectionLocationQuoId(long tslPolLinkingId, long tslPolEndtNo,
			short tslSecId, long tslLocId) {
		this.tslPolLinkingId = tslPolLinkingId;
		this.tslPolEndtNo = tslPolEndtNo;
		this.tslSecId = tslSecId;
		this.tslLocId = tslLocId;
	}

	public long getTslPolLinkingId() {
		return this.tslPolLinkingId;
	}

	public void setTslPolLinkingId(long tslPolLinkingId) {
		this.tslPolLinkingId = tslPolLinkingId;
	}

	public long getTslPolEndtNo() {
		return this.tslPolEndtNo;
	}

	public void setTslPolEndtNo(long tslPolEndtNo) {
		this.tslPolEndtNo = tslPolEndtNo;
	}

	public short getTslSecId() {
		return this.tslSecId;
	}

	public void setTslSecId(short tslSecId) {
		this.tslSecId = tslSecId;
	}

	public long getTslLocId() {
		return this.tslLocId;
	}

	public void setTslLocId(long tslLocId) {
		this.tslLocId = tslLocId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnSectionLocationQuoId))
			return false;
		TTrnSectionLocationQuoId castOther = (TTrnSectionLocationQuoId) other;

		return (this.getTslPolLinkingId() == castOther.getTslPolLinkingId())
				&& (this.getTslPolEndtNo() == castOther.getTslPolEndtNo())
				&& (this.getTslSecId() == castOther.getTslSecId())
				&& (this.getTslLocId() == castOther.getTslLocId());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getTslPolLinkingId();
		result = 37 * result + (int) this.getTslPolEndtNo();
		result = 37 * result + this.getTslSecId();
		result = 37 * result + (int) this.getTslLocId();
		return result;
	}

}