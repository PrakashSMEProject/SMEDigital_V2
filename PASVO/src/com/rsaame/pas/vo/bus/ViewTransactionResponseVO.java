/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.base.BaseVO;

/**
 * @author
 *
 */
public class ViewTransactionResponseVO extends BaseVO{

	private static final long serialVersionUID = 1L;
	private List<SectionDetailsVO> sectionDetailsVOList;

	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "sectionDetailsVOList".equals( fieldName ) ) fieldValue = getSectionDetailsVOList();

		return fieldValue;
	}

	/**
	 * @return the sectionDetailsVOList
	 */
	public List<SectionDetailsVO> getSectionDetailsVOList(){
		return sectionDetailsVOList;
	}

	/**
	 * @param sectionDetailsVOList the sectionDetailsVOList to set
	 */
	public void setSectionDetailsVOList( List<SectionDetailsVO> sectionDetailsVOList ){
		this.sectionDetailsVOList = sectionDetailsVOList;
	}

}
