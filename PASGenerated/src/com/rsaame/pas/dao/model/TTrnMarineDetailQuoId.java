package com.rsaame.pas.dao.model;

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJOId;

// Generated Jul 10, 2012 6:17:09 PM by Hibernate Tools 3.4.0.CR1

/**
 * TTrnMarineDetailQuoId generated by hbm2java
 */
public class TTrnMarineDetailQuoId implements java.io.Serializable, POJOId {

    private static final long serialVersionUID = 1L;
    private long mdPolicyId;
    private long mdEndtId;
    private long mdDeclarationId;
    private int mdSerialNo;
    private long mdCommodityId;
    private Date mdValidityStartDate;

    public TTrnMarineDetailQuoId() {
    }

    public TTrnMarineDetailQuoId(long mdPolicyId, long mdEndtId,
	    long mdDeclarationId, int mdSerialNo,long mdCommodityId,Date mdValidityStartDate) {
	this.mdPolicyId = mdPolicyId;
	this.mdEndtId = mdEndtId;
	this.mdDeclarationId = mdDeclarationId;
	this.mdSerialNo = mdSerialNo;
	this.mdCommodityId = mdCommodityId;
	this.mdValidityStartDate = mdValidityStartDate;
    }

    public long getMdPolicyId() {
	return this.mdPolicyId;
    }

    public void setMdPolicyId(long mdPolicyId) {
	this.mdPolicyId = mdPolicyId;
    }

    public long getMdEndtId() {
	return this.mdEndtId;
    }

    public void setMdEndtId(long mdEndtId) {
	this.mdEndtId = mdEndtId;
    }

    public long getMdDeclarationId() {
	return this.mdDeclarationId;
    }

    public void setMdDeclarationId(long mdDeclarationId) {
	this.mdDeclarationId = mdDeclarationId;
    }

    public int getMdSerialNo() {
	return this.mdSerialNo;
    }

    public void setMdSerialNo(int mdSerialNo) {
	this.mdSerialNo = mdSerialNo;
    }

    public long getMdCommodityId() {
        return mdCommodityId;
    }

    public void setMdCommodityId(long mdCommodityId) {
        this.mdCommodityId = mdCommodityId;
    }

    public static long getSerialversionuid() {
        return serialVersionUID;
    }

    public Date getMdValidityStartDate() {
	return this.mdValidityStartDate;
    }

    public void setMdValidityStartDate(Date mdValidityStartDate) {
	this.mdValidityStartDate = mdValidityStartDate;
    }

    @Override
    public int hashCode() {
	final int prime = 37;
	int result = 17;
	result = prime * result
		+ (int) (mdCommodityId ^ (mdCommodityId >>> 32));
	result = prime * result
		+ (int) (mdDeclarationId ^ (mdDeclarationId >>> 32));
	result = prime * result + (int) (mdEndtId ^ (mdEndtId >>> 32));
	result = prime * result + (int) (mdPolicyId ^ (mdPolicyId >>> 32));
	result = prime * result + mdSerialNo;
	result = prime
		* result
		+ ((mdValidityStartDate == null) ? 0 : mdValidityStartDate
			.hashCode());
	return result;
    }

    @Override
    public boolean equals(Object obj) {
	if (this == obj)
	    return true;
	if (obj == null)
	    return false;
	if (getClass() != obj.getClass())
	    return false;
	TTrnMarineDetailQuoId other = (TTrnMarineDetailQuoId) obj;
	if (mdCommodityId != other.mdCommodityId)
	    return false;
	if (mdDeclarationId != other.mdDeclarationId)
	    return false;
	if (mdEndtId != other.mdEndtId)
	    return false;
	if (mdPolicyId != other.mdPolicyId)
	    return false;
	if (mdSerialNo != other.mdSerialNo)
	    return false;
	if (mdValidityStartDate == null) {
	    if (other.mdValidityStartDate != null)
		return false;
	} else if (!mdValidityStartDate.equals(other.mdValidityStartDate))
	    return false;
	return true;
    }
    
   

}
