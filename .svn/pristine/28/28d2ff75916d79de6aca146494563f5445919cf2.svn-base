package com.rsaame.pas.b2c.wsValidators;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.RetrieveHomeOptionalCoversRequest;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class RetrieveHomeOptionalCoversValidator {
	ValidationException validationException = new ValidationException();
	List<ValidationError> errors = new ArrayList<ValidationError>();
	public ValidationException validate(Object arg0) {
		RetrieveHomeOptionalCoversRequest retrieveHomeOptionalCoversRequest = (RetrieveHomeOptionalCoversRequest)arg0;
		if(retrieveHomeOptionalCoversRequest!=null && !Utils.isEmpty(retrieveHomeOptionalCoversRequest))
		{
			ResourceBundle resourceBundle = ResourceBundle.getBundle("config.appconfig");
			/* for ClassCode */
			if(retrieveHomeOptionalCoversRequest.getClassCode()==null || Utils.isEmpty(retrieveHomeOptionalCoversRequest.getClassCode()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_037");
				errors.add(error);
			}
			else
			{
				if(retrieveHomeOptionalCoversRequest.getClassCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(retrieveHomeOptionalCoversRequest.getClassCode()!=Integer.parseInt(resourceBundle.getString("HOME_CLASS_CODE")))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_CLASSCODE, "WS_038");
					errors.add(error);
				}
			}
			
			/* for PolicyType */
			if(retrieveHomeOptionalCoversRequest.getPolicyType()==null || Utils.isEmpty(retrieveHomeOptionalCoversRequest.getPolicyType()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPE, "WS_043");
				errors.add(error);
			}
			else
			{
				if(retrieveHomeOptionalCoversRequest.getPolicyType()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(retrieveHomeOptionalCoversRequest.getPolicyType()!=Integer.parseInt(resourceBundle.getString("HOME_POLICY_TYPE")))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_POLICYTYPE, "WS_044");
					errors.add(error);
				}
			}
			
			/* for SchemeCode */
			/*if(retrieveHomeOptionalCoversRequest.getSchemeCode()==null || Utils.isEmpty(retrieveHomeOptionalCoversRequest.getSchemeCode()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, "WS_007");
				errors.add(error);
			}
			else
			{
				if(retrieveHomeOptionalCoversRequest.getSchemeCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrieveHomeOptionalCoversRequest.getSchemeCode())>WsAppConstants.maxSchemeCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_SCHEMECODE, "WS_008");
					errors.add(error);
				}
			}*/
			
			/* for TariffCode */
			if(retrieveHomeOptionalCoversRequest.getTariffCode()==null || Utils.isEmpty(retrieveHomeOptionalCoversRequest.getTariffCode()))
			{
				ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, "WS_009");
				errors.add(error);
			}
			else
			{
				if(retrieveHomeOptionalCoversRequest.getTariffCode()<0)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, com.Constant.CONST_WS_254);
					errors.add(error);
				}
				else if(ValidationUtil.countDigits(retrieveHomeOptionalCoversRequest.getTariffCode())>WsAppConstants.maxTariffCodeLength)
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TARIFFCODE, "WS_010");
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
