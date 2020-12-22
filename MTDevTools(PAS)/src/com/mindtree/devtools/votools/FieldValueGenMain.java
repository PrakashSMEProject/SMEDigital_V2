package com.mindtree.devtools.votools;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Enumeration;
import java.util.ResourceBundle;

public class FieldValueGenMain{
	private final String OUTPUT_PATH = "D:/Temp/RSA/GeneratedVOs";
	private static ResourceBundle rb = ResourceBundle.getBundle( "com.skn.practice.pas.vo.vos" );
	
	/**
	 * @param args
	 */
	public static void main( String[] args ){
		run();
	}
	
	private static Class[] run(){
		Enumeration<String> keys = rb.getKeys();
		while( keys.hasMoreElements() ){
			String fullyQualifiedVOName = keys.nextElement();
			
			System.out.println( "---------- " + fullyQualifiedVOName + " : Start ----------" );
			
			Class voClass = null;
			try{
				voClass = Class.forName( fullyQualifiedVOName );
				createFieldValueMethodsForClass( voClass );
			}
			catch( ClassNotFoundException e ){
				System.out.println( "VO [" + fullyQualifiedVOName + "] not found" );
			}
			
			System.out.println( "---------- " + fullyQualifiedVOName + " : End ----------" );
		}
		return null;
	}

	private static void createFieldValueMethodsForClass( Class c ){
		Field[] fields = c.getDeclaredFields();
		if( fields == null || fields.length == 0 ) return;
		
		StringBuilder sb = new StringBuilder( "\tpublic Object getFieldValue( String fieldName ){" );
		sb.append( "\r\n\t\tObject fieldValue = null;\r\n" );
		
		for( Field f : fields ){
			sb.append( "\r\n\t\tif( \"" )
			.append( f.getName() )
			.append( "\".equals( fieldName ) ) fieldValue = " );
			
			Method getMethod = getFieldReadMethod( f );
			if( getMethod != null ){
				sb.append( getMethod.getName() + "();" );
			}
			else{
				
			}
		}
		
		sb.append( "\r\n\r\n\t\treturn fieldValue;" )
		.append( "\r\n\t}" ); //End of method
		
		System.out.println( sb.toString() );
	}
	
	private static Method getFieldReadMethod( Field f ){
		PropertyDescriptor pd = getPropertyDescriptor( f.getName(), f.getDeclaringClass() );
		return pd == null ? null : pd.getReadMethod();
	}
	
	private static PropertyDescriptor getPropertyDescriptor( String property, Class clazz ){
		PropertyDescriptor pd = null;; 
		try{
			pd = new PropertyDescriptor( property, clazz );
		}
		catch( IntrospectionException e ){
			e.printStackTrace();
		}
		
		return pd;
	}
	
	private static String getFieldValueMethodForField( Field f ){
		return null;
	}
	
	private static void writeClassFile( StringBuilder sb ){
		//SONARFIX--26-04-2018---DO NOTHING IN METHOD
	}
}
