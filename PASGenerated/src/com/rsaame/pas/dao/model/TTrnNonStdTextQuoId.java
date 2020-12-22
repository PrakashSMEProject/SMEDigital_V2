package com.rsaame.pas.dao.model;

import java.util.Date;

import com.rsaame.pas.cmn.pojo.wrapper.POJOWrapperId;

// Generated May 8, 2012 7:11:08 PM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnNonStdTextId generated by hbm2java
 */
public class TTrnNonStdTextQuoId implements java.io.Serializable, POJOWrapperId {

	/*Default generated serial version Id*/
	private static final long serialVersionUID = 1L;

	private long nstPolicyId;
	private long nstEndtId;
	private int nstTypeCode;

	public TTrnNonStdTextQuoId() {
	}

	public TTrnNonStdTextQuoId(long nstPolicyId, long nstEndtId, int nstTypeCode) {
		this.nstPolicyId = nstPolicyId;
		this.nstEndtId = nstEndtId;
		this.nstTypeCode = nstTypeCode;
	}

	public long getNstPolicyId() {
		return this.nstPolicyId;
	}

	public void setNstPolicyId(long nstPolicyId) {
		this.nstPolicyId = nstPolicyId;
	}

	public long getNstEndtId() {
		return this.nstEndtId;
	}

	public void setNstEndtId(long nstEndtId) {
		this.nstEndtId = nstEndtId;
	}

	public int getNstTypeCode() {
		return this.nstTypeCode;
	}

	public void setNstTypeCode(int nstTypeCode) {
		this.nstTypeCode = nstTypeCode;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof TTrnNonStdTextQuoId))
			return false;
		TTrnNonStdTextQuoId castOther = (TTrnNonStdTextQuoId) other;

		return (this.getNstPolicyId() == castOther.getNstPolicyId())
				&& (this.getNstEndtId() == castOther.getNstEndtId())
				&& (this.getNstTypeCode() == castOther.getNstTypeCode());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result + (int) this.getNstPolicyId();
		result = 37 * result + (int) this.getNstEndtId();
		result = 37 * result + this.getNstTypeCode();
		return result;
	}

	@Override
	public void setId(Long id) {
		this.nstPolicyId = id;
	}

	@Override
	public void setVSD(Date vsd) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEndtId(Long endtId) {
		this.nstEndtId = endtId;
		
	}

	@Override
	public void setPolicyId(Long policyId) {
		this.nstPolicyId = policyId;
		
	}

	@Override
	public Long getId() {
		// TODO Auto-generated method stub
		return this.nstPolicyId;
	}

	@Override
	public Date getVSD() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getEndtId() {
		return this.nstEndtId;
	}

	@Override
	public Long getPolicyId() {
		return this.nstPolicyId;
	}

	@Override
	public String toStringPojoId() {
		// TODO Auto-generated method stub
		return null;
	}

}
