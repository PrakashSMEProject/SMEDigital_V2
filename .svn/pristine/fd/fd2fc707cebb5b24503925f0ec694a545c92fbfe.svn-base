package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;
import com.mindtree.ruc.cmn.constants.CommonConstants;
import com.mindtree.ruc.cmn.utils.Utils;


public class IntegerStringConverter extends BaseConverter<Integer, String>{

		
		public IntegerStringConverter( String aPropsString, String bPropsString ){
			super( aPropsString, bPropsString );
		}

		
		
		@Override
		public String getBFromA( Object aObj ){
			Integer a = (Integer) aObj;
			if(!Utils.isEmpty(a))
			{
				return a.toString();
			}
			return null;
		}

		
		
		@Override
		public Integer getAFromB( Object bObj ){
			String b = (String) bObj;
			Integer a = null;
			
			if(!Utils.isEmpty(b) && !b.equals("select")){
				a = new Integer(b);
			}else{
				a = CommonConstants.DEFAULT_LOW_INTEGER;
			}
			
			return a;
		}

		@Override
		public Class<Integer> getTypeOfA(){
			return Integer.class;
		}

		@Override
		public Class<String> getTypeOfB(){
			return String.class;
		}

}
	
