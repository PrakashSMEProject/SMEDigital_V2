package com.rsaame.pas.b2c.cmn.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.exception.SystemException;

/**
 * @author m1020637
 *
 */
public class CommonUtils {

	private static final String MTRUC_PROPERTIES_FILE = "config/mtruc.properties";
	
	private static String appConfigPropertiesFile;
	private static Properties props;
	
	private static final Logger logger = Logger.getLogger( CommonUtils.class );
	
	/**Returns true if collection is refering to null or the collection is empty
	 * @param collection
	 * @return
	 */
	public static boolean isEmpty( Collection<?> collection ){
		boolean target = true;
		if( collection != null ){
			if( !collection.isEmpty() ){
				target = false;
			}
		}
		return target;
	}

	/**Returns true if map is refering to null or the map is empty
	 * @param map
	 * @return
	 */
	public static boolean isEmpty( Map<?, ?> map ){
		boolean target = true;
		if( map != null ){
			if( !map.isEmpty() ){
				target = false;
			}
		}
		return target;
	}

	/**Returns true if object is refering to null
	 * @param object
	 * @return
	 */
	public static boolean isEmpty( Object object ){
		boolean target = true;
		if( object != null ){

			target = false;
		}
		return target;
	}

	/**Returns true if the size of object array or the objects equals to null
	 * @param objects
	 * @return
	 */
	public static boolean isEmpty( Object[] objects ){
		boolean target = true;
		if( objects != null ){
			if( objects.length != 0 ){
				target = false;
			}
		}
		return target;
	}

	/**Returns true if string references to null or the string is empty
	 * @param string
	 * @return
	 */
	public static boolean isEmpty( String string ){
		boolean target = true;
		if( string != null ){
			if( string.length() != 0 ) target = false;
		}
		return target;
	}

	/**
	 * Returns true if string references to null or the string is empty
	 * 
	 * @param string The string to be checked for emptiness
	 * @param trimBeforeCheck A boolean indicating if the String should be trimmed before 
	 * checking for emptiness. If true, then the String is trimmed, else it is equivalent 
	 * to calling <code>isEmpty( String )</code>.
	 * 
	 * @return true, if null or of 0 length
	 */
	public static boolean isEmpty( String string, boolean trimBeforeCheck ){
		boolean target = true;
		
		if( trimBeforeCheck ){
			if( string != null ){
				if( string.trim().length() != 0 ) target = false;
			}
		}
		else{
			target = isEmpty( string );
		}
		return target;
	}
	
	/* There are some load-level activities that are essential for the proper functioning of
	 * Utils. These will be done here because no instance of this class created for us to do
	 * these in the constructor. 
	 * 
	 * NOTE: Please don't change the position of this static block. This has to be after all the 
	 * isEmpty() methods have been declared! */
	static{
		/* Get the name and path of the application configuration file. */
		appConfigPropertiesFile = PropUtils.INSTANCE.getProperty( "APP_CONFIG_PROPERTIES_FILE", MTRUC_PROPERTIES_FILE );
		if( isEmpty( appConfigPropertiesFile ) ){
			throw new SystemException(  null, "The application configuration file is not configured in "+
									   MTRUC_PROPERTIES_FILE+" under the property ");
		}
		
		
	}
	
	
	/**
	 * @param key
	 * @return
	 */
	public static String getSingleValueAppConfig( String key ){
		
		return CommonUtils.isEmpty( PropUtils.INSTANCE.getProperty( key, appConfigPropertiesFile ) )?getSingleValueAppConfig(  key ,true ):PropUtils.INSTANCE.getProperty( key, appConfigPropertiesFile );
	}
	
	/**
	 * @param key
	 * @param APP_SPECIFIC
	 * @return
	 */
	private static String getSingleValueAppConfig( String key , Boolean APP_SPECIFIC ){

		/* No need to load if the properties have been loaded and it is not a case of
		 * reload. */
		if( !CommonUtils.isEmpty( props ) ){
			return props.getProperty( key );
		}
		
		/* Open an input stream to the file. */
		java.io.FileInputStream fin = null;
		try{
			fin = new FileInputStream( new File(PropUtils.INSTANCE.getProperty(  com.Constant.CONST_APP_LOC_PROPERTIES_FILE, MTRUC_PROPERTIES_FILE ) ) );
		}
		catch( FileNotFoundException e ){
			throw new SystemException(  e, "Bundle not found [" + "APP_LOC_PROPERTIES_FILE" + "]" );
		}
		
		
		/* Shun the current instance and create a new one. */
		props = new Properties();
		try{		
			props.load( fin );
			fin.close();
		}
		catch( IOException e ){
			 logger.error( "Couldn't load properties file [" + "APP_LOC_PROPERTIES_FIL_2" + "]" );
			throw new SystemException( e, "Exception while trying to load properties file" );
		}
		
		return props.getProperty( key );
	}
	
	/**
	 * Returns an app config value for the passed key. If the key is not found or is empty, <code>default</code>
	 * is returned.
	 * 
	 * @param key The key for which the value is required
	 * @param defaultValue The default value to be used if the key is not present or is set to empty string
	 * 
	 * @return
	 */
	public static String getSingleValueAppConfig( String key, String defaultValue ){
		String value = getSingleValueAppConfig( key );
		if( Utils.isEmpty( value ) ){
			value = defaultValue;
		}
		
		return value;
	}
	
