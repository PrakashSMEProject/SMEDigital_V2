package com.rsaame.pas.b2b.ws.validators;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;

public class SecurityValidators {
	@JsonProperty("Errors")
	private List<SBSErrors> errors;
	@JsonProperty("Warnings")
	private List<Warnings> warnings;

	List<SBSErrors> getErrors() {
		return errors;
	}

	void setErrors(List<SBSErrors> errors) {
		this.errors = errors;
	}

	List<Warnings> getWarnings() {
		return warnings;
	}

	void setWarnings(List<Warnings> warnings) {
		this.warnings = warnings;
	}

	public List<SecurityValidators> validate(Object object) {
		CreateSBSQuoteRequest createSBSQuoteRequest=  (CreateSBSQuoteRequest) object;
		
		List<SecurityValidators> securityValidators = new ArrayList<SecurityValidators>();
		return securityValidators;
	}

}
