package com.rsaame.pas.b2b.ws.validators;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;

import com.rsaame.pas.b2b.ws.util.WSBusinessValidatorUtils;
import com.rsaame.pas.b2b.ws.vo.CreateSBSQuoteRequest;

public class BusinessValidators {
	@JsonProperty("Errors")
	private List<SBSErrors> errors;
	@JsonProperty("Warnings")
	private List<Warnings> warnings;

	public List<SBSErrors> getErrors() {
		return errors;
	}

	public void setErrors(List<SBSErrors> errors) {
		this.errors = errors;
	}

	public List<Warnings> getWarnings() {
		return warnings;
	}

	public void setWarnings(List<Warnings> warnings) {
		this.warnings = warnings;
	}

	public List<SBSWSValidators> validate(Object object) {
		CreateSBSQuoteRequest createSBSQuoteRequest=  (CreateSBSQuoteRequest) object;
		
		//List<BusinessValidators> businessValidators = new ArrayList<BusinessValidators>();
		List<SBSWSValidators> SBSbusinessValidatorsList = new ArrayList<SBSWSValidators>();
		return SBSbusinessValidatorsList;
	}
}
