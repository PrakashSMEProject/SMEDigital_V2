package com.rsaame.pas.cmn.converter;


import java.math.BigDecimal;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;


public class IntegerBigDecimalConverter extends BaseConverter<BigDecimal, Integer>{



	public IntegerBigDecimalConverter(String aPropsString, String bPropsString) {
		super(aPropsString, bPropsString);
	}

	@Override
	public Integer getBFromA(Object aObj) {
		
		if( aObj == null ){
			return null;
		}
		else{
			BigDecimal a = (BigDecimal)aObj;
			Integer b = Integer.valueOf(a.intValue());
			return b;
		}
	}

	@Override
	public BigDecimal getAFromB(Object bObj) {
		
		if( bObj == null ){
			return null;
		}
		else{
			Integer b= (Integer)bObj;
			BigDecimal a = new BigDecimal(b.intValue());
			return a;
		}
	}

	@Override
	public Class<BigDecimal> getTypeOfA() {
		return BigDecimal.class;
	}

	@Override
	public Class<Integer> getTypeOfB() {
		return Integer.class;
	}

}
