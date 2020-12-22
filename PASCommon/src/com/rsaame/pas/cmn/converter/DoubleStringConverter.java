/**
 * 
 */
package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.utils.Utils;

/**
 * @author M1016284
 *
 */
public class DoubleStringConverter extends BaseConverter<Double, String>{


	
	public DoubleStringConverter( String aPropsString, String bPropsString ){
		super( aPropsString, bPropsString );
	}

	
	@Override
	public String getBFromA( Object aObj ){
		Double a = (Double)aObj;
		String b = null;
		if(!Utils.isEmpty(a))
		{
			b=a.toString();
		}
		
		return b;
	}

	
	@Override
	public Double getAFromB( Object bObj ){
		String b = (String) bObj;
		String c = b.replaceAll("[,]","");
		Double a = null;
		
		if(!Utils.isEmpty(c) && !c.equals("select")){
			a = new Double(c);
		}else{
			a = 0.00;
		}
		
		return a;
	}

	@Override
	public Class<Double> getTypeOfA(){
		return Double.class;
	}

	@Override
	public Class<String> getTypeOfB(){
		return String.class;
	}


}
