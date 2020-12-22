package com.rsaame.pas.b2c.wsValidators;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import com.mindtree.ruc.cmn.utils.Utils;
import com.rsaame.pas.b2c.cmn.constants.AppConstants;
import com.rsaame.pas.b2c.cmn.utils.ValidationUtil;
import com.rsaame.pas.b2c.ws.vo.ErrorMapping;
import com.rsaame.pas.b2c.ws.vo.RetrieveQuoteByPolicyRequest;
import com.rsaame.pas.b2c.ws.vo.WsAppConstants;
import com.rsaame.pas.b2c.wsException.ValidationError;
import com.rsaame.pas.b2c.wsException.ValidationException;

public class RetrieveQuoteByPolicyEmailValidator {
	
	//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start

		ValidationException validationException = new ValidationException();
		List<ValidationError> errors = new ArrayList<ValidationError>();
		public ValidationException validate(Object arg0) {
			RetrieveQuoteByPolicyRequest retrieveQuoteByPolicy = (RetrieveQuoteByPolicyRequest)arg0;
			if(retrieveQuoteByPolicy!=null && !Utils.isEmpty(retrieveQuoteByPolicy))
			{
				/* For RetrieveType*/
				/*if(retrieveQuoteByPolicy.getRetrieveType()==null || Utils.isEmpty(retrieveQuoteByPolicy.getRetrieveType()))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_RETRIEVETYPE, "WS_295");
					errors.add(error);
				}
				else
				{
					if(retrieveQuoteByPolicy.getRetrieveType()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_RETRIEVETYPE, "WS_254");
						errors.add(error);
					}
					else if(retrieveQuoteByPolicy.getRetrieveType()!=WsAppConstants.RetrieveTypeValueForRetrieveQuoteByPolicy
							|| ValidationUtil.countDigits(retrieveQuoteByPolicy.getRetrieveType())>WsAppConstants.maxRetrieveTypeLength)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_RETRIEVETYPE, "WS_296");
						errors.add(error);
					}
				}*/
				
				/* For TransactionNumber*/
				if(retrieveQuoteByPolicy.getTransactionNumber()==null || Utils.isEmpty(retrieveQuoteByPolicy.getTransactionNumber()))
				{
					ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRANSACTIONNUMBER, "WS_297");
					errors.add(error);
				}
				else
				{
					if(retrieveQuoteByPolicy.getTransactionNumber()<0)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRANSACTIONNUMBER, "WS_254");
						errors.add(error);
					}
					else if(ValidationUtil.countDigits(retrieveQuoteByPolicy.getTransactionNumber())>WsAppConstants.maxtransactionNumberLength)
					{
						ValidationError error = ErrorMapping.errorMapping(com.Constant.CONST_TRANSACTIONNUMBER, "WS_298");
						errors.add(error);
					}
				}
				
				/* For DOB*/
				if(retrieveQuoteByPolicy.getDob()!=null && !Utils.isEmpty(retrieveQuoteByPolicy.getDob()))
				{
					DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

					String date = dateFormat.format(retrieveQuoteByPolicy.getDob()).toString();
					if(!ValidationUtil.validateJavaDate(date, "yyyy-MM-dd"))
					{
						ValidationError error = ErrorMapping.errorMapping("DOB", "WS_299");
						errors.add(error);
					}
				}
				
				/* For PartnerTrnReferenceNumber */
				if(retrieveQuoteByPolicy.getPartnerTrnReferenceNumber()!=null || !Utils.isEmpty(retrieveQuoteByPolicy.getPartnerTrnReferenceNumber()))
				{
					if(retrieveQuoteByPolicy.getPartnerTrnReferenceNumber().length()>WsAppConstants.maxPartnerTrnReferenceNumberLength)
					{
						ValidationError error = ErrorMapping.errorMapping("PartnerTrnReferenceNumber", "WS_300");
						errors.add(error);
					}
				}
				//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - Start
				/* For Email Id */
				if(retrieveQuoteByPolicy.getEmailId()==null || Utils.isEmpty(retrieveQuoteByPolicy.getEmailId()))
				{
					ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_001");
					errors.add(error);
				}
				else if(retrieveQuoteByPolicy.getEmailId()!=null && !Utils.isEmpty(retrieveQuoteByPolicy.getEmailId()))
				{
					if(!ValidationUtil.isValidEmail(retrieveQuoteByPolicy.getEmailId())
							|| retrieveQuoteByPolicy.getEmailId().length() > AppConstants.B2C_ALLOWED_MAX_EMAILID_LENGTH){
					
						 ValidationError error = ErrorMapping.errorMapping("EmailId", "WS_002");
						 errors.add(error);
					}
				}
				//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End
			}
			else
			{
				ValidationError error = ErrorMapping.errorMapping("RequestObject", "WS_140");
				errors.add(error);
			}
		validationException.setErrors(errors);
		return validationException;
		}
}

//06.10.2020 - CTS - CR#11645-HomeDIgitalAPI - Renewal Changes - End

