package com.rsaame.pas.sample.svc;

import com.mindtree.ruc.cmn.base.BaseVO;

public class TestVO extends BaseVO{
	private String field = null;
	
	@Override
	public Object getFieldValue( String fieldName ){
		if( "field".equals( fieldName ) ) return getField();
		return null;
	}

	public String getField(){
		return field;
	}

	public void setField( String field ){
		this.field = field;
	}

}
