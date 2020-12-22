/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.Date;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author m1016303
 *
 */
public class ClauseVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	
	private String description;
	private String clauseType;
	private Integer isDefault;
	private Date vsd;
	
	
	public Date getVsd(){
		return vsd;
	}

	public void setVsd( Date vsd ){
		this.vsd = vsd;
	}
	
	/* (non-Javadoc)
	 * @see com.mindtree.ruc.cmn.reflect.IFieldValue#getFieldValue(java.lang.String)
	 */
	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "description".equals( fieldName ) ) fieldValue = getDescription();
		if( "clauseType".equals( fieldName ) ) fieldValue = getClauseType();
		if( "isDefault".equals( fieldName ) ) fieldValue = getIsDefault();

		return fieldValue;
	}


	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}


	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}



	/**
	 * @return the clauseType
	 */
	public String getClauseType(){
		return clauseType;
	}


	/**
	 * @param clauseType the clauseType to set
	 */
	public void setClauseType( String clauseType ){
		this.clauseType = clauseType;
	}


	/**
	 * @return the isDefault
	 */
	public Integer getIsDefault(){
		return isDefault;
	}


	/**
	 * @param isDefault the isDefault to set
	 */
	public void setIsDefault( Integer isDefault ){
		this.isDefault = isDefault;
	}
	

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ClauseVO [description=" + description + ", clauseType=" + clauseType
				+ ", isDefault=" + isDefault  +"]";
	}
}
