package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class IntegerShortConverter extends BaseConverter<Integer, Short>{

	public IntegerShortConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Short getBFromA(Object aObj) {
		Integer a = (Integer)aObj;
		Short b =null;
		if(!Utils.isEmpty(a) && a <= 32767 && a >= -32768)
		{
			 b= Short.valueOf(a.toString());
		}
		
		return b;
	}

	@Override
	public Integer getAFromB(Object bObj) {
		Short b = (Short)bObj;
		Integer a = null;
		if(!Utils.isEmpty(b))
		{
			a = Integer.valueOf(b.toString());
		}
		return a;
	}

	@Override
	public Class<Integer> getTypeOfA() {
		return Integer.class;
	}

	@Override
	public Class<Short> getTypeOfB() {
		return Short.class;
	}

}
