/**
 * 
 */
package com.rsaame.pas.par.val;

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.validation.IValidator;

/**
 * @author Guru
 *
 *This  validator class validates an alphabetic(with spaces) field using regular expressions;
 */
public class FullNameValidator implements IValidator{
	/**
	 * returns true if the passed fieldData is an alphabetic value with spaces
	 */
	@Override
	public boolean validate( Object fieldData, Map<String, String> parameters, List<String> errorKeys ){
		//String expr = "[a-zA-Z]*";//regular expression for the matching alphabets field
		String expr="^[a-zA-Z ]+$";
		boolean validationStatus = true;
		if( fieldData == null || ((String) fieldData).trim().length() < 1 ){
			validationStatus = true;
		}
		else if( !((String) fieldData).matches( expr ) ){
			validationStatus = false;
		}
		return validationStatus;
	}
}
