/**
 * 
 */
package com.rsaame.pas.vo.bus;

import java.util.List;

import com.mindtree.ruc.cmn.reflect.IFieldValue;

/**
 * @author Sarath Varier
 * @since Phase 4 generalization
 *
 */
public class PersonalAccidentVO extends PolicyDataVO implements IFieldValue {

	private static final long serialVersionUID = 1547L;
	
	private List<PersonalAccidentPersonVO> personalAccidentPersonVO = new com.mindtree.ruc.cmn.utils.List<PersonalAccidentPersonVO>( PersonalAccidentPersonVO.class );

	public List<PersonalAccidentPersonVO> getPersonalAccidentPersonVO(){
		return personalAccidentPersonVO;
	}


	@Override
	public String toString(){
		return "PersonalAccidentVO [personalAccidentPersonVO = " + personalAccidentPersonVO + "]";
	}

	@Override
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "personalAccidentPersonVO".equals( fieldName ) ) fieldValue = getPersonalAccidentPersonVO();
		return fieldValue;
	}

	/**
	 * @param personalAccidentPersonVO the personalAccidentPersonVO to set
	 */
	public void setPersonalAccidentPersonVO( List<PersonalAccidentPersonVO> personalAccidentPersonVO) {
		this.personalAccidentPersonVO = personalAccidentPersonVO;
	}
	

}
