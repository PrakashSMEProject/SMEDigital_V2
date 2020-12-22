package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class ShortStringConverter extends BaseConverter<Short, String> {

	public ShortStringConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public String getBFromA(Object aObj) {
		Short a = (Short) aObj;
		if(!Utils.isEmpty(a))
		{
			return a.toString();
		}
		return null;
	}

	@Override
	public Short getAFromB(Object bObj) {
		String b = (String)bObj;
		Short a = null;
		if(!Utils.isEmpty(b))
		{
			a = Short.valueOf(b.toString());
		}
		return a;
	}

	@Override
	public Class<Short> getTypeOfA() {
		// 
		return Short.class;
	}

	@Override
	public Class<String> getTypeOfB() {
		return String.class;
	}

}
