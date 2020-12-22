package com.rsaame.pas.cmn.converter;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class BigDecimalStringConverter extends BaseConverter<BigDecimal, String>{

	public BigDecimalStringConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public String getBFromA(Object aObj) {
		String b = null;
		BigDecimal a = (BigDecimal)aObj;
		if(!Utils.isEmpty(a))
		{
			b = a.toString();
		}
		return b;
	}

	@Override
	public BigDecimal getAFromB(Object bObj) {
		String b= (String)bObj;
		BigDecimal a =null;
		if(!Utils.isEmpty(b))
		{
			a = new BigDecimal(b);
		}
		 
		return a;
	}

	@Override
	public Class<BigDecimal> getTypeOfA() {
		return BigDecimal.class;
	}

	@Override
	public Class<String> getTypeOfB() {
		return String.class;
	}

}