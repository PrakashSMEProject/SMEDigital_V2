/**
 * 
 */
package com.rsaame.pas.dao.model;

import java.io.Serializable;

/**
 * @author M1021201
 *
 */
public class TMasDefaultValuesId implements Serializable {

	private static final long serialVersionUID = -4823736783386375855L;
	
	private String dftPmmId;
	private Integer dftPmmPolicyType;
	private Integer dftPmmClass;
	/**
	 * @return the dftPmmId
	 */
	public String getDftPmmId() {
		return dftPmmId;
	}
	/**
	 * @param dftPmmId the dftPmmId to set
	 */
	public void setDftPmmId(String dftPmmId) {
		this.dftPmmId = dftPmmId;
	}
	/**
	 * @return the dftPmmPolicyType
	 */
	public Integer getDftPmmPolicyType() {
		return dftPmmPolicyType;
	}
	/**
	 * @param dftPmmPolicyType the dftPmmPolicyType to set
	 */
	public void setDftPmmPolicyType(Integer dftPmmPolicyType) {
		this.dftPmmPolicyType = dftPmmPolicyType;
	}
	/**
	 * @return the dftPmmClasse
	 */
	public Integer getDftPmmClass() {
		return dftPmmClass;
	}
	/**
	 * @param dftPmmClasse the dftPmmClasse to set
	 */
	public void setDftPmmClass(Integer dftPmmClass) {
		this.dftPmmClass = dftPmmClass;
	}

}
