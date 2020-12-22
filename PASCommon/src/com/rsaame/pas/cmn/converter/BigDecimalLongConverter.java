package com.rsaame.pas.cmn.converter;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class BigDecimalLongConverter extends BaseConverter<BigDecimal, Long>{

	public BigDecimalLongConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Long getBFromA(Object aObj) {
		Long b = null;
		BigDecimal a = (BigDecimal)aObj;
		if(!Utils.isEmpty(a))
		{
			b= a.longValue();
		}
		return b;
	}

	@Override
	public BigDecimal getAFromB(Object bObj) {
		Long b= (Long)bObj;
		BigDecimal a =null;
		if(!Utils.isEmpty(b))
		{
			a = new BigDecimal(b.longValue());
		}
		 
		return a;
	}

	@Override
	public Class<BigDecimal> getTypeOfA() {
		return BigDecimal.class;
	}

	@Override
	public Class<Long> getTypeOfB() {
		return Long.class;
	}

}
