package com.rsaame.pas.b2b.ws.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import com.rsaame.pas.b2b.ws.validators.SBSErrors;

public class SBSCommonExceptions {
	 List<SBSErrors> sbsErrors = new ArrayList<SBSErrors>();
	 SBSWSValidationException sbsWSValidationException = new SBSWSValidationException();
	public SBSWSValidationException exceptionMapping(String field, String errorCode) {
		ResourceBundle resourceBundle = ResourceBundle.getBundle("config.sbswsmessages");
		SBSErrors sbserror = new SBSErrors();
		sbserror.setCode(errorCode);
		sbserror.setField(field);
		sbserror.setMessage(resourceBundle.getString(errorCode));
		sbsErrors.add(sbserror);
		sbsWSValidationException.setErrors(sbsErrors);
		return sbsWSValidationException;
	}
	
}
