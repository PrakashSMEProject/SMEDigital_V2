package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class ShortLongConverter  extends BaseConverter<Short, Long>{
	public ShortLongConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Long getBFromA(Object aObj) {
		Long b = null;
		Short a = (Short)aObj;
		if(!Utils.isEmpty(a))
		{
			b= a.longValue();
		}
		return b;
	}

	@Override
	public Short getAFromB(Object bObj) {
		Long b= (Long)bObj;
		Short a =null;
		if(!Utils.isEmpty(b))
		{
			a = b.shortValue();
		}
		 
		return a;
	}

	@Override
	public Class<Short> getTypeOfA() {
		return Short.class;
	}

	@Override
	public Class<Long> getTypeOfB() {
		return Long.class;
	}
}