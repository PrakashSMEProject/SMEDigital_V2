package com.rsaame.pas.b2c.wsValidators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.RetrievePolicyByPolicyNo;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class RetrievePolicyByPolicyNoValidator {
	

	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		RetrievePolicyByPolicyNo retrievePolicyByPolicyNo = (RetrievePolicyByPolicyNo)arg0;
		if(retrievePolicyByPolicyNo!=null && !Utils.isEmpty(retrievePolicyByPolicyNo))
		{
			/* for PolicyNo */
			if(retrievePolicyByPolicyNo.getPolicyNo()==null || Utils.isEmpty(retrievePolicyByPolicyNo.getPolicyNo()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYNO, "WS_301");
				errors.add(error);
			}
			else
			{
				if(retrievePolicyByPolicyNo.getPolicyNo()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYNO, "WS_254");
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrievePolicyByPolicyNo.getPolicyNo())>WsAppConstants.maxPolicyNoLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYNO, "WS_302");
					errors.add(error);
				}
			}
			
			/* For Email Id */
			if(retrievePolicyByPolicyNo.getEmailId()!=null && !Utils.isEmpty(retrievePolicyByPolicyNo.getEmailId()))
			{
				if(!ValidationUtil.isValidEmail(retrievePolicyByPolicyNo.getEmailId())
						|| retrievePolicyByPolicyNo.getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH){
				
					 ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_002");
					 errors.add(error);
				}
			}
			
			/* For DOB */
			if(retrievePolicyByPolicyNo.getDob()==null || Utils.isEmpty(retrievePolicyByPolicyNo.getDob()))
			{
				 ValidationError error = ErrorMapping.errorMapping("DOB", "WS_303");
				 errors.add(error);
			}
			else
			{
				DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
				String date = dateFormat.format(retrievePolicyByPolicyNo.getDob()).toString();
                if(!ValidationUtil.validateJavaDate(date,"yyyy-MM-dd"))
                {
                      ValidationError error = ErrorMapping.errorMapping("DOB", "WS_299");
                      errors.add(error);
                }
			}
		}
		else
		{
			ValidationError error5 = ErrorMapping.errorMapping("RequestObject", "WS_140");
			errors.add(error5);
		}
	validationException.setErrors(errors);
	return validationException;
	}
}
