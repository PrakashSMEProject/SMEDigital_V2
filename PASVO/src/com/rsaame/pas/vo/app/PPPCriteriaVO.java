package com.rsaame.pas.vo.app;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

public class PPPCriteriaVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private Integer sectionId;
	private Integer classCode;
	private Integer tariffCode;
	private String	policyType;
	private List<Short> sectionList;
	
	

	/**
	 * @return the policyType
	 */
	public String getPolicyType(){
		return policyType;
	}

	/**
	 * @return the sectionList
	 */
	public List<Short> getSectionList(){
		return sectionList;
	}

	/**
	 * @param sectionList the sectionList to set
	 */
	public void setSectionList( List<Short> sectionList ){
		this.sectionList = sectionList;
	}

	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType( String policyType ){
		this.policyType = policyType;
	}

	public Integer getSectionId() {
		return sectionId;
	}

	public void setSectionId(Integer sectionId) {
		this.sectionId = sectionId;
	}

	public Integer getClassCode() {
		return classCode;
	}

	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	public Integer getTariffCode() {
		return tariffCode;
	}

	public void setTariffCode(Integer tariffCode) {
		this.tariffCode = tariffCode;
	}

	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;

		if( "sectionId".equals( fieldName ) ) fieldValue = getSectionId();
		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();
		if( "tariffCode".equals( fieldName ) ) fieldValue = getTariffCode();
		
		return fieldValue;
	}

	@Override
	public String toString(){
		return "PPPCriteriaVO [sectionId=" + sectionId + ", classCode=" + classCode + ", tariffCode=" + tariffCode + ", policyType=" + policyType + "]";
	}

}
