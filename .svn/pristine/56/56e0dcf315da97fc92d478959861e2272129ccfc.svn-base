package com.rsaame.pas.cmn.converter;



import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class LongIntegerConverter  extends BaseConverter<Integer , Long>{
	public LongIntegerConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Long getBFromA(Object aObj) {
		Long b = null;
		Integer a = (Integer)aObj;
		if(!Utils.isEmpty(a))
		{
			b= a.longValue();
		}
		return b;
	}

	@Override
	public Integer getAFromB(Object bObj) {
		Long b= (Long)bObj;
		Integer a =null;
		if(!Utils.isEmpty(b))
		{
			a = b.intValue();
		}
		 
		return a;
	}

	@Override
	public Class<Integer> getTypeOfA() {
		return Integer.class;
	}

	@Override
	public Class<Long> getTypeOfB() {
		return Long.class;
	}
}
