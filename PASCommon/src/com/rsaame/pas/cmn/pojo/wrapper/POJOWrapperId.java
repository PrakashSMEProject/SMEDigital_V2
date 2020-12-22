package com.rsaame.pas.cmn.pojo.wrapper;

import java.util.Date;

import com.rsaame.pas.cmn.pojo.POJOId;

/**
 * A marker interface to denote a POJO's Id class. Not all POJO classes have a corresponding Id class. However, wherever
 * there are, this interface should be implemented in the Id class.
 */
public interface POJOWrapperId extends java.io.Serializable,POJOId{
	
	void setId(Long id);
	void setVSD(Date vsd);
	void setEndtId(Long endtId);
	void setPolicyId(Long policyId);
	Long getId();
	Date getVSD();
	Long getEndtId();
	Long getPolicyId();
	
	String toStringPojoId();
}
