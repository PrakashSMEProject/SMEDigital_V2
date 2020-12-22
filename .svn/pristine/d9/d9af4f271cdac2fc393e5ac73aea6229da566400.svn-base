
/**
 * Holds optional-cover characteristics for Home / Travel. 
 */
package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;


/**
 * @author M1016284
 * @since Phase 3
 */
public class OptCoverCharacteristics extends BaseVO{

	private static final long serialVersionUID = 1L;

	private String name;
	private String value;

	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 * returns Object
	 */
	@Override
	public Object getFieldValue(String fieldName) {
		
		Object fieldValue = null;
		if( "name".equals( fieldName ) ) fieldValue = getName();
		if( "value".equals( fieldName ) ) fieldValue = getValue();
		
		return fieldValue;
	}

	/**
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return String
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value
	 */
	public void setValue(String value) {
		this.value = value;
	}

	
}
