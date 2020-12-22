package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class IntegerByteConverter extends BaseConverter<Integer, Byte>{

	public IntegerByteConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);

	}

	@Override
	public Byte getBFromA(Object aObj) {
		Integer a=(Integer)aObj;
		Byte b = null;
		if(!Utils.isEmpty(a) && a <= 127 && a >= -128 )
		{
			 b= Byte.valueOf(a.toString());
		}
		return b;
	}

	@Override
	public Integer getAFromB(Object bObj) {
		Byte b=(Byte)bObj;
		Integer a = null;
		if(!Utils.isEmpty(b))
		{
			a= (Integer.valueOf(b.toString()));
		}
		return a;
	}

	@Override
	public Class<Integer> getTypeOfA() {
		return Integer.class;
	}

	@Override
	public Class<Byte> getTypeOfB() {
		return Byte.class;
	}

}
