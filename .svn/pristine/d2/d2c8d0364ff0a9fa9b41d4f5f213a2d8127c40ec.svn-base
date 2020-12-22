/**
 * 
 */
package com.rsaame.pas.b2c.cmn.utils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;

/**
 * @author M1020859
 * 
 *         This utility class is for all data type validations
 * 
 */
public class ValidationUtil {

	/**
	 * This method will check if the provided string is of numeric type
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isNumeric(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.NUMERIC_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}

	/**
	 * This method will check if the provided string contains only alphabets
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isAlphabets(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.ALPHABETS_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}

	/**
	 * This method will check if the provided string contains alphabets with
	 * white space
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isAlphaWithSpace(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.ALPHA_WITH_SPACE_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}

	/**
	 * This method will check if the provided string contains valid email id
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isValidEmail(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.EMAIL_FORMAT_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}

	/**
	 * This method will check if the provided string contains valid alphanumeric
	 * characters
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isValidAlphaNumeric(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.ALPHANUMERIC_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}
	
	/**
	 * This method will check if the provided string contains valid alphanumeric 
	 * characters with white space - added for building name validation
	 * 
	 * @param input
	 * @return
	 */
	public static boolean isAlphaNumericWithSpace(String input) {
		boolean target = false;
		if (Utils.isEmpty(input)) {
			return target;
		}
		if (input.matches(AppConstants.ALPHANUMERIC_WITH_SPACE_VALIDATION_PATTERN)) {
			target = true;
		}
		return target;
	}
	
	/**
	 * This method will check No. of digits given in the integer field
	 * 
	 * @param input
	 * @return
	 */
	public static int countDigits(int input) {
		int count = 0;
		while (input>0)
		{
			count++;
			input=input/10;
		}
		return count;
	}
	
	/*
	 * For Webservices
	 */
	public static boolean validateJavaDate(String strDate , String dateFormat)
	   {
		
		    SimpleDateFormat sdfrmt = new SimpleDateFormat(dateFormat);
		    sdfrmt.setLenient(false);
		    try
		    {
		        Date javaDate = sdfrmt.parse(strDate); 
		    }
		    /* Date format is invalid */
		    catch (ParseException e)
		    {
		        return false;
		    }
		    /* Return true if date format is valid */
		    return true;
	}
	
	/*
	 * For Webservices
	 */
	public static int getAge(int _year, int _month, int _day) {

        GregorianCalendar cal = new GregorianCalendar();
        int y, m, d, a;         

        y = cal.get(Calendar.YEAR);
        m = cal.get(Calendar.MONTH);
        d = cal.get(Calendar.DAY_OF_MONTH);
        cal.set(_year, _month, _day);
        a = y - cal.get(Calendar.YEAR);
        if ((m < cal.get(Calendar.MONTH))
                        || ((m == cal.get(Calendar.MONTH)) && (d < cal
                                        .get(Calendar.DAY_OF_MONTH)))) {
                --a;
        }
        if(a < 0)
                throw new IllegalArgumentException("Age < 0");
        return a;
    }
	
	public static int integerDigits(BigDecimal n) {
    	return n.signum() == 0 ? 1 : n.precision() - n.scale();
	}

}