	/**
	 * Returns a new instance of the class as indicated by the passed string
	 * 
	 * @param classNameAsString Fully qualified name of the class of which a new 
	 * instance is expected
	 * @return
	 * @throws SystemException if class could not be loaded
	 */
	public static Object newInstance( String classNameAsString ) throws SystemException{
		return newInstance( classNameAsString, 0 );
	}
	
	/**
	 * Returns a new instance of the class as indicated by the passed string
	 * 
	 * @param classNameAsString Fully qualified name of the class of which a new 
	 * instance is expected
	 * @param numElems The number of elements that need to be instantiated and added to the collection
	 * or array (if the passed class is that of a collection or an array)
	 * @return
	 * @throws SystemException if class could not be loaded
	 */
	public static Object newInstance( String classNameAsString, int numElems ) throws SystemException{
		Object instance = null;

		try{
			if( classNameAsString != null ){
				Class<?> clazz = Class.forName( classNameAsString );
				instance = newInstance( clazz, numElems );
			}
		}
		catch( ClassNotFoundException e ){
			throw new SystemException(  e, " is not a qualified class name" );
		}
		return instance;
	}
	
	/**
	 * returns instance of the passed class
	 * 
	 * @param clazz only the name of the class
	 * @param numElems The number of elements that need to be instantiated and added to the collection
	 * or array (if the passed class is that of a collection or an array)
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> T newInstance( Class<T> clazz, int numElems ) throws SystemException{
		T instance = null;
		try{
			if( clazz != null ){
				instance = (T) checkAndInstantiateForSpecialCase( clazz, numElems );

				if( Utils.isEmpty( instance ) ){
					instance = clazz.newInstance();
				}
			}
		}
		catch( InstantiationException e ){
			throw new SystemException(  e, "instantiation of class failed" );
		}
		catch( IllegalAccessException e ){
			throw new SystemException(  e, "illegal excess of methods" );
		}
		return instance;
	}
	
	private static Object checkAndInstantiateForSpecialCase( Class<?> clazz, int numElems ){
		Object instance = null;
		String className = clazz.getName();
		
		if( clazz.isArray() ){
			Class c = clazz.getComponentType();
			instance = Array.newInstance( c, numElems ); //TODO We have to figure out a way to mention size.
		}
		else if( isSubClassOf( clazz, java.util.Map.class ) ){
			instance = new java.util.HashMap();
		}
		else if( isSubClassOf( clazz, java.util.List.class ) ){
			instance = new java.util.ArrayList();
		}
		else if( isSubClassOf( clazz, java.util.Set.class ) ){
			instance = new java.util.HashSet();
		}
		else if( className.equals( "java.lang.Integer" ) ){
			instance = new java.lang.Integer( -9999 ); /* Since we don't have an input value and java.lang.Integer
			 											* requires an initial value, we are setting to a default 
			 											* value, -9999. There are risks involved here which will need
			 											* to be taken care of after the usage of the method. */
		}
		else if( className.equals( "java.lang.Long" ) ){
			instance = new java.lang.Long( -9999L );   /* Since we don't have an input value and java.lang.Long
			 											* requires an initial value, we are setting to a default 
			 											* value, -9999. There are risks involved here which will need
			 											* to be taken care of after the usage of the method. */
		}
		else if( className.equals( "java.lang.Double" ) ){
			instance = new java.lang.Double( -9999.0 );/* Since we don't have an input value and java.lang.Double
			 											* requires an initial value, we are setting to a default 
			 											* value, -9999. There are risks involved here which will need
			 											* to be taken care of after the usage of the method. */
		}
		
		return instance;
	}
	
	
	/**
	 * Checks if <code>childClass</code> is a child class of <code>superClass</code>.
	 * 
	 * @param <T>
	 * @param <U>
	 * @param childClass Class representing the child class
	 * @param superClass Class representing the super class
	 * @return
	 */
	public static <T, U> boolean isSubClassOf( Class<T> childClass, Class<U> superClass ){
		boolean isChildClass = false;
		if( Utils.isEmpty( childClass ) || Utils.isEmpty( superClass ) ){
			return isChildClass;
		}
		
		try{
			childClass.asSubclass( superClass );
			isChildClass = true;
		}
		catch( ClassCastException e ){
		    logger.debug("Class Cast Exception"+e);
		}
		
		return isChildClass;
	}
	
	/**
	 * A convenience method to concatenate multiple strings without the use of the '+' operator.
	 * 
	 * @param strings
	 * @return
	 */
	public static String concat( String... strings ){
		if( Utils.isEmpty( strings ) ){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for( String s : strings ){
			sb.append( s );
		}
		
		return sb.toString();
	}
	
	/**
	 * Checks if the input string is one of "YES", "Y" or "TRUE". If yes, return <code>true</code>.
	 * For any other value of <code>input</code> or null input, returns <code>false</code>.
	 * 
	 * @param input
	 * @return
	 */
	public static boolean toDefaultFalseBoolean( String input ){
		boolean convertedResult = false;
		
		if( "YES".equalsIgnoreCase( input ) || 
			"Y".equalsIgnoreCase( input ) ||
			"TRUE".equalsIgnoreCase( input ) ){
			convertedResult = true;
		}
		
		return convertedResult;
	}
	
}
