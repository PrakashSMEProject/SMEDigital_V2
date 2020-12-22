package com.mindtree.devtools.utils;

import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.MissingResourceException;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.StringTokenizer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.xml.XmlBeanFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import com.mindtree.devtools.b2b.utils.BeanMapperConstants;
import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.log.Logger;


public class DevToolsUtils{
	/* The name of the application (in short hand) set as a validationContext setting for the application
	 * in whose environment this class has been loaded.
	 */
	private static String appName = null;
	
	private static String validationContext = null;

	private static final String EMPTY_STRING = "";

	private static final Logger logger = Logger.getLogger(  DevToolsUtils.class );
	private static Context ctx;
	private static Map<String, Object> resourceMap;
	
	private static final String CMVCOMMON_PROPERTIES_FILE = "config/cmvcommon.properties";
	//private static IAppSpecificConfig appSpecificConfigImpl;
	
	static{
		resourceMap = new HashMap<String, Object>();
	}

	/**
	 * This is the portal configuration Id of the portal in which environment this DevToolsUtils is being called. This valus is
	 * expected to be set in a static block of a class, say FacesPortlet, which is expected to be loaded before other
	 * classes.
	 */
	private static int portalConfigurationId = -1;

	/**
	 * This method converts a set to list. This method returns an unmodifiable empty list of the source set is empty.
	 * @param <T>
	 * @param srcSet
	 * @return list
	 */
	public static <T> List<T> asList( Set<T> srcSet ){
		if( DevToolsUtils.isEmpty( srcSet ) ){
			Collections.emptyList();
		}
		List<T> list = new ArrayList<T>();
		list.addAll( srcSet );
		return list;
	}

	/**
	 * This method converts an array to a list.
	 * @param <T>
	 * @param array
	 * @return List<T>
	 */
	public static <T> List<T> asList( T[] array ){
		List<T> list = null;
		if( !DevToolsUtils.isEmpty( array ) ){
			list = Arrays.asList( array );
		}
		return list;
	}

	/**
	 * This method converts a list to a set. This method returns an unmodifiable empty set if the source list is empty.
	 * @param <T>
	 * @param srcList
	 * @return set
	 */
	public static <T> Set<T> asSet( List<T> srcList ){
		if( DevToolsUtils.isEmpty( srcList ) ){
			Collections.emptySet();
		}
		Set<T> set = new HashSet<T>();
		set.addAll( srcList );
		return set;
	}

	/**
	 * This method converts an array to a set.
	 * @param <T>
	 * @param array
	 * @return Set<T>
	 */
	public static <T> Set<T> asSet( T[] array ){
		Set<T> set = null;
		List<T> list = null;
		if( !DevToolsUtils.isEmpty( array ) ){
			list = Arrays.asList( array );
			if( !DevToolsUtils.isEmpty( list ) ){
				set = DevToolsUtils.asSet( list );
			}
		}
		return set;
	}

	/**
	 * This method constructs the accessor method chain from the field.
	 * @param fieldName
	 * @return accessorMethodChain
	 */
	public static String buildAccessorMethodChain( String fieldName ){
		String accessorMethodChain = null;
		StringBuffer methodChain = new StringBuffer();
		String[] fields = DevToolsUtils.tokenize( fieldName, "." );
		if( !DevToolsUtils.isEmpty( fields ) && fields.length > 0 ){
			for( String field : fields ){
				methodChain.append( "get" );
				methodChain.append( firstCharToUpperCase( field ) );
				methodChain.append( "()." );
			}
			int index = methodChain.length() - 1;
			accessorMethodChain = deleteCharAtIndex( methodChain.toString(), index );
		}
		return accessorMethodChain;
	}

	/**
	 * This method constructs the mutator method chain from the field.
	 * @param fieldName
	 * @return mutatorMethodChain
	 */
	public static String buildMutatorMethodChain( String fieldName ){
		StringBuffer methodChain = new StringBuffer();
		String[] fields = DevToolsUtils.tokenize( fieldName, "." );
		if( !DevToolsUtils.isEmpty( fields ) ){
			int length = fields.length;
			if( length == 1 ){
				methodChain.append( "set" );
				methodChain.append( firstCharToUpperCase( fields[ 0 ] ) );
				return methodChain.toString();
			}
			else if( length > 1 ){
				for( int index = 0; index < length - 1; index++ ){
					methodChain.append( "get" );
					methodChain.append( firstCharToUpperCase( fields[ index ] ) );
					methodChain.append( "()." );
				}
				methodChain.append( "set" );
				methodChain.append( firstCharToUpperCase( fields[ length - 1 ] ) );
			}
		}
		return methodChain.toString();
	}

	/**
	 * This method compares values in the fields of two instances of the same class. This method does not support fields
	 * that are arrays and of types that have not implemented their own .equals( Object ) method.
	 * @param first - The first object to compare
	 * @param second - The second object to compare
	 * @return <PRE>
	 * 		   true, if all fields have same value
	 *         false, in the following conditions:
	 *         (i)   the types of the passed beans don't match
	 *         (ii)  the values returned by the &quot;get&quot;/&quot;is&quot;/&quot;has&quot; method for a field
	 *         		 doesn't match the value returned by the same method in the other
	 *         		 bean.
	 *         (iii) a &quot;get&quot;/&quot;is&quot;/&quot;has&quot; method in one bean is not found in the other
	 * </PRE>
	 */
	public static boolean compareBeans( Object first, Object second ){
		boolean passed = true;

		/* If both the passed objects are null, return true. */
		if( first == null && second == null ){
			return true;
		}
		else if( first != null && second != null ){
			/* Do nothing. We have to compare the fields individually. */
		}
		/* If only one is null, then return false. */
		else{
			return false;
		}

		/*
		 * Get the types of the two objects. They should match. If they don't return false.
		 */
		if( !first.getClass().getName().equals( second.getClass().getName() ) ){
			return false;
		}

		/*
		 * Get the list of the declared fields in the two beans. Iterate through each and compare the values. At the
		 * first instance of no match, break the loops and return false.
		 */
		Method[] getMethsInFirst = first.getClass().getMethods();
		Method[] getMethsInSecond = second.getClass().getMethods();

		if( getMethsInFirst == null && getMethsInSecond == null ){
			return true;
		}
		else if( getMethsInFirst != null && getMethsInSecond != null ){
			/* Do nothing. We have to compare the fields individually. */
		}
		/* If only one is null, then return false. */
		else{
			return false;
		}

		for( int iFM = 0; iFM < getMethsInFirst.length; iFM++ ){
			Method methodInFirst = getMethsInFirst[ iFM ];
			String methodName = methodInFirst.getName();

			/* Support only those methods that start with "get", "is" or "has". */
			if( !methodName.startsWith( "get" ) && !methodName.startsWith( "is" ) && !methodName.startsWith( "has" ) ){
				logger.debug( "DevToolsUtils.Class :compareBeans() Continuing [" + methodName + "]" );
				continue;
			}

			/*
			 * From the above filtration, "hashCode()" which is defined in java.lang.Object would have escaped.
			 */
			if( methodName.equals( "hashCode" ) ){
				logger.debug( "DevToolsUtils.Class :compareBeans() Continuing [" + methodName + "]" );
				continue;
			}

			/* Arrays are not supported. */
			if( methodInFirst.getReturnType().isArray() ){
				continue;
			}

			Class[] parameters = methodInFirst.getParameterTypes();

			try{
				Method methodInSecond = second.getClass().getMethod( methodName, parameters );

				if( methodInSecond == null ){
					return false;
				}

				Object returnValFromFirst = methodInFirst.invoke( first, null );
				Object returnValFromSecond = methodInSecond.invoke( second, null );
				logger.debug( "DevToolsUtils.Class :compareBeans() method name: " + methodInFirst.getName()
						+ "....first value: " + returnValFromFirst + "....second value: " + returnValFromSecond );
				if( returnValFromFirst == null && returnValFromSecond == null ){
					continue;
				}
				else if( returnValFromFirst != null && returnValFromSecond != null ){
					if( !returnValFromFirst.equals( returnValFromSecond ) ){
						logger.debug( "DevToolsUtils.Class :compareBeans() Method _1" + methodInFirst.getName()
								+ "] returned different value_1" );
						return false;
					}

					continue;
				}
				/* If only one is null, then return false. */
				else{
					logger.debug( "DevToolsUtils.Class :compareBeans() Method _2" + methodInFirst.getName()
							+ "] returned null in one bean" );
					return false;
				}
			}
			catch( SecurityException e ){
				logger.error( e, "DevToolsUtils.Class :compareBeans() Cannot browse through the bean methods." );
				return false;
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "DevToolsUtils.Class :compareBeans() A method available in 'first' bean is not available in 'second' bean." );
				return false;
			}
			catch( InvocationTargetException ite ){
				logger.error( ite, "DevToolsUtils.Class :compareBeans() Cannot invoke method on the passed beans" );
				return false;
			}
			catch( IllegalAccessException iae ){
				logger.error( iae, "DevToolsUtils.Class :compareBeans() Don't have access to invoke method on the passed beans" );
				return false;
			}
		}

