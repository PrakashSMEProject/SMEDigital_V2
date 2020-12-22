package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.utils.Utils;

public class LongStringConverter extends BaseConverter<Long, String>{

	
	public LongStringConverter( String aPropsString, String bPropsString ){
		super( aPropsString, bPropsString );
	}

	
	
	@Override
	public String getBFromA( Object aObj ){
		Long a = (Long) aObj;
		if(!Utils.isEmpty(a))
		{
			return a.toString();
		}
		return null;
	}

	
	
	@Override
	public Long getAFromB( Object bObj ){
		String b = (String) bObj;
		Long a = null;
		
		if(!Utils.isEmpty(b) && !b.equals("select")){
			a = new Long(b);
		}else{
			a = CommonConstants.DEFAULT_LOW_LONG;
		}
		
		return a;
	}

	@Override
	public Class<Long> getTypeOfA(){
		return Long.class;
	}

	@Override
	public Class<String> getTypeOfB(){
		return String.class;
	}

}
