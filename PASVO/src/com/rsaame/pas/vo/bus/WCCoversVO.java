package com.rsaame.pas.vo.bus;

import com.mindtree.ruc.cmn.base.BaseVO;

public class WCCoversVO extends BaseVO {

	private static final long serialVersionUID = 1L;
	private Boolean PACover;
	
	public Object getFieldValue( String fieldName ){
		Object fieldValue = null;

		if( "PACover".equals( fieldName ) ) fieldValue = getPACover();

		return fieldValue;
	}

	public Boolean getPACover(){
		return PACover;
	}

	public void setPACover( Boolean pACover ){
		PACover = pACover;
	} 
}
