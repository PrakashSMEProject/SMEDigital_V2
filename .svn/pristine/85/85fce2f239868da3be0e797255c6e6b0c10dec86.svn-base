package com.rsaame.pas.cmn.converter;

import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

public class BigDecimalDoubleConverter extends BaseConverter<BigDecimal, Double>{

	public BigDecimalDoubleConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Double getBFromA(Object aObj) {
		Double b = null;
		BigDecimal a = (BigDecimal)aObj;
		if(!Utils.isEmpty(a))
		{
			b= a.doubleValue();
		}
		return b;
	}

	@Override
	public BigDecimal getAFromB(Object bObj) {
		Double b= (Double)bObj;
		BigDecimal a =null;
		if(!Utils.isEmpty(b))
		{
			a = new BigDecimal(b.doubleValue());
		}
		 
		return a;
	}

	@Override
	public Class<BigDecimal> getTypeOfA() {
		return BigDecimal.class;
	}

	@Override
	public Class<Double> getTypeOfB() {
		return Double.class;
	}

}
