package com.mindtree.devtools.utils;

import org.xml.sax.ErrorHandler;

import com.sun.tools.xjc.Options;
import com.sun.tools.xjc.Plugin;
import com.sun.tools.xjc.outline.ClassOutline;
import com.sun.tools.xjc.outline.FieldOutline;
import com.sun.tools.xjc.outline.Outline;

public class XJCPlugin extends Plugin{

	private static final String GSCV_URI = "http://www.nfumutual.co.uk/services/gscv/interface";

	@Override
	public String getOptionName(){
		return "XgenListSetter";
	}

	@Override
	public java.util.List<String> getCustomizationURIs() {
		java.util.List<String> list = new java.util.ArrayList<String>();
		list.add( GSCV_URI );
		list.add( "http://www.nfumutual.co.uk/services/gscv/search" );
		list.add( "http://www.nfumutual.co.uk/services/gscv/get" );
		list.add( "http://www.nfumutual.co.uk/services/gscv/delete" );
		return list;
	}
	
	@Override
	public boolean isCustomizationTagName(String nsUri, String localName) {
		if( nsUri.startsWith( "http://www.nfumutual.co.uk/services/gscv" ) ) return true;
		return false;
	}
	
	@Override
	public String getUsage(){
		return " -XgenListSetter : Create setter methods for list fields";
	}

	@Override
	public boolean run( Outline model, Options options, ErrorHandler errorHandler ){
		//options.
		for( ClassOutline co : model.getClasses() ){
			FieldOutline[] fieldOuts = co.getDeclaredFields();
			for( FieldOutline f : fieldOuts ){
				if( f.getPropertyInfo().isCollection() ){
					String setMethodContent = getSetMethodForCollection( f );
					System.out.println( "Generating set method for field [" + f.getPropertyInfo().getName( false ) + "] [" + setMethodContent + "]" );
					
					co.implClass.direct( setMethodContent );
				}
			}
		}
		return false;
	}
	
	/**
	 * Prepares and returns the method code text.
	 * @param f
	 * @return
	 */
	private String getSetMethodForCollection( FieldOutline f ){
		String type = f.getRawType().name();
		String fieldName = f.getPropertyInfo().getName( false );
		
		StringBuffer contentBuff = new StringBuffer( "\r\n\tpublic void set" )
								   .append( Character.toUpperCase( fieldName.charAt( 0 ) ) )
								   .append( fieldName.substring( 1 ) )
								   .append( "( " )
								   .append( type )
								   .append( " " )
								   .append( fieldName )
								   .append( " )" )
								   .append( "{\r\n\t\tthis." )
								   .append( fieldName )
								   .append( " = " )
								   .append( fieldName )
								   .append( ";\r\n\t}" );
		
		return contentBuff.toString();
	}
}
