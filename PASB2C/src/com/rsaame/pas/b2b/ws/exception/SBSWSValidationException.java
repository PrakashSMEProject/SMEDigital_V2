package com.rsaame.pas.b2b.ws.exception;

import java.util.ArrayList;
import java.util.List;

import com.rsaame.pas.b2b.ws.validators.SBSErrors;

public class SBSWSValidationException {

	List<SBSErrors> errors = new ArrayList<SBSErrors>();

	public List<SBSErrors> getErrors() {
		return errors;
	}

	public void setErrors(List<SBSErrors> errors) {
		this.errors = errors;
	}

}
