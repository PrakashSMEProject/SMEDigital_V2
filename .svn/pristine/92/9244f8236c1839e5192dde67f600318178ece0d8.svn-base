/**
 * Holds Optional / Additional cover details for Home / Travel.
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;
import com.mindtree.ruc.cmn.utils.List;


/**
 * @author M1016284
 * @since Phase 3
 */
public class OptionalCoverDetailsVO extends BaseVO{
	
	private static final long serialVersionUID = 1L;

	private	String coverName;
	private	String coverDesc;
	private List<OptCoverCharacteristics> optCoverCharacteristics;


	/**
	 * @return String
	 */
	public String getCoverName() {
		return coverName;
	}


	/**
	 * @param coverName
	 */
	public void setCoverName(String coverName) {
		this.coverName = coverName;
	}


	/**
	 * @return String
	 */
	public String getCoverDesc() {
		return coverDesc;
	}


	/**
	 * @param coverDesc
	 */
	public void setCoverDesc(String coverDesc) {
		this.coverDesc = coverDesc;
	}


	/**
	 * @return List<OptCoverCharacteristics>
	 */
	public List<OptCoverCharacteristics> getOptCoverCharacteristics() {
		return optCoverCharacteristics;
	}


	/**
	 * @param optCoverCharacteristics
	 */
	public void setOptCoverCharacteristics(
			List<OptCoverCharacteristics> optCoverCharacteristics) {
		this.optCoverCharacteristics = optCoverCharacteristics;
	}


	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 * returns Object
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if( "coverName".equals( fieldName ) ) fieldValue = getCoverName();
		if( "coverDesc".equals( fieldName ) ) fieldValue = getCoverDesc();
		if( "optCoverCharacteristics".equals( fieldName ) ) fieldValue = getOptCoverCharacteristics();
		return fieldValue;
	}

}
