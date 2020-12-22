package com.rsaame.pas.util;

import javax.servlet.http.HttpServletRequest;

import com.mindtree.ruc.cmn.exception.SystemException;
import com.mindtree.ruc.cmn.utils.BeanUtils;
import com.mindtree.ruc.cmn.utils.Utils;

public class PASJSONUtils{
	public static String toJSONDateString( HttpServletRequest request, String beanField, String beanReqAttr, String dateFormat ){
		String dateAsString = Utils.getDateAsString( 
				(java.util.Date) BeanUtils.getDeepFieldBean( beanField, 
															 request.getAttribute( beanReqAttr ) ), 
				dateFormat );
		
		if( Utils.isEmpty( dateAsString ) ) dateAsString = "null";
		
		return dateAsString;
	}
	
	/**
	 * Returns a string constructed from the value of the specified <code>property</code> without the decimal part.
	 * 
	 * @param request
	 * @param property
	 * @param beanReqAttr
	 * @param numberType Either of "INT", "LONG", "DOUBLE"
	 * @return
	 */
	public static String toJSONNoDecNumber( HttpServletRequest request, String property, String beanReqAttr ){
		Object fieldValue = BeanUtils.getDeepFieldBean( property, request.getAttribute( beanReqAttr ) );
		
		String returnString = "";
		
		determineValue: {
			/* Return "" if the value is not present. */
			if( Utils.isEmpty( fieldValue ) ) break determineValue;
			
			/* Handle specific cases of Number instances. */
			if( fieldValue instanceof Number ){
				if( fieldValue instanceof Double ){ returnString = String.valueOf( ( (Double) fieldValue ).longValue() ); break determineValue; }
				if( fieldValue instanceof Long ){ returnString = String.valueOf( ( (Long) fieldValue ).longValue() ); break determineValue; }
				if( fieldValue instanceof Integer ){ returnString = String.valueOf( ( (Integer) fieldValue ).intValue() ); break determineValue; }
				if( fieldValue instanceof Short ){ returnString = String.valueOf( ( (Short) fieldValue ).shortValue() ); break determineValue; }
				if( fieldValue instanceof Float ){ returnString = String.valueOf( ( (Float) fieldValue ).longValue() ); break determineValue; }
			}
			else{
				throw new SystemException( "cmn.fieldIsNotANumber", null, "The field [", property, "] is not a number" );
			}
		}
		
		return returnString;
	}
	//Added for Informap Changes
	public static String toJSONNoDecNumberForDoubleVal( HttpServletRequest request, String property, String beanReqAttr ){
		Object fieldValue = BeanUtils.getDeepFieldBean( property, request.getAttribute( beanReqAttr ) );
		
		String returnString = "";
		
		determineValue: {
			/* Return "" if the value is not present. */
			if( Utils.isEmpty( fieldValue ) ) break determineValue;
			
			/* Handle specific cases of Number instances. */
			if( fieldValue instanceof Number ){
				if( fieldValue instanceof Double ){ returnString = String.valueOf( ( (Double) fieldValue )); break determineValue; }				
			}
			else{
				throw new SystemException( "cmn.fieldIsNotANumber", null, "The field [", property, "] is not a number" );
			}
		}
		
		return returnString;
	}
}
