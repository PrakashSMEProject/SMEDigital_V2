package com.rsaame.pas.b2c.validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.vo.bus.TravelInsuranceVO;


/**
 * @author m1020637
 *
 */
public class TravelGIQuoteFetchValidator implements Validator{

	@Override
	public boolean supports(Class<?> arg0) {
		return false;
	}

	/* (non-Javadoc)
	 * @see org.springframework.validation.Validator#validate(java.lang.Object, org.springframework.validation.Errors)
	 */
	@Override
	public void validate(Object arg0, Errors errors) {
		
		TravelInsuranceVO travelInsuranceVO = (TravelInsuranceVO) arg0;
		if(	Utils.isEmpty(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId()) || 
				!ValidationUtil.isValidEmail(travelInsuranceVO.getGeneralInfo().getInsured().getEmailId())){
			errors.rejectValue("errorMessage", "generalInfo.insured.emailId.invalid", "Invalid email id");
		}
		if(!ValidationUtil.isNumeric(String.valueOf(travelInsuranceVO.getCommonVO().getQuoteNo()))) {
			errors.rejectValue("errorMessage", "commonVO.quoteNo.invalid", "Quote number should be numeric");
		}
	}
	
}
