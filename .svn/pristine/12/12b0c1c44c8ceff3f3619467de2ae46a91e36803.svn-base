package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;
import com.mindtree.ruc.cmn.utils.List;

public class ParContentHolder extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 1L;
	private Integer classCode;
	private Integer policyType;
	private List<CommissionVO> commission = new List<CommissionVO>(CommissionVO.class);
	private List<ParContent> parContent = new List<ParContent>(ParContent.class);
	
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();
		if( "policyType".equals( fieldName ) ) fieldValue = getPolicyType();
		if( "commission".equals( fieldName ) ) fieldValue = getCommission();
		if( "parContent".equals( fieldName ) ) fieldValue = getParContent();

		return fieldValue;
	}

	/**
	 * @return the parContent
	 */
	public List<ParContent> getParContent() {
		return parContent;
	}

	/**
	 * @param parContent the parContent to set
	 */
	public void setParContent(List<ParContent> parContent) {
		this.parContent = parContent;
	}

	/**
	 * @return the commission
	 */
	public List<CommissionVO> getCommission() {
		return commission;
	}

	/**
	 * @param commission the commission to set
	 */
	public void setCommission(List<CommissionVO> commission) {
		this.commission = commission;
	}

	/**
	 * @return the classCode
	 */
	public Integer getClassCode() {
		return classCode;
	}

	/**
	 * @param classCode the classCode to set
	 */
	public void setClassCode(Integer classCode) {
		this.classCode = classCode;
	}

	/**
	 * @return the policyType
	 */
	public Integer getPolicyType() {
		return policyType;
	}

	/**
	 * @param policyType the policyType to set
	 */
	public void setPolicyType(Integer policyType) {
		this.policyType = policyType;
	}

	
	
}
