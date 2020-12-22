/**
 * 
 */
package com.rsaame.pas.b2c.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;



import com.mindtree.ruc.cmn.log.Logger;

import com.rsaame.pas.vo.bus.InsuredVO;

/**
 * @author m1027303
 * 
 */
public class GolfInsuranceValidator implements Validator {
	private final static Logger LOGGER = Logger
			.getLogger(GolfInsuranceValidator.class);

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#supports(java.lang.Class)
	 */
	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.springframework.validation.Validator#validate(java.lang.Object,
	 * org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object input, Errors err) {
		LOGGER.info("Validation for Golf Insurance Started");

		InsuredVO insuredVO = (InsuredVO) input;
		if (null != insuredVO) {
			if (insuredVO.getName() == "" || insuredVO.getEmailId() == ""
					|| insuredVO.getMobileNo() == "") {
				err.rejectValue(
						"errorMessage",
						"errorMessage.invalid",
						"We are sorry! There is an error in the information	provided. Please check the items marked in red and rectify the"
								+ "errors before continuing.");
			}
		}
	}

}
