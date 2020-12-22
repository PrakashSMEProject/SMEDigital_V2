package com.rsaame.pas.cmn.validation;

/**
 * 
 */

import java.util.List;
import java.util.Map;

import com.mindtree.ruc.cmn.utils.Utils;
import com.mindtree.ruc.cmn.validation.IValidator;

/**
 * This validator checks for the maximum length of the passed fieldData.<br/><br/>
 * 
 * The map <code>parameters</code> passed as an argument must have a key named <code>length</code>.
 * The value is fetched and parsed to Integer and <code>fieldData</code> is checked for the maximum length
 * against this value.
 */
public class RequiredValidator implements IValidator{

	@Override
	public boolean validate( Object fieldData, Map<String, String> parameters, List<String> errorKeys ){
		boolean validationStatus = true;
		
		VALIDATION: {
			if(Utils.isEmpty( ( String)fieldData)){
				validationStatus = true;
				break VALIDATION;
			}
			
		}
		
		return validationStatus;
	}

}
