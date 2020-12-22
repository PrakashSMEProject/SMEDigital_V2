package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;

public class ByteStringConverter extends BaseConverter<String, Byte>{

	public ByteStringConverter( String aPropsString, String bPropsString ){
		super( aPropsString, bPropsString );
	}

	@Override
	public Byte getBFromA( Object aObj ){
		if( aObj == null ){
			return null;
		}
		else{
			String a = aObj.toString();
			Byte b = Byte.valueOf( a );
			return b;
		}
	}

	@Override
	public String getAFromB( Object bObj ){
		if( bObj == null ){
			return null;
		}
		else{
			Byte b = (Byte)bObj;
			String a = b.toString();
			return a;
		}
	}

	@Override
	public Class<String> getTypeOfA(){
		return String.class;
	}

	@Override
	public Class<Byte> getTypeOfB(){
		return Byte.class;
	}
}
