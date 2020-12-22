/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.io.Serializable;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author Sarath Varier
 * @since Phase 3 generalization
 *
 */
public class PersonalAccidentPersonVO implements IFieldValue,Serializable{
	
	private java.util.List<CoverDetailsVO> covers = new com.mindtree.ruc.cmn.utils.List<CoverDetailsVO>( CoverDetailsVO.class );
	
	private PersonDetailsVO personDetailsVO;

	public java.util.List<CoverDetailsVO> getCovers() {
		return covers;
	}

	public void setCovers(java.util.List<CoverDetailsVO> covers) {
		this.covers = covers;
	}

	public PersonDetailsVO getPersonDetailsVO() {
		return personDetailsVO;
	}

	public void setPersonDetailsVO(PersonDetailsVO personDetailsVO) {
		this.personDetailsVO = personDetailsVO;
	}
	
	
	
	@Override
	public String toString(){
		return "PersonalAccidentPersonVO [covers = " + covers + ", personDetailsVO = "+personDetailsVO+"]";
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		
		
		if( "covers".equals( fieldName ) ) fieldValue = getCovers();
		if( "personDetailsVO".equals( fieldName ) ) fieldValue = getPersonDetailsVO();
		
		return fieldValue;
	}

	
}
