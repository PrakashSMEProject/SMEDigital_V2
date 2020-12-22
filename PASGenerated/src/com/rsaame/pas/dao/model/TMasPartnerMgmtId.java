/**
 * 
 */
package com.rsaame.pas.dao.model;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author Sarath
 *
 */
public class TMasPartnerMgmtId implements Serializable {
	
	private static final long serialVersionUID = 1819353705193328204L;
	private String pmmId;
	private Integer pmmPtCode;
	private Integer pmmClassCode;

	public Integer getPmmPtCode() {
		return this.pmmPtCode;
	}

	public void setPmmPtCode(Integer pmmPtCode) {
		this.pmmPtCode = pmmPtCode;
	}

	public Integer getPmmClassCode() {
		return this.pmmClassCode;
	}

	public void setPmmClassCode(Integer pmmClassCode) {
		this.pmmClassCode = pmmClassCode;
	}

	/**
	 * @return the pmmId
	 */
	public String getPmmId() {
		return pmmId;
	}

	/**
	 * @param pmmId the pmmId to set
	 */
	public void setPmmId(String pmmId) {
		this.pmmId = pmmId;
	}


}
