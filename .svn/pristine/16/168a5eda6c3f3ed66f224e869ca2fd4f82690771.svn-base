/**
 * 
 */
package com.rsaame.pas.vo.app;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author m1016303
 *
 */
public class CommissionVO extends BaseVO implements IFieldValue{

	private static final long serialVersionUID = 1L;
	private Integer comCode;
	private Integer classCode;
	private Double comPrec;
	private String comDescription;
	private String comADescription;
	
	
	@Override
	public Object getFieldValue(String fieldName) {
		Object fieldValue = null;
		
		if( "comCode".equals( fieldName ) ) fieldValue = getComCode();
		if( "classCode".equals( fieldName ) ) fieldValue = getClassCode();
		if( "comPrec".equals( fieldName ) ) fieldValue = getComPrec();
		if( "comDescription".equals( fieldName ) ) fieldValue = getComDescription();
		if( "comADescription".equals( fieldName ) ) fieldValue = getComADescription();
		
		return fieldValue;
	}


	public Integer getComCode() {
		return comCode;
	}


	public void setComCode(Integer comCode) {
		this.comCode = comCode;
	}


	public String getComDescription() {
		return comDescription;
	}


	public void setComDescription(String comDescription) {
		this.comDescription = comDescription;
	}


	/**
	 * @return the comADescription
	 */
	public String getComADescription() {
		return comADescription;
	}


	/**
	 * @param comADescription the comADescription to set
	 */
	public void setComADescription(String comADescription) {
		this.comADescription = comADescription;
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
	 * @return the comPrec
	 */
	public Double getComPrec() {
		return comPrec;
	}


	/**
	 * @param comPrec the comPrec to set
	 */
	public void setComPrec(Double comPrec) {
		this.comPrec = comPrec;
	}


	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "CommissionVO [comCode=" + comCode + ", classCode=" + classCode
				+ ", comPrec=" + comPrec + ", comDescription=" + comDescription
				+ ", comADescription=" + comADescription + "]";
	}


	

	
}
