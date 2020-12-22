package com.rsaame.pas.b2c.ws.vo;

import java.util.ResourceBundle;

import com.rsaame.pas.b2c.wsException.ValidationError;

public class ErrorMapping {
	public static ValidationError errorMapping(String field,String errorCode)
	{
	    ResourceBundle resourceBundle = ResourceBundle.getBundle("config.messages");
		ValidationError error = new ValidationError();
		error.setCode(errorCode);
		error.setField(field);
		error.setMessage(resourceBundle.getString(errorCode));
		return error;
	}
}