		return passed;
	}

	@SuppressWarnings( "unchecked" )
	public static boolean compareBeans( Object first, Object second, String[] props ){

		Method methodFirst = null;
		Method methodSecond = null;
		Object valueFirst = null;
		Object valueSecond = null;
		Boolean flag = null;

		try{
			/* Check the two objects for null conditions */
			flag = performRequiredChecks( first, second );
			if( !isEmpty( flag ) ){
				return flag;
			}

			/* Get the class names of the two objects */
			Class classFirst = first.getClass();
			Class classSecond = second.getClass();

			if( !isEmpty( props ) ){
				for( String property : props ){

					String mtdName = getMethodName( property );
					methodFirst = classFirst.getMethod( mtdName );
					methodSecond = classSecond.getMethod( mtdName );

					valueFirst = methodFirst.invoke( first, null );
					valueSecond = methodSecond.invoke( second, null );

					if( compareValues( valueFirst, valueSecond ) ){
						continue;
					}
					else{
						return false;
					}
				}
			}
		}
		catch( Exception ex ){
			logger.error( " An error occurred while comparing bean [DevToolsUtils][compareBeans( "
					+ "Object first, Object second, String[] props )]" );
			logger.error( ex );
		}

		return true;
	}

	/**
	 * This method returns true if the start date is before end date.
	 * @param startDate
	 * @param endDate
	 * @return true | false
	 */
	public static boolean compareDates( Date startDate, Date endDate ){
		if( !DevToolsUtils.isEmpty( startDate ) && !DevToolsUtils.isEmpty( endDate ) ){
			return startDate.before( endDate );
		}
		return false;
	}

	/**
	 * This will compare two objects using equals method
	 * @param valueFirst
	 * @param valueSecond
	 * @returns a boolean
	 */
	@SuppressWarnings( "unchecked" )
	private static Boolean compareValues( Object valueFirst, Object valueSecond ){

		if( valueFirst == null && valueSecond == null ){
			return true;
		}
		else if( valueFirst != null && valueSecond != null ){
			/*
			 * If the two objects are instances of Number or String then use equals
			 */
			if( ( valueFirst instanceof Number && valueSecond instanceof Number )
					|| ( valueFirst instanceof String && valueSecond instanceof String ) ){
				if( !valueFirst.equals( valueSecond ) ){
					logger.debug( "DevToolsUtils.Class :compareBeans() Method _3" + "] returned different value_2" );
					return false;
				}
			}
			/*
			 * If objects are arrays check size and then call compareBeans for every object in them (first,second)
			 */
			if( valueFirst instanceof Object[] && valueSecond instanceof Object[] ){
				List firstList = Arrays.asList( (Object[]) valueFirst );
				Collections.sort( firstList );
				List secList = Arrays.asList( (Object[]) valueSecond );
				Collections.sort( secList );
				if( firstList.size() != secList.size() ){
					return false;
				}
				else{
					for( int counter = 0; counter < firstList.size(); counter++ ){
						Object firstObject = (Object) firstList.get( counter );
						Object secondObject = (Object) secList.get( counter );
						boolean checkStatus = firstObject.equals( secondObject );
						if( !checkStatus ){
							return false;
						}
					}
				}
			}

			if( ( valueFirst instanceof Boolean && valueSecond instanceof Boolean ) ){
				if( !valueFirst.equals( valueSecond ) ){
					logger.debug( "DevToolsUtils.Class :compareBeans() Method _4" + "] returned different value_3" );
					return false;
				}
			}

			return true;
		}
		/* If only one is null, then return false. */
		else{
			logger.debug( "DevToolsUtils.Class :compareBeans() Method _5" + "] returned null in one bean" );
			return false;
		}
	}

	/**
	 * This method returns true if an input string contains an ampersand, false otherwise.
	 * @param string
	 * @return true | false
	 */
	public static Boolean containsAmpersand( String string ){
		if( !DevToolsUtils.isEmpty( string ) && string.contains( "&" ) ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if an input string contains an apostrophe, false otherwise.
	 * @param string
	 * @return true | false
	 */
	public static boolean containsApostrophe( String string ){
		if( !DevToolsUtils.isEmpty( string ) && string.contains( "'" ) ){
			return true;
		}
		return false;
	}

	/*****
	 * Convert date from source format to destination format
	 * @param sourceFormat Source Format String
	 * @param destFormat Destination Format String
	 * @param dateString Date in String format.
	 * @return
	 */
	public static String convertDateFormat( String sourceFormat, String destFormat, String dateString )
			throws SystemException{
		if( sourceFormat == null || destFormat == null || isEmpty( dateString ) ){
			return null;
		}

		try{
			SimpleDateFormat sdf1 = new SimpleDateFormat( sourceFormat );
			SimpleDateFormat sdf2 = new SimpleDateFormat( destFormat );
			return sdf2.format( sdf1.parse( dateString ) );

		}
		catch( Exception e ){
			logger.error( e );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
		}
	}

	/**
	 * Converts date to string based on the pattern/format that is passed in
	 * @param date = Date object that needs to be converted to a String
	 * @param formatToConvert = The pattern to convert to
	 * @return = String representation of date.
	 * @throws CIException
	 */
	public static String convertDateToString( Date date, String formatToConvert ) throws SystemException{

		SimpleDateFormat dateFormat = null;
		String formatedStr = "";
		try{
			dateFormat = new SimpleDateFormat( formatToConvert );
			formatedStr = dateFormat.format( date );
		}
		catch( NullPointerException ne ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ne, "" );
		}
		catch( IllegalArgumentException ire ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ire, "" );
		}
		return formatedStr;
	}

	/**
	 * This method converts a Date to a specified format.
	 * @param date
	 * @param dateFormat
	 * @return formattedDate representation of a string
	 */
	public static String convertStringToDate( Date date, String dateFormat ){
		SimpleDateFormat formatter = new SimpleDateFormat( dateFormat );
		String formattedDate = formatter.format( date );
		return formattedDate;
	}

	/**
	 * This method converts a string to a date value.
	 * @param dateAsString
	 * @param dateFormat
	 * @return date representation of a string
	 * @throws ParseException
	 */
	public static Date convertStringToDate( String dateAsString, String dateFormat ) throws ParseException{
		Date date = null;
		if( !DevToolsUtils.isEmpty( dateAsString ) && !DevToolsUtils.isEmpty( dateFormat ) ){
			DateFormat formatter = new SimpleDateFormat( dateFormat );
			date = formatter.parse( dateAsString );
		}
		return date;
	}

	/**
	 * This method copies the contents of a source list to a destination list.
	 * @param <T>
	 * @param srcList
	 * @param destList
	 * @param List <T>
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> List<T> copy( List<T> srcList, List<T> destList ){
		if( !DevToolsUtils.isEmpty( srcList ) ){
			T[] srcArray = (T[]) new Object[ srcList.size() ];
			DevToolsUtils.toArray( srcList, srcArray );
			destList = DevToolsUtils.asList( srcArray );
		}
		return destList;
	}

	/**
	 * This method copies the contents of a source list to a destination set.
	 * @param <T>
	 * @param srcList
	 * @param destList
	 * @param Set <T>
	 */
	public static <T> Set<T> copy( List<T> srcList, Set<T> destList ){
		if( !DevToolsUtils.isEmpty( srcList ) ){
			destList = DevToolsUtils.asSet( srcList );
		}
		return destList;
	}

	/**
	 * This method copies the contents of the list to the array.
	 * @param <T>
	 * @param list
	 * @param array
	 * @param T []
	 */

	public static <T> T[] copy( List<T> list, T[] array ){
		if( !DevToolsUtils.isEmpty( list ) ){
			array = DevToolsUtils.toArray( list, array );
		}
		return (T[]) array;
	}

	/**
	 * This method copies the list<U> to list<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> List<V> copy( List<U> src, List<V> dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}
		/*
		 * If the dest list is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (List<V>) DevToolsUtils.newInstance( src.getClass().getName() );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_1" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, 
						"The destination parameter type the method mapBean() in the mapper class is null_1" );
			}
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest
							.add( i, (V) method.invoke( t, src.get( i ), (V) DevToolsUtils
									.newInstance( destClassType.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the list<U> to set<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> Set<V> copy( List<U> src, Set<V> dest, Class<T> mapperClass ){
		List<V> destList = null;

		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/*
		 * If the dest list is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			destList = (List<V>) DevToolsUtils.newInstance( src.getClass().getName() );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_2" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_2" );
			}
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					destList.add( i, (V) method.invoke( t, src.get( i ), (V) DevToolsUtils
							.newInstance( destClassType.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		/* Convert the result back to set before returning. */
		dest = DevToolsUtils.asSet( destList );

		return dest;
	}

	/**
	 * This method copies the list<U> to array<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> V[] copy( List<U> src, V[] dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_3" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_3" );
			}
		}

		/*
		 * If the dest array is empty, instantiate it to the suitable array type.
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (V[]) Array.newInstance( destClassType, src.size() );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest[ i ] = (V) method.invoke( t, src.get( i ), (V) DevToolsUtils.newInstance( destClassType.getName() ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the contents of a source set to a destination list.
	 * @param <T>
	 * @param srcSet
	 * @param destList
	 * @param List <T>
	 */
	public static <T> List<T> copy( Set<T> srcSet, List<T> destList ){
		return DevToolsUtils.asList( srcSet );
	}

	/**
	 * This method copies the contents of a source set to a destination set.
	 * @param <T>
	 * @param srcSet
	 * @param destSet
	 * @param Set <T>
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> Set<T> copy( Set<T> srcSet, Set<T> destSet ){
		if( !DevToolsUtils.isEmpty( srcSet ) ){
			T[] srcArray = (T[]) new Object[ srcSet.size() ];
			List<T> srcList = DevToolsUtils.asList( srcSet );
			DevToolsUtils.toArray( srcList, srcArray );
			List<T> destList = DevToolsUtils.asList( srcArray );
			destSet = DevToolsUtils.asSet( destList );
		}
		return destSet;
	}

	/**
	 * This method copies the contents of the set to the array.
	 * @param <T>
	 * @param set
	 * @param array
	 * @param T []
	 */

	public static <T> T[] copy( Set<T> set, T[] array ){
		if( !DevToolsUtils.isEmpty( set ) ){
			array = DevToolsUtils.toArray( set, array );
		}
		return (T[]) array;
	}

	/**
	 * This method copies the set<U> to list<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> List<V> copy( Set<U> src, List<V> dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/* Convert the source set to list */
		List<U> list = DevToolsUtils.asList( src );

		/*
		 * If the dest list is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (List<V>) DevToolsUtils.newInstance( list.getClass().getName() );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_4" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException(CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_4" );
			}
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest
							.add( i, (V) method.invoke( t, list.get( i ), (V) DevToolsUtils.newInstance( destClassType
									.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the set<U> to set<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> Set<V> copy( Set<U> src, Set<V> dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}
		/*
		 * If the dest set is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (Set<V>) DevToolsUtils.newInstance( src.getClass().getName() );
		}

		/* Convert the source set to list */
		List<U> srcList = (List<U>) DevToolsUtils.asList( src );

		/* Convert the destintation set to list */
		List<V> destList = (List<V>) DevToolsUtils.asList( dest );

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_5" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_5" );
			}
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					destList.add( i, (V) method.invoke( t, srcList.get( i ), (V) DevToolsUtils.newInstance( destClassType
							.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		/* Convert the list to set before returning from the method */
		dest = DevToolsUtils.asSet( destList );

		return dest;
	}

	/**
	 * This method copies the set<U> to array<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> V[] copy( Set<U> src, V[] dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/* This method converts a set to a list */
		List<U> list = DevToolsUtils.asList( src );

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_6" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_6" );
			}
		}

		/* If the dest array is empty, instantiate it to the suitable type */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (V[]) Array.newInstance( destClassType, src.size() );
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.size(); i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest[ i ] = (V) method.invoke( t, list.get( i ), (V) DevToolsUtils.newInstance( destClassType.getName() ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the contents of the array to the list.
	 * @param <T>
	 * @param array
	 * @param list
	 * @param List <T>
	 */

	public static <T> List<T> copy( T[] array, List<T> list ){
		if( !DevToolsUtils.isEmpty( array ) ){
			list = DevToolsUtils.asList( array );
		}
		return list;
	}

	/**
	 * This method copies the contents of the set to the array.
	 * @param <T>
	 * @param array
	 * @param set
	 * @param Set <T>
	 */

	public static <T> Set<T> copy( T[] array, Set<T> set ){
		if( !DevToolsUtils.isEmpty( array ) ){
			set = DevToolsUtils.asSet( array );
		}
		return set;
	}

	/**
	 * This method copies the contents of a source array to a destination array.
	 * @param <T>
	 * @param srcArray
	 * @param destArray
	 * @param T []
	 */

	public static <T> T[] copy( T[] srcArray, T[] destArray ){
		if( !DevToolsUtils.isEmpty( srcArray ) ){
			int length = srcArray.length;
			destArray = copyOf( srcArray, 0, length );
		}
		return (T[]) destArray;
	}

	/**
	 * This method copies the array<U> to List<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> List<V> copy( U[] src, List<V> dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_7" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_7" );
			}
		}

		/*
		 * If the dest set is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (List<V>) DevToolsUtils.newInstance( ArrayList.class.getName() );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.length; i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest.add( i, (V) method.invoke( t, src[ i ], (V) DevToolsUtils.newInstance( destClassType.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the array<U> to Set<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> Set<V> copy( U[] src, Set<V> dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_8" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_8" );
			}
		}

		/*
		 * If the dest set is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (Set<V>) DevToolsUtils.newInstance( destClassType.getName() );
		}

		/* Convert the set to list */
		List<V> destList = DevToolsUtils.asList( dest );

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.length; i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					destList
							.add( i, (V) method.invoke( t, src[ i ], (V) DevToolsUtils.newInstance( destClassType.getName() ) ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return DevToolsUtils.asSet( destList );
	}

	/**
	 * This method copies the set<U> to set<V> by invoking the mapBean() method iteratively on the mapper class.
	 * @param <U>
	 * @param <V>
	 * @param <T>
	 * @param src
	 * @param dest
	 * @param mapperClass
	 * @return dest
	 */
	@SuppressWarnings( "unchecked" )
	public static <U, V, T> V[] copy( U[] src, V[] dest, Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( src ) ){
			return null;
		}
		/*
		 * If the dest set is empty, instantiate it to the suitable list implementation class
		 */
		if( DevToolsUtils.isEmpty( dest ) ){
			dest = (V[]) Array.newInstance( src.getClass().getComponentType(), src.length );
		}

		/* Get the mapper class instance */
		T t = DevToolsUtils.getMapperInstance( mapperClass );

		/* Get the parameter types for the method mapBean() in the mapper class */
		Class<?>[] parameterTypes = getMethodParameters( com.Constant.CONST_MAPBEAN, mapperClass );
		Class<?> destClassType = null;

		if( DevToolsUtils.isEmpty( parameterTypes ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "No input parameter type found for the method mapBean() in the mapper class_9" );
		}

		/*
		 * The number of parameters is expected to be exactly equal to 2 for the method mapBean() in the mapper class
		 */
		if( parameterTypes.length == 2 ){
			/*
			 * The 2nd parameter in the mapBean() method represents the class type of the bean to be copied into.
			 */
			destClassType = parameterTypes[ 1 ];
			if( DevToolsUtils.isEmpty( destClassType ) ){
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The destination parameter type the method mapBean() in the mapper class is null_9" );
			}
		}

		Method method = null;
		// Get the method object for the method com.Constant.CONST_MAPBEAN
		if( !DevToolsUtils.isEmpty( t ) ){
			try{
				method = t.getClass().getMethod( com.Constant.CONST_MAPBEAN, parameterTypes );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( NoSuchMethodException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}

		for( int i = 0; i < src.length; i++ ){
			try{
				if( !DevToolsUtils.isEmpty( t ) ){
					dest[ i ] = (V) method.invoke( t, src[ i ], (V) DevToolsUtils.newInstance( destClassType.getName() ) );
				}
			}
			catch( IllegalArgumentException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( IllegalAccessException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( InvocationTargetException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
			catch( SecurityException e ){
				logger.error( e, "" );
				throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "" );
			}
		}
		return dest;
	}

	/**
	 * This method copies the source array to destination array. This is a wrapper around System.arraycopy( array1,
	 * start1, array2, start2, length ).
	 * @param srcArray the array to copy out of
	 * @param srcArray the starting index in array1
	 * @param destArray the array to copy into
	 * @param startIndex2 the starting index in array2
	 * @param length the number of elements in the array to copy
	 */
	public static void copyArray( Object srcArray, int startIndex1, Object destArray, int startIndex2, int length ){
		System.arraycopy( srcArray, startIndex1, destArray, startIndex2, length );
	}

	/**
	 * This method copies the contents of the source to destination array.
	 * @param <T>
	 * @param original
	 * @param start
	 * @param end
	 * @return
	 */
	@SuppressWarnings( "unchecked" )
	private static <T> T[] copyOf( T[] original, int start, int end ){
		if( original.length >= start && 0 <= start ){
			if( start <= end ){
				int length = end - start;
				int copyLength = Math.min( length, original.length - start );
				T[] copy = (T[]) Array.newInstance( original.getClass().getComponentType(), length );
				System.arraycopy( original, start, copy, 0, copyLength );
				return copy;
			}
			throw new IllegalArgumentException();
		}
		throw new ArrayIndexOutOfBoundsException();
	}

	/**
	 * This method deletes a character at a given index from the string supplied.
	 * @param source
	 * @return updatedString
	 */
	public static String deleteCharAtIndex( String source, int index ){
		if( source == null ){
			return source;
		}
		StringBuffer updatedString = new StringBuffer();
		updatedString.append( source );
		updatedString.deleteCharAt( index );
		return updatedString.toString();
	}

	/**
	 * This method filters the list of strings by checking if each string in the source list "starts with a prefix"
	 * provided by the client. If the string in the list matches the criteria, it adds to the filtered list.
	 * @param srcList
	 * @param prefix
	 * @return filteredList
	 */
	public static List<String> filterBy( List<String> srcList, String prefix ){

		if( DevToolsUtils.isEmpty( srcList ) || DevToolsUtils.isEmpty( prefix ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "List to be filtered is empty or the filter criteria is empty." );
		}

		List<String> filteredList = new ArrayList<String>();
		for( String string : srcList ){
			if( !DevToolsUtils.isEmpty( string ) ){
				if( string.startsWith( prefix ) ){
					filteredList.add( string );
				}
			}

		}
		return filteredList;
	}

	/**
	 * This method filters the set of strings by checking if each string in the source set "starts with a prefix"
	 * provided by the client. If the string in the set matches the criteria, it adds to the filtered set.
	 * @param srcSet
	 * @param prefix
	 * @return filteredSet
	 */
	public static Set<String> filterBy( Set<String> srcSet, String prefix ){

		if( DevToolsUtils.isEmpty( srcSet ) || DevToolsUtils.isEmpty( prefix ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Set to be filtered is empty or the filter criteria is empty." );
		}

		Set<String> filteredSet = new HashSet<String>();
		for( String string : srcSet ){
			if( !DevToolsUtils.isEmpty( string ) ){
				if( string.startsWith( prefix ) ){
					filteredSet.add( string );
				}
			}

		}
		return filteredSet;
	}

	/**
	 * This method filters the duplicates elements from the list of specified type
	 * @param unfilteredList
	 * @return uniqueList
	 */

	public static <T> List<T> filterDuplicates( List<T> unfilteredList ){
		if( DevToolsUtils.isEmpty( unfilteredList ) ){
			return unfilteredList;
		}

		/* Filter the duplicate items in the list using hash set */
		Set<T> set = new HashSet<T>();
		set.addAll( unfilteredList );

		/* Add the resultant set to the new list */
		List<T> uniqueList = new ArrayList<T>();
		uniqueList.addAll( set );
		return uniqueList;
	}

	/**
	 * This method converts the firstCharacter of the given string to uppercase and returns the string.
	 * @param string
	 * @return String
	 */
	public static String firstCharToUpperCase( String string ){
		if( DevToolsUtils.isEmpty( string ) ){
			return null;
		}
		StringBuffer sb = new StringBuffer();
		char firstCh = string.charAt( 0 );
		String firstChUppercase = String.valueOf( firstCh ).toUpperCase();
		String remainderString = string.substring( 1 );
		sb.append( firstChUppercase );
		sb.append( remainderString );
		return sb.toString();
	}

	/**
	 * This method generates a random number in the hexa decimal string format
	 * @return String - Random Heaxdecimal string
	 */
	public static String generateRandomHexString(){
		Random random = new Random();
		Long randomNumber = random.nextLong();
		String token = Long.toHexString( randomNumber );
		return token;
	}


	/**
	 * This method returns the Class object associated with the class or interface with the given string name.
	 * @param clazzName
	 * @return
	 */
	public static Class<?> getClass( String clazzName ){
		Class<?> clazz = null;
		try{
			clazz = Class.forName( clazzName );
		}
		catch( ClassNotFoundException e ){
			logger.error( e, "An error occurred while getting the class object for the class or interface_1" );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "An error occurred while getting the class object for the class or interface_2" );
		}
		return clazz;
	}

	/**
	 * This method returns the Class object associated with the class or interface with the given string name and a
	 * given class loader
	 * @param clazzName
	 * @param initialize
	 * @param classLoader
	 * @return class object
	 */
	public static Class<?> getClass( String clazzName, boolean initialize, ClassLoader classLoader ){
		Class<?> clazz = null;
		try{
			clazz = Class.forName( clazzName, initialize, classLoader );
		}
		catch( ClassNotFoundException e ){
			logger.error( e, "An error occurred while getting the class object for the class or interface_3" );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "An error occurred while getting the class object for the class or interface_4" );
		}
		return clazz;
	}

	/**
	 * @return the validationContext
	 */
	public static String getValidationContext(){
		return validationContext;
	}

	/**
	 * This method returns a custom date based on whether you need a future date or previous date. If any of the input
	 * params are null, the method returns null.
	 * @param currentDate
	 * @param Number of days
	 * @param isFutureDate
	 * @return Date
	 */
	public static Date getCustomDate( Date currentDate, Integer days, Boolean isFutureDate ){
		long customTimeInMilliSec;
		Date customDate = null;
		long oneDayInMilliSec = 60 * 60 * 24 * 1000;

		if( currentDate != null && days != null && isFutureDate != null ){
			if( isFutureDate ){
				// If the flag is set to true, return future date.
				customTimeInMilliSec = currentDate.getTime() + ( days * oneDayInMilliSec );
			}
			else{
				// If the flag is set to false, return a previous date.
				customTimeInMilliSec = currentDate.getTime() - ( days * oneDayInMilliSec );
			}
			customDate = new Date( customTimeInMilliSec );
		}
		return customDate;
	}

	/**
	 * This method returns the type of the element of a list. Note: This method returns the type of the first non-null
	 * element found in the list. This method returns reliable results for type safe lists. This method shouldn't be
	 * used with lists which are not type safe since they may contain more than one element type.
	 * @param <T>
	 * @param list
	 * @return elementType
	 */
	private static <T> Class<?> getElementType( List<T> list ){
		Class<?> elementType = null;
		if( !DevToolsUtils.isEmpty( list ) ){
			for( T t : list ){
				if( !DevToolsUtils.isEmpty( t ) ){
					/*
					 * Return the element type of the first non-null element in the list
					 */
					elementType = t.getClass();
					break;
				}
			}
		}
		return elementType;
	}

	/**
	 * Convert a number to a Locale specific currency. The maximum fractions digits associated with the currency can be
	 * specified.
	 * @param number = Number that will be converted to a string that represents Locale specific currency.
	 * @param locale = Locale to which
	 * @param maxfractiondigits = maximum number of fraction digits currency representation
	 * @return
	 */
	public static String getFormattedCurrency( double number, Locale locale, int maxfractiondigits ){
		NumberFormat formatNum = NumberFormat.getCurrencyInstance( locale );
		formatNum.setMaximumFractionDigits( maxfractiondigits );
		return formatNum.format( number );
	}

	/**
	 * Return the Formatted Number.
	 * @param number String representation of the passed in number.
	 * @return
	 */
	public static String getFormattedNumberWithDecimals( double number, int maxfractiondigits ){
		NumberFormat formatNum = NumberFormat.getInstance();
		// formatNum.
		formatNum.setMaximumFractionDigits( maxfractiondigits );
		return formatNum.format( number );
	}

	/**
	 * This method returns the log file location.
	 * @return logFileLocation
	 */
	public static String getLogConfigLocation(){
		String logFileLocation = null; //TODO: This is not yet implemented. (16th Feb, 2011)
		if( !isEmpty( logFileLocation ) ){
			return logFileLocation;
		}
		else{
			return EMPTY_STRING;
		}
	}

	@SuppressWarnings( "unchecked" )
	public static <T> T getMapperInstance( Class<T> mapperClass ){
		if( DevToolsUtils.isEmpty( mapperClass ) ){
			return null;
		}
		T t = (T) DevToolsUtils.newInstance( mapperClass.getName() );
		return t;
	}

	/**
	 * This method returns the method object based on the object, method name and input parameters.
	 * @param object
	 * @param methodName
	 * @param params
	 * @return method
	 */
	public static Method getMethod( Object object, String methodName, Object... params ){
		Method method = null;
		if( DevToolsUtils.isEmpty( object ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "The bean on which the method is to be invoked is null" );
		}
		Class<?>[] parameterTypes = DevToolsUtils.getParameterTypes( params );
		try{
			method = object.getClass().getMethod( methodName, parameterTypes );
		}
		catch( SecurityException e ){
			logger.error( e );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "An error occurred while getting the method object." );
		}
		catch( NoSuchMethodException e ){
			logger.error( e );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "An error occurred while getting the method object." );
		}
		return method;
	}

	/**
	 * This method will return the name of the method in the class .
	 * @param property
	 * @returns the method name
	 */
	public static String getMethodName( String property ){
		StringBuffer getMthodName = null;
		String methodName = null;
		try{
			getMthodName = new StringBuffer( "get" + property );
			methodName = getMthodName.substring( 3, 4 ).toUpperCase();
			getMthodName.setCharAt( 3, methodName.toCharArray()[ 0 ] );
		}
		catch( Exception ex ){
			logger.error( ex );
			logger.error( " Exception while comparing beans [DevToolsUtils] [getMethodName]" );
		}
		return getMthodName.toString();
	}

	/**
	 * This method returns an array of parameter types
	 * @param methodName
	 * @param clazz
	 * @return parameterTypes
	 */
	public static Class<?>[] getMethodParameters( String methodName, Class<?> clazz ){
		Class<?>[] parameterTypes = null;

		if( DevToolsUtils.isEmpty( methodName ) || DevToolsUtils.isEmpty( clazz ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Method name or the class is null." );
		}

		Method[] methods = clazz.getMethods();
		for( Method method : methods ){
			if( methodName.equals( method.getName() ) ){
				parameterTypes = method.getParameterTypes();
			}
		}
		return parameterTypes;
	}

	/**
	 * This method returns the array of the parameter types.
	 * @param params
	 * @return Class<?>[]
	 */
	public static Class<?>[] getParameterTypes( Object... params ){
		Class<?>[] parameterTypes = null;
		if( !DevToolsUtils.isEmpty( params ) ){
			parameterTypes = new Class[ params.length ];
			for( int index = 0; index < params.length; index++ ){
				if( !DevToolsUtils.isEmpty( params[ index ] ) ){
					parameterTypes[ index ] = params[ index ].getClass();
				}
			}
		}
		return parameterTypes;
	}

	/**
	 * Returns an instance of Method with the only parameter passed.
	 * @Author jraghu on Aug 11, 2010
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Method getMethodInObject( Object object, String methodName, Object param ){
		Class clazz = object.getClass();

		final Object[] params = new Object[ 1 ];
		params[ 0 ] = param;

		return getMethodInClass( clazz, methodName, params );
	}

	/**
	 * Returns an instance of Method with the passed parameters.
	 * @Author jraghu on Aug 11, 2010
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Method getMethodInObject( Object object, String methodName, Object[] params ){
		Class clazz = object.getClass();

		return getMethodInClass( clazz, methodName, DevToolsUtils.getParameterTypes( params ) );
	}

	public static Method getMethodInClass( Class clazz, String methodName, Class[] params ){
		Class<?>[] parameterTypes = params;

		Method method = null;
		try{
			method = clazz.getDeclaredMethod( methodName, parameterTypes );
		}
		catch( SecurityException e ){
			if( logger.isDebug() )
				logger.debug( "Not allowed to lookup method [" + methodName + "] in class [" + clazz.getName()
						+ "] with params [" + parameterTypes + "]" );
		}
		catch( NoSuchMethodException e ){
			if( logger.isDebug() )
				logger.debug( "No method [" + methodName + "] in class [" + clazz.getName() + "] with params ["
						+ parameterTypes + "]" );
		}

		return method;
	}

	/**
	 * Returns an instance of Method with the only parameter passed.
	 * @Author jraghu on Aug 11, 2010
	 * @param object
	 * @param methodName
	 * @param params
	 * @return
	 */
	public static Method getMethodInClass( Class clazz, String methodName, Object param ){
		final Object[] params = new Object[ 1 ];
		params[ 0 ] = param;

		return getMethodInClass( clazz, methodName, DevToolsUtils.getParameterTypes( params ) );
	}

	/**
	 * Invoke a static method on the given class name using the only parameter passed.
	 * @Author jraghu on Aug 11, 2010
	 * @param className
	 * @param methodName
	 * @param param
	 * @return
	 */
	public static Object invokeStaticMethod( String className, String methodName, Object param, Class paramType ){
		final Object[] params = new Object[ 1 ];
		params[ 0 ] = param;

		Class[] paramTypes = new Class[ 1 ];
		if( !isEmpty( paramType ) ){
			paramTypes[ 0 ] = paramType;
		}
		else if( !isEmpty( param ) ){
			paramTypes[ 0 ] = param.getClass();
		}

		return invokeStaticMethod( className, methodName, params, paramTypes );
	}

	/**
	 * Invoke a static method on the given class name having all the parameters passed in the signature.
	 * @Author jraghu on Aug 11, 2010
	 * @param className
	 * @param methodName
	 * @param param
	 * @return
	 */
	public static Object invokeStaticMethod( String className, String methodName, Object[] param, Class[] paramTypes ){
		// BaseVO baseVO = (BaseVO) DevToolsUtils.newInstance( className );
		Class clazz = null;
		try{
			clazz = Class.forName( className );
		}
		catch( ClassNotFoundException e ){
			if( logger.isError() )
				logger.error( "Could not load class [" + className + "] to reconstruct PortalEventVO payload." );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e,  "Could not load class [", className, "] to reconstruct PortalEventVO payload." );
		}

		Method valueOfMethod = DevToolsUtils.getMethodInClass( clazz, methodName, paramTypes );

		/*
		 * Call the valueOf() method of the PayLoadVO. This returns the payload with values set based on
		 * publicRenderParamMap.
		 */
		try{
			return valueOfMethod.invoke( clazz, param );
		}
		catch( Exception e ){
			if( logger.isError() )
				logger.error( "Could not invoke method [" + methodName + "] on [" + className + "]." );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "Could not invoke method [", methodName, "] on [", className, "]." );
		}
	}

	/**
	 * This method returns the Portal Configuration Id of the portal in the environment of which this DevToolsUtils is running.
	 * @return
	 */
	public static int getPortalConfigurationId(){
		return portalConfigurationId;
	}

	/**
	 * @param property
	 * @param clazz
	 * @return propertyDescriptor
	 */
	public static PropertyDescriptor getPropertyDescriptor( String property, Class<?> clazz ){
		PropertyDescriptor propertyDescriptor = null;

		if( DevToolsUtils.isEmpty( property ) || DevToolsUtils.isEmpty( clazz ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Property or class name is empty." );
		}
		try{
			propertyDescriptor = new PropertyDescriptor( property, clazz );
		}
		catch( IntrospectionException e ){
			logger.error( e, "An error occurred while getting the information about attribute provided in the java bean." );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "Property or class name is empty." );
		}
		return propertyDescriptor;
	}

	/**
	 * This method returns the configured key from the properties file.
	 * @param resourceBundle A named resource
	 * @param key An identifier for the values stored in the properties file
	 * @return value A value for a given key
	 * @throws MissingResourceException
	 */
	public static String getPropertyValue( ResourceBundle resourceBundle, String key ) throws MissingResourceException{
		if( DevToolsUtils.isEmpty( resourceBundle ) || DevToolsUtils.isEmpty( key ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Either the resourceBundle is null or the property key is empty" );
		}
		return resourceBundle.getString( key );
	}

	/**
	 * This method returns the resource url from a class loader.
	 * @param classLoader
	 * @param resName
	 * @return resource url
	 */
	public static URL getResource( ClassLoader classLoader, String resName ){
		if( DevToolsUtils.isEmpty( classLoader ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Class loadeer information not found." );
		}

		if( DevToolsUtils.isEmpty( resName ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource name is empty." );
		}

		return classLoader.getResource( resName );
	}

	/**
	 * This method returns the resource url from a class loader.
	 * @param classLoader
	 * @param resName
	 * @return resource url
	 */
	public static java.io.File getResourceAsFile( String resURI ){
		java.io.File file = null;
		if( DevToolsUtils.isEmpty( resURI ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource URI is empty_1" );
		}

		file = new java.io.File( resURI );

		return file;
	}

	/**
	 * This method returns the resource url from a class loader.
	 * @param classLoader
	 * @param resName
	 * @return resource url
	 */
	public static URL getResourceAsFileURL( String resURI ){
		URL url = null;
		if( DevToolsUtils.isEmpty( resURI ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource URI is empty_2" );
		}

		try{
			url = new URL( "file:///" + resURI );
		}
		catch( MalformedURLException e ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource URI is invalid." );
		}
		return url;
	}

	/**
	 * This method returns the resource url from a class loader.
	 * @param classLoader
	 * @param resName
	 * @return resource url
	 */
	public static URL getResourceAsHttpURL( String resURI ){
		URL url = null;
		if( DevToolsUtils.isEmpty( resURI ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource URI is empty_3" );
		}

		try{
			url = new URL( resURI );
		}
		catch( MalformedURLException e ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Resource URI is invalid." );
		}
		return url;
	}

	/**
	 * This method returns the resource url from a class loader.
	 * @param resource
	 * @return null
	 * @throws MissingResourceException
	 * @deprecated This method shouldn't be used anymore as this is a potential place for reloaded
	 * properties not being visible. This has no replacement. Please use 
	 * <code>com.keenan.cmv.utils.Configurations</code> instead. 
	 */
	public static ResourceBundle getResourceBundle( String resource ) throws MissingResourceException{
		return null;
	}

	/**
	 * This method returns the current time in .
	 * @return
	 */
	public static long getTimeInMillis(){
		return Calendar.getInstance().getTimeInMillis();
	}

	public static Object initializeBeanField( String fieldName, Object bean ){
		fieldName = fieldName.replace( ".", "#" );
		return initializeDeepBeanField( fieldName, bean );
	}

	public static Object initializeDeepBeanField( String fieldName, Object bean ){
		if( bean == null ){
			return null;
		}
		if( fieldName.contains( "#" ) ){
			String[] fieldNames = fieldName.split( "#" );
			StringBuffer sb = new StringBuffer( fieldNames[ 0 ] );
			sb.append( "#" );
			bean = inspectAndInitializeField( fieldNames[ 0 ], bean );
			fieldName = fieldName.replace( sb.toString(), "" );
		}
		else{
			return inspectAndInitializeField( fieldName, bean );
		}
		return initializeDeepBeanField( fieldName, bean );
	}

	public static Object inspectAndInitializeField( String fieldName, Object bean ){
		Object fieldData = null;
		Object args[] = null;

		if( bean == null ){
			return fieldData;
		}
		PropertyDescriptor pd = DevToolsUtils.getPropertyDescriptor( fieldName, bean.getClass() );
		Method getterMethod = pd.getReadMethod();
		Method setterMethod = pd.getWriteMethod();
		try{
			fieldData = getterMethod.invoke( bean, args );
			if( fieldData == null ){
				/*
				 * Try instantiating the non-initialized fields by invoking the default constructor. If it fails, return
				 * the fieldData as is.
				 */
				try{
					fieldData = Class.forName( pd.getPropertyType().getName() ).newInstance();
				}
				catch( Exception ex ){
					logger.error( ex );
					return fieldData;
				}
				setterMethod.invoke( bean, fieldData );
			}
		}
		catch( IllegalArgumentException e ){
			logger.error( e );
		}
		catch( IllegalAccessException e ){
			logger.error( e );
		}
		catch( InvocationTargetException e ){
			logger.error( e );
		}
		return fieldData;
	}

	/**
	 * This method returns true if a string contains a specified character at a negatvie index
	 * @param string
	 * @return true | false
	 */
	public static boolean isCharAtNegativeIndex( String string, char character ){
		if( !DevToolsUtils.isEmpty( string ) ){
			return string.indexOf( character ) < 1 ? true : false;
		}
		return false;
	}

	/**
	 * This method returns true if the collection is null or is empty.
	 * @param collection
	 * @return true | false
	 */
	public static boolean isEmpty( Collection<?> collection ){
		if( collection == null || collection.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true of the map is null or is empty.
	 * @param map
	 * @return true | false
	 */
	public static boolean isEmpty( Map<?, ?> map ){
		if( map == null || map.isEmpty() ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the objet is null.
	 * @param object
	 * @return true | false
	 */
	public static boolean isEmpty( Object object ){
		if( object == null ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input array is null or its length is zero.
	 * @param array
	 * @return true | false
	 */
	public static boolean isEmpty( Object[] array ){
		if( array == null || array.length == 0 ){
			return true;
		}
		return false;
	}

	/**
	 * This method returns true if the input string is null or its length is zero.
	 * @param string
	 * @return true | false
	 */
	public static boolean isEmpty( String string ){
		if( string == null || string.trim().length() == 0 ){
			return true;
		}
		return false;
	}

	/**
	 * This method <code>maskAmpersands</code> accpets an array of strings with ampersands and returns with an array of
	 * string with masked apostrophe. This masking is required usually during insertion or updation of a record into
	 * database. The purpose of masking is to avoid prompts from the DB which would be encountered because of '&'
	 * character. Note: If the inpur string is null, the method returns the same string unmodified
	 * @param string
	 * @return masked string
	 */
	public static String maskAmpersands( String string ){
		String str = string;

		if( string != null ){
			if( string.contains( "&" ) ){
				str = string.replaceAll( "&", "'||'&'||'" );
			}
		}
		return str;
	}

	/**
	 * This method <code>maskApostrophe</code> replaces the apostrophes in the input string with another apostrophe.
	 * Returns TRUE if yes, else it returns FALSE.
	 * @param string
	 * @return masked string
	 */
	public static String maskApostrophe( String string ){
		String str = string;

		if( string != null ){
			if( string.contains( "'" ) ){
				str = string.replaceAll( "'", "''" );
			}
		}
		return str;
	}

	/**
	 * This method creates a new instance of a class provided by the client.
	 * @param className
	 * @return new instance of a class
	 */
	public static Object newInstance( String className ){
		Object obj = null;

		if( DevToolsUtils.isEmpty( className ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Classs name is empty" );

		}

		try{
			obj = Class.forName( className ).newInstance();
		}
		catch( InstantiationException ex ){
			logger.error( ex );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Class [" + className + "] cannot be instantiated." );
		}
		catch( IllegalAccessException ex ){
			logger.error( ex );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Class[" + className + "] cannot be accessed" );
		}
		catch( ClassNotFoundException ex ){
			logger.error( ex );
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Invalid class name [" + className + "]" );
		}
		return obj;
	}

	/**
	 * This method will perform the required checks on the two objects being compared
	 * @param first
	 * @param second
	 * @returns a boolean
	 */
	private static Boolean performRequiredChecks( Object first, Object second ){
		/* If both the passed objects are null, return true. */
		if( first == null && second == null ){
			return true;
		}
		/* If only one is null, then return false. */
		else if( first == null || second == null ){
			return false;
		}

		/*
		 * Get the types of the two objects. They should match. If they don't return false.
		 */
		if( !first.getClass().getName().equals( second.getClass().getName() ) ){
			return false;
		}
		return null;
	}

	/**
	 * This method will replace the old character with new character
	 * @param str
	 * @param oldCharSeq
	 * @param newCharSeq
	 * @return
	 */
	public static String replace( String str, String oldCharSeq, String newCharSeq ){
		if( DevToolsUtils.isEmpty( str ) || DevToolsUtils.isEmpty( oldCharSeq ) || DevToolsUtils.isEmpty( newCharSeq ) ){
			return str;
		}
		String res = "";
		int count = str.indexOf( oldCharSeq, 0 );
		int lastpos = 0;
		while( count != -1 ){
			res += str.substring( lastpos, count ) + newCharSeq;
			lastpos = count + oldCharSeq.length();
			count = str.indexOf( oldCharSeq, lastpos );
		}
		res += str.substring( lastpos );
		return res;

	}

	/**
	 * This method returns the reverse order comparator for a given comparator.
	 * @param <T>
	 * @param comparator
	 * @return
	 */
	private static <T> Comparator<? super T> reverseOrder( Comparator<? super T> comparator ){
		return Collections.reverseOrder( comparator );
	}

	/**
	 * This method sets the contextId which is used to get information about bean validationContext. The validationContext information is
	 * allowed to be initialized only once. This method throws a system exception if the contextId passed by the client
	 * is null.
	 * @param context
	 * @throws SystemException
	 */

	public static void setValidationContext( String context ){
		if( DevToolsUtils.isEmpty( context ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Context Id is empty." );
		}

		synchronized( DevToolsUtils.class ){
			if( validationContext == null ){
				validationContext = context;
			}
		}
	}

	/**
	 * This method initializes DevToolsUtils with the Portal Configuration Id of the portal in the environment of which this
	 * DevToolsUtils instance is running.
	 * @param portalConfigId
	 */
	public static void setPortalConfigurationId( int portalConfigId ){
		if( portalConfigurationId == -1 ){
			portalConfigurationId = portalConfigId;
		}
	}

	/**
	 * This method sorts a given collection based on the comparator provided and the sorting order.
	 * @param <T>
	 * @param list
	 * @param comparator
	 * @param ascending
	 */
	public static <T> void sort( List<T> list, Comparator<? super T> comparator, boolean ascending ){
		if( DevToolsUtils.isEmpty( list ) ){
			return;
		}

		if( ascending ){
			sortAscending( list, comparator );
		}
		else{
			sortDescending( list, comparator );
		}
	}

	/**
	 * @param <T>
	 * @param list
	 * @param comparator
	 */
	private static <T> void sortAscending( List<T> list, Comparator<? super T> comparator ){
		Collections.sort( list, comparator );
	}

	/**
	 * @param <T>
	 * @param list
	 * @param comparator
	 */
	private static <T> void sortDescending( List<T> list, Comparator<? super T> comparator ){
		Collections.sort( list, reverseOrder( comparator ) );
	}

	/**
	 * This method converts a list to an array.
	 * @param <T>
	 * @param list
	 * @param array
	 * @return T[]
	 */
	@SuppressWarnings( "unchecked" )
	public static <T> T[] toArray( List<T> list, T[] array ){
		Class<?> elementType = null;
		/* If the list to be converted to the array is empty, return null */
		if( DevToolsUtils.isEmpty( list ) ){
			return null;
		}

		/*
		 * Identify the type of the array to be created based on the type of the elements in the list
		 */
		elementType = getElementType( list );

		/*
		 * If the elementType is null all the elements of the list are null, hence the skip any further copy activity
		 */
		if( DevToolsUtils.isEmpty( elementType ) ){
			return null;
		}

		/*
		 * If the array to be copied is empty, create a new instance of the array of size same as the source collection
		 * and populate it.
		 */
		if( DevToolsUtils.isEmpty( array ) ){
			array = (T[]) Array.newInstance( elementType, list.size() );
		}

		/*
		 * Copy the elements individually since direct reference assignment will return an Object[]
		 */
		Object[] destArray = list.toArray( array );
		if( !DevToolsUtils.isEmpty( destArray ) ){
			for( int i = 0; i < destArray.length; i++ ){
				if( !DevToolsUtils.isEmpty( destArray[ i ] ) ){
					array[ i ] = (T) destArray[ i ];
				}
			}
		}

		return array;
	}

	/**
	 * This method converts a list to an array.
	 * @param <T>
	 * @param set
	 * @param array
	 * @return T[]
	 */
	public static <T> T[] toArray( Set<T> set, T[] array ){
		if( DevToolsUtils.isEmpty( set ) ){
			return null;
		}

		/* This method converts a set to a list */
		List<T> list = DevToolsUtils.asList( set );

		return (T[]) toArray( list, array );
	}

	/**
	 * This method converts a String Variable to a BigDecimal
	 * @param strValue
	 * @return
	 */
	public static BigDecimal toBigDecimal( String strValue ){
		Double doubleVal = Double.valueOf( strValue );
		BigDecimal bigDecimalVal = BigDecimal.valueOf( doubleVal );
		return bigDecimalVal;
	}

	/**
	 * This method returns the tokens from the string for a specified delimiter.
	 * @param property
	 * @param delimiter
	 * @return String[]
	 */
	public static String[] tokenize( String property, String delimiter ){
		String[] strTokens = null;
		if( !DevToolsUtils.isEmpty( property ) ){
			java.util.StringTokenizer strTokenizer = new java.util.StringTokenizer( property, delimiter );
			strTokens = new String[ strTokenizer.countTokens() ];
			int index = 0;
			while( strTokenizer.hasMoreElements() ){
				strTokens[ index++ ] = (String) strTokenizer.nextElement();
			}
		}
		return strTokens;
	}

	/**
	 * This method returns a string after trimming the input string and converting it to the lower case.
	 * @param string
	 * @return string value in lower case
	 */
	public static String trimToLowerCase( String string ){
		String str = null;
		if( !DevToolsUtils.isEmpty( string ) ){
			str = string.trim().toLowerCase();
		}
		return str;
	}

	
	public static String removeLeadingTrailingSpaces( String source ){
		source = source.replaceAll( "^\\s+", "" );
		return source.trim();

	}

	/**
	 * This method converts the list to comma seperated String
	 * @param List of Strings
	 * @return comma sepearted String
	 */
	public static String convertListToCommaSprtdStr( List<String> stringList ){
		String delimiter = ",";
		if( stringList.isEmpty() ) return "";
		StringBuilder sb = new StringBuilder();
		for( String x : stringList )
			sb.append( x + delimiter );
		sb.delete( sb.length() - delimiter.length(), sb.length() );
		return sb.toString();
	}

	/**
	 * This method converts the list to comma seperated String surrounded by quotes
	 * @param List of Strings
	 * @return comma sepearted String
	 */
	public static String convertListToCommaSprtdStrSurrQuotes( List<String> stringList ){
		String delimiter = ",";
		String quote = "'";
		if( stringList.isEmpty() ) return "";
		StringBuilder sb = new StringBuilder();
		for( String x : stringList )
			sb.append( quote + x + quote + delimiter );
		sb.delete( sb.length() - delimiter.length(), sb.length() );
		return sb.toString();
	}


	/**
	 * Makes a JNDI lookup and returns the lookup object.The object is stored in a HashMap.If the object is already
	 * there in the hashmap then it is returned and the lookup is not made again. *
	 * @param jndiname
	 * @return- resource object
	 */
	public static Object getResource( String jndiname ) throws NamingException{
		Object resource = null;
		// if resourceMap contains the resource object, then it is returned.
		if( resourceMap.containsKey( jndiname ) ){

			resource = resourceMap.get( jndiname );
		}
		else{
			if( null == ctx ){
				ctx = new InitialContext();
			}
			resource = ctx.lookup( jndiname );
			resourceMap.put( jndiname, resource );
		}
		return resource;

	}

	/**
	 * @Desc <p>
	 *       This method returns boolean value true if the user is impersonated.
	 *       </p>
	 * @Author alatheef on Jun 22, 2010
	 * @return boolean.
	 */
	public static boolean isUserImpersonated(){
		//TODO To be implemented (16th Feb, 2011)
		boolean userImpersonated = false;

		return userImpersonated;

	}

	/**
	 * This class will decode the url
	 * @param url
	 * @return decoded url
	 */
	public static String decodeUrl( String url ){
		String decodedUrl = null;
		if( !isEmpty( url ) ){
			try{
				decodedUrl = URLDecoder.decode( url, "UTF-8" );
			}
			catch( UnsupportedEncodingException e ){
				decodedUrl = url;
			}
		}
		return decodedUrl;

	}


	/**
	 * Returns ellipsised String.
	 * @param string which need to be ellipsis
	 * @param maxWidth length
	 * @return ellipsised String.
	 */
	public static String cutWithEllipsis( String str, int maxWidth ){

		if( null == str ){
			return null;
		}

		if( str.length() <= maxWidth ){
			return str;
		}

		return str.substring( 0, str.substring( 0, maxWidth ).lastIndexOf( " " ) ) + " ...";

	}


	/**
	 * This is a utility method to get a scanner.
	 * @param file
	 * @return scanner
	 */
	public static Scanner getScanner( File file ){
		Scanner scanner = null;
		try{
			scanner = new Scanner( file );
		}
		catch( FileNotFoundException e ){
			if( logger.isError() ){
				logger.error( e, "Unable to create the scanner object since the file was not found at the location ["
						+ file.getName() + " ] specified." );
			}
			scanner = null;
			// throw new SystemException("", "", e);
		}
		return scanner;
	}

	/**
	 * The method which will escape the quote character HTML with equivalent
	 * 
	 * @param inStr String which should be escaped
	 * @return The replaced string
	 */
	public static String replaceXmlSpecialString( String inStr ){
		if( inStr != null ){
			inStr = replaceString( inStr, "'", "&#39;" );
		}
		return inStr;
	}

	/**
	 * This method replaces the  
	 * 
	 * @param inStr Input string which needs to be replaced
	 * @param searchString The String which should be replaced with replaceString
	 * @param replaceString The String which will be replaced with all occurrence of searchString 
	 * @return
	 */
	private static String replaceString( String inStr, String searchString, String replaceString ){
		StringTokenizer st = new StringTokenizer( inStr, searchString );
		StringBuffer outStr = new StringBuffer();
		if( st.countTokens() > 0 ){
			while( st.hasMoreTokens() ){
				String temp = st.nextToken();
				outStr.append( temp );
				if( st.hasMoreTokens() ){
					outStr.append( replaceString );
				}
			}
		}
		else{
			outStr.append( inStr );
		}
		return outStr.toString();
	}

	/**
	 * This method is created to replace single occurrence of quote(') character with two quote character. 
	 * This method will be used in PreparedStatementRunner class.   
	 * @param param parameter which will should be converted.
	 * @return converted string
	 */
	public static Object escapeQuotesForParam( Object param ){
		Object retVal = param;
		if( param instanceof String ){
			retVal = ( (String) param ).replace( "'", "''" );
		}
		return retVal;
	}

	/**
	 * This method can be used to ensure that null strings are converted
	 * to empty, so that there is no case of a null object being processed
	 * upon.
	 *  
	 * @param input
	 * @return Empty string if <code>null</code> is passed, else the passed string
	 */
	public static String toNonNullString( String input ){
		String retString = input;
		if( isEmpty( input ) ){
			retString = "";
		}

		return retString;
	}

	/**
	 * This method generates a random number within a range end with the given argument. eg. If range = 100, the menthod will generate random numbers between 0 and 100
	 * @return int
	 */
	public static int generateRandomNumWithinRange( int range ){
		Random randomGenerator = new Random();
		return randomGenerator.nextInt( range );

	}

	/**
	 * This method will load the Spring validationContext XML and return the BeanFactory instance.
	 * The purpose of this method to load the Spring XML in a uniform manner across 
	 * applications.
	 * 
	 * @param contextXMLInClassPath - The name of Spring validationContext XML file along with path
	 * within the classpath.
	 * 
	 * @return - null, if passed path is null or there was an error during the load of the XML
	 *           An instance of BeanFactory representing the loaded XML
	 */
	public static BeanFactory loadSpringBeansFactory( String contextXMLInClassPath ){
		if( isEmpty( contextXMLInClassPath ) ){
			return null;
		}

		Resource res = new ClassPathResource( contextXMLInClassPath );
		BeanFactory factory = new XmlBeanFactory( res );

		return factory;
	}

	/* ******** BEAN MANAGEMENT METHODS - START ******** */
	private static BeanFactory factory;

	/**
	 * Constructs a Spring Bean for the given String object The bean Name should
	 * be existing in the applicationContext.xml as a bean id.
	 * 
	 * @param beanName
	 * @return
	 */
	public static void setSpringContext( BeanFactory factory ){
		DevToolsUtils.factory = factory;

		if( logger.isDebug() ){
			logger.debug( "DevToolsUtils.factory>>" + DevToolsUtils.factory );
		}
	}
	
	public static BeanFactory getSpringContext(){
		return factory;
	}

	/**
	 * This method returns an instance of the bean using bean manager
	 * 
	 * @param beanName The id of the bean as configured in the config xml based on which the "factory"
	 * instance has been created.
	 * @return beanInstance returned by bean manager.
	 */

	//TODO Make this private after removing dependencies. Only the getBean( IBeanName ) method
	// should be available.
	public static Object getBean( String beanName ){

		Object beanInstance = null;
		if( DevToolsUtils.isEmpty( beanName ) ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Bean Name should not be empty" );
		}
		if( factory == null ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, null, "Context xml is not loaded" );
		}

		/* Create XmlBeanFactory only once. */
		beanInstance = factory.getBean( beanName );

		return beanInstance;
	}

	/**
	 * This method returns an instance of the bean using bean manager
	 * 
	 * @param beanName The id of the bean as configured in the config xml based on which the "factory"
	 * instance has been created.
	 * @return beanInstance returned by bean manager.
	 */
	/*public static Object getBean( IBeanName beanName ){

		Object beanInstance = getBean( beanName.getBeanName() );

		return beanInstance;
	}*/

	/* ******** BEAN MANAGEMENT METHODS - END ********** */

	/**
	 * This method posts the passed request parameter values to the specified URL.
	 * 
	 * @param serviceUrl The url to which the post request is made.
	 * @param urlParamData The request parameters
	 * @return response returned by the POST request handler.
	 */
	public static String sendPostRequest( String serviceUrl, String urlParamData ){

		StringBuffer response = new StringBuffer();

		try{
			// Send the request
			URL url = new URL( serviceUrl );
			URLConnection conn = url.openConnection();
			conn.setDoOutput( true );
			OutputStreamWriter writer = new OutputStreamWriter( conn.getOutputStream() );

			//write parameters
			writer.write( urlParamData );
			writer.flush();

			// Get the response

			BufferedReader reader = new BufferedReader( new InputStreamReader( conn.getInputStream() ) );
			String line;
			while( ( line = reader.readLine() ) != null ){
				response.append( line );
			}
			writer.close();
			reader.close();

		}
		catch( MalformedURLException ex ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Malformed URL" );
		}
		catch( UnsupportedEncodingException ex ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Contains unsupported charcters for encoding." );
		}
		catch( IOException ex ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, ex, "Error occured while posting the request to the URL." );
		}

		return response.toString();
	}


	/**
	 * Returns a URL for the underlying class path resource
	 *
	 * @param resourceName Resource present in classpath
	 * @return URL for the class path resource
	 */
	public static URL getClassPathResourceURL( String resourceName ){

		if( !DevToolsUtils.isEmpty( resourceName ) ){

			ClassPathResource classPathResource = new ClassPathResource( resourceName );

			if( !DevToolsUtils.isEmpty( classPathResource ) ){
				try{
					if( null != classPathResource.getFile() ){
						return classPathResource.getURL();
					}
				}
				catch( IOException e ){
					throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "Error occured while reading the classpath resource." );
				}
			}
		}
		return null;
	}


	/**
	 * Reads the input stream to a string.
	 * @param InputStream
	 * @return String containg the input stream data.
	 * @throws IOException
	 */
	public static String streamToString( InputStream in ){

		try{
			if( !DevToolsUtils.isEmpty( in ) ){

				StringBuffer out = new StringBuffer();
				byte[] b = new byte[ 4096 ];

				for( int n; ( n = in.read( b ) ) != -1; ){
					out.append( new String( b, 0, n ) );
				}
				return out.toString();
			}
		}
		catch( IOException e ){
			throw new SystemException( CommonErrorKey.UNKNOWN_ERROR, e, "Error occured while reading the input stream." );
		}
		return null;

	}


	
	/**
	 * This method will give unique objects from list
	 * @param clients
	 * @return list if unique objects
	 */
	public static <T> List<T> removeDuplicatesFromList( List<T> objects ){
		Set<T> uniqueClientList = null;
		if( DevToolsUtils.isEmpty( objects ) ){
			return objects;
		}
		uniqueClientList = new HashSet<T>();
		List<T> clientsList = new ArrayList<T>();
		uniqueClientList.addAll( objects );
		clientsList.addAll( uniqueClientList );
		return clientsList;
	}

	
	/**
	 * Gets the content type for a particular file extension
	 */

	public static String getContentType( String fileName ){

		String contType = "";

		if( !DevToolsUtils.isEmpty( fileName ) ){

			String ext = fileName.substring( fileName.lastIndexOf( '.' ) + 1, fileName.length() );

			if( !DevToolsUtils.isEmpty( ext ) ){

				if( ext.equalsIgnoreCase( "txt" ) )
					contType = "text/plain";
				else if( ext.equalsIgnoreCase( "xls" ) )
					contType = "application/vnd.ms-excel";
				else if( ext.equalsIgnoreCase( "doc" ) || ext.equalsIgnoreCase( "docx" ) )
					contType = "application/msword";
				else if( ext.equalsIgnoreCase( "html" ) || ext.equalsIgnoreCase( "htm" ) )
					contType = "text/html";
				else if( ext.equalsIgnoreCase( "jpg" ) || ext.equalsIgnoreCase( "jpeg" ) )
					contType = "image/jpeg";
				else if( ext.equalsIgnoreCase( "bmp" ) )
					contType = "image/bmp";
				else if( ext.equalsIgnoreCase( "pdf" ) )
					contType = "application/pdf";
				else if( ext.equalsIgnoreCase( "ppt" ) )
					contType = "application/vnd.ms-powerpoint";
				else if( ext.equalsIgnoreCase( "xml" ) )
					contType = "text/xml";
				else if( ext.equalsIgnoreCase( "zip" ) ) contType = "application/vnd.ms-zip";
			}
		}

		return contType;
	}
	
	
	public static String currentDate(){
		 Calendar currentDate = Calendar.getInstance();
		 SimpleDateFormat formatter= 
			    new SimpleDateFormat("yyyy-MM-dd");
		 String dateNow = formatter.format(currentDate.getTime());
		 return dateNow;
	 }
	
	/**
	 * Method to get a "clone" or a full copy of an object.
	 * 
	 * @param <T>
	 * @param t
	 * @return
	 */
	public static <T> T getCopy( T t ){
		T copy = null;
		try{
			Class clazz = t.getClass();
			copy = (T) clazz.newInstance();
			BeanUtils.copyProperties( t, copy );
		}
		catch( BeansException e ){
			if( logger.isError() ) logger.error( e, "BeansException: Could not create copy of bean" );
		}
		catch( InstantiationException e ){
			if( logger.isError() ) logger.error( e, "InstantiationException: Could not create copy of bean" );
		}
		catch( IllegalAccessException e ){
			if( logger.isError() ) logger.error( e, "IllegalAccessException: Could not create copy of bean" );
		}
		return copy;
	}
	
	/**
	 * A method to read the content from an input stream into a byte array.
	 * 
	 * @param in
	 * @return
	 */
	/*public static byte[] readInputStreamToByteArray( InputStream in ){
		byte[] inBytes = null;
		try{
			inBytes = IOUtils.toByteArray( in );
		}
		catch( IOException e ){
			logger.error( "Couldn't read from input stream", e );
		}
		return inBytes;
	 }
	*/
	/**
	 * This method checks if the passed value is already present in the array.
	 * 
	 * @param <T>
	 * @param array
	 * @param value
	 * @return
	 */
	public static <T> boolean contains( T[] array, T value ){
		for( T t : array ){
			if( t.equals( value ) ){
				return true;
			}
		}
		return false;
	}
	

	/**
	 * Returns the application name (in short hand) set as a context for the application in
	 * whose environment this DevToolsUtils class was loaded.
	 * 
	 * @return
	 */
	public static String getAppName(){
		return appName;
	}

	/**
	 * Set the application name (in short hand) set as a context for the application in
	 * whose environment this DevToolsUtils class was loaded. This can be used by the application to do
	 * some initialization activities.
	 * 
	 * @param appName
	 */
	public static void setAppName( String appName ){
		DevToolsUtils.appName = appName;
		
		/* Also use the same value as the context name for Validation Engine configuration. */
		DevToolsUtils.setValidationContext( DevToolsUtils.appName );
	}
	/**
	 * A method to read the content from a File into a byte array.
	 * 
	 * @param path
	 * @return
	 */
	public static byte[] readFileToByteArray(String path) {
		byte[] bytes = null;
		DataInputStream dis = null; //SONARFIX - 20-apr-2018 -- took the variable outside the try block scope
		try {
			File file = new File(path);
			// File length
			int size = (int) file.length();
		
			dis = new DataInputStream(new FileInputStream(file));
			int read = 0;
			int numRead = 0;
			bytes = new byte[size];
			while (read < bytes.length
					&& (numRead = dis.read(bytes, read, bytes.length - read)) >= 0) {
				read = read + numRead;
			}			
		} catch (Exception e) {
			e.getMessage();
		}
		
		finally{  //SONARFIX - 20-apr-2018 -- added finally block
			try{
			if(dis!=null){
				dis.close();
			  }
			}
			catch(Exception e){
				e.getMessage();
			 }
			}
		
		return bytes;
	}
	
	
	public static String formatDate(String date) {
		if(!isEmpty(date)){
		String[] value = date.split("-");
		date = value[1] + "/" + value[2] + "/" + value[0];
		}
		return date;
	}
	
	public static String yesterDate(){
		Calendar currentDate = Calendar.getInstance();
		SimpleDateFormat formatter = new SimpleDateFormat( "yyyy-MM-dd" );
		int cal = currentDate.get( currentDate.DATE );
		currentDate.set( currentDate.get( currentDate.YEAR ), currentDate.get( currentDate.MONTH ), cal - 1 );
		String dateYest = formatter.format( currentDate.getTime() );
		return dateYest;
	}

	/**
	 * A convenience method to concatenate multiple strings without the use of the '+' operator.
	 * 
	 * @param strings
	 * @return
	 */
	public static String concat( String... strings ){
		if( isEmpty( strings ) ){
			return null;
		}
		
		StringBuilder sb = new StringBuilder();
		
		for( String s : strings ){
			sb.append( s );
		}
		
		return sb.toString();
	}

	public static String getProperty( ResourceBundle config, String key ){
		String value = "";
		try{
			value = config.getString( key );
		}
		catch( Exception e ){
			e.printStackTrace();
		}
		return value;
	}

	public static String stripLastField( String deepFieldChain ){
		if( isEmpty( deepFieldChain ) || deepFieldChain.lastIndexOf( '.' ) == -1 ) return deepFieldChain;
		
		String deepFieldChainWithoutLastPart = deepFieldChain.substring( 0, deepFieldChain.lastIndexOf( '.' ) );
		return deepFieldChainWithoutLastPart;
	}

	public static String trimCollBraces( String attr ){
		String trimmedAttr = attr;
		if( !isEmpty( attr ) && attr.endsWith( BeanMapperConstants.MULTI_ELEM_COLL_CONFIG_BRACES ) ) trimmedAttr = attr.substring( 0, attr.length() - 2 );
		return trimmedAttr;
	}
}
