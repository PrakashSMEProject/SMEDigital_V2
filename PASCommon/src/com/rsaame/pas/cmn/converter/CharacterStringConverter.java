/**
 * 
 */
package com.rsaame.pas.cmn.converter;

import com.mindtree.ruc.cmn.beanmap.BaseConverter;

/**
 * @author M1016284
 *
 */
public class CharacterStringConverter extends BaseConverter<Character, String>{

	public CharacterStringConverter( String aPropsString, String bPropsString ){
		super( aPropsString, bPropsString );
	}

	@Override
	public String getBFromA( Object aObj ){
		if( aObj == null ){
			return null;
		}
		else{
			Character a = aObj.toString().charAt( 0 );
			String b = a.toString();
			return b;
		}
	}

	@Override
	public Character getAFromB( Object bObj ){
		if( bObj == null ){
			return null;
		}
		else{
			String b = bObj.toString();
			Character a = b.charAt( 0 );
			return a;
		}
	}

	@Override
	public Class<Character> getTypeOfA(){
		return Character.class;
	}

	@Override
	public Class<String> getTypeOfB(){
		return String.class;
	}

}
