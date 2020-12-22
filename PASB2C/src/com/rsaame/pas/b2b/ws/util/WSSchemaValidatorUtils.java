package com.rsaame.pas.b2b.ws.util;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.rsaame.pas.b2b.ws.validators.SBSErrors;
import com.rsaame.pas.b2b.ws.validators.SchemaValidators;
import com.rsaame.pas.b2b.ws.validators.Warnings;

public class WSSchemaValidatorUtils {
	
	public static SchemaValidators schemaErrorMapping(String field,String errorCode)
	{
	    ResourceBundle resourceBundle = ResourceBundle.getBundle("config.sbswsmessages");
	    SchemaValidators schemaValidators= new SchemaValidators();
	    List<SBSErrors> errorsList = new ArrayList<SBSErrors>();
	    SBSErrors error = new SBSErrors();
		error.setCode(errorCode);
		error.setField(field);
		error.setMessage(resourceBundle.getString(errorCode));
		errorsList.add(error);
		schemaValidators.setErrors(errorsList);
		return schemaValidators;
	}
	public static SchemaValidators schemaWarningMapping(String field,String errorCode)
	{
	    ResourceBundle resourceBundle = ResourceBundle.getBundle("config.sbswsmessages");
	    SchemaValidators schemaValidators= new SchemaValidators();
	    List<Warnings> warningList = new ArrayList<Warnings>();
	    Warnings warning = new Warnings();
	    warning.setCode(errorCode);
	    warning.setField(field);
	    warning.setMessage(resourceBundle.getString(errorCode));
	    warningList.add(warning);
	    schemaValidators.setWarnings(warningList);
		return schemaValidators;
	}
	

}
